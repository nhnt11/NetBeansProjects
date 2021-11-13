package NStock;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;

public class MainApp extends JFrame implements ActionListener {

    boolean firstTimeSwitchToGraph = true;
    JMenuBar mainMenu = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem fileNewSymbol = new JMenuItem("New Symbol...");
    JMenuItem fileDeleteSymbol = new JMenuItem("Delete Symbol");
    JMenuItem fileExport = new JMenuItem("Export selected symbol as CSV...");
    JMenuItem fileExportAll = new JMenuItem("Export all symbols as CSV...");
    JMenuItem fileUpdate = new JMenuItem("Update Quotes");
    JMenuItem fileExit = new JMenuItem("Exit");
    JTabbedPane tabPane = new JTabbedPane();
    JPanel tablePanel = new JPanel(new BorderLayout());
    JTable stockTable;
    JScrollPane tableSP;
    JList portfolioList = new JList();
    JPanel graphPanel = new JPanel(new BorderLayout());
    JPanel graphPane = new JPanel(new BorderLayout());
    JComboBox symbolList = new JComboBox();
    JPanel graphPanel2 = new JPanel(new BorderLayout());
    JLabel scaleLabel = new JLabel("", JLabel.CENTER);
    GraphPanel graph = new GraphPanel(scaleLabel, "", GraphPanel.CLOSE);
    javax.swing.Timer t = new javax.swing.Timer(1000 * 60, this);
    int nextRow = 0;

