package qegrapher;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Vector;
import java.awt.event.*;
import javax.swing.border.LineBorder;

public class Main extends JFrame {

    Grapher graphP = new Grapher(this);
    JScrollPane jsp = new JScrollPane(graphP);
    JTabbedPane jtp = new JTabbedPane();
    JTextField aTF = new JTextField("1", 2);
    JTextField bTF = new JTextField("0", 2);
    JTextField cTF = new JTextField("0", 2);
    JTextField dTF = new JTextField("0", 2);
    JButton applyB = new JButton("Graph y = ax³ + bx² + cx + d");
    JPanel abcP = new JPanel();
    JButton sinB = new JButton("y = sinx");
    JButton cosB = new JButton("y = cosx");
    JButton tanB = new JButton("y = tanx");
    JButton cosecB = new JButton("y = cosecx");
    JButton secB = new JButton("y = secx");
    JButton cotB = new JButton("y = cotx");
    JPanel trigP = new JPanel();
    JPanel functionsP = new JPanel();
    JButton siB = new JButton("+");
    JButton sdB = new JButton("-");
    JPanel graphSizeP = new JPanel(new GridLayout(2, 1));
    JPanel buttonsP = new JPanel();
    boolean md = false;
    final int POLY = 0;
    final int SIN = 1;
    final int COS = 2;
    final int TAN = 3;
    final int COSEC = 4;
    final int SEC = 5;
    final int COT = 6;

