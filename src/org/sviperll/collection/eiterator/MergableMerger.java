/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

/**
 *
 * @author vir
 */
public class MergableMerger<T extends Mergable<T>>  implements Merger<T> {
    @Override
    public T merge(T a, T b) {
        return a.mergeWith(b);
    }

    @Override
    public int compare(T a, T b) {
        return a.compareTo(b);
    }

}
