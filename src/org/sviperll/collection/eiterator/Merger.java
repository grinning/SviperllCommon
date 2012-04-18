/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import java.util.Comparator;

/**
 *
 * @author vir
 */
public interface Merger<T> extends Comparator<T> {
    T merge(T a, T b);
}
