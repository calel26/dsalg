package com.lawsmat.hash;

import com.lawsmat.linlist.LinList;

public class HashNode<K, V> {
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
}