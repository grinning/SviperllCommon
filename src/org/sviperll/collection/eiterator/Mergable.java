/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

/**
 *
 * @author vir
 */
public interface Mergable<T> extends Comparable<T> {
    T mergeWith(T that);
}
