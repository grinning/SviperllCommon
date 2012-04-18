/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection;

import org.sviperll.collection.ecollection.ECollection;
import org.sviperll.collection.emap.EMap;
import org.sviperll.collection.eset.ESet;

/**
 *
 * @author vir
 */
public interface Table<C extends Comparable<? super C>, R extends Comparable<? super R>, V> {

    void clear();

    boolean containsCol(C k1);

    boolean containsRow(R k2);

    ESet<TableEntry<C, R, V>> entrySet();

    V get(C k1, R k2);

    EMap<R, V> getCol(C k1);

    EMap<C, V> getRow(R k2);

    boolean isEmpty();

    ESet<C> colSet();

    ESet<R> rowSet();

    V put(C k1, R k2, V value);

    void putAll(Table<C, R, ? extends V> src);

    V remove(C k1, R k2);

    EMap<R, V> removeCol(C k1);

    EMap<C, V> removeRow(R k2);

    int size();

    ECollection<V> values();

    interface TableEntry<C extends Comparable<? super C>, R extends Comparable<? super R>, V> extends Comparable<TableEntry<C, R, V>> {
        C getCol();
        R getRow();
        V getValue();
    }
}
