/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.cli;

import java.io.File;
import org.sviperll.property.SimpleProperty;

/**
 *
 * @author vir
 */
public class FileProperty extends SimpleProperty<File> implements CLISpec.ParameterHandler {
    public FileProperty(File value) {
        super(value);
    }
    @Override
    public void handle(String param) {
        set(new File(param));
    }
}
