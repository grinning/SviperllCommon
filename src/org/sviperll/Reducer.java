/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll;

/**
 *
 * @author vir
 */
public interface Reducer<R, A> {
    R reduce(R base, A argument);
}
