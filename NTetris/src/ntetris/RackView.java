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
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author Nihanth
 */
public class RackView extends JPanel implements KeyListener {

    private Square[][] squares = new Square[20][10];
    private Tetromino currentOmino;
    private Tetromino nextOmino;
    private int score = 0;
    private MainApp app;
    private boolean downable = true;
    private int init_level = 0;
    private int level = init_level;
    private int newLevel = init_level;
    private int lines = 0;
    private int numBlocks = 0;
    private int scheme = 0;
    private int newScheme = scheme;
    private int[] counts = {0, 0, 0, 0, 0, 0, 0};

    public RackView(MainApp appp, int scheme) {
        this.app = appp;
        this.scheme = scheme;
        newScheme = scheme;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension((int) screenSize.getHeight() / 3, (int) screenSize.getHeight() * 2 / 3));
        setLayout(new GridLayout(20, 10));
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j] = new Square(Square.blank, i, j);
                add(squares[i][j]);
                squares[i][j].addKeyListener(this);
            }
        }
        try {
            nextOmino = new Tetromino(new Random().nextInt(7) + 1, level, scheme, this);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        requestFocus();
        spawnTetromino(false);
    }

    void setNewScheme(int scheme) {
        newScheme = scheme;
    }

    void setLevel(int level) {
        newLevel = level;
    }

    synchronized void setDownable(boolean b) {
        downable = b;
    }

    int getLines() {
        return lines;
    }

    int getLevel() {
        return level;
    }

    int getNumBlocks() {
        return numBlocks;
    }

    int getScore() {
        return score;
    }

    void spawnTetromino(boolean shouldReset) {
        level = init_level + ((lines - (lines % 10)) / 10);
        currentOmino = nextOmino.clone();
        try {
            nextOmino = new Tetromino(new Random().nextInt(7) + 1, level, scheme, this);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        counts[nextOmino.getType() - 1]++;
        app.setNextOmino(nextOmino);
        //currentOmino = new Tetromino(Tetromino.O, level, 0, this);
        Square[] s = currentOmino.getSquares();
        downable = true;
        for (int i = 0; i < s.length && currentOmino.getDownCount() == 0; i++) {
            if (!squares[0][s[i].getCol()].getColor().equals(Square.blank)) {
                downable = false;
            }
        }
        if (!downable && shouldReset) {
            reset();
        } else {
            currentOmino.start();
            numBlocks++;
            repaint();
        }
    }

    void displayCounts() {
        for (int i = 0; i < counts.length; i++) {
            System.out.println(counts[i]);
        }
    }

    void reset() {
        downable = false;
        currentOmino.stop(false, false);
        JOptionPane.showMessageDialog(this, "<html>Game Over!<br />" + "Score: " + getScore() + "<br />Lines Cleard: " + getLines() + "<br />Level: " + getLevel() + "<br />Blocks faced: " + getNumBlocks(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
        score = 0;
        init_level = app.getLevel();
        level = init_level;
        lines = 0;
        numBlocks = 0;
        scheme = newScheme;
        app.setScore(score, lines, level);
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j].setColor(Square.blank);
            }
        }
        repaint();
        currentOmino = null;
        try {
            nextOmino = new Tetromino((int) Math.rint((Math.random() * 6) + 1), level, scheme, this);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        spawnTetromino(false);
    }

    void right(Tetromino t) {
        Square[] s = t.getSquares();
        for (int i = 3; i >= 0; i--) {
            int row = s[i].getRow();
            int col = s[i].getCol() + 1;
            if (col < 10) {
                if (row > -1 && !squares[row][col].getColor().equals(Square.blank)) {
                    boolean is_itself = false;
                    for (int j = 0; j < s.length; j++) {
                        if (s[j].getCol() == col && s[j].getRow() == row) {
                            is_itself = true;
                        } else {
                            continue;
                        }
                    }
                    if (!is_itself) {
                        return;
                    }
                }
            } else {
                return;
            }
        }
        for (int i = 0; i < s.length; i++) {
            int row = s[i].getRow();
            int col = s[i].getCol() + 1;
            s[i].setCol(col);
            s[i].setRow(row);
            col--;
            if (s[i].getRow() > -1) {
                squares[row][col].setColor(Square.blank);
            }
        }
        for (int i = 0; i < s.length; i++) {
            if (s[i].getRow() > -1) {
                squares[s[i].getRow()][s[i].getCol()].setColor(s[i].getColor());
            }
        }
        repaint();
    }

    void fullDown(Tetromino t) {
        while (down(t)) {
            continue;
        }
        score += 2;
        app.setScore(score, lines, level);
    }

    void rotate(Tetromino t) {
        downable = false;
        Square[] temp = {null, null, null, null};
        Square[] ss = t.getSquares();
        for (int i = 0; i < temp.length; i++) {
            temp[i] = ss[i].clone();
        }
        switch (t.getType()) {
            case Tetromino.I: {
                t.rotate();
                Square[] s = t.getSquares();
                if (t.getRotation() % 2 != 0) {
                    right(t);
                    right(t);
                    for (int i = 0; i < s.length; i++) {
                        if (s[i].getRow() > -1) {
                            squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                        }
                    }
                    int firstRow = s[0].getRow() - 3;
                    int firstCol = s[0].getCol();
                    for (int i = 0; i < s.length; i++) {
                        s[i].setRow(firstRow++);
                        s[i].setCol(firstCol);
                    }
                } else {
                    left(t);
                    left(t);
                    for (int i = 0; i < s.length; i++) {
                        if (s[i].getRow() > -1) {
                            squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                        }
                    }
                    int firstRow = s[0].getRow() + 3;
                    int firstCol = s[0].getCol();
                    for (int i = 0; i < s.length; i++) {
                        s[i].setRow(firstRow);
                        s[i].setCol(firstCol++);
                    }
                }
                break;
            }
            case Tetromino.O: {
                t.rotate();
                break;
            }
            case Tetromino.L: {
                t.rotate();
                switch (t.getRotation()) {
                    case 0: {
                        left(t);
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        int firstRow = s[0].getRow();
                        int firstCol = s[0].getCol();
                        s[1].setPos(firstRow, firstCol + 1);
                        s[2].setPos(s[1].getRow() + 1, s[1].getCol());
                        s[3].setPos(s[2].getRow() + 1, s[2].getCol());
                        while (s[3].getRow() > 19) {
                            for (int i = 0; i < s.length; i++) {
                                s[i].setRow(s[i].getRow() - 1);
                            }
                        }
                        break;
                    }
                    case 3: {
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        int firstRow = s[0].getRow();
                        int firstCol = s[0].getCol() - 1;
                        s[0].setPos(firstRow, firstCol);
                        firstRow--;
                        for (int i = 1; i < 4; i++) {
                            s[i].setPos(firstRow, firstCol++);
                        }
                        break;
                    }
                    case 2: {
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        int firstRow = s[0].getRow() + 1;
                        int firstCol = s[0].getCol();
                        s[0].setPos(firstRow, firstCol);
                        s[1].setPos(firstRow, firstCol - 1);
                        s[2].setPos(s[1].getRow() - 1, s[1].getCol());
                        s[3].setPos(s[2].getRow() - 1, s[2].getCol());
                        break;
                    }
                    case 1: {
                        right(t);
                        right(t);
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        int firstRow = s[0].getRow();
                        int firstCol = s[0].getCol();
                        s[0].setPos(firstRow, firstCol);
                        s[1].setPos(firstRow + 1, firstCol);
                        s[2].setPos(s[1].getRow(), s[1].getCol() - 1);
                        s[3].setPos(s[2].getRow(), s[2].getCol() - 1);
                        break;
                    }
                }
                break;
            }
            case Tetromino.J: {
                t.rotate();
                switch (t.getRotation()) {
                    case 2: {
                        right(t);
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        int firstRow = s[0].getRow();
                        int firstCol = s[0].getCol();
                        s[0].setRow(firstRow);
                        s[1].setPos(firstRow, firstCol - 1);
                        s[2].setPos(s[1].getRow() + 1, s[1].getCol());
                        s[3].setPos(s[2].getRow() + 1, s[2].getCol());
                        while (s[3].getRow() > 19) {
                            for (int i = 0; i < s.length; i++) {
                                s[i].setRow(s[i].getRow() - 1);
                            }
                        }
                        break;
                    }
                    case 1: {
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        int firstRow = s[0].getRow();
                        int firstCol = s[0].getCol() - 1;
                        s[0].setPos(firstRow, firstCol);
                        firstRow++;
                        for (int i = 1; i < 4; i++) {
                            s[i].setPos(firstRow, firstCol++);
                        }
                        break;
                    }
                    case 0: {
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        int firstRow = s[0].getRow();
                        int firstCol = s[0].getCol();
                        s[0].setPos(firstRow, firstCol);
                        s[1].setPos(firstRow + 1, firstCol);
                        s[2].setPos(s[1].getRow() + 1, s[1].getCol());
                        s[3].setPos(s[2].getRow(), s[2].getCol() - 1);
                        break;
                    }
                    case 3: {
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        int firstRow = s[0].getRow() + 1;
                        int firstCol = s[0].getCol();
                        s[0].setPos(firstRow, firstCol);
                        s[1].setPos(firstRow - 1, firstCol);
                        s[2].setPos(s[1].getRow(), s[1].getCol() - 1);
                        s[3].setPos(s[2].getRow(), s[2].getCol() - 1);
                        break;
                    }
                }
                break;
            }
            case Tetromino.Z: {
                t.rotate();
                if (t.getRotation() % 2 == 0) {
                    left(t);
                    down(t);
                    Square[] s = t.getSquares();
                    for (int i = 0; i < s.length; i++) {
                        if (s[i].getRow() > -1) {
                            squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                        }
                    }
                    int firstRow = s[0].getRow();
                    int firstCol = s[0].getCol();
                    s[1].setPos(firstRow, firstCol + 1);
                    s[2].setPos(s[1].getRow() + 1, s[1].getCol());
                    s[3].setPos(s[2].getRow(), s[2].getCol() + 1);
                } else {
                    right(t);
                    Square[] s = t.getSquares();
                    for (int i = 0; i < s.length; i++) {
                        if (s[i].getRow() > -1) {
                            squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                        }
                    }
                    int firstRow = s[0].getRow();
                    int firstCol = s[0].getCol();
                    s[0].setPos(firstRow, firstCol);
                    s[1].setPos(s[0].getRow() + 1, s[0].getCol());
                    s[2].setPos(s[0].getRow() + 1, s[0].getCol() - 1);
                    s[3].setPos(s[0].getRow() + 2, s[0].getCol() - 1);
                }
                break;
            }
            case Tetromino.S: {
                t.rotate();
                if (t.getRotation() % 2 == 0) {
                    down(t);
                    Square[] s = t.getSquares();
                    for (int i = 0; i < s.length; i++) {
                        if (s[i].getRow() > -1) {
                            squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                        }
                    }
                    int firstRow = s[0].getRow();
                    int firstCol = s[0].getCol() + 1;
                    s[0].setPos(firstRow, firstCol);
                    s[1].setPos(s[0].getRow(), s[0].getCol() - 1);
                    s[2].setPos(s[1].getRow() + 1, s[1].getCol());
                    s[3].setPos(s[2].getRow(), s[2].getCol() - 1);
                } else {
                    left(t);
                    Square[] s = t.getSquares();
                    for (int i = 0; i < s.length; i++) {
                        if (s[i].getRow() > -1) {
                            squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                        }
                    }
                    int firstRow = s[0].getRow();
                    int firstCol = s[0].getCol();
                    s[0].setPos(firstRow, firstCol);
                    s[1].setPos(s[0].getRow() + 1, s[0].getCol());
                    s[2].setPos(s[1].getRow(), s[1].getCol() + 1);
                    s[3].setPos(s[2].getRow() + 1, s[2].getCol());
                }
                break;
            }
            case Tetromino.T: {
                t.rotate();
                switch (t.getRotation()) {
                    case 0: {
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        s[1].setPos(s[0].getRow() + 1, s[0].getCol() - 1);
                        s[2].setPos(s[0].getRow() + 1, s[0].getCol());
                        s[3].setPos(s[0].getRow() + 1, s[0].getCol() + 1);
                        break;
                    }
                    case 1: {
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        s[1].setPos(s[0].getRow() - 1, s[0].getCol() - 1);
                        s[2].setPos(s[0].getRow(), s[0].getCol() - 1);
                        s[3].setPos(s[0].getRow() + 1, s[0].getCol() - 1);
                        break;
                    }
                    case 2: {
                        down(t);
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        s[1].setPos(s[0].getRow() - 1, s[0].getCol() - 1);
                        s[2].setPos(s[0].getRow() - 1, s[0].getCol());
                        s[3].setPos(s[0].getRow() - 1, s[0].getCol() + 1);
                        break;
                    }
                    case 3: {
                        down(t);
                        Square[] s = t.getSquares();
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].getRow() > -1) {
                                squares[s[i].getRow()][s[i].getCol()].setColor(Square.blank);
                            }
                        }
                        //s[0].setRow(s[0].getRow() - 1);
                        s[1].setPos(s[0].getRow() - 1, s[0].getCol() + 1);
                        s[2].setPos(s[0].getRow(), s[0].getCol() + 1);
                        s[3].setPos(s[0].getRow() + 1, s[0].getCol() + 1);
                        break;
                    }
                }
            }
        }
        Square[] s = t.getSquares();
        int lowestCol = 10;
        int highestCol = -1;
        for (int i = 0; i < s.length; i++) {
            if (s[i].getCol() < lowestCol) {
                lowestCol = s[i].getCol();
            }
        }
        for (int i = 0; i < s.length; i++) {
            if (s[i].getCol() > highestCol) {
                highestCol = s[i].getCol();
            }
        }
        int numLeft = highestCol - 9;
        int numRight = -lowestCol;
        for (int i = 0; i < numLeft; i++) {
            for (int j = 0; j < s.length; j++) {
                s[j].setCol(s[j].getCol() - 1);
            }
        }
        for (int i = 0; i < numRight; i++) {
            for (int j = 0; j < s.length; j++) {
                s[j].setCol(s[j].getCol() + 1);
            }
        }
        for (int i = 0; i < s.length; i++) {
            if (s[i].getRow() > -1 && !squares[s[i].getRow()][s[i].getCol()].getColor().equals(Square.blank)) {
                s = temp;
                t.setSquares(temp);
            }
        }
        for (int i = 0; i < s.length; i++) {
            if (s[i].getRow() > -1) {
                squares[s[i].getRow()][s[i].getCol()].setColor(s[i].getColor());
            }
        }
        downable = true;
    }

    void left(Tetromino t) {
        Square[] s = t.getSquares();
        for (int i = 3; i >=
                0; i--) {
            int row = s[i].getRow();
            int col = s[i].getCol() - 1;
            if (col >= 0) {
                if (s[i].getRow() > -1 && !squares[row][col].getColor().equals(Square.blank)) {
                    boolean is_itself = false;
                    for (int j = 0; j <
                            s.length; j++) {
                        if (s[j].getCol() == col && s[j].getRow() == row) {
                            is_itself = true;
                        } else {
                            continue;
                        }

                    }
                    if (!is_itself) {
                        return;
                    }

                }
            } else {
                return;
            }

        }
        for (int i = 0; i < s.length; i++) {
            int row = s[i].getRow();
            int col = s[i].getCol() - 1;
            s[i].setCol(col);
            s[i].setRow(row);
            col++;
            if (s[i].getRow() > -1) {
                squares[row][col].setColor(Square.blank);
            }
        }

        for (int i = 0; i <
                s.length; i++) {
            if (s[i].getRow() > -1) {
                squares[s[i].getRow()][s[i].getCol()].setColor(s[i].getColor());
            }
        }

        repaint();
    }

    void finishLines() {
        setDownable(false);
        Vector finishedLines = new Vector();
        outer:
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if (squares[i][j].getColor().equals(Square.blank)) {
                    continue outer;
                }
            }
            finishedLines.add(new Integer(i));
        }
        if (finishedLines.size() == 0) {
            return;
        }
        Iterator it = finishedLines.iterator();
        final long time = System.currentTimeMillis() + 200;
        while (it.hasNext()) {
            int row = ((Integer) it.next()).intValue();
            for (int i = 0; i < squares[row].length; i++) {
                squares[row][i].flash(time);
            }
        }
        it = finishedLines.iterator();
        while (System.currentTimeMillis() < time + 400) {
            continue;
        }
        while (it.hasNext()) {
            int row = ((Integer) it.next()).intValue();
            for (int i = 0; i < squares[row].length; i++) {
                for (int j = row; j > 0; j--) {
                    squares[j][i].setColor(squares[j - 1][i].getColor());
                }
            }
        }
        int n = finishedLines.size();
        int base_score = 0;
        switch (n) {
            case 1: {
                base_score = 40;
                break;
            }
            case 2: {
                base_score = 100;
                break;
            }
            case 3: {
                base_score = 300;
            }
            case 4: {
                base_score = 1200;
            }
        }
        score += (level * base_score) + base_score;
        lines += n;
        app.setScore(score, lines, level);
    }

    boolean down(Tetromino t) {
        synchronized (this) {
            if (!downable) {
                return true;
            }
        }
        Square[] s = t.getSquares();
        for (int i = 3; i >=
                0; i--) {
            int row = s[i].getRow() + 1;
            int col = s[i].getCol();
            if (row < 20) {
                if (s[i].getRow() > -1 && !squares[row][col].getColor().equals(Square.blank)) {
                    boolean is_itself = false;
                    for (int j = 0; j <
                            s.length; j++) {
                        if (s[j].getCol() == col && s[j].getRow() == row) {
                            is_itself = true;
                        } else {
                            continue;
                        }

                    }
                    if (!is_itself) {
                        t.stop(true, true);
                        score += 3;
                        app.setScore(score, lines, level);
                        return false;
                    }

                }
            } else {
                t.stop(true, true);
                score += 3;
                app.setScore(score, lines, level);
                return false;
            }

        }
        for (int i = 3; i >= 0; i--) {
            int row = s[i].getRow();
            int col = s[i].getCol();
            if (s[i].getRow() > -1) {
                squares[row][col].setColor(Square.blank);
            }
            row++;
            s[i].setCol(col);
            s[i].setRow(row);
        }

        for (int i = 0; i <
                s.length; i++) {
            if (s[i].getRow() > -1) {
                squares[s[i].getRow()][s[i].getCol()].setColor(s[i].getColor());
            }
        }
        repaint();
        t.downCountUp();
        return true;
    }

    public void keyTyped(KeyEvent e) {
        return;
    }

    Tetromino getCurrentOmino() {
        return currentOmino;
    }
    boolean keypressed = false;
    boolean keypressedu = false;
    boolean keypressedd = false;
    boolean keypressedr = false;
    boolean keypressedl = false;

    @Override
    public synchronized void keyPressed(KeyEvent ke) {
        if (keypressed) {
            return;
        }
        keypressed = true;
        if (currentOmino == null) {
            keypressed = false;
            keypressedu = false;
            keypressedd = false;
            keypressedr = false;
            keypressedl = false;
            return;
        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            keypressedr = true;
            new Thread(new Runnable() {

                public void run() {
                    synchronized (this) {
                        while (keypressedr) {
                            right(currentOmino);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                }
            }).start();
            repaint();
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            keypressedl = true;
            new Thread(new Runnable() {

                public void run() {
                    synchronized (this) {
                        while (keypressedl) {
                            left(currentOmino);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                }
            }).start();
            repaint();
        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            keypressedd = true;
            new Thread(new Runnable() {

                public void run() {
                    synchronized (this) {
                        while (keypressedd) {
                            down(currentOmino);
                            try {
                                Thread.sleep(75);
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                }
            }).start();
            repaint();
        } else if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            keypressed = false;
            keypressedu = false;
            keypressedd = false;
            keypressedr = false;
            keypressedl = false;
            fullDown(currentOmino);
            repaint();
        } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
            keypressedu = true;
            new Thread(new Runnable() {

                public void run() {
                    synchronized (this) {
                        while (keypressedu) {
                            rotate(currentOmino);
                            repaint();
                            try {
                                Thread.sleep(150);
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                }
            }).start();
        } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            keypressed = false;
            keypressedu = false;
            keypressedd = false;
            keypressedr = false;
            keypressedl = false;
            downable = false;
            JOptionPane.showMessageDialog(this, "Game paused. Press OK to continue.", "Paused", JOptionPane.INFORMATION_MESSAGE);
            downable = true;
        } else if (ke.getKeyCode() == KeyEvent.VK_O) {
            keypressed = false;
            keypressedu = false;
            keypressedd = false;
            keypressedr = false;
            keypressedl = false;
            app.showOptions();
        }
    }

    public synchronized void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                keypressedu = false;
                break;
            case KeyEvent.VK_DOWN:
                keypressedd = false;
                break;
            case KeyEvent.VK_RIGHT:
                keypressedr = false;
                break;
            case KeyEvent.VK_LEFT:
                keypressedl = false;
                break;
            default:
                break;
        }
        if (!(keypressedu || keypressedl || keypressedr || keypressedu)) {
            keypressed = false;
        }
    }
}
