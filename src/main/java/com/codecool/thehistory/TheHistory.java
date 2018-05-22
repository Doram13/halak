package com.codecool.thehistory;

public interface TheHistory {
    /**
     * Adds the 'text' to the container of the implementing class which stores all the text
     *
     * @param text: a string containing words separated with spaces
     */
    void add(String text);

    /**
     * Remove all occurrences of a word from the stored text
     *
     * @param wordToBeRemoved: only one word. No spaces just the word otherwise it won't remove anything
     */
    void removeWord(String wordToBeRemoved);

    /**
     * Returns the number of words in the stored text
     *
     * @return the number of words the stored text contains
     */
    int size();

    /**
     * Empties the stored text
     */
    void clear();

    /**
     * Replaces all occurrences of a word to another word.
     * NOTE: replace() method uses this method!
     *
     * @param from: all occurrences of this word will be replaced
     * @param to:   all occurrences of 'from' will be replaced with this word
     */
    void replaceOneWord(String from, String to);

    /**
     * Replaces all occurrences of a sentence or part of a sentence with another (part of a) sentence.
     * The order of words are important. Also the 'fromWords' and 'toWords' arrays are not necessarily same sized.
     * NOTE: replace() method uses this method!
     *
     * @param fromWords: array of words what should be replaced
     * @param toWords:   array of words which should replace the words of 'fromWords'
     */
    void replaceMoreWords(String[] fromWords, String[] toWords);

    /**
     * DON'T rewrite this method!
     * replaces all occurrences of sentences or words with sentences or words.
     * The tests are using this method instead of replaceOneWord() or replaceMoreWords().
     *
     * @param from: the sentence or word what needs to be replaced
     * @param to:   the sentence or word which replaces the sentence found in 'from'
     */
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
