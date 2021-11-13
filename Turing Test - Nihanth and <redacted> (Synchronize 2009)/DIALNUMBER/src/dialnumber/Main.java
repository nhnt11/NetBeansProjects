/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dialnumber;

import javax.swing.*;

/**
 *
 * @author SYNCHRONIZE
 */
public class Main {

    int keypad[][] = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9},
        {Integer.MAX_VALUE, 0, Integer.MAX_VALUE}
    };

    public Main() {
        int num = 0;
        while (true) {
            try {
                num = Integer.parseInt(JOptionPane.showInputDialog("Enter a phone number"));
            } catch (Exception e) {
                continue;
            }
            if (String.valueOf(num).length() > 6) {
                continue;
            }
            break;
        }
        dialnumber(String.valueOf(num));
    }

    void dialnumber(String num) {
        int x = 0;
        int y = 3;
        int newx = 0;
        int newy = 0;
        char nums[] = num.toCharArray();
        for (int i = 0; i < nums.length; i++) {
            switch (nums[i]) {
                case '0': {
                    newx = 1;
                    newy = 3;
                    break;
                }
                case '1': {
                    newx = 0;
                    newy = 0;
                    break;
                }
                case '2': {
                    newx = 1;
                    newy = 0;
                    break;
                }
                case '3': {
                    newx = 2;
                    newy = 0;
                    break;
                }
                case '4': {
                    newx = 0;
                    newy = 1;
                    break;
                }
                case '5': {
                    newx = 1;
                    newy = 1;
                    break;
                }
                case '6': {
                    newx = 2;
                    newy = 1;
                    break;
                }
                case '7': {
                    newx = 0;
                    newy = 2;
                    break;
                }
                case '8': {
                    newx = 1;
                    newy = 2;
                    break;
                }
                case '9': {
                    newx = 2;
                    newy = 2;
                    break;
                }
            }
            System.out.print("(" + (newx - x) + ", 0) " + "(0, " + (-(newy - y)) + ") P");
            x = newx;
            y = newy;
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
