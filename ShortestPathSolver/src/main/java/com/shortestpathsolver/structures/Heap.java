package com.shortestpathsolver.structures;

import com.shortestpathsolver.domain.Node;

/**
 * Custom implementation of PriorityQueue
 *
 * @author kaihartz
 */
public class Heap {

    private Node[] heap;
    private int size;
    private int top = 1;

    public Heap() {
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

    /**
     * Constructs the heap so that the conditions are valid.
     */
    private void makeMinHeap(int a) {

        if (!(a >= (size / 2) && a <= size)) {
            if ((heap[a] != null && heap[leftChild(a)] != null && heap[a].getDist() > heap[leftChild(a)].getDist())
                    || (heap[a] != null && heap[rightChild(a)] != null && heap[a].getDist() > heap[rightChild(a)].getDist())) {

                if (heap[leftChild(a)] != null && heap[rightChild(a)] != null && heap[leftChild(a)].getDist() < heap[rightChild(a)].getDist()) {
                    swap(a, leftChild(a));
                    makeMinHeap(leftChild(a));
                } else {
                    swap(a, rightChild(a));
                    makeMinHeap(rightChild(a));
                }
            }
        }
    }

    /**
     * Adds a Node to the heap.
     *
     * @param n Node
     */
    public void add(Node n) {
        if (size < heap.length) {
            heap[++size] = n;
            int current = size;

            while (heap[current] != null && heap[parent(current)] != null && ((Node) heap[current]).getDist() < ((Node) heap[parent(current)]).getDist()) {
                swap(current, parent(current));
                current = parent(current);
            }
        }
    }

    /**
     * Removes and returns the head of the heap.
     *
     * @return the head of the heap
     */
    public Node poll() {
        Node polled = heap[top];
        heap[top] = heap[size--];
        makeMinHeap(top);
        return polled;
    }

    /**
     *
     * @return size of the heap
     */
    public int size() {
        return size;
    }

    /**
     * Clears the heap.
     */
    public void clear() {
        this.size = 0;
        heap = new Node[101010];
    }

    /**
     * Returns if heap contains a specified Node.
     *
     * @param node
     *
     * @return if heap contains node
     */
    public boolean contains(Node node) {
        for (int i = 0; i < heap.length; i++) {
            if (heap[i] != null && heap[i].equals(node)) {
                return true;
            }
        }
        return false;
    }
}
