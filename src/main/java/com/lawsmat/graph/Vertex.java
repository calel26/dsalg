package com.lawsmat.graph;

import java.util.ArrayList;

public class Vertex<E, V> {
    private V data;
    private final ArrayList<Edge<E, V>> connections;

    public Vertex(V data) {
        this.data = data;
        this.connections = new ArrayList<>();
    }

    public ArrayList<Edge<E, V>> getConnections() {
        return connections;
    }

    public void connectTo(Vertex<E, V> otherNode, E edgeVal) {
        var edge = new Edge<>(otherNode, edgeVal);
        connections.add(edge);
        otherNode.connections.add(edge);
    }

    public void connectTo(Vertex<E, V> otherNode) {
        connectTo(otherNode, null);
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }
}
