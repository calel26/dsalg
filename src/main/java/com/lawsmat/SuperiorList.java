package com.lawsmat;

@SuppressWarnings("unchecked")
public class SuperiorList<E> {
    private E[] array;

    public SuperiorList() {
        this.array = (E[]) new Object[0];
    }

    public void add(int index, E elem) {
        // make a new array to store the old data + new element
        E[] newArray = (E[]) new Object[array.length + 1];
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
        E[] newArray = (E[]) new Object[array.length - 1];
        int j = 0;
        // copy things to the smaller array
        for(int i = 0; i < array.length; i++) {
            // unless it's the one that's getting removed
            if(i == index) continue;
            E o = array[i];
            newArray[j] = o;
            j++;
        }
        E elem = array[index];
        array = newArray; // swap in the new array
        return elem;
    }

    public E set(int i, E val) {
        E old = array[i];
        array[i] = val;
        return old;
    }

    public E get(int i) {
        return array[i];
    }

    public int size() {
        return array.length;
    }

    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for(int i = 0; i < array.length; i++) {
            E o = array[i];
            s.append(o != null ? o.toString() : "null");
            if(i + 1 != array.length) {
                s.append(", ");
            }

        }
        return s.append("]").toString();
    }
}
