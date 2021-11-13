/*
 * NTetris  Copyright (C) 2012  Nihanth Subramanya
 * 
 * NTetris is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * NTetris is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with NTetris.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
 
package ntetris;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Nihanth
 */
public class Square extends JComponent implements Cloneable {

    private Color color;
    private int row;
    private int col;
    protected static final Color blank = Color.BLACK;

    public Square(Color c, int col, int row) {
        color = c;
        this.row = row;
        this.col = col;
        setDoubleBuffered(true);
    }

    @Override
    public Square clone() {
        return new Square(color, col, row);
    }

    void setColor(Color c) {
        color = c;
    }

    void setRow(int row) {
        this.row = row;
    }

    void setCol(int col) {
        this.col = col;
    }

    void setPos(Point p) {
        this.col = p.y;
        this.row = p.x;
    }

    void setPos(int row, int col) {
        this.col = col;
        this.row = row;
    }

    int getCol() {
        return col;
    }

    int getRow() {
        return row;
    }

    Point getPos() {
        return new Point(row, col);
    }

    Color getColor() {
        return color;
    }

    void flash(final long when) {
        new Thread(new Runnable() {
            public void run() {
                while (System.currentTimeMillis() < when) {
                    continue;
                }
                setColor(Color.WHITE);
                repaint();
            }
        }).start();
    }

    @Override
    public void paint(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        if (color.equals(blank)) {
            g.setColor(color);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
//            for (int i = 0; i < 3; i++) {
//                Color c = color;
//                for (int j = (2 - i); j > 0; j--) {
//                    c = c.darker().darker();
//                }
//                g.setColor(c);
//                g.fillRect(i, i, getWidth() - 2 * i, getHeight() - 2 * i);
//            }
//            
//
//            g.setColor(color.brighter());
//            g.fillPolygon(new int[]{0, getWidth(), 0}, new int[]{0, 0, getHeight()}, 3);
//            g.setColor(color.darker().darker());
//            g.fillPolygon(new int[]{getWidth(), getWidth(), 0}, new int[]{0, getHeight(), getHeight()}, 3);
//            g.setColor(color);
//            g.fillRect(3, 3, getWidth() - 6, getHeight() - 6);
//
//
            g.setColor(color.darker().darker().darker());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(color);
            g.fillRect(2, 2, getWidth() - 4, getHeight() - 4);
            g.setColor(new Color(255, 255, 255, 225));
            g.drawLine(3, 3, getWidth() - 4, 3);
        }
    }
}
