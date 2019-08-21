package com.shortestpathsolver.structures;

/**
 * Implementation of Pair
 *
 * @author kaihartz
 */
public class Pair<A, B> {
    private A key;
    private B value;
    
    public Pair(A key, B value) {
        this.key = key;
        this.value = value;
    }
    
    public A getKey() {
        return this.key;
    }
    
    public B getValue() {
        return this.value;
    }
    
}
