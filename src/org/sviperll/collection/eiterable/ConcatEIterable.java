/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.sviperll.collection.eiterator.Merger;
import org.sviperll.collection.eiterator.EIterator;
import org.sviperll.collection.eiterator.EIterators;
import org.sviperll.Mapper;
import org.sviperll.Mappers;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class ConcatEIterable<E> implements EIterable<E> {
    private final Mapper<Iterable<? extends E>, Iterator<? extends E>> iteratorMapper = new IteratorMapper<E>();
    private final Iterable<? extends Iterable<? extends E>> eiterables;
    public ConcatEIterable(Iterable<? extends Iterable<? extends E>> eiterables) {
        this.eiterables = eiterables;
    }

    @Override
    public EIterator<E> iterator() {
        return EIterators.concat(EIterators.map(eiterables.iterator(), iteratorMapper));
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

    private static class IteratorMapper<E> implements Mapper<Iterable<? extends E>, Iterator<? extends E>> {

        @Override
        public Iterator<? extends E> map(Iterable<? extends E> t) {
            return t.iterator();
        }

        @Override
        public <V> Mapper<Iterable<? extends E>, V> composeWith(Mapper<Iterator<? extends E>, V> f) {
            return Mappers.compose(this, f);
        }

    }
}
