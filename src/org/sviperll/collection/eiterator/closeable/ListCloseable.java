/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator.closeable;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * @author vir
 */
public class ListCloseable implements Closeable {
    private final Iterable<? extends Closeable> list;
    public ListCloseable(Iterable<? extends Closeable> list) {
        this.list = list;
    }

    @Override
    public void close() throws IOException {
        IOException exception = null;
        for (Closeable c: list) {
            try {
                c.close();
            } catch (IOException ex) {
                exception = ex;
            }
        }
        if (exception != null)
            throw exception;
    }
}
