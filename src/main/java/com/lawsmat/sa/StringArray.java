package com.lawsmat.sa;

import com.lawsmat.sl.SuperiorList;

public class StringArray {
    private String array = "";

    public StringArray(String initialString) {
        array = initialString;
    }

    private void serialize(Object[] d) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < d.length; i++) {
            if(d[i] == null) { continue; }
            String n = d[i].toString();
            builder.append(n);
            if(i != d.length - 1)
                builder.append(",");
        }
        array = builder.toString();
    }

    private String[] deserialize() {
        return array.split(",");
    }

    public void push(String elem) {
        if(!array.isEmpty()) {
            elem = "," + elem;
        }
        array += elem;
    }

    public void add(int idx, String elem) {
        SuperiorList<String> s = new SuperiorList<>(deserialize());
        s.add(idx, elem);
        serialize(s.getArray());
    }

    public void remove(int idx) {
        SuperiorList<String> s = new SuperiorList<>(deserialize());
        s.remove(idx);
        serialize(s.getArray());
    }

    public String get(int idx) {
        return deserialize()[idx];
    }

    public void set(int idx, String elem) {
        String[] s = deserialize();
        s[idx] = elem;
        serialize(s);
    }

    public String toString() {
        return array;
    }

    public int size() {
        return deserialize().length;
    }
}
