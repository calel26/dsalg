package com.lawsmat.graph;

import java.util.ArrayList;

public class GRunner {
        public static void main(String[] args) {
            Graph<Integer, Integer> g = new Graph<>();
            var nodes = new ArrayList<Vertex<Integer, Integer>>();

            for(int i = 0; i <= 4; i++) {
                nodes.add(new Vertex<>(i));
                g.getVertices().add(nodes.get(i));
            }

            nodes.get(0).connectTo(nodes.get(1));
            nodes.get(0).connectTo(nodes.get(4));
            nodes.get(1).connectTo(nodes.get(2));
            nodes.get(1).connectTo(nodes.get(3));
            nodes.get(1).connectTo(nodes.get(4));
            nodes.get(2).connectTo(nodes.get(3));
            nodes.get(3).connectTo(nodes.get(4));

            for(var vertex : g.getVertices()) {
                System.out.println(vertex.getData() + " " + vertex.getConnections().stream().map(e -> e.getTo().getData())
                        .toList());
            }
        }
}
