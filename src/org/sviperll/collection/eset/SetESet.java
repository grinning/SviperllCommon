/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eset;

import java.util.Set;
import org.sviperll.collection.ecollection.CollectionECollection;

/**
 *
 * @author vir
 */
public class SetESet<T> extends CollectionECollection<T> implements ESet<T> {
    static <T> ESet<T> createInstance(Set<T> set) {
        if (set instanceof ESet)
            return (ESet<T>)set;
        else
            return new SetESet<T>(set);
    }

    protected SetESet(Set<T> set) {
        super(set);
    }

    @Override
    public ESet<T> union(Set<T> set) {
        return ESets.union(this, set);
    }

    @Override
    public ESet<T> intersection(Set<T> set) {
        return ESets.intersection(this, set);
    }

    @Override
    public ESet<T> difference(Set<T> set) {
        return ESets.difference(this, set);
    }
}
