/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterable;

import java.util.ArrayList;
import java.util.Collection;
import org.sviperll.collection.Collections;
import org.sviperll.collection.elist.EList;
import org.sviperll.collection.eiterator.Mergable;
import org.sviperll.collection.eiterator.MergableMerger;
import org.sviperll.collection.eiterator.Merger;
import org.sviperll.collection.eiterator.EIterators;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class EIterables {
    public static <T> EIterable<T> wrap(Iterable<T> iterable) {
        return IterableEIterable.createInstance(iterable);
    }

    public static <E, F> EIterable<F> map(Iterable<? extends E> eiterable, Mapper<E, F> mapper) {
        return new MappedEIterable<E, F>(wrap(eiterable), mapper);
    }

    public static <E, F> EIterable<E> filter(Iterable<E> eiterable, Predicate<? super E> predicate) {
        return new FilteredEIterable<E>(wrap(eiterable), predicate);
    }

    public static <E, F> F reduce(Iterable<? extends E> eiterable, F base, Reducer<F, E> reducer) {
        return EIterators.wrap(eiterable.iterator()).reduce(base, reducer);
    }

    public static <E> EIterable<E> concat(Iterable<? extends Iterable<? extends E>> eitrables) {
        return new ConcatEIterable<E>(eitrables);
    }

    public static <E extends Mergable<E>> EIterable<E> merge(Collection<? extends Iterable<? extends E>> eitrables) {
        return merge(eitrables, new MergableMerger<E>());
    }

    public static <E> EIterable<E> merge(Collection<? extends Iterable<? extends E>> eitrables, Merger<E> merger) {
        EList<EIterable<? extends E>> sourceables = Collections.wrapList(new ArrayList<EIterable<? extends E>>(eitrables.size()));
        for(Iterable<? extends E> eitrable: eitrables)
            sourceables.add(wrap(eitrable));
        return new MergedEIterable<E>(sourceables, merger);
    }

    public static <E> EIterable<E> truncate(Iterable<E> eitrable, int count) {
        return new TruncatedEIterable<E>(wrap(eitrable), count);
    }

    private EIterables() {}
}
