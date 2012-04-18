/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator.closeable;

import java.io.Closeable;
import java.io.IOException;
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
public class CloseableEIteratorDelegate<T> implements CloseableEIterator<T> {
    private final EIterator<T> actualImplementation;
    private final Closeable resourceToClose;
    public CloseableEIteratorDelegate(EIterator<T> actualImplementation, Closeable resourceToClose) {
        this.actualImplementation = actualImplementation;
        this.resourceToClose = resourceToClose;
    }

    @Override
    public boolean hasNext() {
        return actualImplementation.hasNext();
    }

    @Override
    public T next() {
        return actualImplementation.next();
    }

    @Override
    public void remove() {
        actualImplementation.remove();
    }

    @Override
    public void close() throws IOException {
        resourceToClose.close();
    }

    @Override
    public <U> CloseableEIterator<U> map(Mapper<? super T, U> mapper) {
        return CloseableEIterators.map(this, mapper);
    }

    @Override
    public CloseableEIterator<T> filter(Predicate<? super T> predicate) {
        return CloseableEIterators.filter(this, predicate);
    }

    @Override
    public CloseableEIterator<T> truncate(int count) {
        return CloseableEIterators.truncate(this, count);
    }

    @Override
    public <U> U reduce(U base, Reducer<U, ? super T> reducer) {
        return EIterators.reduce(this, base, reducer);
    }
}
