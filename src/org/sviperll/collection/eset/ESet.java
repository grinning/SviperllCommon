/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eset;

import java.util.Set;
import org.sviperll.collection.ecollection.ECollection;
import org.sviperll.collection.eiterator.EIterator;

/**
 *
 * @author vir
 */
public interface ESet<T> extends Set<T>, ECollection<T> {
    @Override
    EIterator<T> iterator();

    ESet<T> union(Set<T> set);
    ESet<T> intersection(Set<T> set);
    ESet<T> difference(Set<T> set);
}
