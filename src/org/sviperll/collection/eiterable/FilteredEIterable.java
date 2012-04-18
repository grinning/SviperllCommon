/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterable;

import org.sviperll.collection.eiterator.Merger;
import org.sviperll.collection.eiterator.EIterator;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class FilteredEIterable<E> implements EIterable<E> {
    private final EIterable<E> eiterable;
    private final Predicate<? super E> predicate;
    public FilteredEIterable(EIterable<E> eiterable, Predicate<? super E> predicate) {
        this.eiterable = eiterable;
        this.predicate = predicate;
    }

    @Override
    public EIterator<E> iterator() {
        return eiterable.iterator().filter(predicate);
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
