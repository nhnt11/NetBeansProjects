/*
 * NTetris  Copyright (C) 2012  Nihanth Subramanya
 * 
 * NTetris is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * NTetris is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with NTetris.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
 
package ntetris;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nihanth
 */
public class MainApp extends JFrame {

    private RackView view;
    private JPanel nextOminoPanel = new JPanel();
    private Square[][] nextOminoSquares = new Square[20][5];
    private JLabel scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
    private int time_left = 10 * 60;
    private int startTime = time_left / 60;
    private JPanel optionsPanel = new JPanel(new GridLayout(2, 1));
    private JPanel mainp = new JPanel();
    private JSlider levelSlider = new JSlider(0, 20);
    private JComboBox colorSchemes;
    private int level = 0;
//    private JLabel timeLabel = new JLabel("Time Left: 5:00", JLabel.CENTER);
//    private javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
//        public void actionPerformed(ActionEvent ae) {
//            time_left--;
//            setTime(parseTime(time_left));
//            if (time_left == 0) {
//                reset();
//            } else timer.restart();
//        }
//    });

    int getStartTime() {
        return startTime;
    }

    int getLevel() {
        return level;
    }

//    void setTime(String time) {
//        timeLabel.setText("Time Left: " + time);
//    }
    public MainApp() {
        levelSlider.setPaintLabels(true);
        levelSlider.setPaintTicks(true);
        levelSlider.setSnapToTicks(true);
        final JLabel levelLbl = new JLabel("Level: ");
        JPanel levelPanel = new JPanel(new BorderLayout());
        levelSlider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                levelLbl.setText("Level: " + levelSlider.getValue());
            }
        });
        levelPanel.add(levelLbl, BorderLayout.WEST);
        levelPanel.add(levelSlider, BorderLayout.CENTER);
        Vector v = new Vector();
        v.add("Tetris Worlds");
        v.add("Netris");
        v.add("Tetris: The Soviet Mind Game");
        v.add("Atari / Arcade");
        v.add("The New Tetris / Kids Tetris");
        v.add("Sega/Arika (TGM Series)");
        v.add("Microsoft Tetris");
        v.add("Tetris 3.12");
        colorSchemes = new JComboBox(v);
        JLabel schemeLbl = new JLabel("Color Scheme: ");
        JPanel schemePanel = new JPanel(new BorderLayout());
        schemePanel.add(schemeLbl, BorderLayout.WEST);
        schemePanel.add(colorSchemes, BorderLayout.CENTER);
        optionsPanel.add(levelPanel);
        optionsPanel.add(schemePanel);
        setScore(0, 0, 0);
        nextOminoPanel.setLayout(new GridLayout(20, 5));
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 5; j++) {
                nextOminoSquares[i][j] = new Square(Square.blank, i, j);
                nextOminoPanel.add(nextOminoSquares[i][j]);
            }
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        nextOminoPanel.setPreferredSize(new Dimension((int) screenSize.getHeight() / 6, (int) screenSize.getHeight() * 2 / 3));
        setLayout(new BorderLayout());
        final JPanel viewPanel = new JPanel(new GridBagLayout());
        view = new RackView(this, 0);
        view.setDownable(false);
        levelSlider.setValue(view.getLevel());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 1;
        c.ipady = 0;
        viewPanel.add(view, c);
        c.gridx = 1;
        c.ipadx = 0;
        viewPanel.add(nextOminoPanel, c);
        mainp = new JPanel(new BorderLayout());
        mainp.add(viewPanel, BorderLayout.CENTER);
        mainp.add(scoreLabel, BorderLayout.SOUTH);
        final JPanel introP = new JPanel(new BorderLayout());
        JLabel introL = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/startGraphic.png")), JLabel.CENTER);
        viewPanel.setBackground(Color.BLACK);
        viewPanel.setOpaque(true);
        introL.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                remove(introP);
                getContentPane().add(mainp, BorderLayout.CENTER);
                validate();
                pack();
                //
                setResizable(false);
                viewPanel.addKeyListener(view);
                view.addKeyListener(view);
                nextOminoPanel.addKeyListener(view);
                mainp.addKeyListener(view);
                optionsPanel.addKeyListener(view);
                addKeyListener(view);
                view.setDownable(true);
            }
        });
        introP.add(introL, BorderLayout.CENTER);
        introP.setSize(introL.getWidth(), introL.getHeight());
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(introP, BorderLayout.CENTER);
        pack();
        view.setDownable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                view.displayCounts();
                System.exit(0);
            }
        });
        setTitle("NTetris");
        setVisible(true);
    }

    void showOptions() {
        view.setDownable(false);
        final JPanel p = new JPanel(new BorderLayout());
        p.add(optionsPanel, BorderLayout.CENTER);
        JButton OK = new JButton("OK");
        OK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                remove(p);
                getContentPane().add(mainp, BorderLayout.CENTER);
                validate();
                pack();
                level = levelSlider.getValue();
                view.setNewScheme(colorSchemes.getSelectedIndex());
                view.requestFocus();
                if (JOptionPane.showConfirmDialog(view, "<html>The settings won't be applied until you start a new game.<br />Start a new game now?", "Start new game?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    view.reset();
                } else {
                    view.setDownable(true);
                }
            }
        });
        p.add(OK, BorderLayout.SOUTH);
        remove(mainp);
        getContentPane().add(p, BorderLayout.CENTER);
        validate();
        pack();
    }

    void setScore(int score, int lines, int level) {
        scoreLabel.setText("Score: " + score + " Lines Cleared: " + lines + " Level: " + level);
    }

    String parseTime(int seconds) {
        String secs = String.valueOf(seconds % 60);
        String mins = String.valueOf((seconds - (seconds % 60)) / 60);
        if (seconds % 60 < 10) {
            secs = "0" + secs;
        }
        return mins + ":" + secs;
    }

    void setNextOmino(Tetromino t) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 5; j++) {
                nextOminoSquares[i][j].setColor(Square.blank);
            }
        }
        Square[] s = t.getSquares();
//        int colm = t.getType() == Tetromino.BAR ? 4 : 3;
        for (int i = 0; i < s.length; i++) {
            nextOminoSquares[s[i].getRow() + 9][s[i].getCol() - 3].setColor(s[i].getColor());
        }
        repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new MainApp();
    }
}
