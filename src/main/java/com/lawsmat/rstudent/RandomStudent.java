package com.lawsmat.rstudent;

import com.lawsmat.linlist.LinList;

import java.util.Random;

public class RandomStudent {
    private static final String[] names = {
            "Lawson",
            "Kate",
            "Dawson",
            "Avery",
            "Saige",
            "Luke",
            "David"
    };

    private final Random random = new Random();
    private LinList<String> nameList;

    public RandomStudent() {
        reset();
    }

    public void reset() {
        nameList = new LinList<>();
        for(String name : names) {
            nameList.add(name);
        }
    }

    public void add(String name) {
        if(!nameList.contains(name)) {
            nameList.add(name);
        } else {
            System.out.println("Already in list");
        }
    }

    public boolean isEmpty() {
        return nameList.size() == 0;
    }

    private int pickIndex() {
        return random.nextInt(0, nameList.size());
    }

    public String show() {
        if(isEmpty()) { return null; }
        return nameList.get(pickIndex());
    }

    public String remove() {
        if(isEmpty()) { return null; }
        return nameList.remove(pickIndex());
    }

    public LinList<String> getAll() {
        return nameList;
    }
}
