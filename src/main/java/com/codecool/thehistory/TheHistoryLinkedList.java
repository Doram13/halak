package com.codecool.thehistory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
        //wordsLinkedList.replaceAll(e -> {return (e.contentEquals(from)) ? to : e;}); // sloooow
        ListIterator<String> iterator = wordsLinkedList.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().contentEquals(from)) {
                iterator.set(to);
            }
        }

    }

    @Override
    public void replaceMoreWords(String[] fromWords, String[] toWords) {
        ListIterator<String> startIt = wordsLinkedList.listIterator();

        do {
            findNextMatch(startIt, fromWords);
            if (startIt.nextIndex() < wordsLinkedList.size()) {
                // copy the first part (expecting at least one element in both fromWords and toWords)
                int copyLen = Math.min(fromWords.length, toWords.length);
                for (int i = 0; i < copyLen; ++i) {
                    if (startIt.hasNext()) startIt.next();
                    startIt.set(toWords[i]);
                }

                // addition is required if toWords has more elements than fromWords
                if (fromWords.length < toWords.length) {
                    for (int i = copyLen; i < toWords.length; ++i) {
                        startIt.add(toWords[i]);
                    }
                    continue;
                }

                // deletion is required if toWords has less elements than fromWords
                if (fromWords.length > toWords.length) {
                    for (int i = copyLen; i < fromWords.length; ++i) {
                        startIt.next(); // need to step because can't remove more than once without stepping
                        startIt.remove();
                    }
                    continue;
                }
            }
        } while (startIt.nextIndex() < wordsLinkedList.size());

    }

    private void stepIteratorBack(ListIterator<String> it, int numOfSteps) {
        if (numOfSteps > 0) {
            while (numOfSteps > 0 && it.hasPrevious()) {
                it.previous();
                --numOfSteps;
            }
        }
    }

    private void findNextMatch(ListIterator<String> startIterator, String[] fromWords) {
        boolean matchFound = false;

        while (!matchFound && startIterator.hasNext()) {
            if (startIterator.next().contentEquals(fromWords[0])) {
                matchFound = true;
                for (int j = 1; j < fromWords.length; ++j) {
                    if (!startIterator.hasNext()) {
                        matchFound = false;
                        break;
                    }
                    if (!startIterator.next().contentEquals(fromWords[j])) {
                        // go back one index after the first matching string to not miss possible matching
                        stepIteratorBack(startIterator, j);
                        matchFound = false;
                        break;
                    }
                }
            }
        }

        if (matchFound) {
            // go back to the start of the match
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
