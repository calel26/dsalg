package com.lawsmat;

public class SuperiorList<E> {
    private Object[] array;

    public SuperiorList() {
        this.array = new Object[0];
    }

    public void add(int index, E elem) {
        // make a new array to store the old data + new element
        Object[] newArray = new Object[array.length + 1];
        for(int i = 0; i < index; i++) {
            newArray[i] = array[i]; // copy from old array to new array
        }
        newArray[index] = elem; // add element in its position
        for(int i = index + 1; i < newArray.length; i++) {
            newArray[i] = array[i-1]; // copy the rest of the array
        }
        array = newArray; // swap in the new array
    }

    public void add(E elem) {
        add(array.length, elem); // array.length is the end
    }

    public E remove(int index) {
        // make a smaller array
        Object[] newArray = new Object[array.length - 1];
        int j = 0;
        // copy things to the smaller array
        for(int i = 0; i < array.length; i++) {
            // unless it's the one that's getting removed
            if(i == index) continue;
            Object o = array[i];
            newArray[j] = o;
            j++;
        }
        E elem = (E) array[index];
        array = newArray; // swap in the new array
        return elem;
    }

    public E set(int i, E val) {
        E old = (E) array[i];
        array[i] = val;
        return old;
    }

    public E get(int i) {
        return (E) array[i];
    }

    public int size() {
        return array.length;
    }

    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for(int i = 0; i < array.length; i++) {
            Object o = array[i];
            s.append(o != null ? o.toString() : "null");
            if(i + 1 != array.length) {
                s.append(", ");
            }

        }
        return s.append("]").toString();
    }
}
