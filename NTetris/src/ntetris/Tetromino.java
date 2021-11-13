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
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Nihanth
 */
public class Tetromino implements ActionListener, Cloneable {

    private Square[] squares;
    private Color c;
    public static final int I = 1;
    public static final int J = 2;
    public static final int L = 3;
    public static final int O = 4;
    public static final int S = 5;
    public static final int T = 6;
    public static final int Z = 7;
    private int type;
    private int rotation = 0;
    private RackView view;
    private Timer timer;
    private int down_count = 0;
    private int level = 0;
    private int delay = 1000;
    private int scheme = 0;
    private Color[][] schemes = {
        {Color.CYAN, Color.BLUE, Color.ORANGE, Color.YELLOW, Color.GREEN, new Color(170, 0, 255), Color.RED},
        {Color.RED, Color.ORANGE, Color.MAGENTA, Color.BLUE, Color.GREEN, new Color(85, 107, 47), Color.CYAN},
        {Color.RED, Color.YELLOW, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.ORANGE},
        {Color.CYAN, new Color(50, 0, 255), Color.MAGENTA, Color.LIGHT_GRAY, Color.GREEN, Color.YELLOW, Color.RED},
        {Color.RED, Color.BLUE, Color.ORANGE, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.GREEN},
        {Color.RED, Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.GREEN, Color.LIGHT_GRAY, Color.BLUE},
        {Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.GREEN, Color.LIGHT_GRAY, Color.RED},
        {new Color(170, 0, 0), new Color(170, 170, 170), Color.MAGENTA, new Color(0, 0, 170), new Color(0, 170, 0), new Color(170, 85, 0), new Color(0, 170, 170)},};

    public Tetromino(int type, int level, int scheme, RackView view) throws Exception {
        this.view = view;
        this.type = type;
        this.level = level;
        delay -= (level * 50);
        if (delay < 1) {
            delay = 1;
        }
        timer = new Timer(delay, this);
        timer.setInitialDelay(0);
        timer.setRepeats(true);
        c = schemes[scheme][type - 1];
        this.scheme = scheme;
        switch (type) {
            case I: {
                squares = new Square[]{new Square(c, 3, -1), new Square(c, 4, -1), new Square(c, 5, -1), new Square(c, 6, -1)};
                break;
            }
            case L: {
                squares = new Square[]{new Square(c, 6, -2), new Square(c, 6, -1), new Square(c, 5, -1), new Square(c, 4, -1)};
                rotate();
                break;
            }
            case J: {
                squares = new Square[]{new Square(c, 4, -2), new Square(c, 4, -1), new Square(c, 5, -1), new Square(c, 6, -1)};
                rotate();
                break;
            }
            case Z: {
                squares = new Square[]{new Square(c, 4, -2), new Square(c, 5, -2), new Square(c, 5, -1), new Square(c, 6, -1)};
                break;
            }
            case S: {
                squares = new Square[]{new Square(c, 6, -2), new Square(c, 5, -2), new Square(c, 5, -1), new Square(c, 4, -1)};
                break;
            }
            case O: {
                squares = new Square[]{new Square(c, 5, -2), new Square(c, 4, -2), new Square(c, 4, -1), new Square(c, 5, -1)};
                break;
            }
            case T: {
                squares = new Square[]{new Square(c, 5, -2), new Square(c, 4, -1), new Square(c, 5, -1), new Square(c, 6, -1)};
                break;
            }
            default: {
                throw new Exception("TYPE OUT OF BOUNDS: " + type);
            }
        }
    }

    @Override
    public Tetromino clone() {
        try {
            return new Tetromino(getType(), level, scheme, view);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    void start() {
        timer.start();
    }

    void downCountUp() {
        down_count++;
    }

    int getDownCount() {
        return down_count;
    }

    void rotate() {
        rotation = rotation == 3 ? 0 : rotation + 1;
    }

    int getType() {
        return type;
    }

    int getRotation() {
        return rotation;
    }

    void stop(boolean shouldSpawn, boolean shouldReset) {
        timer.stop();
        if (shouldSpawn) {
            view.finishLines();
        }
        if (shouldSpawn) {
            view.spawnTetromino(shouldReset);
        }
    }

    void setSquares(Square[] s) {
        squares = s;
    }

    public void actionPerformed(ActionEvent ae) {
        if (view.getCurrentOmino() != this) {
            return;
        }
        view.down(this);
    }

    Square[] getSquares() {
        return squares;
    }
}
