package com.shortestpathsolver.structures;

public class CustomArrayList<E> {

    private int currentIndex;
    private Object[] list;

    /**
     * Oma implementaatio ArrayLististä
     *
     * @author kaihartz
     */
    public CustomArrayList() {
        list = new Object[100];
        currentIndex = 0;
    }

    /**
     * Lisää listan loppuun objektin
     *
     * @param e lisättävä objekti
     */
    public void add(E e) {
        if (currentIndex >= list.length) {
            increaseSize();
        }
        list[currentIndex++] = e;
    }

    /**
     * Lisää listaan elementin tietyn indeksin kohdalle
     *
     * @param index uuden objektin indeksi
     * @param e lisättävä objekti
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
     * Poistaa objektin listasta
     *
     * @param e poistettava objekti
     */
    public void remove(E e) {
        for (int i = 0; i < currentIndex; i++) {
            if (list[i].equals(e)) {
                remove(i);
            }
        }
    }

    /**
     * Poistaa objektin listan tietystä indeksistä
     *
     * @param index indeksi
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
     * @return listan pituus
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
     *
     * @param i indeksi
     * @return objektin indeksistä i
     */
    public E get(int i) {
        return (E) list[i];
    }

    /**
     *
     * @return onko lista tyhjä
     */
    public boolean isEmpty() {
        return currentIndex == 0;
    }

    /**
     *
     * @param e haettava objekti
     * @return sisältääkö lista objektin
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
     * Tyhjentää listan
     */
    public void clear() {
        for (int i = 0; i < currentIndex; i++) {
            list[i] = null;
        }
        currentIndex = 0;
    }

}
