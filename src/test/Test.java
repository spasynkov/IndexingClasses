package test;

import indexing.ISearcher;
import indexing.ISearcherImpl;
import indexing.tree.ISearcherImplTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static test.DataGenerator.generateDates;
import static test.DataGenerator.generateNames;

/**
 * VM options to be set: -Xmx64m -Xms64m -Xss64m
 * */
public class Test {
    private static String[] names;

    private static final List<Integer> REFRESH_TIMES = new ArrayList<>(100000);

    public static void main(String[] args) {
        int numberOfEntries = 45000;
        byte maxLengthOfEachWord = 32;

        names = generateNames(
                numberOfEntries,
                false,
                false,
                maxLengthOfEachWord);

        long[] dates = generateDates(
                numberOfEntries,
                false);

        System.out.println("Testing data created. Running test now...\n");
        System.gc();

        ISearcher searcher = new ISearcherImplTree();

        long timeRefresh = System.currentTimeMillis();
        searcher.refresh(names, dates);
        timeRefresh = System.currentTimeMillis() - timeRefresh;
        System.gc();

        for (int i = 0; i < 100000; i++) {
            System.out.print((i + 1) + ": ");
            runRefresh(searcher);
            System.gc();
        }
        System.out.println();
        System.out.println("Refresh took: " + timeRefresh + " ms.");
        System.out.println("Guess took (in average): " + calculateAverageTime() + " ms.");
    }

    private static double calculateAverageTime() {
        int sum = 0;
        for (int i : REFRESH_TIMES) {
            sum += i;
        }
        return sum / (double) REFRESH_TIMES.size();
    }

    private static void runRefresh(ISearcher searcher) {
        String randomWord = names[(int) (Math.random() * names.length)];
        String firstFewLettersOfTheWordsToFind = randomWord.
                substring(0, (int) (Math.random() * Math.sqrt(randomWord.length())) + 1);

        long timeGuess = System.currentTimeMillis();
        String[] result = searcher.guess(firstFewLettersOfTheWordsToFind);
        timeGuess = System.currentTimeMillis() - timeGuess;
        REFRESH_TIMES.add((int) timeGuess);
        System.out.println("Guess took " + timeGuess + " ms for string: \"" + firstFewLettersOfTheWordsToFind + "\".");

        for (String s : result) {
            System.out.print(s + " ");
        }
        System.out.println("\n");
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
