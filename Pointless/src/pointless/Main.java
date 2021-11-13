/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pointless;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.KeyStroke;

/**
 *
 * @author Nihanth
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Thread(new Server()).start();
    }
}

class Server implements Runnable {

    private static final int PORT = 13371;
    private BufferedReader mBIS;
    private float mX = 0;
    private float mY = 0;

    void print(String s) {
        System.out.println(s);
    }

    String readline() {
        try {
            return mBIS.readLine();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public void run() {
        final int fact = 100000;
        try {
            System.out.println("Server listening on port " + PORT);
            Socket socket = new ServerSocket(PORT).accept();
            System.out.println("Incoming connection accepted");
            mBIS = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Obtained remote socket input stream. Starting listener loop...");
            String s;
            HStack ys = new HStack(25);
            HStack xs = new HStack(20);
            Robot bot = new Robot();
            int scrX = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int scrY = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            while ((s = readline()) != null) {
                StringTokenizer st = new StringTokenizer(s);
                if (s.startsWith("cal")) {
                    st.nextToken();
                    mX = (int) (fact * convertX(Float.parseFloat(st.nextToken())));
                    mY = (int) (fact * Float.parseFloat(st.nextToken()));
                } else if (s.startsWith("m1dn")) {
                    bot.mousePress(InputEvent.BUTTON1_MASK);
                } else if (s.startsWith("m1up")) {
                    bot.mouseRelease(InputEvent.BUTTON1_MASK);
                } else if (s.startsWith("m2dn")) {
                    bot.mousePress(InputEvent.BUTTON2_MASK);
                } else if (s.startsWith("m2up")) {
                    bot.mouseRelease(InputEvent.BUTTON2_MASK);
                } else if (s.startsWith("m3dn")) {
                    bot.mousePress(InputEvent.BUTTON3_MASK);
                } else if (s.startsWith("m3up")) {
                    bot.mouseRelease(InputEvent.BUTTON3_MASK);
                } else if (s.startsWith("keydn")) {
                    st.nextToken();
                    String key = st.nextToken();
                    bot.keyPress(KeyStroke.getKeyStroke(key).getKeyCode());
                }  else if (s.startsWith("keyup")) {
                    st.nextToken();
                    String key = st.nextToken();
                    bot.keyRelease(KeyStroke.getKeyStroke(key).getKeyCode());
                } else {
                    float xraw = convertX(Float.parseFloat(st.nextToken()));
                    float yraw = Float.parseFloat(st.nextToken());
                    ys.push(yraw);
                    xs.push(xraw);
                    xraw = xs.wtcubeavg();
                    yraw = ys.wtcubeavg();
                    float x = (int) (fact * xraw);
                    float y = (int) (fact * yraw);
                    int mx = (int) ((float) (scrX / 2) * ((float) (1) + ((x - mX) / (float) (fact * 19))));
                    int my = (int) ((float) (scrY / 2) * ((float) (1) + (((y - mY) / (float) (fact * 19)))));
                    //System.out.println("x: " + xraw + ", y: " + yraw);
                    bot.mouseMove(mx, my);
                    //dot.setXY(mx, my);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int sign(float n) {
        if (n > 0) return 1;
        if (n == 0) return 0;
        else return -1;
    }

    float convertX(float x) {
        if (x < 180) {
            return x;
        } else {
            return x - (360);
        }
    }
}

class HStack extends ArrayList<Float> {

    private int n = 0;
    private float sum = 0;
    private int howmany = 0;

    public HStack(int howmany) {
        this.howmany = howmany;
    }

    void push(float y) {
        add(y);
        sum += y;
        if (n < howmany) {
            n++;
        } else {
            sum -= (Float) remove(0);
        }
    }

    float sum() {
        return sum;
    }

    float avg() {
        return sum / n;
    }

    float wtcubeavg() {
        Object nums[] = toArray();
        float wtsum = 0;
        for (int i = 0; i < nums.length; i++) {
            wtsum += (i + 1) * (i + 1) * (i + 1) * (Float) nums[i];
        }
        return wtsum / sigmaNCube(nums.length);
    }

    float wtsqavg() {
        Object nums[] = toArray();
        float wtsum = 0;
        for (int i = 0; i < nums.length; i++) {
            wtsum += (i + 1) * (i + 1) * (Float) nums[i];
        }
        return wtsum / sigmaNSq(nums.length);
    }

    float wtavg() {
        Object nums[] = toArray();
        float wtsum = 0;
        for (int i = 0; i < nums.length; i++) {
            wtsum += (i + 1) * (Float) nums[i];
        }
        return wtsum / sigmaN(nums.length);
    }

    int sigmaNSq(int n) {
        return (n * (n + 1) * ((2 * n) + 1)) / 6;
    }

    int sigmaN(int n) {
        return n * (n + 1) / 2;
    }

    int sigmaNCube(int n) {
        return (n * n * (n + 1) * (n + 1)) / 4;
    }
}
