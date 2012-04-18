/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.ecollection;

import java.util.ArrayList;
import java.util.Collection;
import org.sviperll.collection.Collections;
import org.sviperll.collection.eiterable.EIterable;
import org.sviperll.collection.elist.EList;
import org.sviperll.collection.eiterable.EIterables;
import org.sviperll.Mapper;
import org.sviperll.Mappers;
import org.sviperll.Predicate;

/**
 *
 * @author vir
 */
public class ECollections {
    public static Mapper<ECollection<?>, Integer> sizeMapper = new SizeMapper();

    public static <T> ECollection<T> wrap(Collection<T> collection) {
        return CollectionECollection.createInstance(collection);
    }

    public static <T> ECollection<T> filter(Collection<T> ecollection, Predicate<? super T> predicate) {
        EList<T> result = Collections.wrapList(new ArrayList<T>(ecollection.size()));
        for (T element: EIterables.filter(ecollection, predicate))
            result.add(element);
        return result;
    }
    public static <T, U> ECollection<U> map(Collection<? extends T> ecollection, Mapper<T, U> mapper) {
        EList<U> result = Collections.wrapList(new ArrayList<U>(ecollection.size()));
        for (U element: EIterables.map(ecollection, mapper))
            result.add(element);
        return result;
    }
    public static <T> ECollection<T> concat(Collection<? extends Collection<? extends T>> ecollections) {
        int size = 0;
        for (Collection<? extends T> ecollection: ecollections)
            size += ecollection.size();
        EList<T> result = Collections.wrapList(new ArrayList<T>(size));
        for (T element: EIterables.concat(ecollections))
            result.add(element);
        return result;
    }

    public static Mapper<ECollection<?>, Integer> sizeMapper() {
        return sizeMapper;
    }

    private ECollections() {}

    private static class SizeMapper implements Mapper<ECollection<?>, Integer> {
        @Override
        public Integer map(ECollection<?> t) {
            return t.size();
        }

        @Override
        public <V> Mapper<ECollection<?>, V> composeWith(Mapper<Integer, V> f) {
            return Mappers.compose(this, f);
        }

    }
}
