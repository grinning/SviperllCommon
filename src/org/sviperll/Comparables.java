package org.sviperll;

import org.sviperll.CompareException.Greater;
import org.sviperll.CompareException.Less;

/**
 *
 * @author vir
 */
public class Comparables {

    public static void compareTo(int i, int j) throws Less, Greater {
        if (i < j)
            throw new Less();
        else if (i > j)
            throw new Greater();
    }

    public static void compareTo(long i, long j) throws Less, Greater {
        if (i < j)
            throw new Less();
        else if (i > j)
            throw new Greater();
    }

    public static void compareTo(float i, float j) throws Less, Greater {
        if (i < j)
            throw new Less();
        else if (i > j)
            throw new Greater();
    }

    public static void compareTo(double i, double j) throws Less, Greater {
        if (i < j)
            throw new Less();
        else if (i > j)
            throw new Greater();
    }

    public static void compareTo(char i, char j) throws Less, Greater {
        if (i < j)
            throw new Less();
        else if (i > j)
            throw new Greater();
    }

    public static void compareTo(byte i, byte j) throws Less, Greater {
        if (i < j)
            throw new Less();
        else if (i > j)
            throw new Greater();
    }

    public static <T extends Comparable<? super T>> void compareTo(T m, T n) throws Less, Greater {
        int res = m.compareTo(n);
        if (res < 0)
            throw new Less();
        else if (res > 0)
            throw new Greater();
    }

    private Comparables() {
    }
}
