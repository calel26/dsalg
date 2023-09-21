package com.lawsmat.sa;

import com.lawsmat.sl.SuperiorList;

public class StringArray {
    private String array = "";

    public StringArray(String initialString) {
        array = initialString;
    }

    private void serialize(String[] d) {
        array = "";
        for(String n : d) {
            array += n;
        }
    }

    private String[] deserialize() {
        return array.split(",");
    }

    public void push(String elem) {
        if(!array.isEmpty()) {
            elem += ",";
        }
        array += elem;
    }

    public void add(int idx, String elem) {
        SuperiorList<String> s = new SuperiorList<>();
        String[] now = deserialize();
        for(String st : now) {
            s.add(st);
        }
        s.add(idx, elem);
        serialize(s.getArray());
    }

    public void remove(int idx) {
        SuperiorList<String> s = new SuperiorList<>();
        String[] now = deserialize();
        for(String st : now) {
            s.add(st);
        }
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
}
