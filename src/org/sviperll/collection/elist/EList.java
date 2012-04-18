/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.elist;

import java.util.List;
import org.sviperll.collection.ecollection.ECollection;
import org.sviperll.collection.eiterator.EIterator;

/**
 *
 * @author vir
 */
public interface EList<E> extends List<E>, ECollection<E> {
    @Override
    EIterator<E> iterator();

    @Override
    public EList<E> subList(int fromIndex, int toIndex);
}
