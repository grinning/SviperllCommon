/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterable;

import org.sviperll.collection.eiterator.EIterator;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public interface EIterable<E> extends Iterable<E> {
    @Override
    EIterator<E> iterator();

    <F> EIterable<F> map(Mapper<? super E, F> mapper);

    EIterable<E> filter(Predicate<? super E> predicate);

    EIterable<E> truncate(int count);

    <F> F reduce(F base, Reducer<F, ? super E> reducer);
}
