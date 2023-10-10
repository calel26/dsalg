package com.lawsmat.baloneysearch;

public class BsNode<V> {
    private V value;
    private BsNode<V> left, right;

    public BsNode(V value) {
        this.value = value;
        left = right = null;
    }

    public void setLeft(BsNode<V> left) {
        this.left = left;
    }

    public void setRight(BsNode<V> right) {
        this.right = right;
    }

    public void set(V value) {
        this.value = value;
    }

    public BsNode<V> getLeft() {
        return left;
    }

    public BsNode<V> getRight() {
        return right;
    }

    public V get() {
        return value;
    }

    public String toString() {
        return get().toString();
    }
}
