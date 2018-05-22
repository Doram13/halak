package com.codecool.thehistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class TheHistoryArrayList implements TheHistory {
    /**
     * This implementation should use a String ArrayList so don't change that!
     */
    private List<String> wordsArrayList = new ArrayList<>();

    @Override
    public void add(String text) {
        Collections.addAll(wordsArrayList, text.split("\\s+"));

    }

    @Override
    public void removeWord(String wordToBeRemoved) {
        List<String> elementToRemove = new ArrayList<>();
        elementToRemove.add(wordToBeRemoved);
        wordsArrayList.removeAll(elementToRemove);
    }

    @Override
    public int size() {
        return wordsArrayList.size();
    }

    @Override
    public void clear() {
        wordsArrayList.clear();

    }

    @Override
    public void replaceOneWord(String from, String to) {
        //wordsArrayList.replaceAll(e -> {return (e.contentEquals(from)) ? to : e;}); // sloooow
        ListIterator<String> iterator = wordsArrayList.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().contentEquals(from)) {
                iterator.set(to);
            }
        }

    }

    @Override
    public void replaceMoreWords(String[] fromWords, String[] toWords) {
        int idx = 0;
        do {
            idx = findNextMatch(idx, fromWords);
            if (idx <= wordsArrayList.size() - fromWords.length) {
                // copy the first part (expecting at least one element in both fromWords and toWords)
                int copyLen = Math.min(fromWords.length, toWords.length);
                // replace elements
                for (int i = 0; i < copyLen; ++i) {
                    wordsArrayList.set(idx + i, toWords[i]);
                }

                if (fromWords.length != toWords.length) {
                    if (fromWords.length < toWords.length) {
                        // addition
                        for (int i = copyLen; i < toWords.length; ++i) {
                            wordsArrayList.add(idx + i, toWords[i]);
                        }
                        idx += toWords.length - copyLen;
                    } else {
                        // deletion
                        for (int i = copyLen; i < fromWords.length; ++i) {
                            wordsArrayList.remove(idx + 1);
                        }
                    }
                }
            }

            ++idx;
        } while (idx <= wordsArrayList.size() - fromWords.length);

    }

    private int findNextMatch(int startIndex, String[] fromWords) {
        int idx = startIndex;
        while (idx <= wordsArrayList.size() - fromWords.length) {
            if (wordsArrayList.get(idx).contentEquals(fromWords[0])) {
                int currentIdx = idx + 1;
                int charIdx = 1;
                while (charIdx < fromWords.length && wordsArrayList.get(currentIdx).contentEquals(fromWords[charIdx])) {
                    ++currentIdx;
                    ++charIdx;
                }
                if (charIdx == fromWords.length) break;
            }
            ++idx;
        }
        return idx;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : wordsArrayList) {
            sb.append(word).append(" ");
        }
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1); // last space char
        return sb.toString();
    }

}
