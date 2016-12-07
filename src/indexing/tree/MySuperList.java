package indexing.tree;

class MySuperList {
    private final Node root = new Node();

    private Node currentNodeForAdding;
    private Node currentNodeForGetting;

    MySuperList() {
        currentNodeForAdding = root;
        currentNodeForGetting = root;
    }

    void add(int value) {
        Node newNode = new Node(value);
        currentNodeForAdding.nextNode = newNode;
        currentNodeForAdding = newNode;
    }

    int getNext() {
        if (currentNodeForGetting.nextNode == null) {
            throw new IndexOutOfBoundsException("No more nodes left in this list");
        }
        return (currentNodeForGetting = currentNodeForGetting.nextNode).value;
    }

    private class Node {
        private Node nextNode;
        private int value;

        /*
        * Used only for root node
        * */
        private Node() {
            this.value = -1;
        }

        private Node(int value) {
            this.value = value;
        }
    }
}
