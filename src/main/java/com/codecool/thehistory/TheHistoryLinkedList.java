package com.codecool.thehistory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/*
 *
 * READ THIS AFTER THE TheHistoryArrayList.java!
 *
 *
 * Linked List is probably the most tricky if you want speed. You shouldn't use get() with linked list because it
 * slaps performance in the face. Use iterators to move in the list.
 * Please read about Singly Linked Lists and Doubly Linked Lists. You won't understand the whys without knowing about
 * the theory behind linked list in general.
 *
 * Have you noticed we are using the List interface for both ArrayList and LinkedList? Interfaces can be
 * used to simply change the type in the background (for ex. from ArrayList to LinkedList) with changing
 * only one line. But beware! If speed is important, you'll probably need to measure if there is a performance
 * hit. Linked list performs badly with the implementation style used for ArrayList. Try it! Head to the
 * TheHistoryArrayList.java and change wordsArrayList to LinkedList.
 *
 */

public class TheHistoryLinkedList implements TheHistory {
    /**
     * This implementation should use a String LinkedList so don't change that!
     */
    private List<String> wordsLinkedList = new LinkedList<>();

    @Override
    public void add(String text) {
        Collections.addAll(wordsLinkedList, text.split("\\s+"));
    }

    @Override
    public void removeWord(String wordToBeRemoved) {
        // again: I haven't checked performance here. If you are curious implement remove with iterators and compare
        // the running times!
        List<String> elementToRemove = new LinkedList<>();
        elementToRemove.add(wordToBeRemoved);
        wordsLinkedList.removeAll(elementToRemove);
    }

    @Override
    public int size() {
        return wordsLinkedList.size();

    }

    @Override
    public void clear() {
        wordsLinkedList.clear();

    }

    @Override
    public void replaceOneWord(String from, String to) {
        // the same as before. replaceAll is slower than a hand-made solution.
        // wordsLinkedList.replaceAll(e -> {return (e.contentEquals(from)) ? to : e;}); // sloooow

        // Iterators also have interfaces. Here we are setting an iterator to the first element of the list and
        // walking through it. Found a match? Replace it! Note that replacing is done with the iterator's set() method.
        ListIterator<String> iterator = wordsLinkedList.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().contentEquals(from)) {
                iterator.set(to);
            }
        }
    }

    @Override
    public void replaceMoreWords(String[] fromWords, String[] toWords) {
        // here is our one and only iterator we will use
        ListIterator<String> startIt = wordsLinkedList.listIterator();
        // The basic method is the same as it was for ArrayList
        do {
            findNextMatch(startIt, fromWords);
            if (startIt.nextIndex() < wordsLinkedList.size()) {
                int copyLen = Math.min(fromWords.length, toWords.length);
                // replace the replacable part
                for (int i = 0; i < copyLen; ++i) {
                    if (startIt.hasNext()) startIt.next();
                    startIt.set(toWords[i]);
                }

                // Addition: required if toWords has more elements than fromWords
                if (fromWords.length < toWords.length) {
                    for (int i = copyLen; i < toWords.length; ++i) {
                        startIt.add(toWords[i]);
                    }
                    // we can skip the rest and start from the beginning of the loop
                    // Note that we don't need to increase the iterator because add() adds elements
                    // before the iterator
                    continue;
                }

                // Deletion: required if toWords has less elements than fromWords
                if (fromWords.length > toWords.length) {
                    for (int i = copyLen; i < fromWords.length; ++i) {
                        // need to call next() because can't remove more than once without calling next() or previous()
                        startIt.next();
                        startIt.remove();
                    }
                    continue;
                }
            }
        } while (startIt.nextIndex() < wordsLinkedList.size());

    }

    /**
     * Steps back numOfSteps with the received iterator if it is possible
     *
     * @param it         the list iterator which points to somewhere in a list
     * @param numOfSteps number of steps we want to go backwards
     */
    private void stepIteratorBack(ListIterator<String> it, int numOfSteps) {
        while (numOfSteps > 0 && it.hasPrevious()) {
            it.previous();
            --numOfSteps;
        }
    }

    private void findNextMatch(ListIterator<String> startIterator, String[] fromWords) {
        boolean matchFound = false;
        // searching until we find a match or run out of the list
        while (!matchFound && startIterator.hasNext()) {
            if (startIterator.next().contentEquals(fromWords[0])) {
                // optimist attitude: let's pretend we have a match and change it if it turns out
                // later that it's not a match
                matchFound = true;
                for (int j = 1; j < fromWords.length; ++j) {
                    // if there is no next element, we ran out of the data -> no need to revert back
                    // to the previous position
                    if (!startIterator.hasNext()) {
                        matchFound = false;
                        break;
                    }
                    // Note that we don't need a separate command to move the iterator forward because next()
                    // does this after giving back the element
                    if (!startIterator.next().contentEquals(fromWords[j])) {
                        // go back (almost) to start: one index after the first matching string
                        // to not miss possible matching when continuing searching
                        stepIteratorBack(startIterator, j);
                        matchFound = false;
                        break;
                    }
                }
            }
        }

        if (matchFound) {
            // The replaceMoreWords() method expects the iterator to point to the first word of the match
            // so go back to where we started
            stepIteratorBack(startIterator, fromWords.length);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : wordsLinkedList) {
            sb.append(word).append(" ");
        }
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1); // last space char
        return sb.toString();
    }
}
