package com.lawsmat.hash;

public class HashNode<K, V> implements Comparable<HashNode<K, V>> {
    private final K key;
    private V value;

    public HashNode(K k, V v) {
        key = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getKey())
                .append(" = ")
                .append(getValue());
        return sb.toString();
    }

    @Override
    public int compareTo(HashNode<K, V> o) {
        return (int)((double)o.value - (double)this.value);
    }
}