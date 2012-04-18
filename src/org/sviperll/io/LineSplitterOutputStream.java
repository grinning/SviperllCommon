/*
 * Copyright (C) 2012 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author vir
 */
public class LineSplitterOutputStream extends OutputStream {
    private final int splitLinesAt;
    private int lineLength = 0;
    private final OutputStream os;

    public LineSplitterOutputStream(OutputStream os) {
        this(os, 76);
    }
    public LineSplitterOutputStream(OutputStream os, int splitLinesAt) {
        this.os = os;
        this.splitLinesAt = splitLinesAt;
    }

    @Override
    public void write(int b) throws IOException {
        os.write(b);
        lineLength++;
        if (lineLength == splitLinesAt) {
            os.write("\r\n".getBytes("ASCII"));
            lineLength = 0;
        }
    }

    @Override
    public void flush() throws IOException {
        os.flush();
    }

    @Override
    public void close() throws IOException {
        os.close();
    }
}
