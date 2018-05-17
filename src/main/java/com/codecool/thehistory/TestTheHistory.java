package com.codecool.thehistory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestTheHistory {

    private static String readFromFile(String filename) {
        BufferedReader in = null;
        String s;
        StringBuilder sb = new StringBuilder();
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

    private static String runFunctionalityTest(TheHistory theHistory, String sourceText, String fromWords, String toWords) {
        theHistory.add(sourceText);
        theHistory.replace(fromWords, toWords);
        String result = theHistory.toString();
        theHistory.clear();
        return result;
    }

    public static void runAllFunctionalityTests(TheHistory theHistory) {
        String sourceText = "replace replace me replace me me me replace me me";
        String result;

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

    public static void runAllTests(TheHistory theHistory) {
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

    public static void main(String[] args) {
        System.out.println("****** Functionality Tests - Array *******");
        runAllFunctionalityTests(new TheHistoryArray());
        System.out.println("****** Functionality Tests - ArrayList *******");
        runAllFunctionalityTests(new TheHistoryArrayList());
        System.out.println("****** Functionality Tests - LinkedList *******");
        runAllFunctionalityTests(new TheHistoryLinkedList());

        System.out.println("****** Array Tests *******");
        runAllTests(new TheHistoryArray());
        System.out.println("****** ArrayList Tests *******");
        runAllTests(new TheHistoryArrayList());
        System.out.println("****** LinkedList Tests *******");
        runAllTests(new TheHistoryLinkedList());
    }

}
