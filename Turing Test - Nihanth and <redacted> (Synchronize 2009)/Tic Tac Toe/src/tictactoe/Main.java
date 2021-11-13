package tictactoe;

import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {

    Board board;
    Referee referee;
    
    public Main() {
        setLayout(new BorderLayout());
        board = new Board();
        add(board, BorderLayout.CENTER);
        referee = new Referee(board, this);
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new Main();
    }

}
