package ntflashcards;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class CardLabel extends JComponent {

    String text = "";
    Font font = new Font("Dialog", Font.BOLD, 14);
    int size = 14;

    public CardLabel(String text) {
        super();
        this.text = text;
    }

    void setText(String text) {
        this.text = text;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        int width = getWidth();
        FontMetrics fm = getFontMetrics(font);
        StringTokenizer st = new StringTokenizer(text, "\r\n");
        Vector lines = new Vector();
        String longest = "";
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            if (s.length() > longest.length()) longest = s;
            lines.add(s);
        }
        int strwidth;
        while ((strwidth = (fm = getFontMetrics(font)).stringWidth(longest)) < width - (width / 5)) {
            font = new Font("Dialog", Font.BOLD, ++size);
        }
        while ((strwidth = (fm = getFontMetrics(font)).stringWidth(longest)) > width - (width / 5)) {
            font = new Font("Dialog", Font.BOLD, --size);
        }
        int height = lines.size() * fm.getHeight();
        g.setFont(font);
        Iterator it = lines.iterator();
        while (it.hasNext()) {
            g.drawString(text, (getWidth() / 2) - ((fm.stringWidth(text)) / 2), (getHeight() / 2) + (height / 2));
        }
        super.paintBorder(g);
    }

}
