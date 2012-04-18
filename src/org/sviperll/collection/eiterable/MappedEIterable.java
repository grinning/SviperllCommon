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
public class MappedEIterable<E, F> implements EIterable<F> {
    private final EIterable<? extends E> eiterable;
    private final Mapper<E, F> mapper;
    public MappedEIterable(EIterable<? extends E> eiterable, Mapper<E, F> mapper) {
        this.eiterable = eiterable;
        this.mapper = mapper;
    }

    @Override
    public EIterator<F> iterator() {
        return eiterable.iterator().map(mapper);
    }

    @Override
    public <G> EIterable<G> map(Mapper<? super F, G> mapper) {
        return EIterables.map(this, mapper);
    }

    @Override
    public EIterable<F> filter(Predicate<? super F> predicate) {
        return EIterables.filter(this, predicate);
    }

    @Override
    public EIterable<F> truncate(int count) {
        return EIterables.truncate(this, count);
    }

    @Override
    public <G> G reduce(G base, Reducer<G, ? super F> reducer) {
        return EIterables.reduce(this, base, reducer);
    }
}
