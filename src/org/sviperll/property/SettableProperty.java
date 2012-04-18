/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.property;

/**
 *
 * @author vir
 */
public interface SettableProperty<T> extends Property<T> {
    boolean hasBeenSet();
    void resetHasBeenSet();
    public T set(T t);
}
