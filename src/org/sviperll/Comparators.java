/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll;

import java.util.Comparator;
import org.sviperll.CompareException.Greater;
import org.sviperll.CompareException.Less;

/**
 *
 * @author vir
 */
public class Comparators {
    public static <T> void compare(Comparator<T> cmp, T m, T n) throws Less, Greater {
        int res = cmp.compare(m, n);
        if (res < 0)
            throw new Less();
        else if (res > 0)
            throw new Greater();
    }

    private Comparators() {
    }
}
