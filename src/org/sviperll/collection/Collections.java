/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.sviperll.collection.ecollection.ECollection;
import org.sviperll.collection.ecollection.ECollections;
import org.sviperll.collection.eiterable.EIterable;
import org.sviperll.collection.elist.EList;
import org.sviperll.collection.elist.ELists;
import org.sviperll.collection.emap.EMap;
import org.sviperll.collection.emap.EMaps;
import org.sviperll.collection.eset.ESet;
import org.sviperll.collection.eset.ESets;
import org.sviperll.collection.eiterable.EIterables;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class Collections {
    public static <K, V> EMap<K, V> newTreeMap() {
        return EMaps.wrap(new TreeMap<K, V>());
    }
    public static <K, V> EMap<K, V> newHashMap() {
        return EMaps.wrap(new HashMap<K, V>());
    }
    public static <E> ESet<E> newTreeSet() {
        return ESets.wrap(new TreeSet<E>());
    }
    public static <E> ESet<E> newHashSet() {
        return ESets.wrap(new HashSet<E>());
    }
    public static <E> EList<E> newArrayList() {
        return ELists.wrap(new ArrayList<E>());
    }
    public static <E> EList<E> newArrayList(EIterable<E> contents) {
        EList<E> list = newArrayList();
        for (E e: contents)
            list.add(e);
        return list;
    }
    public static <E> EList<E> newArrayList(E e1) {
        EList<E> elist = newArrayList();
        elist.add(e1);
        return elist;
    }
    public static <E> EList<E> newArrayList(E e1, E e2) {
        EList<E> elist = newArrayList();
        elist.add(e1);
        elist.add(e2);
        return elist;
    }
    public static <E> EList<E> newArrayList(E e1, E e2, E e3) {
        EList<E> elist = newArrayList();
        elist.add(e1);
        elist.add(e2);
        elist.add(e3);
        return elist;
    }
    public static <T> ECollection<T> wrapCollection(Collection<T> collection) {
        return ECollections.wrap(collection);
    }
    public static <T> EList<T> wrapList(List<T> collection) {
        return ELists.wrap(collection);
    }
    public static <T> ESet<T> wrapSet(Set<T> collection) {
        return ESets.wrap(collection);
    }
    public static <T, U> EMap<T, U> wrapMap(Map<T, U> collection) {
        return EMaps.wrap(collection);
    }
    public static <T> EIterable<T> wrapIterable(Iterable<T> iterable) {
        return EIterables.wrap(iterable);
    }

    public static <T> ECollection<T> filter(Collection<T> ecollection, Predicate<? super T> predicate) {
        return ECollections.filter(ecollection, predicate);
    }
    public static <T, U> ECollection<U> map(Collection<? extends T> ecollection, Mapper<T, U> mapper) {
        return ECollections.map(ecollection, mapper);
    }
    public static <T, U> U reduce(Iterable<? extends T> ecollection, U base, Reducer<U, T> reducer) {
        return EIterables.wrap(ecollection).reduce(base, reducer);
    }
    public static <T> ECollection<T> concat(Collection<? extends Collection<? extends T>> ecollections) {
        return ECollections.concat(ecollections);
    }
    private Collections() {}
}
