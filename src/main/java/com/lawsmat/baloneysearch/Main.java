package com.lawsmat.baloneysearch;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random r = new Random();
        Bs<Integer> bs = new Bs<>(5);

        for(int i = 0; i < 30; i += 1) {
            int n;
            do {
                n = r.nextInt(0, 100);
            } while(bs.find(n) != null);

            bs.insert(n);
        }

        bs.print();

        System.out.println(bs.find(42) + " <- 42 found node (null if not found)");

        System.out.println("max value is " + bs.max());
        System.out.println("min value is " + bs.min());
    }
}
