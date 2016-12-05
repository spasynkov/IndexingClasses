package test;

import indexing.ISearcherImpl;

import java.util.*;

/**
 * VM options to be set: -Xmx64m -Xms64m -Xss64m
 * */
public class Test {
    private static Random random = new Random();

    public static void main(String[] args) {
        int numberOfEntries = 100000;
        String[] names = generateNames(numberOfEntries);
        long[] dates = generateDates(numberOfEntries);

        ISearcherImpl searcher = new ISearcherImpl();

        long timeRefresh = System.currentTimeMillis();
        searcher.refresh(names, dates);
        timeRefresh = System.currentTimeMillis() - timeRefresh;
        System.out.println("Refresh took: " + timeRefresh + "ms.");
        // searcher.print();


    }

    private static String[] generateNames(int numberOfEntries) {
        Set<String> strings = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        while (strings.size() < numberOfEntries) {
            sb.delete(0, sb.length());  // cleaning
            for (int i = 0; i < 32; i++) {
                sb.append((char) (97 + (int)(Math.random() * 26)));
            }
            strings.add(sb.toString());
        }
        return strings.toArray(new String[strings.size()]);
    }

    private static long[] generateDates(int numberOfEntries) {
        long[] result = new long[numberOfEntries];

        for (int i = 0; i < numberOfEntries; i++) {
            long l = random.nextLong();
            if (l < 0) l *= -1;
            result[i] = l;
        }

        return result;
    }
}
