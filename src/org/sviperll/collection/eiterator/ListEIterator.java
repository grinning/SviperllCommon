/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import java.util.Deque;
import java.util.List;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class ListEIterator<T> implements EIterator<T> {
    private final List<? extends EIterator<? extends T>> sources;

    public ListEIterator(List<? extends EIterator<? extends T>> sources) {
        this.sources = sources;
        concat();
    }

    @Override
    public boolean hasNext() {
        return !sources.isEmpty();
    }

    @Override
    public T next() {
        T res = sources.get(0).next();
        concat();
        return res;
    }

    private void concat() {
        while (!sources.isEmpty() && !sources.get(0).hasNext()) {
            sources.remove(0);
        }
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
