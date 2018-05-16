package com.codecool.thehistory;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.lang.reflect.Array;
import java.util.*;

public class TheHistory {

    private DataStructureType dataStructureType = DataStructureType.Array;

    private String[] wordsArray = new String[0];
    private List<String> wordsArrayList = new ArrayList<>();
    private List<String> wordsLinkedList = new LinkedList<>();

    public void add(String text) {
        switch (dataStructureType) {
            case Array:
                int origLength = wordsArray.length;
                String[] newArray = text.split("\\s+");
                wordsArray = Arrays.copyOf(wordsArray, origLength + newArray.length);
                System.arraycopy(newArray, 0, wordsArray, origLength, newArray.length);
                break;
            case ArrayList:
                Collections.addAll(wordsArrayList, text.split("\\s+"));
                break;
            case LinkedList:
                Collections.addAll(wordsLinkedList, text.split("\\s+"));
                break;
        }
    }

    public void removeWord(String wordToBeRemoved) {
        switch (dataStructureType) {
            case Array:
                for (int i = 0; i < wordsArray.length; ++i) {
                    if (wordsArray[i].contentEquals(wordToBeRemoved)) {
                        String[] destArray = new String[wordsArray.length - 1];
                        System.arraycopy(wordsArray, 0, destArray, 0, i);
                        System.arraycopy(wordsArray, i + 1, destArray, i, destArray.length - i);
                        wordsArray = destArray;
                    }
                }
                break;
            case ArrayList: {
                List<String> elementToRemove = new ArrayList<>();
                elementToRemove.add(wordToBeRemoved);
                wordsArrayList.removeAll(elementToRemove);
            }
            break;
            case LinkedList: {
                List<String> elementToRemove = new LinkedList<>();
                elementToRemove.add(wordToBeRemoved);
                wordsLinkedList.removeAll(elementToRemove);
            }
            break;
        }
    }

    public void replace(String from, String to) {
        String[] fromWords = from.split("\\s+");
        String[] toWords = to.split("\\s+");
        if (fromWords.length == 1 && toWords.length == 1) {
            replaceOneWord(from, to);
        } else {
            replaceMoreWords(fromWords, toWords);
        }
    }

    public int size() {
        int size = 0;
        switch (dataStructureType) {
            case Array:
                size = wordsArray.length;
                break;
            case ArrayList:
                size = wordsArrayList.size();
                break;
            case LinkedList:
                size = wordsLinkedList.size();
                break;
        }
        return size;
    }

    public void clear() {
        switch (dataStructureType) {
            case Array:
                wordsArray = new String[0];
                break;
            case ArrayList:
                wordsArrayList.clear();
                break;
            case LinkedList:
                wordsLinkedList.clear();
                break;
        }
    }

    public void setDataStructureType(DataStructureType dataStructureType) {
        this.dataStructureType = dataStructureType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (dataStructureType) {
            case Array:
                for (String word : wordsArray) {
                    sb.append(word).append(" ");
                }
                break;
            case ArrayList:
                for (String word : wordsArrayList) {
                    sb.append(word).append(" ");
                }
                break;
            case LinkedList:
                for (String word : wordsLinkedList) {
                    sb.append(word).append(" ");
                }
                break;
        }
        sb.deleteCharAt(sb.length() - 1); // last space char
        return sb.toString();
    }

    private void replaceOneWord(String from, String to) {
        switch (dataStructureType) {
            case Array:
                for (int i = 0; i < wordsArray.length; ++i) {
                    if (wordsArray[i].contentEquals(from)) {
                        wordsArray[i] = to;
                    }
                }
                break;
            case ArrayList: {
                //wordsArrayList.replaceAll(e -> {return (e.contentEquals(from)) ? to : e;}); // sloooow
                ListIterator<String> iterator = wordsArrayList.listIterator();
                while (iterator.hasNext()) {
                    if (iterator.next().contentEquals(from)) {
                        iterator.set(to);
                    }
                }
            }
            break;
            case LinkedList: {
                //wordsLinkedList.replaceAll(e -> {return (e.contentEquals(from)) ? to : e;}); // sloooow
                ListIterator<String> iterator = wordsLinkedList.listIterator();
                while (iterator.hasNext()) {
                    if (iterator.next().contentEquals(from)) {
                        iterator.set(to);
                    }
                }
            }
            break;
        }
    }

    private int findNextMatch(String[] words, int startIndex, String[] searchWords) {
        int idx = startIndex;
        while (idx <= words.length - searchWords.length) {
            if (words[idx].contentEquals(searchWords[0])) {
                int currentIdx = idx + 1;
                int charIdx = 1;
                while (charIdx < searchWords.length && words[currentIdx].contentEquals(searchWords[charIdx])) {
                    ++currentIdx;
                    ++charIdx;
                }
                if (charIdx == searchWords.length) break;
            }
            ++idx;
        }
        return idx;
    }

