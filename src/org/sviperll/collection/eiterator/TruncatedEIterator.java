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
public class TruncatedEIterator<T> implements EIterator<T> {
    private final EIterator<T> source;
    private int count;
    public TruncatedEIterator(EIterator<T> source, int count) {
        this.source = source;
        this.count = count;
    }

    @Override
    public boolean hasNext() {
        return count > 0 && source.hasNext();
    }

    @Override
    public T next() {
        if (count > 0) {
            T res = source.next();
            count--;
            return res;
        } else
            return null;
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
