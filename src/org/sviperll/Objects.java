package org.sviperll;

/**
 *
 * @author vir
 */
public class Objects {
    public static String toString(String name, Object... fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("new ");
        sb.append(name);
        sb.append("(");
        if (fields.length > 0) {
            if (fields[0] instanceof String)
                sb.append(Strings.quote((String)fields[0]));
            else
                sb.append(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                sb.append(", ");
                if (fields[0] instanceof String)
                    sb.append(Strings.quote((String)fields[i]));
                else
                    sb.append(fields[i]);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static int hashCode(Object... fields) {
        int hash = 7;
        for (int i = 0; i < fields.length; i++)
            hash = 73 * hash + fields[0].hashCode();
        return hash;
    }

    public static boolean equals(int a, int b) {
        return a == b;
    }

    public static boolean equals(long a, long b) {
        return a == b;
    }

    public static boolean equals(byte a, byte b) {
        return a == b;
    }

    public static boolean equals(char a, char b) {
        return a == b;
    }

    public static boolean equals(float a, float b) {
        return a == b;
    }

    public static boolean equals(double a, double b) {
        return a == b;
    }

    public static <T> boolean equals(T a, T b) {
        if (a == null)
            return b == null;
        else
            return b != null && a.equals(b);
    }

    private Objects() {
    }
}
