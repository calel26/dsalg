package com.lawsmat.baloneysearch;

public class Bs<E extends Comparable<E>> {
    private BsNode<E> root;

    public Bs(E root) {
        this.root = new BsNode<>(root);
    }

    public Bs(BsNode<E> root) {
        this.root = root;
    }

    public void print() {
        print("R", root, 0);
    }

    private String depthStr(int depth) {
        StringBuilder s = new StringBuilder();
        s.append(" ".repeat(Math.max(0, depth)));
        return s.toString();
    }

    public void print(String label, BsNode<E> node, int depth) {
        if(node == null) return;
        System.out.println(depthStr(depth) + label + "> " + node.get());
        print("l", node.getLeft(), depth+1);
        print("r", node.getRight(), depth+1);
    }

    public BsNode<E> insert(E data) {
        return insert(root, data);
    }

    public BsNode<E> find(E data) {
        return find(root, data);
    }

    public BsNode<E> find(BsNode<E> n, E data) {
        if(n == null) {
            return null;
        }
        int comp = n.get().compareTo(data);
        if(comp > 0) {
            return find(n.getLeft(), data);
        } else if(comp < 0) {
            return find(n.getRight(), data);
        } else { return n; }
    }

    public BsNode<E> insert(BsNode<E> n, E data) {
        int comp = n.get().compareTo(data);
        if(comp > 0) {
            if(n.getLeft() == null) {
                BsNode<E> newNode = new BsNode<>(data);
                n.setLeft(newNode);
                return newNode;
            }
            return insert(n.getLeft(), data);
        } else if(comp < 0) {
            if(n.getRight() == null) {
                BsNode<E> newNode = new BsNode<>(data);
                n.setRight(newNode);
                return newNode;
            }
            return insert(n.getRight(), data);
        } else {
            throw new RuntimeException("cannot insert duplicate element `" + data + "`");
        }
    }

    public BsNode<E> max() {
        return max(root);
    }

    public BsNode<E> max(BsNode<E> search) {
        if(search.getRight() == null) {
            return search;
        }
        return max(search.getRight());
    }

    public BsNode<E> min() {
        return min(root);
    }

    public BsNode<E> min(BsNode<E> search) {
        if(search.getLeft() == null) {
            return search;
        }
        return min(search.getLeft());
    }

    public BsNode<E> getRoot() {
        return root;
    }
}
