package com.codecool.thehistory;

import org.omg.CORBA.DynAnyPackage.InvalidValue;

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
            while((s = in.readLine())!= null)
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

    public static void runAllFunctionalityTests(TheHistory theHistory) {
        theHistory.add("replace replace me replace me me me replace me me");
        theHistory.replace("replace me", "HAPPY FUN");
        if(!"replace HAPPY FUN HAPPY FUN me me HAPPY FUN me".equals(theHistory.toString())) {
            System.out.println("replace IS NOT WORKING AS EXPECTED!");
        }
        theHistory.clear();

        theHistory.add("replace replace me replace me me me replace me me");
        theHistory.replace("replace", "REPLACE");
        if(!"REPLACE REPLACE me REPLACE me me me REPLACE me me".equals(theHistory.toString())) {
            System.out.println("replace IS NOT WORKING AS EXPECTED!");
        }
        theHistory.clear();

        theHistory.add("replace replace me replace me me me replace me me");
        theHistory.replace("me", "HAPPY FUN");
        if(!"replace replace HAPPY FUN replace HAPPY FUN HAPPY FUN HAPPY FUN replace HAPPY FUN HAPPY FUN".equals(theHistory.toString())) {
            System.out.println("replace IS NOT WORKING AS EXPECTED!");
        }
        theHistory.clear();

        theHistory.add("replace replace me replace me me me replace me me");
        theHistory.replace("me me", "SUPER HAPPY FUN");
        if(!"replace replace me replace SUPER HAPPY FUN me replace SUPER HAPPY FUN".equals(theHistory.toString())) {
            System.out.println("replace IS NOT WORKING AS EXPECTED!");
        }
        theHistory.clear();

        theHistory.add("replace replace me replace me me me replace me me");
        theHistory.replace("me", "SUPER me FUN");
        if(!"replace replace SUPER me FUN replace SUPER me FUN SUPER me FUN SUPER me FUN replace SUPER me FUN SUPER me FUN"
                .equals(theHistory.toString())) {
            System.out.println("replace IS NOT WORKING AS EXPECTED!");
        }
        theHistory.clear();

        theHistory.add("replace replace me replace me me me replace me me");
        theHistory.replace("me replace me", "AWE SUPER HAPPY FUN");
        if(!"replace replace AWE SUPER HAPPY FUN me AWE SUPER HAPPY FUN me".equals(theHistory.toString())) {
            System.out.println("replace IS NOT WORKING AS EXPECTED!");
        }
        theHistory.clear();

        theHistory.add("replace replace me replace me me me replace me me");
        theHistory.replace("replace replace me replace me me me replace me me", "replace replace me replace me me me replace me me");
        if(!"replace replace me replace me me me replace me me".equals(theHistory.toString())) {
            System.out.println("replace IS NOT WORKING AS EXPECTED!");
        }
        theHistory.clear();

        theHistory.add("replace replace me replace me me me replace me me");
        theHistory.replace("me me me", "REPLACE");
        if(!"replace replace me replace REPLACE replace me me".equals(theHistory.toString())) {
            System.out.println("replace IS NOT WORKING AS EXPECTED!");
        }
        theHistory.clear();

        theHistory.add("replace replace me replace me me me replace me me");
        theHistory.replace("replace replace", "REPLACE");
        if(!"REPLACE me replace me me me replace me me".equals(theHistory.toString())) {
            System.out.println("replace IS NOT WORKING AS EXPECTED!");
        }
        theHistory.clear();

        theHistory.add("replace replace me replace me me me replace me me");
        theHistory.replace("replace replace me replace me me me replace me me", "REPLACE");
        if(!"REPLACE".equals(theHistory.toString())) {
            System.out.println("replace IS NOT WORKING AS EXPECTED!");
        }
        theHistory.clear();
    }

    public static void runAllTests(TheHistory theHistory) {
        long start;

        start = System.currentTimeMillis();
//        theHistory.add("The great Achilles and Agamemnon fought");
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
        TheHistory theHistory = new TheHistory();

        System.out.println("****** Functionality Tests - Array *******");
        theHistory.setDataStructureType(DataStructureType.Array);
        runAllFunctionalityTests(theHistory);

        System.out.println("****** Functionality Tests - ArrayList *******");
        theHistory.setDataStructureType(DataStructureType.ArrayList);
        runAllFunctionalityTests(theHistory);

        System.out.println("****** Functionality Tests - LinkedList *******");
        theHistory.setDataStructureType(DataStructureType.LinkedList);
        runAllFunctionalityTests(theHistory);

        System.out.println("****** Array Tests *******");
        theHistory.setDataStructureType(DataStructureType.Array);
        runAllTests(theHistory);

        System.out.println("****** LinkedList Tests *******");
        theHistory.setDataStructureType(DataStructureType.LinkedList);
        runAllTests(theHistory);

        System.out.println("****** ArrayList Tests *******");
        theHistory.setDataStructureType(DataStructureType.ArrayList);
        runAllTests(theHistory);
//        System.out.println(theHistory);
    }

}
