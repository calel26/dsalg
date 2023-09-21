package com.lawsmat.rstudent;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RandomStudent r = new RandomStudent();
        Scanner s = new Scanner(System.in);

        LOOP:
        while(true) {
            System.out.println("Type `s` to show a name, `r` to show and remove a name, `a` to add a name, `reset` to repopulate the list, or `x` to quit.");
            if(r.isEmpty()) {
                System.out.println("The list is empty. Type `reset` to repopulate the list, or add a name by typing `a`");
            } else {
                System.out.println("Current list: " + r.getAll());
            }
            String input = s.nextLine().trim();
            switch(input) {
                case "x" -> { break LOOP; }
                case "s" -> {
                    String name = r.show();
                    if(name == null) {
                        System.out.println("Could not show name because the list is empty");
                        continue;
                    }
                    System.out.println("Selected: " + name);
                }
                case "a" -> {
                    System.out.print("Type the name to add or `_` to cancel -> ");
                    String name = s.nextLine().trim();
                    if(name.equals("_")) continue;
                    r.add(name);
                    System.out.println("Added " + name + " to the class");
                }
                case "r" -> {
                    String name = r.remove();
                    if(name == null) {
                        System.out.println("Could not remove a name from the list because it's empty");
                        continue;
                    }
                    System.out.println("Removed: " + name);
                }
                case "reset" -> {
                    r.reset();
                    System.out.println("Reset the list!");
                }
            }
        }
    }
}
