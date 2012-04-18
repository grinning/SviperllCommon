/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import org.sviperll.Predicates;
import org.sviperll.Predicate;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vir
 */
public class FilteredEIteratorTest {
    private static final Predicate<Integer> isEvenPredicate = new IsEvenPredicate();

    @Test
    public void testFiters() {
        Integer[] ints = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        EIterator<Integer> src = new ArrayEIterator<Integer>(ints);
        EIterator<Integer> result = src.filter(isEvenPredicate);
        List<Integer> list = new ArrayList<Integer>();
        while (result.hasNext())
            list.add(result.next());
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        assertArrayEquals(new int[] {2, 4, 6, 8, 10}, array);
    }

    private static class IsEvenPredicate implements Predicate<Integer> {
        @Override
        public boolean eval(Integer t) {
            return t.intValue() % 2 == 0;
        }

        @Override
        public Predicate<Integer> not() {
            return Predicates.not(this);
        }

        @Override
        public Predicate<Integer> and(Predicate<Integer> p) {
            return Predicates.and(this, p);
        }

        @Override
        public Predicate<Integer> or(Predicate<Integer> p) {
            return Predicates.or(this, p);
        }
    }

}