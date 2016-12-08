package indexing.tree;

class MySuperList<E> {
    private final Node root = new Node();

    private Node currentNodeForAdding;
    private Node currentNodeForGetting;

    MySuperList() {
        currentNodeForAdding = root;
        currentNodeForGetting = root;
    }

    void add(E value) {
        Node newNode = new Node(value);
        currentNodeForAdding.nextNode = newNode;
        currentNodeForAdding = newNode;
    }

    void startGettingFromTheBeginning() {
        currentNodeForGetting = root;
    }

    E getNext() {
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
        private E value;

        /*
        * Used only for root node
        * */
        private Node() {
        }

        private Node(E value) {
            this.value = value;
        }
    }
}