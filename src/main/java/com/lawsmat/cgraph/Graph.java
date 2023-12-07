package com.lawsmat.cgraph;

public class Graph {
    private final int size;
    private final Node[] nodes;

    public Node[] getNodes() {
        return nodes;
    }

    public Graph(int s) {
        size = s;
        nodes = new Node[s];
        for(int i = 0; i < s; i++) {
            nodes[i] = new Node(i);
        }
    }

    public void connect(int i, int to) {
        nodes[i].connections.add(to);
        nodes[to].connections.add(i);
    }

    public void connect(int i, int to, int weight) {
        var wedge = new Edge(weight, to);
        var wedge2 = new Edge(weight, i);
        nodes[i].wedges.add(wedge);
        nodes[to].wedges.add(wedge2);
    }
    public int size() {
        return size;
    }
}
