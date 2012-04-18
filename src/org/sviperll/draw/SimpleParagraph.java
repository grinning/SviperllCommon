/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sviperll.draw;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Map;
import java.util.TreeMap;
import org.sviperll.draw.TextLine.TextAlignment;

/**
 *
 * @author vir
 */
public class SimpleParagraph implements Drawable {
    private final TextLine lines;
    private final ParagraphPlacement placement;
    private final Integer width;

    public SimpleParagraph(TextLine textLine, ParagraphPlacement paragraphPlacement, Integer width) {
        this.lines = textLine;
        this.placement = paragraphPlacement;
        this.width = width;
    }

    @Override
    public void draw(Graphics2D graphics, Point location) {
        Map<TextAttribute, Object> attrs = new TreeMap<TextAttribute, Object>();
        attrs.put(TextAttribute.FONT, graphics.getFont());

        AttributedString as = new AttributedString(lines.text(), attrs);
        AttributedCharacterIterator asi = as.getIterator();
        FontRenderContext frc = graphics.getFontRenderContext();

        float y = location.y;
        if (placement.alignment().equals(ParagraphPlacement.Alignment.BOTTOM)) {
            float height = 0;
            float firstLineHeight = 0;
            LineBreakMeasurer lbm = new LineBreakMeasurer(asi, frc);
            lbm.setPosition(asi.getBeginIndex());
            if (lbm.getPosition() < asi.getEndIndex()) {
                TextLayout tl = lbm.nextLayout(width);
                firstLineHeight = tl.getAscent() + tl.getDescent();
                height = firstLineHeight;
                while (lbm.getPosition() < asi.getEndIndex()) {
                    tl = lbm.nextLayout(width);
                    height += tl.getLeading() + tl.getAscent() + tl.getDescent();
                }
            }
            y = y - height + firstLineHeight;
        }

        LineBreakMeasurer lbm = new LineBreakMeasurer(asi, frc);
        lbm.setPosition(asi.getBeginIndex());
        while (lbm.getPosition() < asi.getEndIndex()) {
            TextLayout tl = lbm.nextLayout(width);
            float x = location.x;
            if (lines.alignment().equals(TextAlignment.RIGHT))
                x -= tl.getAdvance();
            float dy = 0;
            switch (placement.relativeTo()) {
                case BASELINE:
                    dy = 0;
                    break;
                case BORDER:
                    switch (placement.alignment()) {
                        case TOP:
                            dy = tl.getAscent();
                            break;
                        case BOTTOM:
                            dy = -tl.getDescent();
                            break;
                    }
                    break;
            }
            tl.draw(graphics, x, y + dy);
            y += tl.getAscent() + tl.getLeading() + tl.getDescent();
        }
    }

}
