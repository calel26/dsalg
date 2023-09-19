package com.lawsmat.q;

import com.lawsmat.linlist.LinList;

public class Q<E> {
    private final LinList<E> list = new LinList<>();
    private int maxSize = Integer.MAX_VALUE;
    public Q(int maxSize) {
        this.maxSize = maxSize;
    }

    public Q() {}

    public void add(E el) {
        if(isFull()) remove();
        list.add(el);
    }

    public E remove() {
        return list.shift();
    }

    public E peek() {
        return get(0);
    }

    public E poll() {
        if(list.size() == 0) {
            return null;
        }
        return remove();
    }

    public E get(int idx) {
        return list.get(idx);
    }

    public boolean isFull() {
        return list.size() >= maxSize;
    }

    public int size() {
        return list.size();
    }
}
