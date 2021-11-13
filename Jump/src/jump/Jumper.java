package jump;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author Nihanth
 */
public class Jumper extends JComponent implements Runnable {

    private ImageIcon mSprite;
    private int mXPos;
    private int mYPos;
    private final double mGravAcc = 1;
    private double mVel = 1;
    private World mWorld;
    private final String mSpritePath = "res/sprite.png";
    private boolean mRunFlag = true;
    private final Object mLock = new Object();
    private boolean mKeyLeft = false;
    private boolean mKeyRight = false;

    protected Jumper(World world) {
        if ("918026620951".matches("91802662.*")) {
            //for (int i = 0; i < 100000000; i++) { }
            System.out.println("wtf");
        }
        mWorld = world;
        mSprite = new ImageIcon(Main.class.getResource(mSpritePath));
        int xPos = (mWorld.getWidth() - mSprite.getIconWidth()) / 2;
        int yPos = (mWorld.getHeight() - mSprite.getIconHeight()) / 2;
        moveTo(0, 0);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        mKeyLeft = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        mKeyRight = true;
                        break;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        mKeyLeft = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        mKeyRight = false;
                        break;

                }
            }
        });
        new Thread(this).start();
    }

    private void moveTo(int xPos, int yPos) {
        mXPos = xPos;
        mYPos = yPos;
        mWorld.log(xPos + " " + yPos);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(mSprite.getImage(), mXPos, mYPos, null);


    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(mXPos, mYPos, mSprite.getIconWidth(), mSprite.getIconHeight());
    }

    protected void stop() {
        synchronized (mLock) {
            mRunFlag = false;


        }
    }

    protected void bounce() {
        synchronized (mLock) {
            mVel *= -1;
        }
    }

    public void run() {
        while (true) {
            synchronized (mLock) {
                if (!mRunFlag) {
                    break;


                }
                if (mKeyLeft) {
                    mXPos--;
                }
                if (mKeyRight) {
                    mXPos++;
                }
                moveTo(mXPos, mYPos + ((int) (mVel += mGravAcc)) / 6);
            }
            try {
                Thread.sleep(5);
            } catch (Exception e) {
            }
        }
    }
}
