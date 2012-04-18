/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterable;

import java.util.ArrayList;
import java.util.List;
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
public class MergedEIterable<E> implements EIterable<E> {
    private final List<? extends EIterable<? extends E>> eiterables;
    private final Merger<E> merger;
    public MergedEIterable(List<? extends EIterable<? extends E>> eiterables, Merger<E> merger) {
        this.eiterables = eiterables;
        this.merger = merger;
    }

    @Override
    public EIterator<E> iterator() {
        ArrayList<EIterator<? extends E>> sources = new ArrayList<EIterator<? extends E>>(eiterables.size());
        for (EIterable<? extends E> eiterable: eiterables)
            sources.add(eiterable.iterator());
        return EIterators.merge(sources, merger);
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
