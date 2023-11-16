package com.lawsmat.hash;

public class HashRunner {
    public static void main(String[] args) {
        String[] ticker = {"AMZN", "MSFT", "GOOG", "GE", "WMT", "S", "BAC", "TSLA", "INTC", "T"};
        int[] tPrice = {200, 100, 150, 60, 25, 666, 12, 22, 136, 10};//this would ideally come from a dynamic source

        Hashinator<String, Integer> ht = new Hashinator<>(10);

        for (int i = 0; i < ticker.length; i++) {
            HashNode<String, Integer> n = new HashNode<>(ticker[i], tPrice[i]);
            ht.add(n);
        }

        System.out.println("GE = " + ht.find("GE"));
        // GE is up
        ht.add(new HashNode<>("GE", 70));
        System.out.println("GE = " + ht.find("GE"));
        System.out.println("the hashinator = " + ht);
        System.out.println("represented as " + ht.debugInfo());
    }
}