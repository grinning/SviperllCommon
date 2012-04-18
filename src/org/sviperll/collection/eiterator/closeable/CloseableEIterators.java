/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator.closeable;

import java.io.Closeable;
import java.util.Arrays;
import java.util.List;
import org.sviperll.collection.Collections;
import org.sviperll.collection.eiterable.EIterable;
import org.sviperll.collection.eiterator.EIterator;
import org.sviperll.collection.eiterator.EIterators;
import org.sviperll.Mapper;
import org.sviperll.Predicate;

/**
 *
 * @author vir
 */
public class CloseableEIterators {
    public static <T> CloseableEIterator<T> delegateClosable(EIterator<T> actualImplementation, Closeable resourceToClose) {
        return new CloseableEIteratorDelegate<T>(actualImplementation, resourceToClose);
    }

    public static <T, U> CloseableEIterator<T> map(CloseableEIterator<? extends U> source, Mapper<U, T> mapper) {
        return delegateClosable(EIterators.map(source, mapper), source);
    }

    public static <T> CloseableEIterator<T> filter(CloseableEIterator<T> source, Predicate<? super T> predicate) {
        return delegateClosable(EIterators.filter(source, predicate), source);
    }

    public static <T> CloseableEIterator<T> truncate(CloseableEIterator<T> source, int count) {
        return delegateClosable(EIterators.truncate(source, count), source);
    }

    public static <T> CloseableEIterator<T> concat(Iterable<? extends CloseableEIterator<? extends T>> sources) {
        return delegateClosable(EIterators.concat(sources.iterator()), new ListCloseable(sources));
    }

    private CloseableEIterators() {}
}
