package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel {
    JButton grid[][] = {
        {new JButton(), new JButton(), new JButton()},
        {new JButton(), new JButton(), new JButton()},
        {new JButton(), new JButton(), new JButton()}
    };
    Referee referee;
    public Board() {
        setLayout(new GridLayout(3, 3));
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                add(grid[i][j]);
                final int row = i;
                final int col = j;
                grid[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        grid[row][col].setEnabled(false);
                        referee.play(row, col);
                    }
                });
            }
        }
    }
    void setReferee(Referee referee) {
        this.referee = referee;
    }
    void reset() {
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].setText("");
                grid[i][j].setEnabled(true);
            }
        }
    }
}