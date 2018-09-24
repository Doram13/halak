package com.codecool.thehistory;

/*
 *
 * START THE ASSIGNMENT BY READING/UNDERSTANDING THIS FILE!
 *
 *
 * If we want to implement functionalities which share common features with different implementation we can use
 * an interface. An interface is a specialized class which only has the public access modifier, the attributes inside
 * are final static by definition and the methods can't have a definition but only a declaration (except the
 * default methods, check out replace() ).
 * If a class implements an interface it should define all the definitions for the methods (except if
 * it's an abstract class).
 *
 * Why is it good? Because if a class implements an interface we can use polymorphism to access the class' instance
 * through any of the interface it implements. That's what we are using in the TestTheHistory.java to avoid duplicate
 * test case implementations.
 */

public interface TheHistory {
    /**
     * Splits the incoming text to words and adds the words to the container of the
     * implementing class
     *
     * @param text: a string containing words separated with spaces
     */
    void add(String text);

    /**
     * Removes all occurrences of a word from the stored data
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
