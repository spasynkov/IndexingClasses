package indexing;

import java.util.*;

public class ISearcherImplTree implements ISearcher {
    private String[] names;
    private long[] dates;
    private MySuperTree tree = new MySuperTree();

    private static final int MAX_NUMBER_OF_ADVICES = 12;

    @Override
    public void refresh(String[] classNames, long[] modificationDates) {
        this.names = classNames;
        this.dates = modificationDates;

        for (int i = 0; i < classNames.length; i++) {
            tree.add(classNames[i], i);
        }
    }

    @Override
    public String[] guess(String start) {
        Set<Integer> indexes = tree.getIndexes(start);
        if (indexes == null) {
            return new String[0];
        }

        List<Entry> list = new ArrayList<>();
        Iterator<Integer> iterator = indexes.iterator();
        int index;
        while (iterator.hasNext()) {
            index = iterator.next();
            list.add(new Entry(names[index], dates[index]));
        }
        Collections.sort(list);

        String[] result = new String[MAX_NUMBER_OF_ADVICES];
        for (int i = 0; i < MAX_NUMBER_OF_ADVICES; i++) {
            result[i] = list.get(i).getName();
        }
        return result;
    }

    private class Entry implements Comparable {
        private String name;
        private long time;

        Entry(String name, long time) {
            this.name = name;
            this.time = time;
        }

        String getName() {
            return name;
        }

        long getTime() {
            return time;
        }

        @Override
        public int compareTo(Object o) {
            long x = this.time;
            long y = ((Entry) o).getTime();
            // comparison took from Long class
            return (x < y) ? -1 : ((x == y) ? this.name.compareTo(((Entry) o).getName()) : 1);
        }
    }

    private class MySuperTree {
        private final Node root = new Node();

        private void add(final String name, final int index) {
            Node parentNode = root;                         // starting with root node
            Node childNode;

            for (char c : name.toCharArray()) {             // for each letter in the word
                childNode = parentNode.getNodeByKey(c);     // trying to get child node that contains letter (if exists)
                if (childNode != null) {                        // if it exists - add the index of this word to node
                    childNode.addIndex(index);
                } else {                                        // and if such node doesn't exist - create it
                    childNode = parentNode.addNode(c, index);
                }
                parentNode = childNode;                     // switch to child node
            }
        }

        private Set<Integer> getIndexes(final String mask) {
            if (mask == null || mask.isEmpty()) {           // no need to process empty mask
                return null;
            }

            Node parentNode = root;
            Node childNode = null;

            for (char c : mask.toCharArray()) {             // for each letter in the word
                childNode = parentNode.getNodeByKey(c);     // trying to get child node that contains letter (if exists)
                if (childNode == null) {                        // if no such node found -
                    return null;                                // then no such word stored in a tree
                }
                parentNode = childNode;                     // switch to child node
            }
            return childNode.indexes;                       // returns indexes stored in last found node
        }

        private class Node {
            private final List<Node> kids = new ArrayList<>();
            private final char key;
            private final Set<Integer> indexes;

            /**
             * Use only for ROOT node
             * */
            Node() {
                this.key = 0;
                this.indexes = null;
            }

            private Node(Node parent, char key, int index) {
                this.key = key;
                this.indexes = new HashSet<>();
                indexes.add(index);
            }

            private Node getNodeByKey(final char c) {
                for (Node kid : kids) {
                    if (kid.key == c) return kid;
                }
                return null;
            }


            private void addIndex(final int index) {
                indexes.add(index);
            }

            private Node addNode(final char c, final int index) {
                Node childNode = new Node(this, c, index);
                kids.add(childNode);
                return childNode;
            }
        }
    }
}
