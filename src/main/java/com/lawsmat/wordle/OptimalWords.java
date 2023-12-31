package com.lawsmat.wordle;

import com.lawsmat.hash.HashNode;
import com.lawsmat.hash.Hashinator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.function.Predicate;

public class OptimalWords {
    private final static Hashinator<Character, Double> freqs;
    private final PriorityQueue<HashNode<String, Double>> q;
    private Predicate<String> additionalRequirements = null;

    String[] w = new String[5];

    public static void main(String[] args) {
        var ow = new OptimalWords(Words.getWords().length);
        ow.calculateFreqs(true);
        System.out.println(Arrays.toString(ow.getOptimalWords()));
    }

    static {
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        double[] frequencies = {8.17, 1.49, 2.78, 4.25, 12.70, 2.23, 2.02, 6.09, 6.97, 0.15, 0.77, 4.03, 2.41, 6.75, 7.51, 1.93, 0.10, 5.99, 6.33, 9.06, 2.76, 0.98, 2.36, 0.15, 1.97, 0.07};
        freqs = new Hashinator<>(letters.length);
        for(int i = 0; i < letters.length; i++) {
            freqs.add(new HashNode<>(letters[i], frequencies[i]));
        }
    }

    public OptimalWords(int n) {
        q = new PriorityQueue<>(n);
    }

    public OptimalWords(int n, Predicate<String> additionalRequirements) {
        this(n);
        this.additionalRequirements = additionalRequirements;
    }

    public void calculateFreqs(boolean checkDupes) {
        final String[] words = Words.getWords();
        for (String word : words) {
            double f = 0.0;
            ArrayList<Character> found = new ArrayList<>(word.length());
            for (char c : word.toCharArray()) {
                if (found.contains(c) && checkDupes) {
                    f = 0.0; // duplicate char
                    break;
                } else {
                    f += freqs.find(c);
                }
                found.add(c);
            }
            if(additionalRequirements != null) {
                if(!additionalRequirements.test(word)) {
                    f = 0.0; // did not meet requirements
                }
            }
            q.add(new HashNode<>(word, f));
        }
        for(int i = 0; i < w.length; i++) {
            w[i] = q.poll().getKey();
        }
    }

    public String[] getOptimalWords() {
        return w;
    }
}