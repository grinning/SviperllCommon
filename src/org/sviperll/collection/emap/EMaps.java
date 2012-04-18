/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.emap;

import java.util.Map;
import org.sviperll.collection.ecollection.ECollection;
import org.sviperll.Mapper;
import org.sviperll.Mappers;

/**
 *
 * @author vir
 */
public class EMaps {
    public static <K, V> EMap<K, V> wrap(Map<K, V> map) {
        return MapEMap.createInstance(map);
    }

    public static <K, V> Mapper<EMap<K, V>, ECollection<V>> valuesMapper() {
        return new ValueMapper<K, V>();
    }

    private EMaps() {}

    private static class ValueMapper<K, V> implements Mapper<EMap<K, V>, ECollection<V>> {
        @Override
        public ECollection<V> map(EMap<K, V> t) {
            return t.values();
        }

        @Override
        public <T> Mapper<EMap<K, V>, T> composeWith(Mapper<ECollection<V>, T> f) {
            return Mappers.compose(this, f);
        }
    }
}
