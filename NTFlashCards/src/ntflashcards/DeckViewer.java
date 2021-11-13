package ntflashcards;

import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileFilter;

public class DeckViewer extends JFrame implements ActionListener, ChangeListener {

    JTabbedPane deckPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    JMenuBar mbar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem fileOpen = new JMenuItem("Open Deck...");
    JMenuItem fileImport = new JMenuItem("Import Deck (tab-delmited)");
    JMenuItem fileCloseDeck = new JMenuItem("Close current deck");
    JMenuItem fileExit = new JMenuItem("Exit");
    int selectedIndex = 0;
    JFrame THIS = this;

    public DeckViewer() {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(deckPane, BorderLayout.CENTER);
        file.add(fileOpen);
        file.add(fileImport);
        file.add(fileCloseDeck);
        file.add(fileExit);
        mbar.add(file);
        setJMenuBar(mbar);
        setSize(1024, 640);
        setVisible(true);
        setTitle("NTFlashCard Deck Viewer");
        fileOpen.addActionListener(this);
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        fileImport.addActionListener(this);
        fileCloseDeck.addActionListener(this);
        fileCloseDeck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.CTRL_MASK));
        fileExit.addActionListener(this);
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_MASK));
        deckPane.addChangeListener(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                int i = JOptionPane.showConfirmDialog(THIS, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String args[]) {
        new DeckViewer();
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src == fileOpen) {
            JFileChooser jfc = new JFileChooser();
            jfc.addChoosableFileFilter(new FileFilter() {

                public String getDescription() {
                    return "Deck Files (*.deck)";
                }

                public boolean accept(File f) {
                    if (f.getName().endsWith(".deck") || f.isDirectory()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    Deck deck = Deck.deckFromFile(jfc.getSelectedFile());
                    deckPane.addTab(deck.getName(), new DeckViewerPanel(deck));
                    deckPane.setSelectedIndex(deckPane.getTabCount() - 1);
                    selectedIndex = deckPane.getSelectedIndex();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (src == fileCloseDeck) {
            int i = JOptionPane.showConfirmDialog(this, "Are you sure you want to close this deck?");
            if (i == JOptionPane.YES_OPTION) deckPane.removeTabAt(selectedIndex);
        } else if (src == fileExit) {
            int i = JOptionPane.showConfirmDialog(this, "Are you sure you want to close this deck?");
            if (i == JOptionPane.YES_OPTION) System.exit(0);
        } else if (src == fileImport) {
            JFileChooser jfc = new JFileChooser();
            jfc.addChoosableFileFilter(new FileFilter() {

                public boolean accept(File f) {
                    if (f.getName().endsWith(".txt") || f.isDirectory()) {
                        return true;
                    } else {
                        return false;
                    }
                }

                public String getDescription() {
                    return "Text Files (*.txt)";
                }
            });
            jfc.setAcceptAllFileFilterUsed(false);
            if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    Deck deck = Deck.deckFromFile(jfc.getSelectedFile(), "\t");
                    deckPane.addTab(deck.getName(), new DeckViewerPanel(deck));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void stateChanged(ChangeEvent ce) {
        selectedIndex = deckPane.getSelectedIndex();
    }
}
