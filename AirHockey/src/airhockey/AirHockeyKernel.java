/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package airhockey;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AirHockeyKernel extends JPanel {
    
    Puck mPuck;

    TimerTask mGameRunner = new TimerTask() {

        @Override
        public void run() {
            //handleCollisions();
            repaint();
        }
    };
    
    AirHockeyKernel() {
        setDoubleBuffered(true);
        mPuck = new Puck(new Dimension(600, 600));
        new Timer().scheduleAtFixedRate(mGameRunner, 0, 30);
    }

    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.add(new AirHockeyKernel(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void handleCollisions() {
    }

    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, 600, 600);
        Rectangle r = mPuck.getBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
    }
}
