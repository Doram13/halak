package com.codecool.thehistory;

public interface TheHistory {
    void add(String text);
    void removeWord(String wordToBeRemoved);
    int size();
    void clear();
    void replaceOneWord(String from, String to);
    void replaceMoreWords(String[] fromWords, String[] toWords);

    default void replace(String from, String to) {
        String[] fromWords = from.split("\\s+");
        String[] toWords = to.split("\\s+");
        if (fromWords.length == 1 && toWords.length == 1) {
            replaceOneWord(from, to);
        } else {
            replaceMoreWords(fromWords, toWords);
        }
    }
}
