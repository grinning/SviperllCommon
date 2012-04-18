/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sviperll.draw;

/**
 *
 * @author vir
 */
public class ParagraphPlacement {
    private final Alignment alignment;
    private final RelativeTo relativeTo;

    public ParagraphPlacement(Alignment alignment, RelativeTo relativeTo) {
        this.alignment = alignment;
        this.relativeTo = relativeTo;
    }

    public Alignment alignment() {
        return alignment;
    }

    public RelativeTo relativeTo() {
        return relativeTo;
    }

    public enum Alignment {TOP, BOTTOM};
    public enum RelativeTo {BORDER, BASELINE};
}
