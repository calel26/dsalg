package com.lawsmat.linlist;

public class Node<E> {
    private E data;
    private Node<E> next;

    public Node(E val) {
        this.data = val;
    }

    /**
     * Retrieves the data from this node
     * @return The data
     */
    public E get() {
        return data;
    }

    /**
     * Changes the data contained by this node
     * @param val The new data
     * @return The original data
     */
    public E set(E val) {
        E original = data;
        data = val;
        return original;
    }

    public Node<E> next() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public String toString() {
        return String.valueOf(get());
    }
}