package com.lawsmat.cgraph;

public class CGRunner {
    public static void main(String[] args) {
        Graph g = new Graph(5);

        g.connect(0, 1);
        g.connect(0, 4);
        g.connect(1, 2);
        g.connect(1, 3);
        g.connect(1, 4);
        g.connect(2, 3);
        g.connect(3, 4);

        for (var node : g.getNodes()) {
            System.out.println(node.data + " " + node.connections);
        }
    }
}
