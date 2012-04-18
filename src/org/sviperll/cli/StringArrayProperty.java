/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.cli;

import org.sviperll.property.SimpleProperty;

/**
 *
 * @author vir
 */
public class StringArrayProperty extends SimpleProperty<String[]> implements CLISpec.ParameterHandler {
    public StringArrayProperty(String[] value) {
        super(value);
    }
    @Override
    public void handle(String param) {
        set(param.split(":"));
    }
}
