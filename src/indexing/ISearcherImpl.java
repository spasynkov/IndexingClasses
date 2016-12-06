package indexing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ISearcherImpl implements ISearcher {
    private List<Entry> data = new ArrayList<>();

    @Override
    public void refresh(String[] classNames, long[] modificationDates) {
        if (classNames.length != modificationDates.length) throw new RuntimeException();

        for (int i = 0; i < classNames.length; i++) {
            data.add(new Entry(classNames[i], modificationDates[i]));
        }

        Collections.sort(data, new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                // long comparison took from Long class
                return (o1.getTimestamp() < o2.getTimestamp())
                        ? -1
                        : ((o1.getTimestamp() == o2.getTimestamp())
                            ? o1.getClassName().compareTo(o2.getClassName())
                            : 1);
            }
        });
    }

    @Override
    public String[] guess(String start) {

        return new String[0];
    }

    // used for testing
    public List<Entry> getData() {
        return data;
    }

    public void print() {
        for (Entry entry : data) {
            System.out.println(entry);
        }
    }

    public class Entry {
        private String className;
        private long timestamp;

        Entry(String className, long timestamp) {
            this.className = className;
            this.timestamp = timestamp;
        }

        String getClassName() {
            return className;
        }

        long getTimestamp() {
            return timestamp;
        }

        // just for testing
        @Override
        public String toString() {
            return "" + timestamp + " " + className;
        }
    }
}
