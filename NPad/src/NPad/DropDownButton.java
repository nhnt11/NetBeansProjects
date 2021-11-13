package NPad;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DropDownButton extends JButton implements ActionListener {
    private JPopupMenu popup = new JPopupMenu();
    private Color fontColor = Color.black;
    private Color colors[] = {Color.black, Color.red, Color.green, Color.blue, Color.yellow, Color.orange, Color.magenta, Color.pink, Color.cyan, Color.lightGray, Color.gray, Color.darkGray};
    private JButton buttons[] = new JButton[colors.length + 1];
    private NPad npad = null;
    private JButton chooserBtn = new JButton("Other...");
    private boolean b = true;
    public DropDownButton(NPad npad) {
        this.npad = npad;
        setFont(new Font("SansSerif", Font.BOLD, 18));
        setForeground(Color.black);
        setText("A");
        JPanel total = new JPanel(new GridBagLayout());
        for (int i = 0; i < colors.length; i+=4) {
            for (int z = i; z < i + 4; z++) {
                final int ii = z;
                JButton btn = new JButton() {
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(Color.black);
                        g.drawRect(2, 1, getWidth() - 4, getHeight() - 2);
                        g.setColor(colors[ii]);
                        g.fillRect(3, 2, getWidth() - 6, getHeight() - 4);
                        setOpaque(false);
                    }
                };
                btn.setBackground(colors[ii]);
                btn.setForeground(colors[ii]);
                btn.setActionCommand("fontcolor");
                btn.addActionListener(this);
                btn.setPreferredSize(new Dimension(16, 16));
                buttons[i] = btn;
                total.add(btn);
            }
        }
        popup.add(total);
        popup.addSeparator();
        popup.pack();
        chooserBtn.setActionCommand("fontcolor");
        chooserBtn.addActionListener(this);
        popup.add(chooserBtn);
        setPreferredSize(new Dimension(32, 32));
        addActionListener(this);
    }
    Color getColor() {
        return fontColor;
    }
    void setColor(Color c) {
        if (b) {
            fontColor = c; 
            setForeground(fontColor);
        }
    }
    void updateUI2() {
        updateUI();
        popup.updateUI();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].updateUI();
        }
        chooserBtn.updateUI();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) popup.show(this, 5, getHeight());
        else if (e.getSource() == chooserBtn) {
            popup.setVisible(false);
            npad.off();
            fontColor = JColorChooser.showDialog(npad, "Choose font color", fontColor);
            setForeground(fontColor);
            npad.actionPerformed(e);
            npad.on();
        } else if (e.getActionCommand().equals("fontcolor")) {
            b = false;
            JButton item = (JButton)e.getSource();
            fontColor = item.getForeground(); 
            setForeground(fontColor);
            popup.setVisible(false);
            npad.actionPerformed(e);
            b = true;
        }
    }
}