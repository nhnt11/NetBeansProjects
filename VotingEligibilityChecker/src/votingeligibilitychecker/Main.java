package votingeligibilitychecker;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {
    JLabel lbl = new JLabel("Enter your age: ");
    JTextField field = new JTextField(3);
    JButton btn = new JButton("OK");
    JLabel output = new JLabel("", JLabel.CENTER);
    
    public Main() {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        JPanel p = new JPanel();
        p.add(lbl);
        p.add(field);
        p.add(btn);
        c.add(p, BorderLayout.NORTH);
        c.add(output, BorderLayout.CENTER);
        setSize(300, 90);
        setResizable(false);
        btn.addActionListener(this);
        field.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new Main();
    }
    
    public void actionPerformed(ActionEvent ae) {
        String msg = "You're too young to vote, sorry!";
        if (Integer.parseInt(field.getText()) > 17) msg = "You can vote!";
        if (Integer.parseInt(field.getText()) < 0) msg = "You're not even born yet! You think I'm a fool?";
        if (Integer.parseInt(field.getText()) > 100) msg = "You're too old to vote!";
        output.setText(msg);
    }
}