    public Main() {
        jsp.setPreferredSize(new Dimension(700, 700));
        setLayout(new BorderLayout());
        add(jsp, BorderLayout.CENTER);
        functionsP.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        abcP.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        abcP.add(new JLabel("a: ", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        abcP.add(aTF, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        abcP.add(new JLabel("b: ", JLabel.RIGHT), gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        abcP.add(bTF, gbc);
        gbc.gridx = 4;
        gbc.gridy = 0;
        abcP.add(new JLabel("c: ", JLabel.RIGHT), gbc);
        gbc.gridx = 5;
        gbc.gridy = 0;
        abcP.add(cTF, gbc);
        gbc.gridx = 6;
        gbc.gridy = 0;
        abcP.add(new JLabel("d: ", JLabel.RIGHT), gbc);
        gbc.gridx = 7;
        gbc.gridy = 0;
        abcP.add(dTF, gbc);
        gbc.gridx = 8;
        gbc.gridy = 0;
        abcP.add(applyB, gbc);
        graphSizeP.add(siB);
        graphSizeP.add(sdB);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        functionsP.add(abcP, gbc);
        trigP.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        trigP.add(sinB, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        trigP.add(cosB, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        trigP.add(tanB, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        trigP.add(cosecB, gbc);
        gbc.gridx = 4;
        gbc.gridy = 0;
        trigP.add(secB, gbc);
        gbc.gridx = 5;
        gbc.gridy = 0;
        trigP.add(cotB, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        functionsP.add(trigP, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonsP.add(functionsP, gbc);
        graphSizeP.setBorder(new LineBorder(Color.BLACK, 1));
        gbc.gridx = 1;
        buttonsP.add(graphSizeP, gbc);
        applyB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                graphP.setData(getCoordinates(graphP, POLY));
                graphP.setType(POLY);
                graphP.repaint();
            }
        });
        sinB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                graphP.setData(getCoordinates(graphP, SIN));
                graphP.setType(SIN);
                graphP.repaint();
            }
        });
        cosB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                graphP.setData(getCoordinates(graphP, COS));
                graphP.setType(COS);
                graphP.repaint();
            }
        });
        tanB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                graphP.setData(getCoordinates(graphP, TAN));
                graphP.setType(TAN);
                graphP.repaint();
            }
        });
        cosecB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                graphP.setData(getCoordinates(graphP, COSEC));
                graphP.setType(COSEC);
                graphP.repaint();
            }
        });
        secB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                graphP.setData(getCoordinates(graphP, SEC));
                graphP.setType(SEC);
                graphP.repaint();
            }
        });
        cotB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                graphP.setData(getCoordinates(graphP, COT));
                graphP.setType(COT);
                graphP.repaint();
            }
        });
        siB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (graphP.getPreferredSize().width > 100000 || graphP.getPreferredSize().height > 100000) {
                    siB.setEnabled(false);
                    return;
                }
                int hv = jsp.getHorizontalScrollBar().getValue();
                int vv = jsp.getVerticalScrollBar().getValue();
                graphP.setPreferredSize(new Dimension((int) (graphP.getPreferredSize().width * 1.2), (int) (graphP.getPreferredSize().height * 1.2)));
                jsp.getHorizontalScrollBar().setValue((int) (hv + graphP.getWidth() / 10));
                jsp.getVerticalScrollBar().setValue((int) (vv + graphP.getHeight() / 10));
                sdB.setEnabled(true);
                graphP.revalidate();
                graphP.setData(getCoordinates(graphP, graphP.getType()));
            }
        });
        sdB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (graphP.getPreferredSize().width / 1.2 > getPreferredSize().width && graphP.getPreferredSize().height / 1.2 > getPreferredSize().height) {
                    int hv = jsp.getHorizontalScrollBar().getValue();
                    int vv = jsp.getVerticalScrollBar().getValue();
                    graphP.setPreferredSize(new Dimension((int) (graphP.getPreferredSize().width / 1.2), (int) (graphP.getPreferredSize().height / 1.2)));
                    jsp.getHorizontalScrollBar().setValue((int) (hv - graphP.getWidth() / 12));
                    jsp.getVerticalScrollBar().setValue((int) (vv - graphP.getHeight() / 12));
                } else {
                    sdB.setEnabled(false);
                }
                siB.setEnabled(true);
                graphP.revalidate();
                graphP.setData(getCoordinates(graphP, graphP.getType()));
            }
        });
        add(buttonsP, BorderLayout.SOUTH);
        setVisible(true);
        jsp.getHorizontalScrollBar().setValue(jsp.getHorizontalScrollBar().getMaximum() / 2 - jsp.getWidth() / 2);
        jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum() / 2 - jsp.getHeight() / 2);
        graphP.repaint();
    }

    void centerGraph() {
        jsp.getHorizontalScrollBar().setValue(jsp.getHorizontalScrollBar().getMaximum() / 2 - jsp.getWidth() / 2);
        jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum() / 2 - jsp.getHeight() / 2);
    }

    Vector<Point> getCoordinates(Grapher grapher, int type) throws NumberFormatException {
        Vector<Point> data = new Vector<Point>();
        Point z = grapher.getZero();
        for (Integer x = z.x - grapher.getPreferredSize().width, y, x2 = 0; x < z.x; x++, x2++) {
            switch (type) {
                case POLY: {
                    double a = Double.parseDouble(aTF.getText());
                    double b = Double.parseDouble(bTF.getText());
                    double c = Double.parseDouble(cTF.getText());
                    double d = Double.parseDouble(dTF.getText());
                    y = z.y - (int) (a * x * x * x / (grapher.getInterval() * grapher.getInterval()) + b * x * x / grapher.getInterval() + c * x + d * grapher.getInterval());
                    data.add(new Point(x2, y));
                    break;
                }
                case SIN: {
                    y = z.y - (int) (Math.sin((double) x / (double) grapher.getInterval()) * grapher.getInterval());
                    data.add(new Point(x2, y));
                    break;
                }
                case COS: {
                    y = z.y - (int) (Math.cos((double) x / (double) grapher.getInterval()) * grapher.getInterval());
                    data.add(new Point(x2, y));
                    break;
                }
                case TAN: {
                    y = z.y - (int) (Math.tan((double) x / (double) grapher.getInterval()) * grapher.getInterval());
                    data.add(new Point(x2, y));
                    break;
                }
                case COSEC: {
                    y = z.y - (int) ((1 / (Math.sin((double) x / (double) grapher.getInterval())) * grapher.getInterval()));
                    data.add(new Point(x2, y));
                    break;
                }
                case SEC: {
                    y = z.y - (int) ((1 / (Math.cos((double) x / (double) grapher.getInterval())) * grapher.getInterval()));
                    data.add(new Point(x2, y));
                    break;
                }
                case COT: {
                    y = z.y - (int) ((1 / (Math.tan((double) x / (double) grapher.getInterval())) * grapher.getInterval()));
                    data.add(new Point(x2, y));
                    break;
                }
                default: {
                    return null;
                }
            }
        }
        return data;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        Main main = new Main();
        main.setBackground(Color.WHITE);
        main.pack();
        main.setResizable(false);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.centerGraph();
        main.setVisible(true);
    }
}

