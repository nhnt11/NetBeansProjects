package tictactoe;

import javax.swing.*;

public class Referee {

    Board board;
    Player player1;
    Player player2;
    Player currentPlayer;
    Main main;
    int turncount = 0;

    public Referee(Board board, Main main) {
        this.board = board;
        this.main = main;
        board.setReferee(this);
        player1 = new Player(JOptionPane.showInputDialog("Enter Player 1's name"), Player.X);
        player2 = new Player(JOptionPane.showInputDialog("Enter Player 2's name"), Player.O);
        currentPlayer = player1;
    }

    void play(int row, int col) {
        board.grid[row][col].setText(currentPlayer.XorO);
        String whowon = checkForWin();
        if (whowon != null) {
            JOptionPane.showMessageDialog(board, whowon + " won!");
            board.reset();
            turncount = 0;
        }
        else {
            turncount++;
            if (turncount == 9) {
            JOptionPane.showMessageDialog(board, "It's a draw!");
            board.reset();
            turncount = 0;
            }
            else changeTurn();
        }
    }

    void changeTurn() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    String checkForWin() {
        //check all rows
        for (int i = 0; i < board.grid.length; i++) {
            String val = "";
            for (int j = 0; j < board.grid[i].length; j++) {
                val += board.grid[i][j].getText();
            }
            if (val.equals("XXX")) {
                return player1.getName();
            } else if (val.equals("OOO")) {
                return player2.getName();
            }
        }
        for (int i = 0; i < board.grid.length; i++) {
            String val = "";
            for (int j = 0; j < board.grid[i].length; j++) {
                val += board.grid[j][i].getText();
            }
            if (val.equals("XXX")) {
                return player1.getName();
            } else if (val.equals("OOO")) {
                return player2.getName();
            }
        }
        String val = board.grid[0][0].getText() + board.grid[1][1].getText() + board.grid[2][2].getText();
        if (val.equals("XXX")) {
            return player1.getName();
        } else if (val.equals("OOO")) {
            return player2.getName();
        }
        val = board.grid[0][2].getText() + board.grid[1][1].getText() + board.grid[2][0].getText();
        if (val.equals("XXX")) {
            return player1.getName();
        } else if (val.equals("OOO")) {
            return player2.getName();
        }
        return null;
    }
}