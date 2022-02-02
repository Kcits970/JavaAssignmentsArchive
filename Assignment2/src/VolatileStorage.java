public class VolatileStorage {
    /*
    double-ended linked list structure
    each node of the list is represented by 'StorageNode'
     */

    private StorageNode first;
    private StorageNode last;
    private int size;

    public void add(Object obj) {
        StorageNode newNode = new StorageNode(obj);
        if (first == null) {
            first = newNode;
            last = first;
        } else {
            last.setNext(newNode);
            last = newNode;
        }

        size++;
    }

    public int getSize() {
        return size;
    }

    public StorageNode getFirst() {
        return first;
    }

    public void empty() {
        last = null;
        first = null;
        size = 0;
    }
}
