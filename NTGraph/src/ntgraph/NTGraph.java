package ntgraph;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class NTGraph extends Canvas {

    private int height;
    private int width;
    private int scale;
    private Dimension range;
    private JFrame frame;
    private Vector data;
    private Graphics2D gr;
    private int offsetX;
    private int offsetY;

    public NTGraph(Vector data, int width, int height, int scale, Dimension range) {

        this.data = data;
        this.height = height;
        this.width = width;
        this.scale = scale;
        this.range = range;
        frame = new JFrame();
        setSize(width, height);
        frame.add(this);
        frame.pack();

    }
    
    public NTGraph() {
        
    }

    void setData(Vector data) {
        this.data = data;
    }

    void setHeight(int height) {
        this.height = height;
        repaint();
    }

    void setWidth(int width) {
        this.width = width;
        repaint();
    }

    void setScale(int scale) {
        repaint();
    }

    void setRange(Dimension range) {
        this.range = range;
        repaint();
    }

    private void plotLine(Point start, Point finish) {

    }

    private void plotAxes(int offX, int offY) {

    }

    private Point getRelativePoint(Point p, Dimension oldD, Dimension newD) { //Gets the relative point for the SAME SCALE (duh)
        double ratioX = newD.getWidth() / oldD.getWidth();
        double ratioY = newD.getHeight() / oldD.getHeight();
        p.x *= ratioX;
        p.y *= ratioY;
        return p;
    }

    @Override
    public void paint(Graphics g) {

    }

    public static void main(String[] args) {

        System.out.println(new NTGraph().getRelativePoint(new Point(23,99), new Dimension(46,366), new Dimension(244,21)));

    }

}
