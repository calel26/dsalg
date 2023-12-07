package com.lawsmat.graph;

public class Edge<E, V> {
    private E data;
    private Vertex<E, V> to;

    public Edge(Vertex<E, V> to, E data) {
        this.to = to;
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Vertex<E, V> getTo() {
        return to;
    }

    public void setTo(Vertex<E, V> to) {
        this.to = to;
    }
}
