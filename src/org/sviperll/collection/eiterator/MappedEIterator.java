/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import java.util.ArrayList;
import java.util.List;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class MappedEIterator<T, U> implements EIterator<U> {
    private final EIterator<? extends T> source;
    private final Mapper<T, U> mapper;
    public MappedEIterator(EIterator<? extends T> source, Mapper<T, U> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return source.hasNext();
    }

    @Override
    public U next() {
        return mapper.map(source.next());
    }

    @Override
    public void remove() {
        source.remove();
    }

    @Override
    public <V> EIterator<V> map(Mapper<? super U, V> mapper) {
        return EIterators.map(this, mapper);
    }

    @Override
    public EIterator<U> filter(Predicate<? super U> predicate) {
        return EIterators.filter(this, predicate);
    }

    @Override
    public EIterator<U> truncate(int count) {
        return EIterators.truncate(this, count);
    }

    @Override
    public <T> T reduce(T base, Reducer<T, ? super U> reducer) {
        return EIterators.reduce(this, base, reducer);
    }

}
