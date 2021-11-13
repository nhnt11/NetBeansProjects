package bhoolbhulaiya;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Main extends JFrame {

    JButton btns[][] = new JButton[9][9];

    public Main() {
        JPanel p = new JPanel(new GridLayout(10, 10));
        setLayout(new BorderLayout());
        for (int i = 0; i < 9; i++) {
            JLabel lbl = new JLabel("" + (9 - (i + 1)), JLabel.CENTER);
            lbl.setBorder(new LineBorder(Color.BLACK));
            p.add(lbl);
            for (int j = 0; j < 9; j++) {
                btns[i][j] = new JButton("1");
                btns[i][j].setFocusPainted(false);
                final int ii = i;
                final int jj = j;
                btns[i][j].setPreferredSize(new Dimension(50, 50));
                btns[i][j].addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        if (btns[ii][jj].getText().equals("0")) {
                            btns[ii][jj].setText("1");
                        } else {
                            btns[ii][jj].setText("0");
                        }
                    }
                });
                p.add(btns[i][j]);
            }
        }
        JLabel lbl = new JLabel("", JLabel.CENTER);
        lbl.setBorder(new LineBorder(Color.BLACK));
        p.add(lbl);
        for (int i = 1; i < 10; i++) {
            JLabel lbl2 = new JLabel("" + i, JLabel.CENTER);
            lbl2.setBorder(new LineBorder(Color.BLACK));
            p.add(lbl2);
        }
        JButton okButton = new JButton("OK");
        final Main THIS = this;
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (isThereAPath()) {
                    JOptionPane.showMessageDialog(THIS, "You can get out, look at the gray path!");
                } else {
                    JOptionPane.showMessageDialog(THIS, "You are awaiting an inevitable death!");
                }
                for (int i = 0; i < btns.length; i++) {
                    for (int j = 0; j < btns.length; j++) {
                        btns[i][j].setEnabled(true);
                    }
                }
            }
        });
        add(p, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    boolean isThereAPath() {
        int x;
        int y;
        while (true) {
            try {
                y = Integer.parseInt(JOptionPane.showInputDialog("Enter floor number"));
            } catch (Exception e) {
                continue;
            }
            break;
        }
        y = btns.length - 1 - y;
        while (true) {
            try {
                x = Integer.parseInt(JOptionPane.showInputDialog("Enter room number"));
            } catch (Exception e) {
                continue;
            }
            break;
        }
        x--;
        btns[y][x].setEnabled(false);
        Vector rooms = new Vector();
        rooms.add(new Point(0, 0));
        boolean returnval = false;
        while (true) {
            int size = rooms.size();
            rooms.addAll(getAllConnectedRooms(rooms));
            if (rooms.size() == size) {
                break;
            }
        }
        returnval = rooms.contains(new Point(y, x));
        Iterator it = rooms.iterator();
        if (returnval) {
            while (it.hasNext()) {
                Point p = (Point) it.next();
                btns[(int) p.getX()][(int) p.getY()].setEnabled(false);
            }
        }
        return returnval;
    }

    Vector getAllConnectedRooms(Vector rooms) {
        Vector connectedRooms = new Vector();
        Iterator it = rooms.iterator();
        while (it.hasNext()) {
            Point p = (Point) it.next();
            try {
                if (btns[(int) p.getX() - 1][(int) p.getY()].getText().equals("0")) {
                    Point pp = new Point((int) p.getX() - 1, (int) p.getY());
                    if (!rooms.contains(pp)) {
                        connectedRooms.add(pp);
                    }
                }
            } catch (Exception e) {
            }
            try {
                if (btns[(int) p.getX() - 1][(int) p.getY() - 1].getText().equals("0")) {
                    Point pp = new Point((int) p.getX() - 1, (int) p.getY() - 1);
                    if (!rooms.contains(pp)) {
                        connectedRooms.add(pp);
                    }
                }
            } catch (Exception e) {
            }
            try {
                if (btns[(int) p.getX() - 1][(int) p.getY() + 1].getText().equals("0")) {
                    Point pp = new Point((int) p.getX() - 1, (int) p.getY() + 1);
                    if (!rooms.contains(pp)) {
                        connectedRooms.add(pp);
                    }
                }
            } catch (Exception e) {
            }
            try {
                if (btns[(int) p.getX() + 1][(int) p.getY()].getText().equals("0")) {
                    Point pp = new Point((int) p.getX() + 1, (int) p.getY());
                    if (!rooms.contains(pp)) {
                        connectedRooms.add(pp);
                    }
                }
            } catch (Exception e) {
            }
            try {
                if (btns[(int) p.getX() + 1][(int) p.getY() + 1].getText().equals("0")) {
                    Point pp = new Point((int) p.getX() + 1, (int) p.getY() + 1);
                    if (!rooms.contains(pp)) {
                        connectedRooms.add(pp);
                    }
                }
            } catch (Exception e) {
            }
            try {
                if (btns[(int) p.getX() + 1][(int) p.getY() - 1].getText().equals("0")) {
                    Point pp = new Point((int) p.getX() + 1, (int) p.getY() - 1);
                    if (!rooms.contains(pp)) {
                        connectedRooms.add(pp);
                    }
                }
            } catch (Exception e) {
            }
            try {
                if (btns[(int) p.getX()][(int) p.getY() - 1].getText().equals("0")) {
                    Point pp = new Point((int) p.getX(), (int) p.getY() - 1);
                    if (!rooms.contains(pp)) {
                        connectedRooms.add(pp);
                    }
                }
            } catch (Exception e) {
            }
            try {
                if (btns[(int) p.getX()][(int) p.getY() + 1].getText().equals("0")) {
                    Point pp = new Point((int) p.getX(), (int) p.getY() + 1);
                    if (!rooms.contains(pp)) {
                        connectedRooms.add(pp);
                    }
                }
            } catch (Exception e) {
            }
        }
        return connectedRooms;
    }

    public static void main(String[] args) {
        new Main();
    }
}
