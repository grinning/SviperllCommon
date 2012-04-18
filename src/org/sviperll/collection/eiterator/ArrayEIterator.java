/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class ArrayEIterator<T> implements EIterator<T> {
    private final T[] array;
    private int position;
    public ArrayEIterator(T[] array) {
        this.array = array;
        position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < array.length;
    }

    @Override
    public T next() {
        T res = array[position];
        position++;
        return res;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <U> EIterator<U> map(Mapper<? super T, U> mapper) {
        return EIterators.map(this, mapper);
    }

    @Override
    public EIterator<T> filter(Predicate<? super T> predicate) {
        return EIterators.filter(this, predicate);
    }

    @Override
    public EIterator<T> truncate(int count) {
        return EIterators.truncate(this, count);
    }

    @Override
    public <U> U reduce(U base, Reducer<U, ? super T> reducer) {
        return EIterators.reduce(this, base, reducer);
    }
}
