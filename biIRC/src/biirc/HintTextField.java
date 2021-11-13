/*
 * biIRC - biIRC is an IRC Remote Control
 * 
 * Copyright (C) 2012 Nihanth Subramanya
 * 
 * biIRC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * biIRC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with biIRC.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package biirc;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;

/**
 *
 * @author nihanth
 */
public class HintTextField extends JTextField {

    String mHint;

    public HintTextField() {
        addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (getForeground().equals(Color.GRAY)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals("")) {
                    setText(mHint);
                    setForeground(Color.GRAY);
                }
            }
        });
    }

    public void setHint(String hint) {
        mHint = hint;
    }
}
