/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class LookAheadEIterator<T> implements EIterator<T> {
    private final Deque<EIterator<T>> deque;
    private final ListEIterator<T> dequeSource;
    private final Deque<RecorderEIterator<T>> recorderStack = new LinkedList<RecorderEIterator<T>>();

    private EIterator<T> currentSource;

    public LookAheadEIterator(EIterator<T> eiterator) {
        LinkedList<EIterator<T>> list = new LinkedList<EIterator<T>>();
        dequeSource = new ListEIterator<T>(list);
        deque = list;
        deque.addLast(eiterator);
        currentSource = dequeSource;
    }

    @Override
    public boolean hasNext() {
        return currentSource.hasNext();
    }

    @Override
    public T next() {
        return currentSource.next();
    }

    public void beginLookAhead() {
        RecorderEIterator<T> recorder = new RecorderEIterator<T>(currentSource, new ArrayList<T>());
        recorderStack.addLast(recorder);
        currentSource = recorder;
    }

    public void commit() {
        recorderStack.removeLast();
        if (recorderStack.isEmpty())
            currentSource = dequeSource;
        else
            currentSource = recorderStack.getLast();
    }

    public void rollback() {
        RecorderEIterator<T> recorder = recorderStack.getLast();
        Iterator<T> iterator = recorder.record().iterator();
        deque.addFirst(IteratorEIterator.createInstance(iterator));
        commit();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <U> EIterator<U> map(Mapper<? super T, U> mapper) {
        return EIterators.map(this, mapper);
    }

    @Override
    public EIterator<T> filter(Predicate<? super T> predicate) {
        return EIterators.filter(this, predicate);
    }

    @Override
    public EIterator<T> truncate(int count) {
        return EIterators.truncate(this, count);
    }

    @Override
    public <U> U reduce(U base, Reducer<U, ? super T> reducer) {
        return EIterators.reduce(this, base, reducer);
    }

    public static class Rollback extends Exception {
    }
}
