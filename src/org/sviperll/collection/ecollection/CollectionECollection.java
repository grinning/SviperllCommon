/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.ecollection;

import java.util.Collection;
import org.sviperll.collection.eiterable.IterableEIterable;

/**
 *
 * @author vir
 */
public class CollectionECollection<E> extends IterableEIterable<E> implements ECollection<E> {
    static <E> ECollection<E> createInstance(Collection<E> collection) {
        if (collection instanceof ECollection)
            return (ECollection<E>)collection;
        else
            return new CollectionECollection<E>(collection);
    }

    private final Collection<E> collection;
    protected CollectionECollection(Collection<E> collection) {
        super(collection);
        this.collection = collection;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        return collection.contains((E)o);
    }

    @Override
    public Object[] toArray() {
        return collection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return collection.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return collection.add(e);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        return collection.remove((E)o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return collection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return collection.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return collection.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return collection.retainAll(c);
    }

    @Override
    public void clear() {
        collection.clear();
    }
}
