package jump;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Nihanth
 */
public class World extends JPanel {

    private boolean mDebug = true;

    public World() {
        setLayout(null);
        final Jumper j = new Jumper(this);
        j.setBounds(0, 0, 500, 500);
        Platform p = new Platform(j);
        j.setOpaque(false);
        p.setBounds(0, 500, 500, 600);
        p.setOpaque(false);
        add(p);
        add(j);
    }

    protected final void log(String msg) {
        if (mDebug) {
            System.out.println(msg);
        }
    }
}

class Platform extends JComponent implements Runnable {

    Jumper j;

    Platform(Jumper j) {
        this.j = j;
        new Thread(this).start();
    }

    public void run() {
        while (true) {
            if (j.getBounds().intersects(getBounds())) {
                j.bounce();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
