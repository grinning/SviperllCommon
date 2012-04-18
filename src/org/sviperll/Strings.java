/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sviperll;

/**
 *
 * @author vir
 */
public class Strings {
    /**
     * Same as escape, but encloses string in double-quotes
     * Ex. "abc\ndef" is converted into "\"abc\\ndef\""
     */
    public static String quote(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append('"');
        sb.append(escape(s));
        sb.append('"');
        return sb.toString();
    }
    /**
     * Escapes special characters in string
     * Ex. "abc\ndef" is converted into "abc\\ndef"
     */
    public static String escape(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c: s.toCharArray()) {
            if (c == '"')
                sb.append("\\\"");
            else if (c == '\n')
                sb.append("\\\n");
            else if (c == '\r')
                sb.append("\\\r");
            else
                sb.append(c);
        }
        return sb.toString();
    }

    private Strings() {
    }
}