class Grapher extends JPanel {

    private double a = 1;
    private double b = 0;
    private double c = 0;
    private int interval = 20;
    private Vector<Point> data = null;
    private Main main;
    private int type = -1;
    final int POLY = 0;
    final int SIN = 1;
    final int COS = 2;
    final int TAN = 3;
    final int COSEC = 4;
    final int SEC = 5;
    final int COT = 6;
    private Point zero = new Point();

    protected Grapher(final Main main) {
        this.main = main;
        setPreferredSize(new Dimension(5000, 5000));
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (getPreferredSize().width < 100000 && getPreferredSize().height < 100000) {
                        setInterval((int) Math.ceil(interval * 1.2));
                        main.sdB.setEnabled(true);
                    } else {
                        main.siB.setEnabled(false);
                    }
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (interval <= 10) {
                        return;
                    } else {
                        while (getPreferredSize().width / 1.2 < main.getPreferredSize().width && getPreferredSize().height / 1.2 < main.getPreferredSize().height) {
                            main.siB.getActionListeners()[0].actionPerformed(null);
                        }
                        setInterval((int) Math.ceil(interval / 1.2));
                        main.siB.setEnabled(true);
                    }
                }
                refetchData();
                revalidate();
            }
        });
        repaint();
    }

    void refetchData() {
        setData(main.getCoordinates(this, getType()));
    }

    void setType(int type) {
        this.type = type;
    }

    void setData(Vector<Point> data) {
        this.data = data;
        repaint();
    }

    int getInterval() {
        return interval;
    }

    void setInterval(int interval) {
        if (this.interval > interval) {
            if (interval < 10) {
                return;
            }
            setPreferredSize(new Dimension((int) (getPreferredSize().width / 1.2), (int) (getPreferredSize().height / 1.2)));
            main.jsp.getHorizontalScrollBar().setValue((int) (main.jsp.getHorizontalScrollBar().getValue() - getWidth() / 12));
            main.jsp.getVerticalScrollBar().setValue((int) (main.jsp.getVerticalScrollBar().getValue() - getHeight() / 12));
        } else if (this.interval < interval) {
            if (interval > getWidth() / 10) {
                return;
            }
            setPreferredSize(new Dimension((int) (getPreferredSize().width * 1.2), (int) (getPreferredSize().height * 1.2)));
            main.jsp.getHorizontalScrollBar().setValue((int) (main.jsp.getHorizontalScrollBar().getValue() + getWidth() / 10));
            main.jsp.getVerticalScrollBar().setValue((int) (main.jsp.getVerticalScrollBar().getValue() + getHeight() / 10));
        }
        this.interval = interval;
    }

    int getType() {
        return type;
    }

    void setA(double a) {
        this.a = a;
    }

    void setB(double b) {
        this.b = b;
    }

    void setC(double c) {
        this.c = c;
    }

    public void setABC(double a, double b, double c) {
        setA(a);
        setB(b);
        setC(c);
        repaint();
    }

    double getA() {
        return a;
    }

    double getB() {
        return b;
    }

    double getC() {
        return c;
    }

    Point getZero() {
        return new Point(getPreferredSize().width / 2, getPreferredSize().height / 2);
    }

    @Override
    public void paint(Graphics G) {
        Graphics2D g = (Graphics2D) G;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.clearRect(0, 0, getWidth(), getHeight());
        int x0 = getPreferredSize().width / 2;
        int y0 = getPreferredSize().height / 2;
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x < x0; x += interval) {
            g.drawLine(x0 + x, 0, x0 + x, getHeight());
            g.drawLine(x0 - x, 0, x0 - x, getHeight());
        }
        for (int y = 0; y < y0; y += interval) {
            g.drawLine(0, y0 + y, getWidth(), y0 + y);
            g.drawLine(0, y0 - y, getWidth(), y0 - y);
        }
        g.setStroke(new BasicStroke(new Double(2.2).floatValue()));
        g.setColor(Color.BLACK);
        g.drawLine(x0, 0, x0, getHeight());
        g.drawLine(0, y0, getWidth(), y0);
        if (data == null) {
            return;
        }
        g.setStroke(new BasicStroke(new Double(1.2).floatValue()));
        g.setColor(Color.BLUE);
        if (data == null) {
            return;
        }
        Iterator<Point> it = data.iterator();
        Point prev = it.next();
        Point p = it.next();
        boolean increasing = true;
        while (it.hasNext()) {
            g.drawLine(prev.x, prev.y, p.x, p.y);
            increasing = prev.y < p.y ? true : false;
            prev = p;
            p = it.next();
            if ((prev.y - p.y < -interval * 25 && !increasing) || (prev.y - p.y > interval * 25 && increasing)) {
                if (!it.hasNext()) {
                    break;
                }
                prev = p;
                p = it.next();
            }
        }
        return;
