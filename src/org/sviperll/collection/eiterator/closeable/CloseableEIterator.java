/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator.closeable;

import java.io.Closeable;
import org.sviperll.collection.eiterator.EIterator;
import org.sviperll.Mapper;
import org.sviperll.Predicate;

/**
 *
 * @author vir
 */
public interface CloseableEIterator<T> extends EIterator<T>, Closeable {
    @Override
    <U> CloseableEIterator<U> map(Mapper<? super T, U> mapper);

    @Override
    CloseableEIterator<T> filter(Predicate<? super T> predicate);

    @Override
    CloseableEIterator<T> truncate(int count);
}
