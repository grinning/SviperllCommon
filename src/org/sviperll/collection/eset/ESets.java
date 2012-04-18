/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eset;

import java.util.Iterator;
import java.util.Set;
import org.sviperll.collection.Collections;
import org.sviperll.Mapper;

/**
 *
 * @author vir
 */
public class ESets {
    public static <T> ESet<T> wrap(Set<T> set) {
        return SetESet.createInstance(set);
    }

    public static <T> ESet<T> union(Iterable<? extends Set<T>> sets) {
        ESet<T> result = Collections.newTreeSet();
        for (Set<T> set: sets)
            result.addAll(set);
        return result;
    }
    public static <T> ESet<T> union(Set<T> set1, Set<T> set2) {
        return union(Collections.newArrayList(set1, set2));
    }

    public static <T> ESet<T> intersection(Iterable<? extends Set<T>> sets) {
        ESet<T> result = Collections.newTreeSet();
        Iterator<? extends Set<T>> iterator = sets.iterator();
        if (iterator.hasNext()) {
            result.addAll(iterator.next());
            while (iterator.hasNext())
                result.retainAll(iterator.next());
        }
        return result;
    }
    public static <T> ESet<T> intersection(Set<T> set1, Set<T> set2) {
        return intersection(Collections.newArrayList(set1, set2));
    }

    public static <T> ESet<T> difference(Set<T> set1, Set<T> set2) {
        ESet<T> result = Collections.newTreeSet();
        result.addAll(set1);
        result.removeAll(set2);
        return result;
    }

    public static <T, U> ESet<T> map(ESet<? extends U> set, Mapper<U, T> mapper) {
        ESet<T> res = Collections.newTreeSet();
        for (T t: set.map(mapper))
            res.add(t);
        return res;
    }

    private ESets() {}
}
