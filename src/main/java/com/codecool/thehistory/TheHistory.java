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

    private int findNextMatch(List<String> wordsList, ListIterator<String> startIterator, String[] fromWords) {
        boolean matchFound = false;
        int nextElemIndex = 0;

        while(!matchFound && startIterator.hasNext()) {
            if (startIterator.next().contentEquals(fromWords[0])) {
                nextElemIndex = startIterator.nextIndex();
                matchFound = true;
                for (int j = 1; j < fromWords.length; ++j) {
                    if(!startIterator.hasNext()) {
                        startIterator = wordsList.listIterator(nextElemIndex);
                        matchFound = false;
                        break;
                    }
                    if (!startIterator.next().contentEquals(fromWords[j])) {
                        startIterator = wordsList.listIterator(nextElemIndex);
                        matchFound = false;
                        break;
                    }
                }
            }
        }

        if(matchFound) return nextElemIndex - 1;
        return wordsList.size();
    }

    private void replaceMoreWordsInArray(String[] fromWords, String[] toWords) {
        int idx = 0;
        do {
            idx = findNextMatch(wordsArray, idx, fromWords);
            if(idx <= wordsArray.length - fromWords.length) {
                // copy the first part (expecting at least one element in both fromWords and toWords)
                int copyLen = Math.min(fromWords.length, toWords.length);
                System.arraycopy(toWords, 0, wordsArray, idx, copyLen);

                // if we just change words (no addition/deletion) exit now
                if(fromWords.length == toWords.length){
                    ++idx;
                    continue;
                }

                // addition or deletion is coming!
                String[] tempArray = new String[wordsArray.length + (toWords.length - fromWords.length)];
                System.arraycopy(wordsArray, 0, tempArray, 0, idx + copyLen);

                // insert the rest of the toWords into the output array if there are any
                if(copyLen < toWords.length){
                    System.arraycopy(toWords, copyLen, tempArray, idx + copyLen, toWords.length - copyLen);
                }

                // and copy the rest unmodified part of the array
                System.arraycopy(wordsArray, idx + fromWords.length, tempArray, idx + toWords.length,
                        wordsArray.length - (idx + fromWords.length));

                // copy the output array back to the wordsArray
                wordsArray = tempArray;

                // step with idx to avoid infinite loops when changing for ex.: 'Il' to 'New Il'
                idx += toWords.length - 1;
            }

            ++idx;
        } while (idx <= wordsArray.length - fromWords.length);
    }

    private void replaceMoreWordsInList(List<String> wordsList, String[] fromWords, String[] toWords) {
        ListIterator<String> startIt;
        ListIterator<String> endIt = wordsList.listIterator();

        int nextStartIdx = 0;
        while(nextStartIdx < wordsList.size()) {
            nextStartIdx = findNextMatch(wordsList, endIt, fromWords);
            if(nextStartIdx < wordsList.size()) {
                endIt = wordsList.listIterator(nextStartIdx + fromWords.length);
                startIt = wordsList.listIterator(nextStartIdx);

                // copy the first part (expecting at least one element in both fromWords and toWords)
                int copyLen = Math.min(fromWords.length, toWords.length);
                for (int i = 0; i < copyLen; ++i) {
                    if (startIt.hasNext()) startIt.next();
                    startIt.set(toWords[i]);
                }
                // addition
                if (fromWords.length < toWords.length) {
                    int startIdx = startIt.previousIndex() + 1;
                    for (int i = copyLen; i < toWords.length; ++i) {
                        wordsList.add(startIdx + i - copyLen, toWords[i]);
                    }
                    endIt = wordsList.listIterator(startIdx + toWords.length - copyLen);
                    continue;
                }
            }

            // if we just change words (no addition/deletion) exit now
            // addition or deletion is coming!
            // insert the rest of the toWords into the output array if there are any
            // and copy the rest unmodified part of the array
            // copy the output array back to the wordsArray
            // step with idx to avoid infinite loops when changing for ex.: 'Il' to 'New Il'

        }

    }

    private int findNextMatch(String[] words, int startIndex, String[] searchWords) {
        int idx = startIndex;
        while (idx <= words.length - searchWords.length) {
            if (words[idx].contentEquals(searchWords[0])) {
                int currentIdx = idx + 1;
                int charIdx = 1;
                while(charIdx < searchWords.length && words[currentIdx].contentEquals(searchWords[charIdx])){
                    ++currentIdx;
                    ++charIdx;
                }
                if(charIdx == searchWords.length) break;
            }
            ++idx;
        }
        return idx;
    }

    private void replaceMoreWords(String[] fromWords, String[] toWords) {
        //List<String> a = Arrays.asList(wordsArray);
        //a.set(0, "");

        switch (dataStructureType) {
            case Array:
                replaceMoreWordsInArray(fromWords, toWords);
            break;
            case ArrayList:
                replaceMoreWordsInList(wordsArrayList, fromWords, toWords);
                // TODO - use wordsArrayList
                break;
            case LinkedList:
                replaceMoreWordsInList(wordsLinkedList, fromWords, toWords);
                // TODO - use wordsLinkedList
                break;
        }
        /*
        if(fromWords.length != toWords.length) throw new IllegalArgumentException("The arrays should be same sized.");
        switch (dataStructureType) {
            case Array: {
                int idx = 0;
                while (idx <= wordsArray.length - fromWords.length) {
                    if (wordsArray[idx].contentEquals(fromWords[0])) {
                        boolean replaceWords = true;
                        int currentIdx = idx + 1;
                        for (int j = 1; j < fromWords.length; ++j) {
                            if (!wordsArray[currentIdx].contentEquals(fromWords[j])) {
                                replaceWords = false;
                                break;
                            }
                            ++currentIdx;
                        }
                        if (replaceWords) {
                            System.arraycopy(toWords, 0, wordsArray, idx, toWords.length);
                            idx += toWords.length;
                            continue;
                        }
                    }
                    ++idx;
                }
            }   // TODO - use wordsArray
                break;
            case ArrayList: {
                ListIterator<String> iterator = wordsArrayList.listIterator();
                while (iterator.hasNext()) {
                    if (iterator.next().contentEquals(fromWords[0])) {
                        boolean replaceWords = true;
                        ListIterator<String> currentIterator = iterator;
                        int j = 1;
                        while(j < fromWords.length) {
                            if(currentIterator.hasNext()){
                                if (!currentIterator.next().contentEquals(fromWords[j])) {
                                    replaceWords = false;
                                    break;
                                }
                            }else{
                                break;
                            }
                            ++j;
                        }
                        if (replaceWords) {
                            for(int i = 0; i < toWords.length; ++i) {
                                iterator.set(toWords[i]);
                                iterator.next();
                            }
                        }
                    }
                }
            }
                // TODO - use wordsArrayList
                break;
            case LinkedList:{
                ListIterator<String> iterator = wordsLinkedList.listIterator();
                while (iterator.hasNext()) {
                    if (iterator.next().contentEquals(fromWords[0])) {
                        boolean replaceWords = true;
                        ListIterator<String> currentIterator = iterator;
                        int j = 1;
                        while(j < fromWords.length) {
                            if(currentIterator.hasNext()){
                                if (!currentIterator.next().contentEquals(fromWords[j])) {
                                    replaceWords = false;
                                    break;
                                }
                            }else{
                                break;
                            }
                            ++j;
                        }
                        if (replaceWords) {
                            for(int i = 0; i < toWords.length; ++i) {
                                iterator.set(toWords[i]);
                                iterator.next();
                            }
                        }
                    }
                }
            }
                // TODO - use wordsLinkedList
                break;
        }
*/
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

}
