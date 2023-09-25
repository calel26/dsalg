package com.lawsmat.stak;

public class Main {
    // Driver code
    public static void main(String[] args)
    {
        Stak<Integer> s = new Stak<>();

        s.push(1);
        s.push(3);
        s.push(5);
        for(int i=0; i<s.size(); i++){
            System.out.print(" " + s.get(i));
        }
        System.out.println("");

        System.out.println(" " + s.pop());

        for(int i=0; i<s.size(); i++){
            System.out.print(" " + s.get(i));
        }
        System.out.println("");


    }
}
