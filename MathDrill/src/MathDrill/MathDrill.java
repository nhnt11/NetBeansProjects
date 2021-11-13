package MathDrill;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/*
 * @version v0.3
 * @author 2007 by Nihanth S.
 * Made specially for Chinpin. 
 */

public class MathDrill extends JFrame implements ActionListener {
    private JButton start = new JButton("          Start          ");
    private JLabel num1 = new JLabel("", JLabel.CENTER);
    private JLabel num2 = new JLabel("", JLabel.CENTER);
    private JLabel operator = new JLabel(" + ", JLabel.CENTER);
    private JTextField ansTF = new JTextField(2);
    private JButton next = new JButton("Next");
    private int ans;
    private int probindex = 0;
    private int score = 0;
    private JLabel scoreLbl = new JLabel("", JLabel.CENTER);
    private int secs = 0;
    private boolean startFlag = false;
    private Thread timer = new Thread(new Runnable() {
        public void run() {
            while (startFlag) {
                try {
                    timerLbl.setText(String.valueOf(secs++) + " seconds");
                    Thread.sleep(1000);
                } catch (InterruptedException e) { }
            }
        }
    });
    private JLabel timerLbl = new JLabel(String.valueOf(secs) + " seconds");
    
    public MathDrill() {
        setLayout(new FlowLayout());
        ansTF.addActionListener(this);
        ansTF.setHorizontalAlignment(JTextField.RIGHT);
        ansTF.setDocument(new NumberDocument());
        next.addActionListener(this);
        start.addActionListener(this);
        add(start);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((d.width - 510) / 2, (d.height - 200) / 2, 510, 100);
        num1.setFont(new Font("SansSerif", Font.BOLD, 32));
        num2.setFont(new Font("SansSerif", Font.BOLD, 32));
        operator.setFont(new Font("SansSerif", Font.BOLD, 32));
        start.setFont(new Font("SansSerif", Font.BOLD, 32));
        next.setFont(new Font("SansSerif", Font.BOLD, 32));
        ansTF.setFont(new Font("SansSerif", Font.BOLD, 32));
        timerLbl.setFont(new Font("SansSerif", Font.BOLD, 32));
        scoreLbl.setFont(new Font("SansSerif", Font.BOLD, 32));
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setTitle("MathDrill - Press Start");
        setVisible(true);
    }

    private void nextProblem() {
        if (probindex == 10) {
            showScore();
        }
        else {
            ansTF.setText("");
            ansTF.requestFocus();
            num1.setText(String.valueOf((int)Math.rint(Math.random() * 10)));
            num2.setText(String.valueOf((int)Math.rint(Math.random() * 10)));
            probindex++;
            setTitle("MathDrill - Problem " + String.valueOf(probindex));
        }
    }

    private void showScore() {
        synchronized (timer) {
            startFlag = false;
        }
        ansTF.removeActionListener(this);
        next.removeActionListener(this);
        remove(ansTF);
        validate();
        remove(next);
        validate();
        remove(num1);
        validate();
        remove(num2);
        validate();
        remove(operator);
        validate();
        remove(timerLbl);
        validate();
        scoreLbl.setText(" You scored: " + String.valueOf(score) + "/10 in " + String.valueOf(secs) + " seconds");
        add(scoreLbl);
        validate();
        setTitle("MathDrill - Score");
    }
    
    private void startDrill() {
        remove(start);
        validate();
        add(num1);
        validate();
        add(operator);
        validate();
        add(num2);
        validate();
        add(ansTF);
        validate();
        add(next);
        validate();
        add(timerLbl);
        validate();
        setTitle("MathDrill - Problem 1");
        nextProblem();
        startFlag = true;
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next || e.getSource() == ansTF) {
            ans = Integer.parseInt(ansTF.getText());
            if (ans == Integer.parseInt(num1.getText()) + Integer.parseInt(num2.getText())) score++;
            nextProblem();
        }
        else if (e.getSource() == start) {
            startDrill();
        }
    }
    
    public static void main(String[] args) {
        new MathDrill();
    }

}

class NumberDocument extends PlainDocument {
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        char[] source = str.toCharArray();
        char[] dest = new char[source.length];
        int destindex = 0;
        for (int i = 0; i < source.length; i++) {
            if (Character.isDigit(source[i])) dest[destindex++] = source[i];
            else Toolkit.getDefaultToolkit().beep();
        }
        String ins = new String(dest, 0, destindex);
        super.insertString(offs, ins, a);
    }
}