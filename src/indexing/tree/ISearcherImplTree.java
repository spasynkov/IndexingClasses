package indexing.tree;

import indexing.ISearcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ISearcherImplTree implements ISearcher {
    private String[] names;
    private long[] dates;
    private MySuperTree tree = new MySuperTree();

    private static final int MAX_NUMBER_OF_ADVICES = 12;

    @Override
    public void refresh(String[] classNames, long[] modificationDates) {
        this.names = classNames;
        this.dates = modificationDates;

        CachedIndex.generateIndexes(classNames.length);

        for (int i = 0; i < classNames.length; i++) {
            tree.add(classNames[i], i);
        }
    }

    @Override
    public String[] guess(String start) {
        MySuperListForPrimitiveInts indexes = tree.getIndexes(start);
        if (indexes == null) {
            return new String[0];
        }

        List<Entry> list = new ArrayList<>();
        int index;
        indexes.startGettingFromTheBeginning();
        while (indexes.hasNext()) {
            index = indexes.getNext();
            list.add(new Entry(names[index], dates[index]));
        }
        Collections.sort(list);

        int maxSize = Math.min(list.size(), MAX_NUMBER_OF_ADVICES);
        String[] result = new String[maxSize];
        for (int i = 0; i < maxSize; i++) {
            result[i] = list.get(i).getName();
        }
        return result;
    }
}
