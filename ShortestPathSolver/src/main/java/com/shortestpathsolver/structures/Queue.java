package com.shortestpathsolver.structures;

/**
 * LIFO Queue structure.
 *
 * @author kaihartz
 */
public class Queue<E> {

    private Object[] queue;
    private int size;
    private int first;
    private int last;

    public Queue() {
        this.size = 0;
        this.first = 0;
        this.last = 0;
        this.queue = new Object[101010];
    }

    /**
     * Adds an element to the queue.
     *
     * @param e element
     */
    public void enqueue(E e) {
        if (size < queue.length) {
            queue[last++] = e;
            size++;
        }
    }

    /**
     * Removes and returns the last element inserted to the queue.
     *
     * @return the last element inserted to the queue.
     */
    public E dequeue() {
        E dequeued = (E) queue[first];
        first++;
        size--;
        return dequeued;
    }

    /**
     *
     * @return size of the queue
     */
    public int size() {
        return size;
    }

    /**
     * Clears the queue.
     */
    public void clear() {
        size = 0;
        first = 0;
        last = 0;
        queue = new Object[101010];
    }
}
