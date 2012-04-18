/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll;

/**
 *
 * @author vir
 */
public interface Predicate<T> {
    boolean eval(T t);

    Predicate<T> not();
    Predicate<T> and(Predicate<T> p);
    Predicate<T> or(Predicate<T> p);
}
