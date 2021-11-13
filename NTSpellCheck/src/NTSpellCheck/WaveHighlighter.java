/*
 * NTSpellCheck  Copyright (C) 2012  Nihanth Subramanya
 * 
 * NTSpellCheck is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * NTSpellCheck is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with NTSpellCheck. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
 
package NTSpellCheck;

import java.awt.*;
import javax.swing.text.*;

class WaveHighlighter extends DefaultHighlighter.DefaultHighlightPainter {

    boolean wave = true;
    Color color = Color.red;

    public WaveHighlighter(Color color, int type) {
        super(color);
        this.color = color;
        wave = (type == NTSpellCheck.WAVE_UNDERLINE);
    }

    void setColor(Color newColor) {
        color = newColor;
    }

    @Override
    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
        try {
            if (color == null) {
                g.setColor(Color.red);
            } else {
                g.setColor(color);
            }

            Shape shape = view.modelToView(offs0, Position.Bias.Forward, offs1, Position.Bias.Backward, bounds);
            int y = (int) (shape.getBounds().getY() + shape.getBounds().getHeight()) - 2;
            int x1 = (int) shape.getBounds().getX();
            int x2 = (int) (shape.getBounds().getX() + shape.getBounds().getWidth()) - 2;
            Rectangle r = (shape instanceof Rectangle) ? (Rectangle) shape : shape.getBounds();
            g.setColor(Color.red);
            for (int i = x1; i <= x2; i++) {
                g.drawLine(i, y, i, y);
                if (++i <= x2) {
                    g.drawLine(i, y, i, y);
                }
                if (++i <= x2) {
                    g.drawLine(i, y + 1, i, y + 1);
                }
                if (++i <= x2) {
                    g.drawLine(i, y + 1, i, y + 1);
                }
            }
            return r;
        } catch (BadLocationException ex) {
            return null;
        }
    }
}