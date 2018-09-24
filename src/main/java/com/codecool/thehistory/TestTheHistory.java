package com.codecool.thehistory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * READ THIS AFTER TheHistory.java!
 *
 *
 * DO NOT CHANGE THE TESTS (please)!
 * You may put extra logs to be able to differentiate which test case fails and/or simply comment out parts
 * which are not implemented yet if the output is hard to read but make sure you restore everything and run
 * all tests into their original form.
 *
 *
 * Unit Testing in general:
 * You'll learn about unit testing later but some small info:
 * - you can't test every possibility so test some good and bad cases
 * But how to decide what to test?
 * - always test with good values but 1-2 test usually is engough
 * - always test the _corner cases_
 * What are the corner cases?
 * Those are the special cases:
 * - The first and last good input (for ex. if you handle a certain set of numbers as input)
 * - The 'one before the first good' and 'one after the last good' cases
 *
 * Of course that's not all but a glimpse from the world of testing. Now check out the tests and the notes!
 * */

/**
 * NOTE: this class doesn't use any official Unit Test framework but made by hand.
 * Later when the Unit testing gets introduced we'll use more sofisticated testing.
 */
public class TestTheHistory {

    /**
     * Helper method for file reading
     *
     * @param filename name of the with path
     * @return the file's content in one big string
     */
    private static String readFromFile(String filename) {
        BufferedReader in = null;
        String s;
        StringBuilder sb = new StringBuilder(); // use a StringBuilder if you are doing lot of string manipulation
        try {
            in = new BufferedReader(new FileReader(filename));
            while ((s = in.readLine()) != null)
                sb.append(s + "\n");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * Helper method for running a functionality test on TheHistory. Note we are using TheHistory interface so we can
     * use this method with _any_ TheHistory implementation!
     *
     * @param theHistory the interface we are using to run the test
     * @param sourceText the text what TheHistory will use to run the test on
     * @param fromWords  these are the words we are looking for in the sourceText
     * @param toWords    we would like to change the text found in the 'fromWords' to this text
     * @return returns the modified text
     */
    private static String runFunctionalityTest(TheHistory theHistory, String sourceText, String fromWords, String toWords) {
        theHistory.add(sourceText);
        theHistory.replace(fromWords, toWords);
        String result = theHistory.toString();
        theHistory.clear();
        return result;
    }

    /**
     * All the functionality-related tests are here. These are not checking performance but if your implementation
     * works correctly. Notice we are using TheHistory interface here also to be able to use all the implementation
     * instead of writing the same tests for all the different implementations (like for ArrayList, LinkedList etc..).
     * NOTE: later when you are doing Unit testing don't make long methods like this but separate
     * every test into a different method!
     *
     * @param theHistory the interface we are using for the testing.
     */
    public static void runAllFunctionalityTests(TheHistory theHistory) {
        String sourceText = "replace replace me replace me me me replace me me";
        String result;

        // All the tests using the same pattern: there is a source text, some text we want to change and some other
        // text we would like to change to. And compare the result, to the expected output.

        // just change words
        result = runFunctionalityTest(theHistory, sourceText, "replace me", "HAPPY FUN");
        if (!"replace HAPPY FUN HAPPY FUN me me HAPPY FUN me".equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }

        result = runFunctionalityTest(theHistory, sourceText, "replace", "REPLACE");
        if (!"REPLACE REPLACE me REPLACE me me me REPLACE me me".equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }

        // replace the whole text
        result = runFunctionalityTest(theHistory, sourceText, sourceText, sourceText);
        if (!sourceText.equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }

        // insert new words into the text
        result = runFunctionalityTest(theHistory, sourceText, "me", "HAPPY FUN");
        if (!"replace replace HAPPY FUN replace HAPPY FUN HAPPY FUN HAPPY FUN replace HAPPY FUN HAPPY FUN".equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }

        result = runFunctionalityTest(theHistory, sourceText, "me me", "SUPER HAPPY FUN");
        if (!"replace replace me replace SUPER HAPPY FUN me replace SUPER HAPPY FUN".equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }

        result = runFunctionalityTest(theHistory, sourceText, "me", "SUPER me FUN");
        if (!"replace replace SUPER me FUN replace SUPER me FUN SUPER me FUN SUPER me FUN replace SUPER me FUN SUPER me FUN"
                .equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }

        result = runFunctionalityTest(theHistory, sourceText, "me replace me", "AWE SUPER HAPPY FUN");
        if (!"replace replace AWE SUPER HAPPY FUN me AWE SUPER HAPPY FUN me".equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }

        // remove words from the text
        result = runFunctionalityTest(theHistory, sourceText, "me me me", "REPLACE");
        if (!"replace replace me replace REPLACE replace me me".equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }

        result = runFunctionalityTest(theHistory, sourceText, "replace replace", "REPLACE");
        if (!"REPLACE me replace me me me replace me me".equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }

        result = runFunctionalityTest(theHistory, sourceText, sourceText, "REPLACE");
        if (!"REPLACE".equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }

        // no match -> nothing changed
        result = runFunctionalityTest(theHistory, sourceText, "cant find", "cant change");
        if (!sourceText.equals(result)) {
            System.out.println("replace() IS NOT WORKING AS EXPECTED!");
        }
    }

    /**
     * Performance testing. After all the functionality test passes, the next thing to take care of is the performance.
     * Can you make your code faster? And no, you shouldn't change the tests.
     *
     * @param theHistory interface to TheHistory implementation
     */
    public static void runAllPerformanceTests(TheHistory theHistory) {
        long start;

        start = System.currentTimeMillis();
        theHistory.add(readFromFile("src/main/resources/Iliad.txt"));
        System.out.println("Reading words from file took " + (System.currentTimeMillis() - start) + " ms (the size is now " + theHistory.size() + ")");

        start = System.currentTimeMillis();
        theHistory.removeWord("king");
        theHistory.removeWord("Zeus");
        theHistory.removeWord("Apollo");
        theHistory.removeWord("it");
        System.out.println("Removing words took " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        theHistory.replace("Achilles", "Il");
        theHistory.replace("Agamemnon", "Il");
        theHistory.replace("Priam", "Trumm");
        theHistory.replace("chariot", "tank");
        theHistory.replace("bow", "missile");
        theHistory.replace("arrow", "nuke");
        theHistory.replace("the", "the");
        System.out.println("Replacing words took " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        theHistory.replace("Il", "Pet Il");
        theHistory.replace("Pet Il", "Pet Il (blessed be his name)");
        theHistory.replace("Trumm", "coward and insane Trumm");
        theHistory.replace("the", "the big");
        theHistory.replace("the big", "the very big");
        theHistory.replace("a", "a big");
        theHistory.replace("a big", "a very big");
        System.out.println("Replacing multiple words (with insertion) took " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        theHistory.replace("Pet Il (blessed be his name)", "Pet Il (blessed be the name)");
        theHistory.replace("coward and insane Trumm", "coward and liar Trumm");
        theHistory.replace("the very big", "the super big");
        theHistory.replace("the super big", "the really big");
        theHistory.replace("a very big", "a super big");
        theHistory.replace("a super big", "a really big");
        System.out.println("Replacing multiple words (equal number) took " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        theHistory.replace("Pet Un (blessed be the name)", "Pet Un The Wise");
        theHistory.replace("coward and liar Trumm", "President Trumm");
        theHistory.replace("the really big", "the big");
        theHistory.replace("the big", "the");
        theHistory.replace("a really big", "a big");
        theHistory.replace("a big", "a");
        System.out.println("Replacing multiple words (with removal) took " + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * Here is where the program starts as usual. All the tests for all the implementations are here.
     *
     * @param args command line arguments, not used.
     */
    public static void main(String[] args) {
        System.out.println("****** Functionality Tests - Array *******");
        runAllFunctionalityTests(new TheHistoryArray());
        System.out.println("****** Functionality Tests - ArrayList *******");
        runAllFunctionalityTests(new TheHistoryArrayList());
        System.out.println("****** Functionality Tests - LinkedList *******");
        runAllFunctionalityTests(new TheHistoryLinkedList());

        System.out.println("****** Array Tests *******");
        runAllPerformanceTests(new TheHistoryArray());
        System.out.println("****** ArrayList Tests *******");
        runAllPerformanceTests(new TheHistoryArrayList());
        System.out.println("****** LinkedList Tests *******");
        runAllPerformanceTests(new TheHistoryLinkedList());
    }

}
