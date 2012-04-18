/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.ecollection;

import java.util.Collection;
import org.sviperll.collection.eiterable.EIterable;
import org.sviperll.collection.eiterator.EIterator;

/**
 *
 * @author vir
 */
public interface ECollection<E> extends Collection<E>, EIterable<E> {
    @Override
    EIterator<E> iterator();
}
