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
public class FilteredEIterator<T> implements EIterator<T> {
    private final EIterator<T> source;
    private final Predicate<? super T> predicate;

    private T next;
    private boolean hasNext = false;

    public FilteredEIterator(EIterator<T> source, Predicate<? super T> predicate) {
        this.source = source;
        this.predicate = predicate;
        hasNext = readNext();
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public T next() {
        T result = next;
        hasNext = readNext();
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean readNext() {
        if (source.hasNext()) {
            next = source.next();
            while (!predicate.eval(next)) {
                if (!source.hasNext())
                    return false;
                next = source.next();
            }
            return true;
        } else
            return false;
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
