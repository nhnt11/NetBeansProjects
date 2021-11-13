package NPad;

import java.awt.*;
import javax.swing.text.*;

class WaveHighlighter extends DefaultHighlighter.DefaultHighlightPainter {

    public WaveHighlighter(Color color) {
        super(color);
    }

    @Override
    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
        try {
            Color color = getColor();

            if (color == null) {
                g.setColor(Color.red);
            } else {
                g.setColor(color);
            }

            Shape shape = view.modelToView(offs0, Position.Bias.Forward, offs1, Position.Bias.Backward, bounds);
            int y = (int) (shape.getBounds().getY() + shape.getBounds().getHeight()) - 2;
            int x1 = (int) shape.getBounds().getX();
            int x2 = (int) (shape.getBounds().getX() + shape.getBounds().getWidth()) - 2;
            Rectangle r = (shape instanceof Rectangle) ? (Rectangle) shape : shape.getBounds();
            for (int i = x1; i <= x2; i++) {
                g.drawLine(i, y, i, y);
                if (++i <= x2) {
                    g.drawLine(i, y, i, y);
                }
                if (++i <= x2) {
                    g.drawLine(i, y + 1, i, y + 1);
                }
                if (++i <= x2) {
                    g.drawLine(i, y + 1, i, y + 1);
                }
            }
            return r;
        } catch (BadLocationException ex) {
        }
        return null;
    }
}