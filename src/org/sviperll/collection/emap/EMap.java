/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.emap;

import java.util.Map;
import java.util.Map.Entry;
import org.sviperll.collection.ecollection.ECollection;
import org.sviperll.collection.eset.ESet;
import org.sviperll.Mapper;

/**
 *
 * @author vir
 */
public interface EMap<K, V> extends Map<K, V> {
    @Override
    public ESet<K> keySet();

    @Override
    public ECollection<V> values();

    @Override
    public ESet<Entry<K, V>> entrySet();
}
