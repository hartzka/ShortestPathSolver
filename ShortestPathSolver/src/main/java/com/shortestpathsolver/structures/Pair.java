package com.shortestpathsolver.structures;

/**
 * Implementation of Pair
 *
 * @author kaihartz
 */
public class Pair<E1, E2> {
    private E1 key;
    private E2 value;
    
    public Pair(E1 key, E2 value) {
        this.key = key;
        this.value = value;
    }
    
    public E1 getKey() {
        return this.key;
    }
    
    public E2 getValue() {
        return this.value;
    }
    
}
