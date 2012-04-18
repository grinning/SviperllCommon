/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll;

/**
 *
 * @author vir
 */
public interface Mapper<T, U> {
    U map(T t);

    <V> Mapper<T, V> composeWith(Mapper<U, V> f);
}
