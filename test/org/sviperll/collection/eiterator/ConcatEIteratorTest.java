/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import org.sviperll.collection.eiterator.EIterator;
import org.sviperll.collection.eiterator.ConcatEIterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.sviperll.collection.Collections;
import org.sviperll.collection.elist.EList;

/**
 *
 * @author vir
 */
public class ConcatEIteratorTest {
    @Test
    public void testBothEmpty() {
        EList<Integer> e1 = Collections.newArrayList();
        EList<Integer> e2 = Collections.newArrayList();
        EList<EIterator<Integer>> iteratorPair = Collections.newArrayList(e1.iterator(), e2.iterator());
        Integer[] result = readIterator(iteratorPair.iterator());
        assertArrayEquals(new Integer[] {}, result);
    }

    @Test
    public void testFirstEmpty() {
        EList<Integer> e1 = Collections.newArrayList();
        EList<Integer> e2 = Collections.newArrayList(1, 2);
        EList<EIterator<Integer>> iteratorPair = Collections.newArrayList(e1.iterator(), e2.iterator());
        Integer[] result = readIterator(iteratorPair.iterator());
        assertArrayEquals(new Integer[] {1, 2}, result);
    }

    @Test
    public void testSecondEmpty() {
        EList<Integer> e1 = Collections.newArrayList(1, 2);
        EList<Integer> e2 = Collections.newArrayList();
        EList<EIterator<Integer>> iteratorPair = Collections.newArrayList(e1.iterator(), e2.iterator());
        Integer[] result = readIterator(iteratorPair.iterator());
        assertArrayEquals(new Integer[] {1, 2}, result);
    }

    @Test
    public void testOneElementPerList() {
        EList<Integer> e1 = Collections.newArrayList();
        e1.add(1);
        EList<Integer> e2 = Collections.newArrayList();
        e2.add(2);
        EList<EIterator<Integer>> iteratorPair = Collections.newArrayList(e1.iterator(), e2.iterator());
        Integer[] result = readIterator(iteratorPair.iterator());
        assertArrayEquals(new Integer[] {1, 2}, result);
    }

    @Test
    public void test() {
        EList<Integer> e1 = Collections.newArrayList(1, 2, 3);
        EList<Integer> e2 = Collections.newArrayList(4, 5, 6);
        EList<EIterator<Integer>> iteratorPair = Collections.newArrayList(e1.iterator(), e2.iterator());
        Integer[] result = readIterator(iteratorPair.iterator());
        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6}, result);
    }

    private Integer[] readIterator(EIterator<EIterator<Integer>> iterator) {
        ConcatEIterator<Integer> concatEIterator = new ConcatEIterator<Integer>(iterator);
        EList<Integer> resultList = Collections.newArrayList();
        while (concatEIterator.hasNext()) {
            Integer elem = concatEIterator.next();
            resultList.add(elem);
        }
        return resultList.toArray(new Integer[resultList.size()]);
    }
}
