package com.lawsmat.linlist;

public class Main {
    public static void main(String[] args) {
        LinList<Integer> ll = new LinList<>();
        ll.add(10);
        ll.add(3);
        ll.add(24);

        for (int i = 0; i < ll.size(); i++) {
            System.out.println(ll.get(i));
        }

        for (int i = 0; i < 5; i++) {
            ll.add(i);
        }
        ll.add(2, 27);
        System.out.println(ll.contains(27) + " " + ll.contains(23));
        ll.remove(3);
        ll.set(4, 22);
        ll.addToFront(47);
        for (int i = 0; i < ll.size(); i++) {
            System.out.println(ll.get(i));
        }
    }
}
