package com.codecool.thehistory;

import java.util.Arrays;

public class TheHistoryArray implements TheHistory {

    private String[] wordsArray = new String[0];

    @Override
    public void add(String text) {
        int origLength = wordsArray.length;
        String[] newArray = text.split("\\s+");
        wordsArray = Arrays.copyOf(wordsArray, origLength + newArray.length);
        System.arraycopy(newArray, 0, wordsArray, origLength, newArray.length);
    }

    @Override
    public void removeWord(String wordToBeRemoved) {
        for (int i = 0; i < wordsArray.length; ++i) {
            if (wordsArray[i].contentEquals(wordToBeRemoved)) {
                String[] destArray = new String[wordsArray.length - 1];
                System.arraycopy(wordsArray, 0, destArray, 0, i);
                System.arraycopy(wordsArray, i + 1, destArray, i, destArray.length - i);
                wordsArray = destArray;
            }
        }
    }

    @Override
    public int size() {
        return wordsArray.length;
    }

    @Override
    public void clear() {
        wordsArray = new String[0];
    }

    @Override
    public void replaceOneWord(String from, String to) {
        for (int i = 0; i < wordsArray.length; ++i) {
            if (wordsArray[i].contentEquals(from)) {
                wordsArray[i] = to;
            }
        }
    }

    @Override
    public void replaceMoreWords(String[] fromWords, String[] toWords) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : wordsArray) {
            sb.append(word).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1); // last space char
        return sb.toString();
    }

    private int findNextMatch(String[] words, int startIndex, String[] fromWords) {
        int idx = startIndex;
        while (idx <= words.length - fromWords.length) {
            if (words[idx].contentEquals(fromWords[0])) {
                int currentIdx = idx + 1;
                int charIdx = 1;
                while (charIdx < fromWords.length && words[currentIdx].contentEquals(fromWords[charIdx])) {
                    ++currentIdx;
                    ++charIdx;
                }
                if (charIdx == fromWords.length) break;
            }
            ++idx;
        }
        return idx;
    }
}
