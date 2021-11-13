package beanmachine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Main {

    public Main() {
        int slots;
        int beans;
        while (true) {
            try {
                slots = Integer.parseInt(JOptionPane.showInputDialog("Enter number of slots"));
            } catch (Exception e) {
                continue;
            }
            break;
        }
        while (true) {
            try {
                beans = Integer.parseInt(JOptionPane.showInputDialog("Enter number of beans"));
            } catch (Exception e) {
                continue;
            }
            break;
        }
        Vector slots_[] = new Vector[slots];
        for (int i = 0; i < slots; i++) {
            slots_[i] = new Vector();
        }
        for (int i = 0; i < beans; i++) {
            System.out.print("Bean " + (i + 1) + ": ");
            String sequence = "";
            for (int j = 0; j < slots - 1; j++) {
                sequence += simulateLorR();
            }
            System.out.print(sequence);
            int pos = 1;
            for (int j = 0; j < sequence.length(); j++) {
                if (sequence.charAt(j) == 'R') pos ++;
            }
            slots_[pos - 1].add("O");
            System.out.println(" - slot " + pos);
        }
        JLabel lbls[][] = new JLabel[beans][slots];
        JPanel p = new JPanel(new GridLayout(beans, slots));
        for (int i = 0; i < beans; i++) {
            for (int j = 0; j < slots; j++) {
                lbls[i][j] = new JLabel(" ", JLabel.CENTER);
                lbls[i][j].setBorder(new LineBorder(Color.BLACK, 1));
                p.add(lbls[i][j]);
            }
        }
        for (int i = 0; i < slots_.length; i++) {
            Iterator it = slots_[i].iterator();
            int count = beans - 1;
            while (it.hasNext()) {
                lbls[count][i].setText((String)it.next());
                count--;
            }
        }
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(p, BorderLayout.CENTER);
        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    String simulateLorR() {
        return (int) Math.rint(Math.random()) == 0 ? "L" : "R";
    }

    public static void main(String[] args) {
        new Main();
    }
}
