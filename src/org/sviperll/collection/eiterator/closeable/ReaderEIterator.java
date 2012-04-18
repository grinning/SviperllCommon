/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator.closeable;

import java.io.IOException;
import java.io.Reader;
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
public class ReaderEIterator implements CloseableEIterator<Character> {
    private final Reader reader;
    private int current;
    ReaderEIterator(Reader reader) throws IOException {
        this.reader = reader;
        current = reader.read();
    }

    @Override
    public boolean hasNext() {
        return current != -1;
    }

    @Override
    public Character next() {
        try {
            char res = (char) current;
            current = reader.read();
            return res;
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
    public <U> CloseableEIterator<U> map(Mapper<? super Character, U> mapper) {
        return CloseableEIterators.map(this, mapper);
    }

    @Override
    public CloseableEIterator<Character> filter(Predicate<? super Character> predicate) {
        return CloseableEIterators.filter(this, predicate);
    }

    @Override
    public CloseableEIterator<Character> truncate(int count) {
        return CloseableEIterators.truncate(this, count);
    }

    @Override
    public <U> U reduce(U base, Reducer<U, ? super Character> reducer) {
        return EIterators.reduce(this, base, reducer);
    }
}
