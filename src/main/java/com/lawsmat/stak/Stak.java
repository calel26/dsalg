package com.lawsmat.stak;

import com.lawsmat.linlist.LinList;

public class Stak<V> {
    private final LinList<V> stack;

    public Stak() {
        stack = new LinList<>();
    }

    public void push(V j) {
        stack.add(0, j);
    }

    public V pop() {
        return stack.removeFront();
    }

    public V get(int index) {
        return stack.get(index);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return stack.size();
    }
}
