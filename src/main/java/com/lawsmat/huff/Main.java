package com.lawsmat.huff;

import com.lawsmat.baloneysearch.Bs;
import com.lawsmat.baloneysearch.BsNode;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Main {
    private static int decodei = 0;

    private record CharFreq(Character ch, int freq) implements Comparable<CharFreq> {
        @Override
        public int compareTo(CharFreq o) {
            return this.freq - o.freq;
        }
    }

    public static void main(String[] args) {
        String s = "shesellsseashells";
        HashMap<Character, Integer> freq = new HashMap<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(freq.containsKey(c)) {
                freq.put(c, freq.get(c) + 1);
            } else {
                freq.put(c, 1);
            }
        }
        System.out.println(freq);
        PriorityQueue<BsNode<CharFreq>> q = new PriorityQueue<>();
        for(Map.Entry<Character, Integer> e : freq.entrySet()) {
            q.add(new BsNode<>(new CharFreq(e.getKey(), e.getValue())));
        }
        while(q.size() > 1) {
            BsNode<CharFreq> l = q.remove();
            BsNode<CharFreq> r = q.remove();

            BsNode<CharFreq> parent = new BsNode<>(new CharFreq(
                    null,
                    l.get().freq() + r.get().freq()
            ));
            parent.setLeft(l);
            parent.setRight(r);
            q.add(parent);
        }
        Bs<CharFreq> tree = new Bs<>(q.remove());
        HashMap<Character, String> results = new HashMap<>();
        outputHuffman(results, tree.getRoot(), "");
        System.out.println(results);

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            result.append(results.get(c));
        }
        System.out.println(result);
        System.out.println("trying to decode...");
        decodei = 0;
        StringBuilder out = new StringBuilder();
        while(decodei < result.length() - 1) {
            out.append(decode_character(result.toString(), tree.getRoot()));
        }
        System.out.println(out);
    }

    private static char decode_character(String source, BsNode<CharFreq> at) {
        if(at.getLeft() == null && at.getRight() == null) {
            return at.get().ch();
        }
        if(source.charAt(decodei) == '0') {
            decodei += 1;
            return decode_character(source, at.getLeft());
        } else {
            decodei += 1;
            return decode_character(source, at.getRight());
        }
    }

    private static void outputHuffman(HashMap<Character, String> results, BsNode<CharFreq> node, String s) {
        if(node.getLeft() != null) {
            outputHuffman(results, node.getLeft(), s + "0");
        }
        if (node.getRight() != null) {
            outputHuffman(results, node.getRight(), s + "1");
        } else if(node.getLeft() == null && node.getRight() == null) {
            results.put(node.get().ch(), s);
        }
    }
}
