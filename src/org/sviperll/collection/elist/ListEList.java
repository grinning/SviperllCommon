/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.elist;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.sviperll.collection.ecollection.CollectionECollection;

/**
 *
 * @author vir
 */
public class ListEList<E> extends CollectionECollection<E> implements EList<E> {
    static <E> EList<E> createInstance(List<E> list) {
        if (list instanceof EList)
            return (EList<E>)list;
        else
            return new ListEList<E>(list);
    }

    public final List<E> list;
    protected ListEList(List<E> list) {
        super(list);
        this.list = list;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return list.addAll(index, c);
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public EList<E> subList(int fromIndex, int toIndex) {
        return new ListEList<E>(list.subList(fromIndex, toIndex));
    }
}
