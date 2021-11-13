/*
 * NTSpellCheck  Copyright (C) 2012  Nihanth Subramanya
 * 
 * NTSpellCheck is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * NTSpellCheck is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with NTSpellCheck. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
 
package NTSpellCheck;

import java.awt.*;
import javax.swing.*;
import java.io.*;

public class SCTest {

    public static void main(String args[]) {
        try {
            JFrame frame = new JFrame("NTSpellCheck Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setLayout(new BorderLayout());
            JTextArea area = new JTextArea();
            File f = new File(System.getProperty("user.home") + "/.NPad/Dictionary/dictionary.txt");
            NTSpellCheck checker = new NTSpellCheck(area, f, true, true, 25, Color.red, NTSpellCheck.WAVE_UNDERLINE);
            frame.getContentPane().add(area, BorderLayout.CENTER);
            frame.setSize(640, 480);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}