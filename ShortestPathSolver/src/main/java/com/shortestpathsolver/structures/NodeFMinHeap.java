package com.shortestpathsolver.structures;

import com.shortestpathsolver.domain.Node;

public class NodeFMinHeap {

    private Node[] heap;
    private int size;
    private int top = 1;

    public NodeFMinHeap() {
        this.size = 0;
        this.heap = new Node[101010];
    }

    private int parent(int a) {
        return a / 2;
    }

    private int leftChild(int a) {
        return (2 * a);
    }

    private int rightChild(int a) {
        return (2 * a) + 1;
    }

    private void swap(int a, int b) {
        Node tmp;
        tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }

    private void makeMinHeap(int a) {

        if (!(a >= (size / 2) && a <= size)) {
            if ((heap[a] != null && heap[leftChild(a)] != null && heap[a].getF() > heap[leftChild(a)].getF())
                    || (heap[a] != null && heap[rightChild(a)] != null && heap[a].getF() > heap[rightChild(a)].getF())) {

                if (heap[leftChild(a)] != null && heap[rightChild(a)] != null && heap[leftChild(a)].getF() < heap[rightChild(a)].getF()) {
                    swap(a, leftChild(a));
                    makeMinHeap(leftChild(a));
                } else {
                    swap(a, rightChild(a));
                    makeMinHeap(rightChild(a));
                }
            }
        }
    }

    public void add(Node n) {
        if (size >= heap.length) {
            return;
        }
        heap[++size] = n;
        int current = size;

        while (heap[current] != null && heap[parent(current)] != null && heap[current].getF() < heap[parent(current)].getF()) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public Node poll() {
        Node polled = heap[top];
        heap[top] = heap[size--];
        makeMinHeap(top);
        return polled;
    }

    public int size() {
        return size;
    }

    public void clear() {
        this.size = 0;
        heap = new Node[10101];
    }

    public boolean contains(Node node) {
        for (int i = 0; i < heap.length; i++) {
            if (heap[i] != null && heap[i].equals(node)) {
                return true;
            }
        }
        return false;
    }
}
