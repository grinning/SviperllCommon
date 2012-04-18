/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterable;

import org.sviperll.collection.eiterator.IteratorEIterator;
import org.sviperll.collection.eiterator.Merger;
import org.sviperll.collection.eiterator.EIterator;
import org.sviperll.collection.eiterator.EIterators;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class IterableEIterable<E> implements EIterable<E> {
    static <E> EIterable<E> createInstance(Iterable<E> iterable) {
        if (iterable instanceof EIterable)
            return (EIterable<E>)iterable;
        else
            return new IterableEIterable<E>(iterable);
    }

    private final Iterable<E> iterable;
    protected IterableEIterable(Iterable<E> iterable) {
        this.iterable = iterable;
    }

    @Override
    public EIterator<E> iterator() {
        return EIterators.wrap(iterable.iterator());
    }

    @Override
    public <F> EIterable<F> map(Mapper<? super E, F> mapper) {
        return EIterables.map(this, mapper);
    }

    @Override
    public EIterable<E> filter(Predicate<? super E> predicate) {
        return EIterables.filter(this, predicate);
    }

    @Override
    public EIterable<E> truncate(int count) {
        return EIterables.truncate(this, count);
    }

    @Override
    public <F> F reduce(F base, Reducer<F, ? super E> reducer) {
        return EIterables.reduce(this, base, reducer);
    }
}
