package indexing;

import java.util.*;

public class AnotherISearcherImpl implements ISearcher {
    private String[] classes;
    private Map<String, Set<Integer>> data = new HashMap<String, Set<Integer>>();

    @Override
    public void refresh(String[] classNames, final long[] modificationDates) {
        classes = classNames;       // storing names to use them in guess() method

        String mask;                // parts of the class name
        Set<Integer> indexes;       // indexes to be associated with every mask

        for (int i = 0; i < classNames.length; i++) {           // foreach classname
            for (int j = 1; j < classNames[i].length(); j++) {  // from 1st letter to last
                mask = classNames[i].substring(0, j + 1);       // take first few letters as a mask

                if (data.containsKey(mask)) {                   // adding new index to set
                    data.get(mask).add(i);
                } else {                                        // adding new mask-index pair
                    indexes = new TreeSet<Integer>(new Comparator<Integer>() {
                        // adding comparator to sort indexes at the addition time
                        @Override
                        public int compare(Integer o1, Integer o2) {
                            long x = modificationDates[o1];
                            long y = modificationDates[o2];
                            // comparison took from Long class
                            return (x < y) ? -1 : ((x == y) ? 0 : 1);
                        }
                    });
                    indexes.add(i);
                    data.put(mask, indexes);
                }
            }
        }
    }

    @Override
    public String[] guess(String start) {
        Set<Integer> indexes = data.get(start);
        String[] result;

        // we need to get only 12 first indexes
        int maxLength = Math.min(indexes.size(), 12);
        result = new String[maxLength];

        Iterator<Integer> iterator = indexes.iterator();
        byte iterationsCounter = 0;
        int value;
        while (iterator.hasNext()) {
            if (iterationsCounter >= maxLength) {
                // result is full now, no need to continue
                break;
            }
            value = iterator.next();
            result[iterationsCounter++] = classes[value];
        }
        return result;
    }
}