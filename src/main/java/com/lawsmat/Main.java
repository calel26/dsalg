package com.lawsmat;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SuperiorList<Double> floats = new SuperiorList<>();
        Random r = new Random();
        for(int i = 0; i < 10; i++) {
            floats.add(Math.round(r.nextDouble() * 10000) / 100.0);
        }
        System.out.println("Original list: " + floats);
        for(int i = 0; i < 2; i++) {
            int randomI = r.nextInt(floats.size());
            double randomF = r.nextInt(100);
            System.out.println("Changed index " + randomI + " from " + floats.set(randomI, randomF) + " to " + randomF);
            System.out.println("New list: " + floats);
        }
        int randomToRemove = r.nextInt(floats.size());
        System.out.println("Deleted index " + randomToRemove + " (" + floats.remove(randomToRemove) + ")");
        System.out.println("Final list: " + floats);

        SuperiorList<String> stringList = new SuperiorList<>();
        stringList.add("friday");
        stringList.add("saturday");
        stringList.add("monday");
        System.out.println("New string list: " + stringList);
        stringList.set(2, "sunday");
        stringList.remove(0);
        System.out.println("Final string list: " + stringList);
    }
}