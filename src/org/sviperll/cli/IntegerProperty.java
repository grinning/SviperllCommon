/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.cli;

import org.sviperll.property.SimpleProperty;

/**
 *
 * @author vir
 */
public class IntegerProperty extends SimpleProperty<Integer> implements CLISpec.ParameterHandler {
    public IntegerProperty(int value) {
        super(value);
    }
    @Override
    public void handle(String param) {
        set(Integer.parseInt(param));
    }
}
