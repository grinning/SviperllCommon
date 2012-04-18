/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import org.sviperll.collection.Collections;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vir
 */
public class MergedEIteratorTest {
    private static final Merger<Integer> merger = new IntegerMerger();

    @Test
    public void testMergeEmpty() {
        EIterator<Integer> s1 = new ArrayEIterator<Integer>(new Integer[] {});
        EIterator<Integer> s2 = new ArrayEIterator<Integer>(new Integer[] {});
        int[] result = readMergedSource(s1, s2);
        assertArrayEquals(new int[] {}, result);
    }

    @Test
    public void testMergeEmptyLeft() {
        EIterator<Integer> s1 = new ArrayEIterator<Integer>(new Integer[] {});
        EIterator<Integer> s2 = new ArrayEIterator<Integer>(new Integer[] {1});
        int[] result = readMergedSource(s1, s2);
        assertArrayEquals(new int[] {1}, result);
    }

    @Test
    public void testMergeEmptyRight() {
        EIterator<Integer> s1 = new ArrayEIterator<Integer>(new Integer[] {1});
        EIterator<Integer> s2 = new ArrayEIterator<Integer>(new Integer[] {});
        int[] result = readMergedSource(s1, s2);
        assertArrayEquals(new int[] {1}, result);
    }

    @Test
    public void testMerge1() {
        EIterator<Integer> s1 = new ArrayEIterator<Integer>(new Integer[] {1});
        EIterator<Integer> s2 = new ArrayEIterator<Integer>(new Integer[] {1});
        int[] result = readMergedSource(s1, s2);
        assertArrayEquals(new int[] {1}, result);
    }


    @Test
    public void testMergeDistinctElements() {
        EIterator<Integer> s1 = new ArrayEIterator<Integer>(new Integer[] {1});
        EIterator<Integer> s2 = new ArrayEIterator<Integer>(new Integer[] {2});
        int[] result = readMergedSource(s1, s2);
        assertArrayEquals(new int[] {1, 2}, result);
    }

    @Test
    public void testMergeDistinctElementsReversed() {
        EIterator<Integer> s1 = new ArrayEIterator<Integer>(new Integer[] {2});
        EIterator<Integer> s2 = new ArrayEIterator<Integer>(new Integer[] {1});
        int[] result = readMergedSource(s1, s2);
        assertArrayEquals(new int[] {1, 2}, result);
    }

    @Test
    public void testMerge4() {
        EIterator<Integer> s1 = new ArrayEIterator<Integer>(new Integer[] {1, 2, 3});
        EIterator<Integer> s2 = new ArrayEIterator<Integer>(new Integer[] {2, 4, 6});
        int[] result = readMergedSource(s1, s2);
        assertArrayEquals(new int[] {1, 2, 3, 4, 6}, result);
    }

    private int[] readMergedSource(EIterator<Integer> s1, EIterator<Integer> s2) {
        EIterator<Integer> mergedSource = EIterators.merge(Collections.newArrayList(s1, s2), merger);
        ArrayList<Integer> record = new ArrayList<Integer>();
        while (mergedSource.hasNext()) {
            record.add(mergedSource.next());
        }
        int[] result = new int[record.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = record.get(i).intValue();
        return result;
    }

    private static class IntegerMerger implements Merger<Integer> {
        @Override
        public Integer merge(Integer a, Integer b) {
            return b;
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }
}
