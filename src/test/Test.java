package test;

import indexing.ISearcher;
import indexing.ISearcherImpl;
import indexing.tree.ISearcherImplTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static test.DataGenerator.*;

/**
 * VM options to be set: -Xmx64m -Xms64m -Xss64m
 * */
public class Test {
    public static void main(String[] args) {
        // careful with large numbers here. Generating test data for more than 10 000 entries could be extremely long
        int numberOfEntries = 10000;
        byte maxLengthOfEachWord = 32;

        String[] names = generateNames(numberOfEntries, false, maxLengthOfEachWord);
        long[] dates = generateDates(numberOfEntries, false);
        System.out.println("Testing data created. Running test now...\n");
        System.gc();

        ISearcher searcher = new ISearcherImplTree();

        long timeRefresh = System.currentTimeMillis();
        searcher.refresh(names, dates);
        timeRefresh = System.currentTimeMillis() - timeRefresh;
        System.out.println("Refresh took: " + timeRefresh + " ms.");

        String firstFewLettersOfTheWordsToFind = getRandomClassNamePart();
        long timeGuess = System.currentTimeMillis();
        String[] result = searcher.guess(firstFewLettersOfTheWordsToFind);
        timeGuess = System.currentTimeMillis() - timeGuess;
        System.out.println("Guess took: " + timeGuess + " ms.");

        System.out.println();
        for (String s : result) {
            System.out.println(s);
        }
    }

    private static boolean checkTheOrderOfTheElements(
            ISearcherImpl implementation,
            String[] names,
            long[] dates,
            int numberOfEntries) {

        List<String> strings = new ArrayList<>(numberOfEntries);
        for (int i = 0; i < numberOfEntries; i++) {
            strings.add("" + dates[i] + " " + names[i]);
        }
        Collections.sort(strings);

        int errorsCount = 0;
        for (int i = 0; i < numberOfEntries; i++) {
            String value = strings.get(i);
            if (!value.equalsIgnoreCase(implementation.getData().get(i).toString())) {
                System.err.println("Strings not equals! #" + i
                        + ": \nExpected: " + value
                        + "\nActual:   " + implementation.getData().get(i).toString());
                System.err.println();
                errorsCount++;
            }
        }
        if (errorsCount == 0) {
            System.out.println("The order of sorted elements is ok.");
        } else {
            System.out.println(errorsCount + " errors found.");
            return false;
        }

        return true;
    }
}
