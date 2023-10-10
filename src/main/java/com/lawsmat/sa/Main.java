package com.lawsmat.sa;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        StringArray why = new StringArray("Lawson,Kate,Dawson,Avery,Saige,Luke,David");
        why.push("James");
        why.add(3, "Matt");

        why.set(2, "Lawson");
        why.set(0, "Dawson");
        for(int i = 0; i < 2; i++) {
            System.out.println("Currently: " + why);
            System.out.print("Choose an index to edit (0-" + why.size() + ") => ");
            int idx = Integer.parseInt(s.nextLine().trim());
            System.out.print("Change " + idx + " to => ");
            String val = s.nextLine().trim();
            why.set(idx, val);
            System.out.println("Output: " + why);
        }
    }
}
