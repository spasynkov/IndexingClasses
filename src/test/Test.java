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
        System.out.println("Refresh took: " + timeRefresh + " ms.");
        // searcher.print();

        if (!checkTheOrderOfTheElements(searcher, names, dates, numberOfEntries)) {
            // failed. no need to continue
            return;
        }


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
            long l;

            if (i >= numberOfEntries - 20) {    // lets make last 20 entries the same
                l = Long.MAX_VALUE;
            } else {
                l = random.nextLong();

                // lets take only full length longs for easier comparison
                if (l < 999999999999999999L) {
                    i--;
                    continue;
                }

                // and only positive ones
                if (l < 0) l *= -1;
            }

            result[i] = l;
        }

        return result;
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
