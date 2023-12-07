package com.lawsmat.graph;

import java.util.ArrayList;

public class Graph<E, V> {
    private final ArrayList<Vertex<E, V>> vertices = new ArrayList<>();

    public ArrayList<Vertex<E, V>> getVertices() {
        return vertices;
    }
}
