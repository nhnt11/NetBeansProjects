package NPaste;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Nihanth
 */
public class GUI extends JFrame {

    NRobot bot = new NRobot();
    JLabel textLbl = new JLabel("Enter text here: ");
    JTextArea textArea = new JTextArea();
    JLabel delayLbl = new JLabel("Delay between characters (in milliseconds): ");
    JTextField delayField = new JTextField();
    JLabel waitLbl = new JLabel("Time to wait before starting (in milliseconds): ");
    JTextField waitField = new JTextField();
    JButton startButton = new JButton("Start");

    public GUI() {
        Container c = getContentPane();
        c.setLayout(new GridLayout(7, 1));
        c.add(textLbl);
        c.add(textArea);
        c.add(delayLbl);
        c.add(delayField);
        c.add(waitLbl);
        c.add(waitField);
        c.add(startButton);
        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                int wait;
                int delay;
                try {
                    delay = Integer.parseInt(delayField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Invalid Delay");
                    return;
                }
                try {
                    wait = Integer.parseInt(waitField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Invalid Wait Time");
                    return;
                }
                String text = textArea.getText();
                startButton.setEnabled(false);
                textArea.setEnabled(false);
                delayField.setEnabled(false);
                waitField.setEnabled(false);
                try {
                     Thread.sleep(wait);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Interrupted while waiting, try again.");
                }
                bot.typeString(text, delay);
                startButton.setEnabled(true);
                textArea.setEnabled(true);
                delayField.setEnabled(true);
                waitField.setEnabled(true);

            }
        });
        setSize(500, 325);
        if (!System.getProperty("os.name").startsWith("Mac")) setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("NPaste");
        setVisible(true);
    }

    public static void main(String[] args) {
        // TODO code application logic here
        new GUI();
    }
}
