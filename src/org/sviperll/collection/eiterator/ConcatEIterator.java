/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import java.util.Iterator;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class ConcatEIterator<T> implements EIterator<T> {
    private final Iterator<? extends Iterator<? extends T>> eiterators;
    private Iterator<? extends T> current = null;
    public ConcatEIterator(Iterator<? extends Iterator<? extends T>> eiterator) {
        this.eiterators = eiterator;
        if (eiterators.hasNext())
            current = eiterators.next();
        concat();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        T res = current.next();
        concat();
        return res;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void concat() {
        while (current != null && !current.hasNext()) {
            if (eiterators.hasNext())
                current = eiterators.next();
            else
                current = null;
        }
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
