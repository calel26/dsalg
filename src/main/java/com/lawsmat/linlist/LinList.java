package com.lawsmat.linlist;

public class LinList<E> {
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    /**
     * Adds a new element to the end of the list
     * @param data The element to add
     */
    public void add(E data) {
        add(size, data); // size would be the at the end of the list
    }

    /**
     * Adds an element to the start of the list
     * @param data The element to add
     */
    public void addToFront(E data) {
        add(0, data); // 0 is the start of the list
    }

    /**
     * Adds an element to the list at the specified index
     * @param data The element to add
     * @param index The index to insert the element at
     */
    public void add(int index, E data) {
        Node<E> d = new Node<>(data); // put it in a node
        // find the node before the one that needs to be added
        Node<E> next = head;
        if(head == null) {
            head = d;
            tail = d;
        } else if(index == 0) {
            d.setNext(head);
            head = d;
        } else {
            for (int i = 1; i < index; i++) {
                next = next.next();
            }
            // get the current node at index
            Node<E> after = next.next();
            // new node -> current index node
            d.setNext(after);
            // node before index -> new node
            next.setNext(d);
        }
        size++;
    }

    /**
     * Removes the first element
     * @return The element that was removed
     */
    public E removeFront() {
        return shift();
    }

    /**
     * Removes the first element
     * @return The element that was removed
     */
    public E shift() {
        return remove(0);
    }

    /**
     * Retrieves the element at the specified index
     * @param idx The index of the element
     * @return The element that was retrieved
     */
    public E get(int idx) {
        if(size < 0 || idx >= size) {
            throw new ArrayIndexOutOfBoundsException(idx + " is out of bounds for linlist of size " + size);
        }
        Node<E> next = head;
        for(int i = 0; i < idx; i++) {
            next = next.next();
        }
        assert next != null;
        return next.get();
    }

    /**
     * Removes the element at the specified index
     * @param idx The index of the element to remove
     * @return The element that was removed
     */
    public E remove(int idx) {
        if(idx >= size || idx < 0) {
            throw new ArrayIndexOutOfBoundsException("index " + idx + " is out of bounds for linlist of size " + size);
        }
        Node<E> previous = null;
        for(int i = 1; i < idx - 1; i++) {
            previous = previous != null ? previous.next() : head;
        }
        Node<E> original;
        if(previous != null) {
            original = previous.next();
            previous.setNext(original.next());
        } else {
            original = head;
            head = head.next();
        }

        size--;
        return original.get();
    }

    /**
     * Retrieves the last element of the linlist.
     * This should be preferred to using get(size()) because this is O(1), rather than O(n)
     * @return The last element in the list
     */
    public E last() {
        return tail.get();
    }

    /**
     * Gets the size of the list
     * @return The size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Scans the list for the specified element
     * @param val Value to look for
     * @return Whether the value was found
     */
    public boolean contains(E val) {
        Node<E> next = null;
        for(int i = 0; i < size; i++) { // go through every element in the linlist
            next = next == null ? head : next.next();
            if(next.get().equals(val)) { // check if they're equal
                return true; // break early with true
            }
        }
        return false; // nothing returned true, not found
    }

    /**
     * Modifies an element in the list
     * @param idx The index of the element
     * @param val The value to replace the element with
     */
    public void set(int idx, E val) {
        if(idx > size) {
            throw new ArrayIndexOutOfBoundsException("index " + idx + "is out of bounds for linlist of length " + size);
        }
        Node<E> next = head; // start at the head
        for(int i = 1; i < idx; i++) {
            next = next.next(); // traverse until idx
        }
        next.set(val); // change that node to val
    }

    public String toString() {
        StringBuilder s = new StringBuilder("ll[");
        Node<E> next = null;
        for(int i = 0; i < size; i++) {
            next = next == null ? head : next.next();
            s.append(next != null ? next : "null");
            if(i + 1 == size) {
                s.append("]");
            } else {
                s.append(", ");
            }
        }
        return s.toString();
    }
}
