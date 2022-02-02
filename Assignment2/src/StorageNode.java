public class StorageNode {
    /*
    each node points to an Object type
    return values of 'getValue()' must be explicitly downcast before usage
     */

    private Object value;
    private StorageNode next;

    public StorageNode(Object obj) {
        value = obj;
    }

    public void setNext(StorageNode next) {
        this.next = next;
    }

    public Object getValue() {
        return value;
    }

    public StorageNode getNext() {
        return next;
    }
}