/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import java.util.List;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class RecorderEIterator<T> implements EIterator<T> {
    private final EIterator<T> source;
    private final List<T> record;
    public RecorderEIterator(EIterator<T> source, List<T> record) {
        this.source = source;
        this.record = record;
    }

    public List<T> record() {
        return record;
    }

    @Override
    public boolean hasNext() {
        return source.hasNext();
    }

    @Override
    public T next() {
        T res = source.next();
        record.add(res);
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
