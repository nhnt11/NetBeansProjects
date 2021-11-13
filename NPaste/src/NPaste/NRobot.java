package NPaste;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 *
 * @author Nihanth
 */
public class NRobot {

    Robot robot;
    Hashtable table = new Hashtable();

    public NRobot() {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        table.put('A', KeyEvent.VK_A);
        table.put('B', KeyEvent.VK_B);
        table.put('C', KeyEvent.VK_C);
        table.put('D', KeyEvent.VK_D);
        table.put('E', KeyEvent.VK_E);
        table.put('F', KeyEvent.VK_F);
        table.put('G', KeyEvent.VK_G);
        table.put('H', KeyEvent.VK_H);
        table.put('I', KeyEvent.VK_I);
        table.put('J', KeyEvent.VK_J);
        table.put('K', KeyEvent.VK_K);
        table.put('L', KeyEvent.VK_L);
        table.put('M', KeyEvent.VK_M);
        table.put('N', KeyEvent.VK_N);
        table.put('O', KeyEvent.VK_O);
        table.put('P', KeyEvent.VK_P);
        table.put('Q', KeyEvent.VK_Q);
        table.put('R', KeyEvent.VK_R);
        table.put('S', KeyEvent.VK_S);
        table.put('T', KeyEvent.VK_T);
        table.put('U', KeyEvent.VK_U);
        table.put('V', KeyEvent.VK_V);
        table.put('W', KeyEvent.VK_W);
        table.put('X', KeyEvent.VK_X);
        table.put('Y', KeyEvent.VK_Y);
        table.put('Z', KeyEvent.VK_Z);
        table.put('0', KeyEvent.VK_0);
        table.put('1', KeyEvent.VK_1);
        table.put('2', KeyEvent.VK_2);
        table.put('3', KeyEvent.VK_3);
        table.put('4', KeyEvent.VK_4);
        table.put('5', KeyEvent.VK_5);
        table.put('6', KeyEvent.VK_6);
        table.put('7', KeyEvent.VK_7);
        table.put('8', KeyEvent.VK_8);
        table.put('9', KeyEvent.VK_9);
        table.put('!', KeyEvent.VK_EXCLAMATION_MARK);
        table.put('@', KeyEvent.VK_AT);
        table.put('#', KeyEvent.VK_NUMBER_SIGN);
        table.put('$', KeyEvent.VK_DOLLAR);
        table.put('^', KeyEvent.VK_CIRCUMFLEX);
        table.put('&', KeyEvent.VK_AMPERSAND);
        table.put('*', KeyEvent.VK_ASTERISK);
        table.put('(', KeyEvent.VK_LEFT_PARENTHESIS);
        table.put(')', KeyEvent.VK_RIGHT_PARENTHESIS);
        table.put(' ', KeyEvent.VK_SPACE);
        table.put('_', KeyEvent.VK_UNDERSCORE);
        table.put('-', KeyEvent.VK_MINUS);
        table.put('+', KeyEvent.VK_PLUS);
        table.put('=', KeyEvent.VK_EQUALS);
        table.put('[', KeyEvent.VK_OPEN_BRACKET);
        table.put('{', KeyEvent.VK_BRACELEFT);
        table.put(']', KeyEvent.VK_CLOSE_BRACKET);
        table.put('}', KeyEvent.VK_BRACERIGHT);
        table.put(';', KeyEvent.VK_SEMICOLON);
        table.put('"', KeyEvent.VK_QUOTEDBL);
        table.put('\'', KeyEvent.VK_QUOTE);
        table.put('<', KeyEvent.VK_LESS);
        table.put(',', KeyEvent.VK_COMMA);
        table.put('>', KeyEvent.VK_GREATER);
        table.put('.', KeyEvent.VK_PERIOD);
        table.put('/', KeyEvent.VK_SLASH);
        table.put('\\', KeyEvent.VK_BACK_SLASH);
        table.put('`', KeyEvent.VK_BACK_QUOTE);
        table.put('\n', KeyEvent.VK_ENTER);
        table.put('\t', KeyEvent.VK_TAB);
    }

    void typeString(String s, int delay) {
        char c[] = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            char orig = c[i];
            char ch = Character.toUpperCase(orig);
            if (orig == '~') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_BACK_QUOTE);
                robot.keyRelease(KeyEvent.VK_BACK_QUOTE);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '!') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '@') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_2);
                robot.keyRelease(KeyEvent.VK_2);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '#') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_3);
                robot.keyRelease(KeyEvent.VK_3);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '$') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_4);
                robot.keyRelease(KeyEvent.VK_4);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '%') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_5);
                robot.keyRelease(KeyEvent.VK_5);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '^') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_6);
                robot.keyRelease(KeyEvent.VK_6);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '&') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_7);
                robot.keyRelease(KeyEvent.VK_7);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '*') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_8);
                robot.keyRelease(KeyEvent.VK_8);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '(') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_9);
                robot.keyRelease(KeyEvent.VK_9);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == ')') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_0);
                robot.keyRelease(KeyEvent.VK_0);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '_') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '+') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_EQUALS);
                robot.keyRelease(KeyEvent.VK_EQUALS);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '{') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
                robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '}') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
                robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '|') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_BACK_SLASH);
                robot.keyRelease(KeyEvent.VK_BACK_SLASH);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == ':') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '"') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_QUOTE);
                robot.keyRelease(KeyEvent.VK_QUOTE);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '<') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_COMMA);
                robot.keyRelease(KeyEvent.VK_COMMA);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '>') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_PERIOD);
                robot.keyRelease(KeyEvent.VK_PERIOD);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '?') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_SLASH);
                robot.keyRelease(KeyEvent.VK_SLASH);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (orig == '\r') {
                continue;
            } else {
                if (Character.isUpperCase(orig)) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                robot.keyPress((Integer) table.get(ch));
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
            while (true) {
                try {
                    Thread.sleep(delay);
                    break;
                } catch (InterruptedException ex) {
                    continue;
                }
            }
        }
    }
}
