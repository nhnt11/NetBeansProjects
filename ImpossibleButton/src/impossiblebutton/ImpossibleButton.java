package impossiblebutton;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Nihanth
 */
public class ImpossibleButton extends JButton {

    JFrame frame;
    int count = 0;

    public ImpossibleButton(JFrame fframe) {
        super();
        frame = fframe;
        setFocusPainted(false);
        setText("Click me!");
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                count++;
                int n = 0;
                while (getMousePosition() != null && n < 10) {
                    setLocation((int) (Math.random() * (frame.getContentPane().getWidth() - getWidth())), (int) (Math.random() * (frame.getContentPane().getHeight() - getHeight())));
                    if (getLocation().getX() < 0) {
                        setLocation(0, (int) getLocation().getY());
                    }
                    if (getLocation().getY() < 0) {
                        setLocation((int) getLocation().getX(), 0);
                    }
                    n++;
                }
                String msg = null;
                if (count == 10) {
                    msg = "Keep trying!";
                }
                if (count == 25) {
                    msg = "Having fun?";
                }
                if (count == 50) {
                    msg = "You'll never catch me!";
                }
                if (count == 100) {
                    msg = "Quit ruining your mouse!";
                }
                if (count == 200) {
                    msg = "Wow you're persistent!";
                }
                if (count == 500) {
                    msg = "Give it up all ready!";
                }
                if (count == 1000) {
                    msg = "Congrats! You have just wasted at least five minute or so of your life!!";
                }
                if (msg != null) {
                    JOptionPane.showMessageDialog(frame, msg);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        ImpossibleButton btn = new ImpossibleButton(frame);
        frame.getContentPane().setPreferredSize(new Dimension(600, 600));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(btn);
        frame.pack();
        frame.setVisible(true);
        btn.setLocation(frame.getWidth() / 2, frame.getHeight() / 2);
    }
}
