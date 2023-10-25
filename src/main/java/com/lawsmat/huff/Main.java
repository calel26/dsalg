package com.lawsmat.huff;

import com.lawsmat.baloneysearch.Bs;
import com.lawsmat.baloneysearch.BsNode;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Main {
    private static int decodei = 0;

    public record CharFreq(Character ch, int freq) implements Comparable<CharFreq> {
        @Override
        public int compareTo(CharFreq o) {
            return this.freq - o.freq;
        }
    }

    public record EncodeResult(String bin, Bs<CharFreq> tree, HashMap<Character, String> map) {}

    public static EncodeResult encode(String s) {
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
        return new EncodeResult(result.toString(), tree, results);
    }

    public static String decode(String bin, Bs<CharFreq> tree) {
        System.out.println("trying to decode...");
        decodei = 0;
        StringBuilder out = new StringBuilder();
        while(decodei < bin.length() - 1) {
            out.append(decode_character(bin, tree.getRoot()));
        }
        return out.toString();
    }

    public static String decodeWithHashMap(String bin, HashMap<Character, String> map) {
        System.out.println("decoding with hashmap");
        HashMap<String, Character> rev = new HashMap<>();
        for(Map.Entry<Character, String> entry : map.entrySet()) {
            rev.put(entry.getValue(), entry.getKey());
        }
        StringBuilder output = new StringBuilder();
        String current = "";
        for(int i = 0; i < bin.length(); i++) {
            current += bin.charAt(i);
            if(rev.containsKey(current)) {
                output.append(rev.get(current));
                current = "";
            }
        }
        return output.toString();
    }

    public static void main(String[] args) {
        String s = "shesellsseashells";
        EncodeResult ec = encode(s);
        String result = ec.bin();
        Bs<CharFreq> tree = ec.tree();
        System.out.println("decoded: " + decode(result, tree));
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
