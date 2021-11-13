/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package airhockey;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

public class Puck {

    private int x = 50;
    private int y = 0;
    private int xVel = 1;
    private int yVel = 2;
    private int width = 50;
    private int height = 50;
    private Dimension boundary;
    final Object mLock = new Object();

    Puck(Dimension boundary) {
        this.boundary = boundary;
        start();
    }
    TimerTask mRunner = new TimerTask() {

        @Override
        public void run() {
            synchronized (mLock) {
                x += xVel;
                y += yVel;
                if (x > boundary.getWidth() - width || x < 0) {
                    xVel = -xVel;
                    x += 2*xVel;
                }
                if (y > boundary.getHeight() - height || y < 0) {
                    yVel = -yVel;
                    y += 2*yVel;
                }
            }
        }
    };

    void start() {
        new Timer().scheduleAtFixedRate(mRunner, 0, 20);
    }

    void setVel(int xVel, int yVel) {
        synchronized (mLock) {
            this.xVel = xVel;
            this.yVel = yVel;
        }
    }

    void setXVel(int xVel) {
        synchronized (mLock) {
            this.xVel = xVel;
        }
    }

    void setYVel(int yVel) {
        synchronized (mLock) {
            this.yVel = yVel;
        }
    }

    int getXVel() {
        return xVel;
    }

    int getYVel() {
        return yVel;
    }

    Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
