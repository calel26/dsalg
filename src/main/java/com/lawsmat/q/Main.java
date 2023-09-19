package com.lawsmat.q;

public class Main {
    public static void main(String[] args) {
        Q<Integer> q = new Q<>(25);
        for(int i = 0; i<50; i++){
            q.add(i);
            for(int x = 0; x<q.size(); x++){
                System.out.print(" " + q.get(x));
            }
            System.out.println(" ");
        }
        System.out.println(" " + q.peek());
        System.out.println(" " + q.remove());
    }
}