    public MainApp(String[][] data) {
        file.add(fileNewSymbol);
        file.add(fileDeleteSymbol);
        file.addSeparator();
        file.add(fileExport);
        file.add(fileExportAll);
        file.addSeparator();
        file.add(fileUpdate);
        file.addSeparator();
        file.add(fileExit);
        mainMenu.add(file);
        setLayout(new BorderLayout());
        setJMenuBar(mainMenu);
        stockTable = new JTable();
        stockTable.setModel(new StockTableModel(stockTable, data, this));
        tableSP = new JScrollPane(stockTable);
        tablePanel.add(tableSP, BorderLayout.CENTER);
        tabPane.addTab("Portfolio", tablePanel);
        graphPane.add(symbolList, BorderLayout.NORTH);
        graphPanel2.add(new JScrollPane(graph), BorderLayout.CENTER);
        graphPanel2.add(scaleLabel, BorderLayout.SOUTH);
        graphPane.add(graphPanel2, BorderLayout.CENTER);
        graphPanel.add(graphPane, BorderLayout.CENTER);
        tabPane.addTab("Graph", graphPanel);
        add(tabPane, BorderLayout.CENTER);
        setSize(800, 600);
        setMinimumSize(new Dimension(480, 360));
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        final JFrame THIS = this;
        final JProgressBar jpb = new JProgressBar(0, 370);
        jpb.setStringPainted(true);
        jpb.setValue(0);
        actionPerformed(new ActionEvent(fileUpdate, 0, "Update Quotes"));
        setVisible(true);
        tabPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (tabPane.getSelectedIndex() == 1) {
                    //tabPane.setEnabled(false);
                    final JProgressBar jpb = new JProgressBar(0, 370);
                    jpb.setStringPainted(true);
                    jpb.setValue(0);
                    try {
                        final String str = (String) stockTable.getModel().getValueAt(stockTable.getSelectedRow(), 0);
                        if (str == null || str.equals("")) {
                            tabPane.setEnabled(true);
                            return;
                        }
                        symbolList.setSelectedIndex(stockTable.getSelectedRow());
                        new Thread(new Runnable() {

                            public void run() {
                                graph.setSymbol(str);
                                //tabPane.setEnabled(true);
                            }
                        }).start();
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(portfolioList, "Please select a symbol first", "Error", JOptionPane.ERROR_MESSAGE);
                        tabPane.setSelectedIndex(0);
                        tabPane.setEnabled(true);
                    }
                }
            }
        });
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    new File(System.getProperty("user.home") + "/.nstock/").mkdirs();
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.home") + "/.nstock/test.dat"));
                    os.writeObject(((StockTableModel) stockTable.getModel()).getAllData());
                    os.flush();
                    os.close();
                    System.exit(0);
                } catch (Exception ex) {
                    System.exit(1);
                }
                System.exit(0);
            }
        });
        addListeners();
    }

    void addListeners() {
        fileNewSymbol.addActionListener(this);
        fileDeleteSymbol.addActionListener(this);
        fileUpdate.addActionListener(this);
        fileExport.addActionListener(this);
        fileExportAll.addActionListener(this);
        fileExit.addActionListener(this);
        symbolList.addActionListener(this);
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            ObjectInputStream os = new ObjectInputStream(new FileInputStream(System.getProperty("user.home") + "/.nstock/test.dat"));
            new MainApp((String[][]) os.readObject());
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            new MainApp(new String[100][10]);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src == fileNewSymbol) {
            while (true) {
                String newSymbol = JOptionPane.showInputDialog("Enter Symbol: ");
                if (newSymbol == null) {
                    return;
                }
                try {
                    ((StockTableModel) stockTable.getModel()).addSymbol(newSymbol);
                    break;
                } catch (IOException e) {
                    if (e.getMessage().startsWith("Server returned HTTP response code:") || e.getMessage().startsWith("Symbol not found")) {
                        JOptionPane.showMessageDialog(this, "Invalid Symbol.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Unable to retrieve data from the internet. Please check your connection.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                } catch (SymbolException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (src == fileDeleteSymbol) {
            if (tabPane.getSelectedIndex() == 0) {
                ((StockTableModel) stockTable.getModel()).removeSymbolAt(stockTable.getSelectedRow());
            }
            if (tabPane.getSelectedIndex() == 1) {
                ((StockTableModel) stockTable.getModel()).removeSymbolAt(symbolList.getSelectedIndex());
            }
        } else if (src == fileExit) {
            try {
                new File(System.getProperty("user.home") + "/.nstock/").mkdirs();
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.home") + "/.nstock/test.dat"));
                os.writeObject(((StockTableModel) stockTable.getModel()).getAllData());
                os.flush();
                os.close();
                System.exit(0);
            } catch (Exception ex) {
            }
        } else if (src == fileUpdate || src == t) {
            tabPane.setEnabled(false);
            stockTable.setModel(new StockTableModel(stockTable, ((StockTableModel) stockTable.getModel()).getAllData(), this));
            tabPane.setEnabled(true);
        } else if (src == symbolList && tabPane.getSelectedIndex() == 1 && tabPane.isEnabled()) {
            tabPane.setEnabled(false);
            final JFrame THIS = this;
            final JProgressBar jpb = new JProgressBar(0, 370);
            jpb.setStringPainted(true);
            jpb.setValue(0);
            new Thread(new Runnable() {

                public void run() {
                    graph.setSymbol((String) symbolList.getSelectedItem());
                }
            }).start();
            tabPane.setEnabled(true);
        } else if (src == fileExportAll) {
            String str = "";
            String data[][] = ((StockTableModel) stockTable.getModel()).getAllData();
            for (int i = 0; i < data.length; i++) {
                String s = "";
                for (int j = 0; j < data[i].length && data[i][j] != null && !data[i][j].equals(""); j++) {
                    if (j == 1) {
                        s = ",";
                    }
                    str += s + data[i][j];
                }
                str += "\r\n";
            }
            str = str.trim();
            saveData(str);
        } else if (src == fileExport) {
            String s[] = ((StockTableModel) stockTable.getModel()).getAllData()[stockTable.getSelectedRow()];
            String str = s[0];
            for (int i = 1; i < s.length; i++) {
                str += "," + s[i];
            }
            str = str.trim();
            saveData(str);
        }
    }

    void saveData(String str) {
        try {
            JFileChooser jfc = new JFileChooser();
            if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = jfc.getSelectedFile();
                if (!f.getName().endsWith(".csv")) {
                    f = new File(f.getAbsolutePath() + ".csv");
                }
                FileWriter fw = new FileWriter(f);
                fw.write(str);
                fw.flush();
                fw.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class StockTableModel extends AbstractTableModel implements Serializable {

    final String columnNames[] = {"Symbol", "Price", "Date", "Time", "$Change", "Open", "High", "Low", "Volume"};
    URL url;
    InputStreamReader ir;
    int nextRow = 0;
    JTable table;
    MainApp app;
    Vector symbols = new Vector();
    String data[][] = new String[100][10];

    public StockTableModel(JTable table, String[][] data_, MainApp app) {
        this.table = table;
        this.app = app;
        String[][] data__ = data_;
        app.symbolList.removeAllItems();
        try {
            for (int i = 0; i < data_.length; i++) {
                if (data_[i][0] == null || data_[i][0].equals("")) {
                    break;
                }
                addSymbol(data_[i][0]);
            }
            //app.symbolList = new JComboBox(symbols);
            //app.addListeners();
        } catch (IOException e) {
            if (e.getMessage().contains("Server returned HTTP response code:")) {
                JOptionPane.showMessageDialog(table, "Invalid Symbol.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(table, "Unable to retrieve data from the internet. Please check your connection.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            this.data = data__;
        } catch (Exception e) {
            if (e.getMessage().contains("Symbol not found")) {
                JOptionPane.showMessageDialog(table, "Invalid Symbol.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            this.data = data__;
        }
    }

    String[] addSymbol(String symbol) throws IOException, SymbolException {
        if (symbols.contains(symbol.toUpperCase())) {
            throw new SymbolException("Symbol already exists!");
        }
        url = getQuotesURL(symbol);
        char c[] = new char[1000];
        ir = new InputStreamReader(url.openStream());
        ir.read(c);
        String str = String.valueOf(c).trim();
        StringTokenizer st = new StringTokenizer(str, "\"\r\n,");
        String temp[] = new String[columnNames.length];
        for (int i = 0; st.hasMoreTokens(); i++) {
            final String token = st.nextToken();
            temp[i] = token;
            if (i == 2 && token.contains("N/A")) {
                throw new IOException("Symbol not found");
            }
        }
        data[nextRow++] = temp;
        symbols.add(temp[0]);
        app.symbolList.addItem(temp[0]);
        table.repaint();
        return temp;
    }

    void removeSymbolAt(int i) {
        if (((String) getValueAt(i, 0)).length() == 0) {
            return;
        }
        for (int j = i; j < 99 - i; j++) {
            data[j] = data[j + 1];
        }
        data[99] = new String[10];
        app.symbolList.removeItemAt(i);
        app.nextRow--;
        nextRow--;
        symbols.removeElementAt(i);
        table.repaint();
    }

    String[][] getAllData() {
        return data;
    }

    URL getQuotesURL(String symbol) throws MalformedURLException {
        return new URL("http://download.finance.yahoo.com/d/quotes.csv?s=" + symbol + "&f=sl1d1t1c1ohgv&e=.csv");
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }
}

class SymbolException extends Exception {

    public SymbolException(String msg) {
        super(msg);
    }
}
