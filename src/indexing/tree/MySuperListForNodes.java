package indexing.tree;

public class MySuperListForNodes {
    private final Node root = new Node();

    private Node currentNodeForAdding;
    private Node currentNodeForGetting;

    MySuperListForNodes() {
        currentNodeForAdding = root;
        currentNodeForGetting = root;
    }

    void add(MySuperTree.Node value) {
        Node newNode = new Node(value);
        currentNodeForAdding.nextNode = newNode;
        currentNodeForAdding = newNode;
    }

    void startGettingFromTheBeginning() {
        currentNodeForGetting = root;
    }

    MySuperTree.Node getNext() {
        if (currentNodeForGetting.nextNode == null) {
            throw new IndexOutOfBoundsException("No more nodes left in this list");
        }
        return (currentNodeForGetting = currentNodeForGetting.nextNode).value;
    }

    boolean hasNext() {
        return currentNodeForGetting.nextNode != null;
    }

    private class Node {
        private Node nextNode;
        private MySuperTree.Node value;

        /*
        * Used only for root node
        * */
        private Node() {
        }

        private Node(MySuperTree.Node value) {
            this.value = value;
        }
    }
}