/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.sviperll.collection.Collections;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class EIterators {
    public static <A> EIterator<A> wrap(Iterator<A> source) {
        return IteratorEIterator.createInstance(source);
    }

    public static <A, B> EIterator<B> map(Iterator<? extends A> source, Mapper<A, B> mapper) {
        return new MappedEIterator<A, B>(wrap(source), mapper);
    }

    public static <A> EIterator<A> filter(Iterator<A> source, Predicate<? super A> predicate) {
        return new FilteredEIterator<A>(wrap(source), predicate);
    }

    public static <A> EIterator<A> concat(Iterator<? extends Iterator<? extends A>> sources) {
        return new ConcatEIterator<A>(sources);
    }

    public static <A> EIterator<A> concat(Iterable<? extends Iterator<? extends A>> sources) {
        return concat(sources.iterator());
    }

    public static <A extends Mergable<A>> EIterator<? extends A> merge(Collection<? extends Iterator<? extends A>> sources) {
        List<EIterator<? extends A>> list = Collections.wrapList(new ArrayList<EIterator<? extends A>>(sources.size()));
        for (Iterator<? extends A> i: sources)
            list.add(wrap(i));
        return MergedEIterator.createInstance(list);
    }

    public static <A> EIterator<A> merge(Collection<? extends Iterator<? extends A>> sources, Merger<A> merger) {
        List<EIterator<? extends A>> list = Collections.wrapList(new ArrayList<EIterator<? extends A>>(sources.size()));
        for (Iterator<? extends A> i: sources)
            list.add(wrap(i));
        return MergedEIterator.createInstance(list, merger);
    }

    public static <T> EIterator<T> truncate(Iterator<T> source, int count) {
        return new TruncatedEIterator<T>(wrap(source), count);
    }

    public static <T, U> T reduce(Iterator<? extends U> source, T base, Reducer<T, U> reducer) {
        T result = base;
        while (source.hasNext())
            result = reducer.reduce(result, source.next());
        return result;
    }

    private EIterators() {}
}
