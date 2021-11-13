package NStock;

import java.awt.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class GraphPanel extends JPanel implements Runnable {

    private int coordinates[][][] = {
        {
            {0, 0}
        }
    };
    private double highvalues[] = {};
    private double lowvalues[] = {};
    private double volumevalues[] = {};
    private double closevalues[] = {};
    private double openvalues[] = {};
    private double myhighest;
    private double mylowest;
    private double myxinterval;
    private double myyinterval;
    private double values[] = {};
    private boolean myisonepixel = false;
    private String symbol = "";
    JLabel scaleLabel;
    Font myFont = new Font("Sans Serif", Font.BOLD, 12);
    public static final int OPEN = 1;
    public static final int HIGH = 2;
    public static final int LOW = 3;
    public static final int CLOSE = 4;
    public static final int VOLUME = 5;
    public static final int ADJ_CLOSE = 6;
    private int whichData = 4;
    private String dates[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private JProgressBar jpb = null;
    int jpbn = 0;
    boolean bgflag = true;

    public GraphPanel(JLabel scaleLabel, String symbol, int which) {
        this.scaleLabel = scaleLabel;
        whichData = which;
        setSymbol(symbol);
        bgflag = true;
        new Thread(this).start();
    }

    void setWithProgressBar(final JProgressBar jpb, String symbol, JFrame parent) {
        synchronized (this) {
            bgflag = false;
        }
        if (symbol.equals(this.symbol)) {
            return;
        }
        this.jpb = jpb;
        jpbn = 0;
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout());
        dialog.add(jpb, BorderLayout.CENTER);
        dialog.setUndecorated(true);
        dialog.setAlwaysOnTop(true);
        int x = parent.getX() + (parent.getWidth() / 2) - 100;
        int y = parent.getY() + (parent.getHeight() / 2) - 20;
        dialog.setBounds(x, y, 200, 40);
        dialog.setVisible(true);
        try {
            values = getValuesWithPB(symbol, whichData);
            lowvalues = getValuesWithPB(symbol, LOW);
            highvalues = getValuesWithPB(symbol, HIGH);
            closevalues = getValuesWithPB(symbol, CLOSE);
            openvalues = getValuesWithPB(symbol, OPEN);
            volumevalues = getValuesWithPB(symbol, VOLUME);
            coordinates = makeGraphWithPB(values, highvalues, lowvalues);
            this.symbol = symbol;
            paintWithPB((Graphics2D) getGraphics());
        } catch (IOException e) {
            if (e.getMessage().startsWith("Server returned HTTP response code:")) {
                JOptionPane.showMessageDialog(this, "Invalid Symbol.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Unable to retrieve data from the internet. Please check your connection.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unknown error while creating graph.", "Error", JOptionPane.ERROR_MESSAGE);
            //return;
        }
        this.jpb.setValue(jpb.getMaximum());
        dialog.dispose();
        this.jpb = null;
        jpbn = 0;
        bgflag = true;
        new Thread(this).start();
    }

    void setWhich(int which) {
        synchronized (this) {
            bgflag = false;
        }
        if (which >= OPEN && which <= ADJ_CLOSE) {
            whichData = which;
            try {
                if (which == whichData) {
                    return;
                }
                values = getValues(symbol, which);
                lowvalues = getValues(symbol, LOW);
                highvalues = getValues(symbol, HIGH);
                closevalues = getValues(symbol, CLOSE);
                openvalues = getValues(symbol, OPEN);
                volumevalues = getValues(symbol, VOLUME);
                coordinates = makeGraph(values, highvalues, lowvalues);
                whichData = which;
                repaint();
            } catch (IOException e) {
                if (e.getMessage().startsWith("Server returned HTTP response code:")) {
                    JOptionPane.showMessageDialog(this, "Invalid Symbol.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to retrieve data from the internet. Please check your connection.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unknown error while creating graph.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        bgflag = true;
        new Thread(this).start();
    }

    void setSymbol(String symbol) {
        try {

            if (symbol.equals(this.symbol)) {
                return;
            }
            synchronized (this) {
                bgflag = false;
            }
            values = getValues(symbol, whichData);
            lowvalues = getValues(symbol, LOW);
            highvalues = getValues(symbol, HIGH);
            closevalues = getValues(symbol, CLOSE);
            openvalues = getValues(symbol, OPEN);
            volumevalues = getValues(symbol, VOLUME);
            coordinates = makeGraph(values, highvalues, lowvalues);
            this.symbol = symbol;
            repaint();
        } catch (IOException e) {
            if (e.getMessage().startsWith("Server returned HTTP response code:")) {
                JOptionPane.showMessageDialog(this, "Invalid Symbol.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Unable to retrieve data from the internet. Please check your connection.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unknown error while creating graph.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        bgflag = true;
        new Thread(this).start();
    }

    double[] getValues(String symbol, int which) throws MalformedURLException, IOException, NumberFormatException {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -365);
        URL url = getHistoricalURL(symbol, c, Calendar.getInstance());
        char ch[] = new char[1000];
        InputStreamReader ir = new InputStreamReader(url.openStream());
        ir.read(ch);
        ir.close();
        String str = String.valueOf(ch).trim();
        StringTokenizer st = new StringTokenizer(str, "\r\n");
        double d[] = new double[st.countTokens() - 1];
        dates = new String[st.countTokens() - 1];
        int i = 0;
        st.nextToken();
        while (st.hasMoreTokens()) {
            String line = st.nextToken();

            String tokens[] = line.split(",");

            dates[i] = tokens[0];

            d[i++] = (Double.parseDouble(tokens[which]));

        }
        dates = (String[]) reverse(dates);
        return reverse(d);
    }

    double[] getValuesWithPB(String symbol, int which) throws MalformedURLException, IOException, NumberFormatException {
        Calendar c = Calendar.getInstance();
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        c.add(Calendar.DATE, -365);
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        URL url = getHistoricalURL(symbol, c, Calendar.getInstance());
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        char ch[] = new char[1000];
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        InputStreamReader ir = new InputStreamReader(url.openStream());
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        ir.read(ch);
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        ir.close();
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        String str = String.valueOf(ch).trim();
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        StringTokenizer st = new StringTokenizer(str, "\r\n");
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        double d[] = new double[st.countTokens() - 1];
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        dates = new String[st.countTokens() - 1];
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        int i = 0;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        st.nextToken();
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        while (st.hasMoreTokens()) {
            String line = st.nextToken();

            //System.out.println(line);

            String tokens[] = line.split(",");

            dates[i] = tokens[0];

            if (which < tokens.length) d[i] = (Double.parseDouble(tokens[which]));
            if (jpb != null) {
                jpb.setValue(++jpbn);
                try {
                    Thread.sleep(0, 1);
                } catch (Exception e) {
                }
            }
        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        dates = (String[]) reverse(dates);
        //System.out.println(dates);
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        return reverse(d);
    }

    public int[][][] makeGraph(double values[], double highvalues[], double lowvalues[]) throws Exception {
        int width = getWidth();
        int height = getHeight();
        int returnVal[][][] = new int[3][values.length][2];
        double lowest = lowvalues[0];
        double highest = highvalues[0];
        for (int i = 1; i < values.length; i++) {
            if (lowvalues[i] < lowest) {
                lowest = lowvalues[i];

            }
            if (highvalues[i] > highest) {
                highest = highvalues[i];

            }
            if (jpb != null) {
                jpb.setValue(++jpbn);
                try {
                    Thread.sleep(0, 1);
                } catch (Exception e) {
                }
            }
        }
        myhighest = highest;
        mylowest = lowest;
        double xInterval = ((width / 8) * 7) / (values.length - 1);
        myxinterval = xInterval;
        double range = highest - lowest + 1.5;
        boolean isOnePixel = range > height;
        myisonepixel = isOnePixel;
        double yInterval;
        if (isOnePixel) {
            yInterval = range / (height) * 1.2;

        } else {
            yInterval = (height) / range / 1.2;

        }
        myyinterval = yInterval;
        for (int i = 0; i < values.length; i++) {
            double value = values[i];

            double highValue = highvalues[i];

            double lowValue = lowvalues[i];

            returnVal[0][i][0] = returnVal[1][i][0] = returnVal[2][i][0] = (int) (xInterval * i) + (width / 17);

            if (isOnePixel) {
                value /= yInterval;

                highValue /= yInterval;

                lowValue /= yInterval;

            } else {
                value *= yInterval;

                highValue *= yInterval;

                lowValue *= yInterval;

            }
            returnVal[0][i][1] = (int) value + (int) myyinterval;

            returnVal[1][i][1] = (int) highValue + (int) myyinterval;

            returnVal[2][i][1] = (int) lowValue + (int) myyinterval;
            if (jpb != null) {
                jpb.setValue(++jpbn);
                try {
                    Thread.sleep(0, 1);
                } catch (Exception e) {
                }
            }
        }
        return returnVal;
    }

    public int[][][] makeGraphWithPB(double values[], double highvalues[], double lowvalues[]) throws Exception {
        int width = getWidth();
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        int height = getHeight();
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        int returnVal[][][] = new int[3][values.length][2];
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        double lowest = lowvalues[0];
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        double highest = highvalues[0];
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        for (int i = 1; i < values.length; i++) {
            if (lowvalues[i] < lowest) {
                lowest = lowvalues[i];

            }
            if (highvalues[i] > highest) {
                highest = highvalues[i];

            }
        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        myhighest = highest;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        mylowest = lowest;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        double xInterval = ((width / 8) * 7) / (values.length - 1);
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        myxinterval = xInterval;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        double range = highest - lowest + 1.5;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        boolean isOnePixel = range > height;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        myisonepixel = isOnePixel;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        double yInterval;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        if (isOnePixel) {
            yInterval = range / (height) * 1.2;

        } else {
            yInterval = (height) / range / 1.2;

        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        myyinterval = yInterval;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < values.length; i++) {
            double value = values[i];

            double highValue = highvalues[i];

            double lowValue = lowvalues[i];

            returnVal[0][i][0] = returnVal[1][i][0] = returnVal[2][i][0] = (int) (xInterval * i) + (width / 17);

            if (isOnePixel) {
                value /= yInterval;

                highValue /= yInterval;

                lowValue /= yInterval;

            } else {
                value *= yInterval;

                highValue *= yInterval;

                lowValue *= yInterval;

            }
            returnVal[0][i][1] = (int) value + (int) myyinterval;

            returnVal[1][i][1] = (int) highValue + (int) myyinterval;

            returnVal[2][i][1] = (int) lowValue + (int) myyinterval;
            if (jpb != null) {
                jpb.setValue(++jpbn);
                try {
                    Thread.sleep(0, 1);
                } catch (Exception e) {
                }
            }
        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        return returnVal;
    }

    @Override
    public void setSize(int x, int y) {
        super.setSize(x, y);
        try {
            coordinates = makeGraph(values, highvalues, lowvalues);
        } catch (Exception e) {
        }
        repaint();
    }

    public void paintWithPB(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD));
        Dimension d = new Dimension();
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        getSize(d);
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        g2.setBackground(Color.white);
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        g2.clearRect(0, 0, (int) d.getWidth(), (int) d.getHeight());
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        g2.setColor(new Color(66, 66, 66));
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        g2.drawLine(0, 0, 0, getHeight());
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        g2.drawLine(1, 0, 1, getHeight());
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        g2.drawLine(0, getHeight(), getWidth(), getHeight());
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        int ylowest = coordinates[0][0][1];
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        int yhighest = coordinates[0][0][1];
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        int ygain = 0;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < coordinates[0].length; i++) {
            if (coordinates[2][i][1] < ylowest) {
                ylowest = coordinates[2][i][1];

            }
            if (coordinates[1][i][1] > yhighest) {
                yhighest = coordinates[1][i][1];

            }
            if (jpb != null) {
                jpb.setValue(++jpbn);
                try {
                    Thread.sleep(0, 1);
                } catch (Exception e) {
                }
            }
        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        while (ylowest < (getHeight() / 2) * -1) {
            ygain++;
            ylowest++;

        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        while (yhighest > (getHeight() / 2)) {
            ygain--;
            yhighest--;

        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        int x = coordinates[0][0][0];
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        int y = (getHeight() / 2) - coordinates[0][0][1] + (int) myyinterval;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        y -= ygain;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        double tempInterval = 0;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        if (myisonepixel) {
            tempInterval = 5;

        } else {
            tempInterval = myyinterval * 5;

        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        double a = 1;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        while (tempInterval < 30) {
            tempInterval *= 2;
            a *= 2;

        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        if (myhighest - mylowest < getHeight() / 30) {
            tempInterval = myyinterval;
            a = 0.2;

        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        int xaxis = (getHeight() / 2) - ygain;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        if (myisonepixel) {
            scaleLabel.setText("Scale: Y axis - 1 unit = $" + String.valueOf((int) (5 * a * myyinterval)) + ", X axis - 1 unit = 1 day");
        } else {
            scaleLabel.setText("Scale: Y axis - 1 unit = $" + String.valueOf((int) (5 * a)) + ", X axis - 1 unit = 1 day");
        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        int z = 0;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        g2.setColor(new Color(220, 220, 220));
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        for (double i = 0; i < ((getHeight() / 2) + ygain) / tempInterval; i++) {
            g2.drawLine(0, xaxis + (int) (tempInterval * i), getWidth(), xaxis + (int) (tempInterval * i));

        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        g2.setColor(new Color(00, 00, 00));
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        ///g2.drawLine(0, (getHeight() / 2) + ygain, getWidth(), (getHeight() / 2) + ygain); ----- Old xaxis, doesnt work anymore HAHAHA
        for (double i = xaxis / tempInterval; i >= 0; i--) { // draw horizontal grid lines and label $$ intervals.
            if (tempInterval * i == xaxis) { // for the xaxis line
                g2.setColor(new Color(100, 100, 100));
            } else {
                g2.setColor(new Color(220, 220, 220));
            }

            g2.drawLine(0, (int) (tempInterval * i), getWidth(), (int) (tempInterval * i));
            g2.setColor(Color.black);

            if (myisonepixel && z == 0) {
                g2.drawString(String.valueOf((int) (z * 5 * a * myyinterval)), 10, (int) (tempInterval * i) + 6);
            } else if (myisonepixel && z != 0) {
                g2.drawString(String.valueOf((int) (z * 5 * a * myyinterval)), 10, (int) (tempInterval * i) + 6);
            } else if (!myisonepixel && z == 0) {
                g2.drawString(String.valueOf((int) (z * 5 * a)), 10, (int) (tempInterval * i) + 6);
            } else {
                g2.drawString(String.valueOf((int) (z * 5 * a)), 10, (int) (tempInterval * i) + 6);
            }

            z++;

        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        for (int i = coordinates[0][0][0], j = 0; j < values.length; i += myxinterval, j++) { //draw vertical grid lines and label xaxis with dates.
            g2.setColor(new Color(220, 220, 220));

            g2.drawLine(i, 0, i, getHeight() - (g2.getFontMetrics().getAscent() + 2));

            g2.setColor(Color.BLACK);

            String datea[];

            try {
                datea = dates[j].split("-");
            } catch (Exception e) {
                continue;
            }

            String date = "";

            for (int k = 1; k < datea.length; k++) {
                if (k != 1) {
                    date += "/";
                }
                String s = datea[k];
                while (s.startsWith("0")) {
                    s = s.substring(1);
                }
                date += s;
                if (jpb != null) {
                    jpb.setValue(++jpbn);
                    try {
                        Thread.sleep(0, 1);
                    } catch (Exception e) {
                    }
                }

            }
            g2.drawString(date, i - (g2.getFontMetrics().stringWidth(date) / 2), getHeight() - 2);
            if (jpb != null) {
                jpb.setValue(++jpbn);
                try {
                    Thread.sleep(0, 1);
                } catch (Exception e) {
                }
            }
        }
        z = 0;
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        for (double i = 0; i <= (getHeight() - xaxis) / tempInterval; i++) {
            if (myisonepixel && z == 0) {
                g2.drawString(String.valueOf((int) (z * -5 * a * myyinterval)), 10, (int) (xaxis + tempInterval * i) + 6);
            } else if (myisonepixel && z != 0) {
                g2.drawString(String.valueOf((int) (z * -5 * a * myyinterval)), 10, (int) (xaxis + tempInterval * i) + 6);
            } else if (!myisonepixel && z == 0) {
                g2.drawString(String.valueOf((int) (z * -5 * a)), 10, (int) (xaxis + tempInterval * i) + 6);
            } else {
                g2.drawString(String.valueOf((int) (z * -5 * a)), 10, (int) (xaxis + tempInterval * i) + 6);
            }

            z++;

        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < coordinates[0].length; i++) { //plot graph
            g2.setColor(new Color(0, 100, 255));

            int x1 = coordinates[0][i][0];

            int y1 = (getHeight() / 2) - coordinates[0][i][1] + (int) myyinterval;

            int lowX = coordinates[2][i][0];

            int highX = coordinates[1][i][0];

            int lowY = (getHeight() / 2) - coordinates[2][i][1] + (int) myyinterval;

            int highY = (getHeight() / 2) - coordinates[1][i][1] + (int) myyinterval;

            y1 -= ygain;

            lowY -= ygain;

            highY -= ygain;

            g2.fillRect(x1 - 2, y1 - 2, 4, 4); //draw points

            g2.setColor(Color.RED);

            g2.fillRect(lowX - 2, lowY - 2, 4, 4);

            g2.setColor(new Color(0, 210, 0));

            g2.fillRect(highX - 2, highY - 2, 4, 4);

            g2.setColor(new Color(0, 100, 255));

            g2.drawLine(x, y, x1, y1); //connect the dots.

            g2.setColor(Color.RED);

            g2.drawLine(lowX, lowY, x1, y1);//draw high-low lines

            g2.setColor(new Color(0, 210, 0));

            g2.drawLine(x1, y1, highX, highY);

            g2.setColor(Color.black);

            //Time to label the dots!
            if (i == 0) {
                if (values[i] > 0) {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 + 7);
                } else {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 - 7);
                }

            } else if (i == values.length - 1) {
                if (values[i] > 0) {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 + 7);
                } else {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 - 7);
                }

            } else {
                if (values[i] > 0) {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 + 7);
                } else {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 - 7);
                }

            }
            x = x1;

            y = y1;
            if (jpb != null) {
                jpb.setValue(++jpbn);
                try {
                    Thread.sleep(0, 1);
                } catch (Exception e) {
                }
            }
        }
        if (jpb != null) {
            jpb.setValue(++jpbn);
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD));
        Dimension d = new Dimension();

        getSize(d);

        g2.setBackground(Color.white);

        g2.clearRect(0, 0, (int) d.getWidth(), (int) d.getHeight());

        g2.setColor(new Color(66, 66, 66));

        g2.drawLine(0, 0, 0, getHeight());

        g2.drawLine(1, 0, 1, getHeight());

        g2.drawLine(0, getHeight(), getWidth(), getHeight());

        int ylowest = coordinates[0][0][1];

        int yhighest = coordinates[0][0][1];

        int ygain = 0;

        for (int i = 0; i < coordinates[0].length; i++) {
            if (coordinates[0][i][1] < ylowest) {
                ylowest = coordinates[0][i][1];

            }
            if (coordinates[0][i][1] > yhighest) {
                yhighest = coordinates[0][i][1];

            }
        }

        while (ylowest < (getHeight() / 2) * -1) {
            ygain++;
            ylowest++;

        }

        while (yhighest > (getHeight() / 2)) {
            ygain--;
            yhighest--;

        }

        int x = coordinates[0][0][0];

        int y = (getHeight() / 2) - coordinates[0][0][1] + (int) myyinterval;

        y -= ygain;

        double tempInterval = 0;

        if (myisonepixel) {
            tempInterval = 5;

        } else {
            tempInterval = myyinterval * 5;

        }

        double a = 1;

        while (tempInterval < 30) {
            tempInterval *= 2;
            a *= 2;

        }

        if (myhighest - mylowest < getHeight() / 30) {
            tempInterval = myyinterval;
            a = 0.2;

        }

        int xaxis = (getHeight() / 2) - ygain;

        if (myisonepixel) {
            scaleLabel.setText("Scale: Y axis - 1 unit = $" + String.valueOf((int) (5 * a * myyinterval)) + ", X axis - 1 unit = 1 day");
        } else {
            scaleLabel.setText("Scale: Y axis - 1 unit = $" + String.valueOf((int) (5 * a)) + ", X axis - 1 unit = 1 day");
        }

        int z = 0;

        g2.setColor(new Color(220, 220, 220));

        for (double i = 0; i < ((getHeight() / 2) + ygain) / tempInterval; i++) {
            g2.drawLine(0, xaxis + (int) (tempInterval * i), getWidth(), xaxis + (int) (tempInterval * i));

        }

        g2.setColor(new Color(00, 00, 00));

        ///g2.drawLine(0, (getHeight() / 2) + ygain, getWidth(), (getHeight() / 2) + ygain); ----- Old xaxis, doesnt work anymore HAHAHA
        for (double i = xaxis / tempInterval; i >= 0; i--) { // draw horizontal grid lines and label $$ intervals.
            if (tempInterval * i == xaxis) { // for the xaxis line
                g2.setColor(new Color(100, 100, 100));
            } else {
                g2.setColor(new Color(220, 220, 220));
            }

            g2.drawLine(0, (int) (tempInterval * i), getWidth(), (int) (tempInterval * i));
            g2.setColor(Color.black);

            if (myisonepixel && z == 0) {
                g2.drawString(String.valueOf((int) (z * 5 * a * myyinterval)), 10, (int) (tempInterval * i) + 6);
            } else if (myisonepixel && z != 0) {
                g2.drawString(String.valueOf((int) (z * 5 * a * myyinterval)), 10, (int) (tempInterval * i) + 6);
            } else if (!myisonepixel && z == 0) {
                g2.drawString(String.valueOf((int) (z * 5 * a)), 10, (int) (tempInterval * i) + 6);
            } else {
                g2.drawString(String.valueOf((int) (z * 5 * a)), 10, (int) (tempInterval * i) + 6);
            }

            z++;

        }

        for (int i = coordinates[0][0][0], j = 0; j < values.length; i += myxinterval, j++) { //draw vertical grid lines and label xaxis with dates.
            g2.setColor(new Color(220, 220, 220));

            g2.drawLine(i, 0, i, getHeight() - (g2.getFontMetrics().getAscent() + 2));

            g2.setColor(Color.BLACK);
            String datea[] = dates[j].split("-");

            String date = "";

            for (int k = 1; k < datea.length; k++) {
                if (k != 1) {
                    date += "/";
                }
                String s = datea[k];
                while (s.startsWith("0")) {
                    s = s.substring(1);
                }
                date += s;

            }
            g2.drawString(date, i - (g2.getFontMetrics().stringWidth(date) / 2), getHeight() - 2);
        }
        z = 0;

        for (double i = 0; i <= (getHeight() - xaxis) / tempInterval; i++) {
            if (myisonepixel && z == 0) {
                g2.drawString(String.valueOf((int) (z * -5 * a * myyinterval)), 10, (int) (xaxis + tempInterval * i) + 6);
            } else if (myisonepixel && z != 0) {
                g2.drawString(String.valueOf((int) (z * -5 * a * myyinterval)), 10, (int) (xaxis + tempInterval * i) + 6);
            } else if (!myisonepixel && z == 0) {
                g2.drawString(String.valueOf((int) (z * -5 * a)), 10, (int) (xaxis + tempInterval * i) + 6);
            } else {
                g2.drawString(String.valueOf((int) (z * -5 * a)), 10, (int) (xaxis + tempInterval * i) + 6);
            }

            z++;

        }

        for (int i = 0; i < coordinates[0].length; i++) { //plot graph
            g2.setColor(new Color(0, 100, 255));

            int x1 = coordinates[0][i][0];

            int y1 = (getHeight() / 2) - coordinates[0][i][1] + (int) myyinterval;

            int lowX = coordinates[2][i][0];

            int highX = coordinates[1][i][0];

            int lowY = (getHeight() / 2) - coordinates[2][i][1] + (int) myyinterval;

            int highY = (getHeight() / 2) - coordinates[1][i][1] + (int) myyinterval;

            y1 -= ygain;

            lowY -= ygain;

            highY -= ygain;

            g2.fillRect(x1 - 2, y1 - 2, 4, 4); //draw points

            g2.setColor(Color.RED);

            g2.fillRect(lowX - 2, lowY - 2, 4, 4);

            g2.setColor(new Color(0, 210, 0));

            g2.fillRect(highX - 2, highY - 2, 4, 4);

            g2.setColor(new Color(0, 100, 255));

            g2.drawLine(x, y, x1, y1); //connect the dots.

            g2.setColor(Color.RED);

            g2.drawLine(lowX, lowY, x1, y1);//draw high-low lines

            g2.setColor(new Color(0, 210, 0));

            g2.drawLine(x1, y1, highX, highY);

            g2.setColor(Color.black);

            g2.setPaintMode();

            //Time to label the dots!
            if (i == 0) {
                if (values[i] > 0) {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 + 7);
                } else {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 - 7);
                }

            } else if (i == values.length - 1) {
                if (values[i] > 0) {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 + 7);
                } else {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 - 7);
                }

            } else {
                if (values[i] > 0) {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 + 7);
                } else {
                    g2.drawString(String.valueOf(values[i]), x1 + 10, y1 - 7);
                }

            }
            x = x1;

            y = y1;
        }

    }

    URL getHistoricalURL(String symbol, Calendar startDate, Calendar endDate) throws MalformedURLException {
        return new URL("http://ichart.finance.yahoo.com/table.csv?s=" + symbol + "&a=" + startDate.get(Calendar.MONTH) + "&b=" + startDate.get(Calendar.DATE) + "&c=" + startDate.get(Calendar.YEAR) + "&d=" + endDate.get(Calendar.MONTH) + "&e=" + endDate.get(Calendar.DATE) + "&f=" + endDate.get(Calendar.YEAR) + "&g=d&ignore=.csv");
    }

    double[] reverse(double b[]) {
        for (int left = 0, right = b.length - 1; left < right; left++, right--) {
            double temp = b[left];
            b[left] = b[right];
            b[right] = temp;
        }
        return b;
    }

    Object[] reverse(Object b[]) {
        for (int left = 0, right = b.length - 1; left < right; left++, right--) {
            Object temp = b[left];
            b[left] = b[right];
            b[right] = temp;
        }
        return b;
    }

//    public static void main(String args[]) {
//        JFrame frame = new JFrame("NStock - Graph");
//        frame.setSize(1024, 768);
//        //frame.setMinimumSize(new Dimension(480, 360));
//        frame.setLayout(new BorderLayout());
//        JLabel label = new JLabel("", JLabel.CENTER);
//        frame.add(new JScrollPane(new GraphPanel(label, "biocon.ns", GraphPanel.CLOSE)), BorderLayout.CENTER);
//        frame.add(label, BorderLayout.SOUTH);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
    public void run() {
        while (bgflag) {
            try {
                if (Math.abs(getMousePosition().x - getWidth() / 17) % myxinterval <= 10) {
                    int n = (int) Math.rint((getMousePosition().x - getWidth() / 17) / myxinterval);
                    double high = highvalues[n];
                    double low = lowvalues[n];
                    double open = openvalues[n];
                    double close = closevalues[n];
                    long volume = new Double(volumevalues[n]).longValue();
                    setToolTipText("<html>Date: <b>" + dates[n] + "</b><br />Open: <b>" + open + "</b><br />High: <b>" + high + "</b><br />Low: <b>" + low + "</b><br />Volume: <b>" + volume + "</b><br />Close: <b>" + close + "</b></html>");
                }
            } catch (Exception e) {
                continue;
            }

        }
    }
}