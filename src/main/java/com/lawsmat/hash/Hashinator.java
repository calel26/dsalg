package com.lawsmat.hash;

// not using linlist because the java one can be iterated over more efficiently
import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class Hashinator<K, V> {
    private final LinkedList<HashNode<K, V>>[] arr;
    public Hashinator(int size) {
        arr = new LinkedList[(int) (size * 1.7)];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = new LinkedList<>();
        }
    }

    private int hash(K key) {
        if(key instanceof String skey) {
            // alternatively (because the other way feels like cheating):
            // int sum = 0;
            // for(char c : skey.chars()) { sum += (int)c }
            int sum = skey.chars().sum();
            return sum % arr.length;
        } else {
            // we can't do it so cheat
            return key.hashCode();
        }
    }

    public V find(K key) {
        // get the hashcode
        int hash = hash(key);
        for(HashNode<K, V> elem : arr[hash]) {
            if(elem.getKey().equals(key)) {
                return elem.getValue();
            }
        }
        // we didn't find it
        throw new RuntimeException(key + " (" + hash + ") not found in THE HASHINATOR");
    }

    /**
     * adds the node to the hashmap
     * @param node the node to add
     * @return the node that was overwritten or null
     */
    public V add(HashNode<K, V> node) {
        int hash = hash(node.getKey());
        var l = arr[hash];
        for(HashNode<K, V> v : l) {
            if(v.getKey().equals(node.getKey())) {
                V oldVal = v.getValue();
                v.setValue(node.getValue());
                return oldVal;
            }
        }
        l.add(node);
        return null;
    }

    public String toString() {
        // probably inefficient
        StringBuilder s = new StringBuilder("{");
        int i = 0;
        int maxIdx = 0;
        for(var l : arr) {
            maxIdx += l.size();
        }
        for(var l : arr) {
            for(var node : l) {
                s.append(node.getKey().toString())
                        .append(" = ")
                        .append(node.getValue().toString());
                if(i != maxIdx - 1)
                    s.append(", ");
                ++i;
            }
        }
        s.append("}");
        return s.toString();
    }
}
