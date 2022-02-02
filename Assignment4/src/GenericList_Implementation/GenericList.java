package GenericList_Implementation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class GenericList<E> implements Iterable<E> {
    private static final int MAX_CAPACITY = 32;

    private E[] elementArray;
    private int size;

    public GenericList() {
        elementArray = (E[]) new Object[MAX_CAPACITY];
    }

    public boolean add(E element) {
        if (size == MAX_CAPACITY || element == null)
            return false;

        elementArray[size++] = element;
        return true;
    }

    public E get(int index) {
        return elementArray[index];
    }

    public void sort(Comparator<? super E> comparator) {
        Arrays.sort(elementArray, 0, size, comparator);
    }

    public int indexOf(E match, Comparator<? super E> comparator) {
        return Arrays.binarySearch(elementArray, 0, size, match, comparator);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int currentPos;

            @Override
            public boolean hasNext() {
                if (elementArray[currentPos] == null)
                    return false;
                else
                    return true;
            }

            @Override
            public E next() {
                return elementArray[currentPos++];
            }
        };
    }
}
