package ntflashcards;

import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileFilter;

public class DeckBuilder extends JFrame implements ActionListener, ChangeListener {

    JTabbedPane deckPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    JMenuBar mbar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem fileNew = new JMenuItem("New Deck...");
    JMenuItem fileOpen = new JMenuItem("Open Deck...");
    JMenuItem fileImport = new JMenuItem("Import Deck (tab-delmited)");
    JMenu fileExport = new JMenu("Export to");
    JMenuItem fileExportFile = new JMenuItem("Text File (tab-delimited)");
    JMenuItem fileExportViewer = new JMenuItem("Deck Viewer");
    JMenuItem fileCloseDeck = new JMenuItem("Close current deck");
    JMenuItem fileExit = new JMenuItem("Exit");
    int selectedIndex = 0;
    JFrame THIS = this;

    public DeckBuilder() {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(deckPane, BorderLayout.CENTER);
        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileImport);
        fileExport.add(fileExportFile);
        fileExport.add(fileExportViewer);
        file.add(fileExport);
        file.add(fileCloseDeck);
        file.add(fileExit);
        mbar.add(file);
        setJMenuBar(mbar);
        setSize(1024, 640);
        setResizable(true);
        setVisible(true);
        setTitle("NTFlashCard Deck Builder");
        fileNew.addActionListener(this);
        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        fileOpen.addActionListener(this);
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        fileImport.addActionListener(this);
        fileExportFile.addActionListener(this);
        fileExportViewer.addActionListener(this);
        fileCloseDeck.addActionListener(this);
        fileCloseDeck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.CTRL_MASK));
        fileExit.addActionListener(this);
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_MASK));
        deckPane.addChangeListener(this);
        actionPerformed(new ActionEvent(fileNew, 0, "new"));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                int i = JOptionPane.showConfirmDialog(THIS, "Save changes to " + ((DeckEditorPanel) (deckPane.getSelectedComponent())).getDeck().getName() + "?");
                if (i == JOptionPane.YES_OPTION) {
                    try {
                        for (selectedIndex = 0; selectedIndex < deckPane.getTabCount(); selectedIndex++) {
                            ((DeckEditorPanel) deckPane.getComponentAt(selectedIndex)).saveDeck();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                } else if (i == JOptionPane.NO_OPTION) {
                    System.exit(0);
                } else {
                }
            }
        });
    }

    public static void main(String args[]) {
        if (JOptionPane.showConfirmDialog(new JFrame(), "Open builder?") == JOptionPane.OK_OPTION) new DeckBuilder();
        else new DeckViewer();
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src == fileNew) {
            Deck deck = new Deck("Untitled Deck");
            deckPane.addTab(deck.getName(), new DeckEditorPanel(deck));
            deckPane.setSelectedIndex(deckPane.getTabCount() - 1);
            selectedIndex = deckPane.getSelectedIndex();
        } else if (src == fileOpen) {
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
                    deckPane.addTab(deck.getName(), new DeckEditorPanel(deck));
                    deckPane.setSelectedIndex(deckPane.getTabCount() - 1);
                    selectedIndex = deckPane.getSelectedIndex();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (src == fileCloseDeck) {
            int i = JOptionPane.showConfirmDialog(this, "Save changes to " + ((DeckEditorPanel) (deckPane.getSelectedComponent())).getDeck().getName() + "?");
            if (i == JOptionPane.YES_OPTION) {
                try {
                    ((DeckEditorPanel) deckPane.getSelectedComponent()).saveDeck();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                deckPane.removeTabAt(selectedIndex);
            } else if (i == JOptionPane.NO_OPTION) {
                deckPane.removeTabAt(selectedIndex);
            } else {
            }
        } else if (src == fileExit) {
            int i = JOptionPane.showConfirmDialog(this, "Save changes to " + ((DeckEditorPanel) (deckPane.getSelectedComponent())).getDeck().getName() + "?");
            if (i == JOptionPane.YES_OPTION) {
                try {
                    for (selectedIndex = 0; selectedIndex < deckPane.getTabCount(); selectedIndex++) {
                        ((DeckEditorPanel) deckPane.getComponentAt(selectedIndex)).saveDeck();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            } else if (i == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else {
            }
        } else if (src == fileExportFile) {
            try {
                ((DeckEditorPanel) deckPane.getSelectedComponent()).saveDeck("\t");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    deckPane.addTab(deck.getName(), new DeckEditorPanel(deck));
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