//        Vector _x = new Vector();
//        Vector _y = new Vector();
//        for (double x = x0 - getWidth(); x < getWidth(); x++) {
//            _x.add(x + x0);
//            _y.add(y0 - (a * x * x / interval + b * x + c * interval));
//        }
//        Iterator itx = _x.iterator();
//        Iterator ity = _y.iterator();
//        int prevx = ((Double) itx.next()).intValue();
//        int prevy = ((Double) ity.next()).intValue();
//        while (itx.hasNext() && ity.hasNext()) {
//            g.drawLine(prevx, prevy, prevx = ((Double) itx.next()).intValue(), prevy = ((Double) ity.next()).intValue());
//        }
//        double discriminant = b * b - 4 * a * c;
//        String x1 = "";
//        String x2 = "";
//        if (discriminant > -1) {
//            x1 = "x = " + ((-b + Math.sqrt(discriminant)) / 2 * a);
//            try {
//                x1 = x1.substring(0, x1.indexOf(".")) + x1.substring(x1.indexOf("."), x1.indexOf(".") + 4);
//            } catch (Exception e) {
//            }
//            x2 = "x = " + ((-b - Math.sqrt(discriminant)) / 2 * a);
//            try {
//                x2 = x2.substring(0, x2.indexOf(".")) + x2.substring(x2.indexOf("."), x2.indexOf(".") + 4);
//            } catch (Exception e) {
//            }
//        } else {
//            String sdb = String.valueOf(Math.sqrt(-discriminant) / 2 * a);
//            try {
//                sdb = sdb.substring(0, sdb.indexOf(".")) + sdb.substring(sdb.indexOf("."), sdb.indexOf(".") + 4);
//            } catch (Exception e) {
//            }
//            x1 = "x = " + -b / 2 * a + " + " + sdb + "i";
//            x2 = "x = " + -b / 2 * a + " - " + sdb + "i";
//        }
//        main.x1 = x1;
//        main.x2 = x2;
//        main.jsp.repaint();
    }
}
