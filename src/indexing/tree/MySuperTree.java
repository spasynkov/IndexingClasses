package indexing.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class MySuperTree {
    private final Node root = new Node();

    void add(final String name, final int index) {
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

    List<CachedIndex> getIndexes(final String mask) {
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
        private final List<Node> kids = new ArrayList<>(0);
        private final char key;
        private final List<CachedIndex> indexes;

        /**
         * Use only for ROOT node
         * */
        Node() {
            this.key = 0;
            this.indexes = null;
        }

        private Node(char key, int index) {
            this.key = key;
            this.indexes = new LinkedList<>();
            indexes.add(CachedIndex.getIndexObject(index));
        }

        private Node getNodeByKey(final char c) {
            for (Node kid : kids) {
                if (kid.key == c) return kid;
            }
            return null;
        }


        private void addIndex(final int index) {
            indexes.add(CachedIndex.getIndexObject(index));
        }

        private Node addNode(final char c, final int index) {
            Node childNode = new Node(c, index);
            kids.add(childNode);
            return childNode;
        }
    }
}
