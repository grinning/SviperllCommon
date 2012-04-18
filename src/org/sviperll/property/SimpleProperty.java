/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.property;

/**
 *
 * @author vir
 */
public class SimpleProperty<T> implements SettableProperty<T> {
    private T value;
    private boolean hasBeenSet = false;
    public SimpleProperty(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T set(T t) {
        T res = value;
        value = t;
        hasBeenSet = true;
        return res;
    }

    @Override
    public boolean hasBeenSet() {
        return hasBeenSet;
    }

    @Override
    public void resetHasBeenSet() {
        hasBeenSet = false;
    }
}
