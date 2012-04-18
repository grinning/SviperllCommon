/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.emap;

import java.util.Map;
import java.util.Map.Entry;
import org.sviperll.collection.ecollection.ECollection;
import org.sviperll.collection.ecollection.ECollections;
import org.sviperll.collection.eset.ESet;
import org.sviperll.collection.eset.ESets;

/**
 *
 * @author vir
 */
public class MapEMap<K, V> implements EMap<K, V> {
    static <K, V> EMap<K, V> createInstance(Map<K, V> map) {
        if (map instanceof EMap)
            return (EMap<K, V>)map;
        else
            return new MapEMap<K, V>(map);
    }

    private final Map<K, V> map;
    protected MapEMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public ESet<K> keySet() {
        return ESets.wrap(map.keySet());
    }

    @Override
    public ECollection<V> values() {
        return ECollections.wrap(map.values());
    }

    @Override
    public ESet<Entry<K, V>> entrySet() {
        return ESets.wrap(map.entrySet());
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean containsKey(Object key) {
        return map.containsKey((K)key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean containsValue(Object value) {
        return map.containsValue((V)value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        return map.get((K)key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        return map.remove((K)key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

}
