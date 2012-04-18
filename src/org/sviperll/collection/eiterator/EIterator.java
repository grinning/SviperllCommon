/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import java.util.Iterator;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public interface EIterator<T> extends Iterator<T> {
    <U> EIterator<U> map(Mapper<? super T, U> mapper);

    EIterator<T> filter(Predicate<? super T> predicate);

    EIterator<T> truncate(int count);

    <U> U reduce(U base, Reducer<U, ? super T> reducer);
}
