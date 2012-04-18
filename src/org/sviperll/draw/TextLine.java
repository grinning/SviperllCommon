/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sviperll.draw;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;

/**
 *
 * @author vir
 */
public class TextLine implements Drawable {
    private final String text;
    private final TextAlignment alignment;

    public TextLine(String text, TextAlignment alignment) {
        this.text = text;
        this.alignment = alignment;
    }

    public TextLine(String text) {
        this(text, TextAlignment.LEFT);
    }

    public TextAlignment alignment() {
        return alignment;
    }

    public String text() {
        return text;
    }

    @Override
    public void draw(Graphics2D graphics, Point location) {
        TextLayout tl = new TextLayout(text, graphics.getFont(), graphics.getFontRenderContext());
        float x = location.x;
        if (alignment.equals(TextAlignment.RIGHT))
            x -= tl.getAdvance();
        tl.draw(graphics, x, location.y);
    }

    public enum TextAlignment {LEFT, RIGHT};

}
