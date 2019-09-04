package com.shortestpathsolver.structures;

/**
 * Custom implementation of ArrayList
 *
 * @author kaihartz
 */
public class CustomArrayList<E> {

    private int currentIndex;
    private Object[] list;

    public CustomArrayList() {
        this.list = new Object[100];
        this.currentIndex = 0;
    }

    /**
     * Adds an object to the end of list.
     *
     * @param e object to add
     */
    public void add(E e) {
        if (currentIndex >= list.length) {
            increaseSize();
        }
        list[currentIndex++] = e;
    }

    /**
     * Adds an object to a specified index.
     *
     * @param index
     * @param e object to add
     */
    public void add(int index, E e) {
        if (index < 0 || index > currentIndex) {
            throw new ArrayIndexOutOfBoundsException("ArrayIndexOutOfBounds");
        }

        if (currentIndex >= list.length - 1) {
            increaseSize();
        }

        if (index < currentIndex) {
            for (int i = currentIndex + 1; i > index; i--) {
                list[i] = list[i - 1];
            }

            currentIndex++;
            list[index] = e;

        } else {
            list[currentIndex++] = e;
        }
    }

    /**
     * Removes an object from the list.
     *
     * @param e object to remove
     */
    public void remove(E e) {
        for (int i = 0; i < currentIndex; i++) {
            if (list[i].equals(e)) {
                remove(i);
            }
        }
    }

    /**
     * Removes an object from a specified index.
     *
     * @param index
     */
    public void remove(int index) {
        if (index < 0 || index > this.currentIndex) {
            throw new ArrayIndexOutOfBoundsException("ArrayIndexOutOfBounds");
        }

        for (int i = index; i < this.currentIndex - 1; i++) {
            list[i] = list[i + 1];
        }

        list[currentIndex - 1] = null;
        currentIndex--;
    }

    /**
     *
     * @return size of the list
     */
    public int size() {
        return currentIndex;
    }

    private void increaseSize() {
        int newcapacity = (list.length * 2) + 1;
        Object[] copy = list.clone();
        list = new Object[newcapacity];
        for (int i = 0; i < copy.length; i++) {
            list[i] = copy[i];
        }
    }

    /**
     * Return an object from a specified index.
     *
     * @param i index
     * @return object from index
     */
    public E get(int i) {
        return (E) list[i];
    }

    /**
     *
     * @return if list is empty
     */
    public boolean isEmpty() {
        return currentIndex == 0;
    }

    /**
     * Returns if list contains a specified object.
     *
     * @param e object
     * @return if list contains object
     */
    public boolean contains(E e) {
        for (int i = 0; i < currentIndex; i++) {
            if (list[i].equals(e)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Clears the list.
     */
    public void clear() {
        this.list = new Object[100];
        currentIndex = 0;
    }

}