    private void replaceMoreWordsInArray(String[] fromWords, String[] toWords) {
        int idx = 0;
        do {
            idx = findNextMatch(wordsArray, idx, fromWords);
            if (idx <= wordsArray.length - fromWords.length) {
                // copy the first part (expecting at least one element in both fromWords and toWords)
                int copyLen = Math.min(fromWords.length, toWords.length);
                System.arraycopy(toWords, 0, wordsArray, idx, copyLen);

                if (fromWords.length != toWords.length) {
                    // addition or deletion is coming, create a temporary array with the proper size and move unchanged data into it
                    String[] tempArray = new String[wordsArray.length + (toWords.length - fromWords.length)];
                    System.arraycopy(wordsArray, 0, tempArray, 0, idx + copyLen);

                    // insert the rest of the toWords into the output array if there are any
                    if (copyLen < toWords.length) {
                        System.arraycopy(toWords, copyLen, tempArray, idx + copyLen, toWords.length - copyLen);
                    }

                    // and copy the rest unmodified part of the array (have you noticed we don't need to take care of deletion?)
                    System.arraycopy(wordsArray, idx + fromWords.length, tempArray, idx + toWords.length,
                            wordsArray.length - (idx + fromWords.length));

                    // copy the output array back to the wordsArray
                    wordsArray = tempArray;

                    // step with idx to avoid infinite loops when changing for ex.: 'Il' to 'New Il'
                    idx += toWords.length - 1;
                }
            }

            ++idx;
        } while (idx <= wordsArray.length - fromWords.length);
    }

    private void stepIteratorBack(ListIterator<String> it, int numOfSteps) {
        if (numOfSteps > 0) {
            while (numOfSteps > 0 && it.hasPrevious()) {
                it.previous();
                --numOfSteps;
            }
        }
    }

    private void findNextMatch(LinkedList<String> wordsList, ListIterator<String> startIterator, String[] fromWords) {
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

    private void replaceMoreWordsInLinkedList(LinkedList<String> wordsList, String[] fromWords, String[] toWords) {
        ListIterator<String> startIt = wordsList.listIterator();

        do {
            findNextMatch(wordsList, startIt, fromWords);
            if (startIt.nextIndex() < wordsList.size()) {
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
        } while (startIt.nextIndex() < wordsList.size());
    }

    private int findNextMatch(ArrayList<String> wordsList, int startIndex, String[] fromWords) {
        int idx = startIndex;
        while (idx <= wordsList.size() - fromWords.length) {
            if (wordsList.get(idx).contentEquals(fromWords[0])) {
                int currentIdx = idx + 1;
                int charIdx = 1;
                while (charIdx < fromWords.length && wordsList.get(currentIdx).contentEquals(fromWords[charIdx])) {
                    ++currentIdx;
                    ++charIdx;
                }
                if (charIdx == fromWords.length) break;
            }
            ++idx;
        }
        return idx;
    }

    private void replaceElements(List<String> sourceArrayList, List<String> destArrayList, int startDestIdx, int num) {
        for(int i = 0; i < num; ++i) {
            destArrayList.set(startDestIdx + i, sourceArrayList.get(i));
        }

    }

    private void replaceMoreWordsInArrayList(ArrayList<String> wordsList, String[] fromWords, String[] toWords) {
        int idx = 0;
        do {
            idx = findNextMatch(wordsList, idx, fromWords);
            if (idx <= wordsList.size() - fromWords.length) {
                // copy the first part (expecting at least one element in both fromWords and toWords)
                int copyLen = Math.min(fromWords.length, toWords.length);
                replaceElements(Arrays.asList(toWords), wordsList, idx, copyLen);
                if (fromWords.length != toWords.length) {
                    if (fromWords.length < toWords.length) {
                        // addition
                        for (int i = copyLen; i < toWords.length; ++i) {
                            wordsList.add(idx + i, toWords[i]);
                        }
                        idx += toWords.length - copyLen;
                    } else {
                        // deletion
                        for (int i = copyLen; i < fromWords.length; ++i) {
                            wordsList.remove(idx + 1);
                        }
                    }
                }
            }

            ++idx;
        } while (idx <= wordsList.size() - fromWords.length);

    }

    private void replaceMoreWords(String[] fromWords, String[] toWords) {
        //List<String> a = Arrays.asList(wordsArray);
        //a.set(0, "");

        switch (dataStructureType) {
            case Array:
                replaceMoreWordsInArray(fromWords, toWords);
                break;
            case ArrayList:
                replaceMoreWordsInArrayList((ArrayList<String>)wordsArrayList, fromWords, toWords);
                // TODO - use wordsArrayList
                break;
            case LinkedList:
                replaceMoreWordsInLinkedList((LinkedList<String>) wordsLinkedList, fromWords, toWords);
                // TODO - use wordsLinkedList
                break;
        }
    }
}
