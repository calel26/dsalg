package com.lawsmat.cgraph;

import java.util.ArrayList;

public class Node {
    public int data;
    public ArrayList<Integer> connections = new ArrayList<>();
    public ArrayList<Edge> wedges = new ArrayList<>();

    public Node(int data) {
        this.data = data;
    }
}
