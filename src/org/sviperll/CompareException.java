/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll;

/**
 *
 * @author vir
 */
public class CompareException extends Exception {
    protected CompareException() {
    }
    public final static class Less extends CompareException {
    }
    public final static class Greater extends CompareException {
    }
}
