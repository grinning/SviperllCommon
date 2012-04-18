/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator.closeable;

import java.io.BufferedReader;
import java.io.IOException;
import org.sviperll.collection.eiterator.Merger;
import org.sviperll.collection.eiterator.EIterator;
import org.sviperll.collection.eiterator.EIterators;
import org.sviperll.Mapper;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class LineReaderEIterator implements CloseableEIterator<String> {
    private final BufferedReader reader;
    private String current;
    public LineReaderEIterator(BufferedReader reader) throws IOException {
        this.reader = reader;
        current = reader.readLine();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public String next() {
        try {
            String result = current;
            current = reader.readLine();
            return result;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public <U> CloseableEIterator<U> map(Mapper<? super String, U> mapper) {
        return CloseableEIterators.map(this, mapper);
    }

    @Override
    public CloseableEIterator<String> filter(Predicate<? super String> predicate) {
        return CloseableEIterators.filter(this, predicate);
    }

    @Override
    public CloseableEIterator<String> truncate(int count) {
        return CloseableEIterators.truncate(this, count);
    }

    @Override
    public <U> U reduce(U base, Reducer<U, ? super String> reducer) {
        return EIterators.reduce(this, base, reducer);
    }
}
