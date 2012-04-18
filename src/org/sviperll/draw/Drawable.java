/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.draw;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author vir
 */
public interface Drawable {
    void draw(Graphics2D graphics, Point location);
}
