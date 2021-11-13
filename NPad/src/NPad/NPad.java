package NPad;

import com.lowagie.text.Chunk;
import com.lowagie.text.FontFactory;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.rtf.*;
import javax.swing.undo.UndoManager;

public class NPad extends JFrame implements ActionListener, ItemListener, UndoableEditListener, Runnable {

    final double version = 3.0;
    boolean return_val;
    String return_val2;
    StyleContext sc = new StyleContext();
    StyledDocument doc = new DefaultStyledDocument(sc);
    EditorKit kit = new RTFEditorKit();
    JTextPane inputField = new JTextPane();
    JScrollPane jsp;

    // <editor-fold defaultstate="collapsed" desc="Menus">
    JMenuBar mbar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem fileNew = new JMenuItem("New");
    JMenuItem fileNew0 = new JMenuItem("New");
    JMenuItem fileOpen = new JMenuItem("Open...");
    JMenuItem fileOpen0 = new JMenuItem("Open...");
    JMenuItem fileSave = new JMenuItem("Save");
    JMenuItem fileSave0 = new JMenuItem("Save");
    JMenuItem fileSaveAs = new JMenuItem("Save as...");
    JMenuItem filePrint = new JMenuItem("Print...");
    JMenuItem filePrint0 = new JMenuItem("Print...");
    JMenuItem fileExit = new JMenuItem("Exit");
    JMenu edit = new JMenu("Edit");
    JMenuItem editCut = new JMenuItem("Cut");
    JMenuItem editCut0 = new JMenuItem("Cut");
    JMenuItem editCopy = new JMenuItem("Copy");
    JMenuItem editCopy0 = new JMenuItem("Copy");
    JMenuItem editPaste = new JMenuItem("Paste");
    JMenuItem editPaste0 = new JMenuItem("Paste");
    JMenuItem editUndo = new JMenuItem("Undo");
    JMenuItem editUndo0 = new JMenuItem("Undo");
    JMenuItem editRedo = new JMenuItem("Redo");
    JMenuItem editRedo0 = new JMenuItem("Redo");
    JMenuItem editSelectAll = new JMenuItem("Select All");
    JMenuItem editSelectAll0 = new JMenuItem("Select All");
    JMenuItem editStamp = new JMenuItem("Date/Time Stamp");
    JMenuItem editStamp0 = new JMenuItem("Date/Time Stamp");
    JMenu style = new JMenu("Style");
    JMenuItem styleBold = new JMenuItem("Bold");
    JMenuItem styleItalics = new JMenuItem("Italics");
    JMenuItem styleUnderline = new JMenuItem("Underlined");
    JMenu styleAlign = new JMenu("Align");
    JMenuItem styleAlignLeft = new JMenuItem("Left");
    JMenuItem styleAlignCenter = new JMenuItem("Center");
    JMenuItem styleAlignRight = new JMenuItem("Right");
    JMenuItem styleAlignJustified = new JMenuItem("Justified");
    JMenu tools = new JMenu("Tools");
    JMenuItem toolsFind = new JMenuItem("Find/Replace...");
    JMenuItem toolsFind0 = new JMenuItem("Find/Replace...");
    JMenuItem toolsGoto = new JMenuItem("Go to Line...");
    JMenuItem toolsGoto0 = new JMenuItem("Go to Line...");
    JMenuItem toolsSpellCheck = new JMenuItem("Spell Check...");
    JMenuItem toolsSpellCheck0 = new JMenuItem("Spell Check...");
    JCheckBoxMenuItem toolsSCRealTime = new JCheckBoxMenuItem("Check Spelling as You Type");
    JMenuItem toolsGC = new JMenuItem("Run Garbage Collection");
    JMenu view = new JMenu("View");
    JCheckBoxMenuItem viewStatusbar = new JCheckBoxMenuItem("Show Status Bar");
    JCheckBoxMenuItem viewToolbar = new JCheckBoxMenuItem("Show Toolbar");
    ButtonGroup lafGroup = new ButtonGroup();
    JMenu help = new JMenu("Help");
    JMenuItem helpHelp = new JMenuItem("NPad Help");
    JMenuItem helpAbout = new JMenuItem("About NPad");
    JPopupMenu rightClickMenu = new JPopupMenu();
    JMenuItem rcIgnore = new JMenuItem("Ignore");
    JMenuItem rcAdd = new JMenuItem("Add to Dictionary");
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel tbar = new JPanel(new BorderLayout());
    JToolBar tbarNStyles = new JToolBar();
    JToolBar tbarFile = new JToolBar();
    JButton btnNew = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/newFile.PNG")));
    JButton btnOpen = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/openFile.PNG")));
    JButton btnSave = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/saveFile.PNG")));
    JButton btnSaveAs = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/saveFileAs.PNG")));
    JButton btnPrint = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/printFile.PNG")));
    JToolBar tbarEdit = new JToolBar();
    JButton btnCut = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/cut.PNG")));
    JButton btnCopy = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/copy.PNG")));
    JButton btnPaste = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/paste.PNG")));
    JButton btnUndo = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/undo.PNG")));
    JButton btnRedo = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/redo.PNG")));
    JToolBar tbarTools = new JToolBar();
    JButton btnFind = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/findSymbol.PNG")));
    JButton btnSC = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/scSymbol.PNG")));
    JToolBar tbarStyles = new JToolBar();
    JToolBar tbarStyle = new JToolBar();
    JToggleButton btnBold = new JToggleButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/bold.PNG")));
    JToggleButton btnItalics = new JToggleButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/italics.PNG")));
    JToggleButton btnUnderline = new JToggleButton(new ImageIcon(this.getClass().getClassLoader().getResource(("images/underline.PNG"))));
    JToolBar tbarAlign = new JToolBar();
    JToggleButton btnLeft = new JToggleButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/left.PNG")));
    JToggleButton btnCenter = new JToggleButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/center.PNG")));
    JToggleButton btnRight = new JToggleButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/right.PNG")));
    JToggleButton btnJustified = new JToggleButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/justified.PNG")));
    JToolBar tbarFonts = new JToolBar();
    JButton btnRaiseFont = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/raiseFont.PNG")));
    JButton btnLowerFont = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("images/lowerFont.PNG")));
    DropDownButton btnFontColor = new DropDownButton(this);
    JComboBox fontList = new JComboBox();
    String fontNames[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    JComboBox fontSizes = new JComboBox();
    String fontName;
    int fontStyle;
    int fontSize;
    JFileChooser jfc = new JFileChooser();
    File saverFile;
    File dictionaryFile;
    FileWriter fw;
    FileReader fr;
    Properties npaddat = new Properties();
    int dimensions[] = new int[4];
    Boolean bTbar = true;
    Boolean bStat = true;
    JDialog spellCheckDialog = new JDialog(this, "NPad - Spell Check", true);
    String dictionaryStr;
    JPanel misspeltPanel = new JPanel();
    JLabel misspeltLbl = new JLabel("Misspelled Word:  ", JLabel.LEFT);
    JTextField misspeltWords = new JTextField();
    JPanel listPanel = new JPanel();
    JLabel similarLbl = new JLabel("Similar Matches:", JLabel.LEFT);
    DefaultListModel dlm = new DefaultListModel();
    JList similarWordsList = new JList(dlm);
    JPanel replacePanel = new JPanel();
    JLabel scReplaceLbl = new JLabel("Replace With:  ", JLabel.LEFT);
    JTextField replaceWords = new JTextField();
    JPanel misspeltButtons = new JPanel();
    JButton replaceMisspelt = new JButton("Replace");
    JButton ignoreMisspelt = new JButton("Ignore");
    JButton addMisspeltToDictionary = new JButton("Add to Dictionary");
    JButton scClose = new JButton("Close");
    JPanel spellCheckPanel = new JPanel();
    String spChars = "\r\n\t\f\"\\'`~!@#$%^&*()_+-={}|[]:;<>?,./ “”‘’\b\u0085\u2028\u2029";
    StringTokenizer badWordTokens = new StringTokenizer("", spChars);
    StringTokenizer dictionaryTokens;
    Properties dictionary = new Properties();
    Hashtable dictionaryWords = new Hashtable();
    boolean unableToLoad = false;
    boolean saveAsFlag = true;
    boolean notModified = true;
    JDialog far;
    JLabel findLbl = new JLabel("Find: ", JLabel.LEFT);
    JLabel replaceLbl = new JLabel("Replace with: ", JLabel.LEFT);
    JLabel findError = new JLabel("", JLabel.CENTER);
    JButton findBtn = new JButton("Find");
    JButton replaceBtn = new JButton("Replace");
    JButton replaceAllBtn = new JButton("Replace All");
    JButton closeBtn = new JButton("Close");
    JRadioButton btnCase = new JRadioButton("Match Case", false);
    JTextField findInput;
    JTextField replaceInput;
    JPanel findPanel;
    JPanel replacePanelF;
    JPanel farPanel;
    JPanel matchCasePanel;
    JPanel combinedPanel;
    JPanel buttonPanel;
    // </editor-fold>
    String findTxt = "";
    String replaceTxt = "";
    int startIndex = 0;
    int endIndex = 0;
    boolean firstTimeFlag = true;
    boolean matchCase = false;
    JLabel statusLbl = new JLabel("Chars: 0/0     Lines: 1/1     Columns: 1/1     Words: 0/0", JLabel.LEFT);
    int whichChar = 0;
    int totChar = 0;
    int whichLine = 0;
    int totLine = 0;
    int whichWord = 0;
    int totWord = 0;
    int whichCol = 0;
    int totCol = 0;
    boolean startFlag = true;
    boolean startFlag2 = true;
    boolean suspendFlag = false;
    boolean statusFlag = true;
    UndoManager um = new UndoManager();
    char c[];
    Boolean blaf = true;
    Boolean scFlag = true;
    javax.swing.Timer scTimer = new javax.swing.Timer(25, this);
    WaveHighlighter underliner = new WaveHighlighter(Color.red);
    NPadSplashScreen screen;
    java.awt.Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("images/npad.PNG"));
    ImageIcon errorImg = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("images/errorSymbol.PNG")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    InputStreamReader dictionaryISR;
    BufferedReader dictionaryBR;
    boolean addedToDictionary = false;
    String dictionaryMsg = "Loading dictionary...";
    JTextField list;
    JTextField sizes;
    int replaceIndex = 0;
    Vector fonts = new Vector();
    boolean doneRegister = false;

    public NPad(String filename) {

        System.out.println(DictionaryCompiler.getPronunciation("precision"));
        System.out.println(DictionaryCompiler.getPronunciation("presicion"));
        System.out.println(DictionaryCompiler.getPronunciation("precicion"));
        screen = new NPadSplashScreen(this);
        screen.validate();
        screen.setVisible(true);
        screen.validate();
        screen.updateStatus("Loading preferences...");
        Thread dictionaryT = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    dictionaryFile = new File(System.getProperty("user.home") + "/.NPad/Dictionary/dictionary.txt");
                    new File(System.getProperty("user.home") + "/.NPad/Dictionary").mkdirs();
                    if (dictionaryFile.createNewFile()) {
                        dictionaryMsg = "Compiling dictionary for first time use...";
                        DictionaryCompiler.compile(this.getClass().getClassLoader().getResource("Dictionary/dictionary.txt"), dictionaryFile);
                    }
                    dictionary.load(new FileReader(dictionaryFile));
                } catch (Exception e) {
                    e.printStackTrace();
                    scFlag = false;
                    unableToLoad = true;
                    toolsSpellCheck.setEnabled(false);
                    toolsSCRealTime.setState(false);
                    toolsSCRealTime.setEnabled(false);
                    btnSC.setEnabled(false);
                }
            }
        });
        dictionaryT.start();

        Thread fontRegisterT = new Thread(new Runnable() {

            @Override
            public void run() {
                FontFactory.registerDirectories();
            }
        });
        fontRegisterT.start();

        final NPad THIS = this;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        um.setLimit(-1);

        try {
            npaddat.load(new FileReader(System.getProperty("user.home") + "/.NPad/Config/npad.ini"));
        } catch (Exception e) {
        }
        fontName = npaddat.getProperty("fontName", "Comic Sans MS");
        fontSize = Integer.parseInt(npaddat.getProperty("fontSize", "14"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        dimensions[0] = Integer.parseInt(npaddat.getProperty("dx", String.valueOf((d.width - 650) / 2)));
        dimensions[1] = Integer.parseInt(npaddat.getProperty("dy", String.valueOf((d.height - 500) / 2)));
        dimensions[2] = Integer.parseInt(npaddat.getProperty("dw", "650"));
        dimensions[3] = Integer.parseInt(npaddat.getProperty("dh", "500"));
        bTbar = Boolean.parseBoolean(npaddat.getProperty("showtbar", "true"));
        bStat = Boolean.parseBoolean(npaddat.getProperty("showstat", "true"));
        blaf = Boolean.parseBoolean(npaddat.getProperty("laf", "true"));
        scFlag = Boolean.parseBoolean(npaddat.getProperty("realtime", "true"));
        btnFontColor.setForeground(new Color(Integer.parseInt(npaddat.getProperty("red", "0")), Integer.parseInt(npaddat.getProperty("blue", "0")), Integer.parseInt(npaddat.getProperty("green", "0"))));
        int alignment = Integer.parseInt(npaddat.getProperty("alignment", String.valueOf(StyleConstants.ALIGN_LEFT)));

        setLayout(new BorderLayout());

        jsp = new JScrollPane(inputField);
        getContentPane().add(jsp, BorderLayout.CENTER);

        inputField.setDocument(doc);

        if (bTbar) {
            getContentPane().add(tbar, BorderLayout.NORTH);
        }
        viewToolbar.setSelected(bTbar);
        if (bStat) {
            getContentPane().add(statusLbl, BorderLayout.SOUTH);
        }
        viewStatusbar.setSelected(bStat);

        new Thread(this).start();
        scTimer.setRepeats(false);



        setBounds(dimensions[0], dimensions[1], dimensions[2], dimensions[3]);
        setExtendedState(Integer.parseInt(npaddat.getProperty("state", String.valueOf(JFrame.NORMAL))));

        setIconImage(icon);

        setTitle("Untitled - NPad");

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                synchronized (THIS) {
                    statusFlag = false;
                }
                if (checkSaveAs() == null) {
                    synchronized (THIS) {
                        statusFlag = false;
                    }
                    new Thread(THIS).start();
                    return;
                }
                setVisible(false);
                try {
                    fr.close();
                    fw.close();
                } catch (Exception ex) {
                }
                try {
                    npaddat = new Properties();
                    npaddat.put("dx", String.valueOf(getX()));
                    npaddat.put("dy", String.valueOf(getY()));
                    npaddat.put("dw", String.valueOf(getWidth()));
                    npaddat.put("dh", String.valueOf(getHeight()));
                    npaddat.put("showtbar", bTbar.toString());
                    npaddat.put("showstat", bStat.toString());
                    npaddat.put("state", String.valueOf(getExtendedState()));
                    npaddat.put("laf", blaf.toString());
                    npaddat.put("realtime", scFlag.toString());
                    new File(System.getProperty("user.home") + "/.NPad/Config").mkdirs();
                    File file = new File(System.getProperty("user.home") + "/.NPad/Config/npad.ini");
                    file.createNewFile();
                    npaddat.store(new FileWriter(file), "NPad Settings");
                    if (addedToDictionary) {
                        dictionary.store(new FileWriter(dictionaryFile), "NPad Dictionary");
                    }
                } catch (IOException ex) {
                }
                System.exit(0);
            }
        });

        screen.updateStatus("Loading Font Interface...");
        fontList = new AutoComplete(fontNames);
        fontList.setEditable(true);
        fontList.setSelectedItem(fontName);
        ((JTextField) fontList.getEditor().getEditorComponent()).addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) fontList.getEditor().getEditorComponent()).selectAll();
            }
        });
        ((JTextField) fontList.getEditor().getEditorComponent()).setText(fontName);
        fontList.addActionListener(this);
        fontList.addItemListener(this);
        list = (JTextField) fontList.getEditor().getEditorComponent();
        list.addActionListener(this);
        sizes = (JTextField) fontSizes.getEditor().getEditorComponent();
        sizes.addActionListener(this);
        for (int i = 6; i <= 48;) {
            fontSizes.addItem(String.valueOf(i));
            if (i < 14) {
                i += 2;
            } else if (i < 18) {
                i += 4;
            } else {
                i += 6;
            }
        }
        fontSizes.setEditable(true);
        fontSizes.addItemListener(this);
        ((JTextComponent) fontSizes.getEditor().getEditorComponent()).setDocument(new NumberDocument((JTextComponent) fontSizes.getEditor().getEditorComponent(), "LIMIT", false));
        ((JTextField) fontSizes.getEditor().getEditorComponent()).setText(String.valueOf(fontSize));
        ((JTextField) fontSizes.getEditor().getEditorComponent()).addActionListener(this);
        ((JTextField) fontSizes.getEditor().getEditorComponent()).addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) fontList.getEditor().getEditorComponent()).select(0, ((JTextField) fontList.getEditor().getEditorComponent()).getText().length());
            }
        });
        fontSizes.setSelectedItem(String.valueOf(fontSize));
        ActionEvent nameE = new ActionEvent(fontList, 0, "list");
        ActionEvent sizeE = new ActionEvent(fontSizes, 0, "sizes");
        actionPerformed(nameE);
        actionPerformed(sizeE);



        screen.updateStatus("Loading Find and Replace...");
        findPanel = new JPanel(new BorderLayout());
        replacePanelF = new JPanel(new BorderLayout());
        matchCasePanel = new JPanel();
        combinedPanel = new JPanel();
        buttonPanel = new JPanel();
        findInput = new JTextField();
        replaceInput = new JTextField();
        findInput.setText(findTxt);
        replaceInput.setText(replaceTxt);
        far = new JDialog(this, "NPad - Find and Replace", false);
        far.setLayout(null);
        findPanel.add(findLbl, BorderLayout.WEST);
        findPanel.add(findInput, BorderLayout.CENTER);
        replacePanelF.add(replaceLbl, BorderLayout.WEST);
        replacePanelF.add(replaceInput, BorderLayout.CENTER);
        farPanel = new JPanel(new BorderLayout(5, 5));
        farPanel.add(findPanel, BorderLayout.NORTH);
        farPanel.add(replacePanelF, BorderLayout.SOUTH);
        findError.setForeground(Color.red);
        findError.setFont(new Font("SansSerif", Font.BOLD, 15));
        findError.setText("");
        matchCasePanel.add(btnCase);
        buttonPanel.add(findBtn);
        buttonPanel.add(replaceBtn);
        buttonPanel.add(replaceAllBtn);
        buttonPanel.add(closeBtn);
        combinedPanel.setLayout(new BorderLayout(5, 5));
        combinedPanel.add(farPanel, BorderLayout.NORTH);
        combinedPanel.add(findError, BorderLayout.CENTER);
        combinedPanel.add(matchCasePanel, BorderLayout.SOUTH);
        combinedPanel.setBounds(5, 5, 335, 105);
        buttonPanel.setBounds(5, 110, 335, 35);
        far.add(combinedPanel, BorderLayout.CENTER);
        far.add(buttonPanel, BorderLayout.SOUTH);
        far.setResizable(false);

        screen.updateStatus("Loading Spell Check...");
        spellCheckDialog.setLayout(null);
        spellCheckPanel.setLayout(new BorderLayout());
        misspeltPanel.setLayout(new BorderLayout());
        listPanel.setLayout(new BorderLayout());
        misspeltPanel.add(misspeltLbl, BorderLayout.NORTH);
        misspeltWords.setEditable(false);
        misspeltWords.setBackground(Color.white);
        misspeltWords.setForeground(Color.red);
        misspeltPanel.add(misspeltWords, BorderLayout.CENTER);
        listPanel.add(similarLbl, BorderLayout.NORTH);
        similarWordsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        similarWordsList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (similarWordsList.getSelectedValue() != null && !similarWordsList.getSelectedValue().equals("No matches were found")) {
                    replaceWords.setText(similarWordsList.getSelectedValue().toString());
                } else {
                    replaceWords.setText("");
                }
            }
        });
        listPanel.add(new JScrollPane(similarWordsList), BorderLayout.CENTER);
        replacePanel.setLayout(new BorderLayout());
        replacePanel.add(scReplaceLbl, BorderLayout.NORTH);
        replacePanel.add(replaceWords, BorderLayout.CENTER);
        listPanel.add(replacePanel, BorderLayout.SOUTH);
        misspeltButtons.add(replaceMisspelt);
        misspeltButtons.add(ignoreMisspelt);
        misspeltButtons.add(addMisspeltToDictionary);
        misspeltButtons.add(scClose);
        misspeltPanel.setSize(380, 30);
        listPanel.setSize(380, 100);
        spellCheckPanel.add(misspeltPanel, BorderLayout.NORTH);
        spellCheckPanel.add(listPanel, BorderLayout.CENTER);
        spellCheckPanel.add(misspeltButtons, BorderLayout.SOUTH);
        spellCheckPanel.setBounds(5, 5, 380, 230);
        spellCheckDialog.add(spellCheckPanel);
        spellCheckDialog.setResizable(false);
        spellCheckDialog.setBounds((getSize().width - 395) / 2 + getX(),
                (getSize().height - 265) / 2 + getY(),
                395, 265);

        screen.updateStatus("Loading Menus...");
        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        fileNew.setIcon(btnNew.getIcon());
        file.add(fileNew);
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileOpen.setIcon(btnOpen.getIcon());
        file.add(fileOpen);
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileSave.setIcon(btnSave.getIcon());
        file.add(fileSave);
        fileSaveAs.setIcon(btnSaveAs.getIcon());
        file.add(fileSaveAs);
        file.addSeparator();
        filePrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        filePrint.setIcon(btnPrint.getIcon());
        file.add(filePrint);
        file.addSeparator();
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        file.add(fileExit);
        file.setMnemonic('f');
        mbar.add(file);
        editCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        editCut.setIcon(btnCut.getIcon());
        edit.add(editCut);
        editCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        editCopy.setIcon(btnCopy.getIcon());
        edit.add(editCopy);
        editPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        editPaste.setIcon(btnPaste.getIcon());
        edit.add(editPaste);
        edit.addSeparator();
        editUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        editUndo.setIcon(btnUndo.getIcon());
        edit.add(editUndo);
        editRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        editRedo.setIcon(btnRedo.getIcon());
        edit.add(editRedo);
        edit.addSeparator();
        editSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        edit.add(editSelectAll);
        editStamp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        edit.add(editStamp);
        edit.setMnemonic('e');
        mbar.add(edit);
        styleBold.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        styleBold.setIcon(btnBold.getIcon());
        style.add(styleBold);
        styleItalics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        styleItalics.setIcon(btnItalics.getIcon());
        style.add(styleItalics);
        styleUnderline.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        styleUnderline.setIcon(btnUnderline.getIcon());
        style.add(styleUnderline);
        style.addSeparator();
        styleAlignLeft.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        styleAlignLeft.setIcon(btnLeft.getIcon());
        styleAlign.add(styleAlignLeft);
        styleAlignCenter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        styleAlignCenter.setIcon(btnCenter.getIcon());
        styleAlign.add(styleAlignCenter);
        styleAlignRight.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        styleAlignRight.setIcon(btnRight.getIcon());
        styleAlign.add(styleAlignRight);
        styleAlignJustified.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
        styleAlignJustified.setIcon(btnJustified.getIcon());
        styleAlign.add(styleAlignJustified);
        style.add(styleAlign);
        mbar.add(style);
        toolsFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        toolsFind.setIcon(btnFind.getIcon());
        tools.add(toolsFind);
        toolsGoto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        tools.add(toolsGoto);
        toolsSpellCheck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
        toolsSpellCheck.setIcon(btnSC.getIcon());
        tools.add(toolsSpellCheck);
        toolsSCRealTime.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));
        tools.add(toolsSCRealTime);
        toolsSCRealTime.setSelected(scFlag);
        tools.addSeparator();
        tools.add(toolsGC);
        tools.setMnemonic('t');
        mbar.add(tools);
        viewStatusbar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));
        view.add(viewStatusbar);
        viewToolbar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));
        view.add(viewToolbar);
        view.setMnemonic('v');
        mbar.add(view);
        helpHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        help.add(helpHelp);
        helpAbout.setIcon(new ImageIcon(this.getIconImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        help.add(helpAbout);
        help.setMnemonic('h');
        mbar.add(help);
        setJMenuBar(mbar);

        screen.updateStatus("Loading Toolbar...");
        btnNew.setToolTipText("Creates new, untitled file (Ctrl - N)");
        tbarFile.add(btnNew);
        btnOpen.setToolTipText("Open a file... (Ctrl - O)");
        tbarFile.add(btnOpen);
        btnSave.setToolTipText("Save the current file (Ctrl - S)");
        tbarFile.add(btnSave);
        btnSaveAs.setToolTipText("Save the current file as...");
        tbarFile.add(btnSaveAs);
        btnPrint.setToolTipText("Print the current file... (Ctrl - P)");
        tbarFile.add(btnPrint);
        tbarNStyles.add(tbarFile);
        tbarNStyles.addSeparator();
        btnCut.setToolTipText("Cut and copy selected text to the clipboard (Ctrl - X)");
        tbarEdit.add(btnCut);
        btnCopy.setToolTipText("Copy selected text to the clipboard (Ctrl - C)");
        tbarEdit.add(btnCopy);
        btnPaste.setToolTipText("Paste the contents of the clipboard (Ctrl - V)");
        tbarEdit.add(btnPaste);
        btnUndo.setToolTipText("Undo (Ctrl - Z)");
        tbarEdit.add(btnUndo);
        btnRedo.setToolTipText("Redo (Ctrl - Y)");
        tbarEdit.add(btnRedo);
        tbarNStyles.add(tbarEdit);
        tbarNStyles.addSeparator();
        btnFind.setToolTipText("Find and replace text (Ctrl - F)");
        tbarTools.add(btnFind);
        btnSC.setToolTipText("Spell check file... (F7)");
        tbarTools.add(btnSC);
        tbarNStyles.add(tbarTools);
        tbar.add(tbarNStyles, BorderLayout.NORTH);
        btnBold.setToolTipText("Bold (Ctrl - B)");
        tbarStyle.add(btnBold);
        btnItalics.setText("");
        btnItalics.setToolTipText("Italics (Ctrl - I)");
        tbarStyle.add(btnItalics);
        btnUnderline.setText("");
        btnUnderline.setToolTipText("Underline (Ctrl - U)");
        tbarStyle.add(btnUnderline);
        tbarStyles.add(tbarStyle);
        tbarStyles.addSeparator();
        btnLeft.setToolTipText("Align Text Left (Ctrl - L)");
        tbarAlign.add(btnLeft);
        btnCenter.setToolTipText("Center Text (Ctrl - E)");
        tbarAlign.add(btnCenter);
        btnRight.setToolTipText("Align Text Right (Ctrl - R)");
        tbarAlign.add(btnRight);
        btnJustified.setToolTipText("Justified (Ctrl - J)");
        tbarAlign.add(btnJustified);
        tbarStyles.add(tbarAlign);
        tbarStyles.addSeparator();
        btnFontColor.addActionListener(this);
        btnFontColor.setToolTipText("Change the font color");
        tbarFonts.add(btnFontColor);
        tbarFonts.add(fontList);
        fontSizes.setEditable(true);
        tbarFonts.add(fontSizes);
        tbarStyles.add(tbarFonts);
        tbar.add(tbarStyles, BorderLayout.CENTER);
        tbarAlign.setFloatable(false);
        tbarEdit.setFloatable(false);
        tbarFile.setFloatable(false);
        tbarFonts.setFloatable(false);
        tbarNStyles.setFloatable(false);
        tbarStyle.setFloatable(false);
        tbarStyles.setFloatable(false);
        tbarTools.setFloatable(false);

        screen.updateStatus("Loading Event Listeners...");
        addListeners();

        screen.updateStatus("Loading FileChoosers...");
        jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else if (f.toString().endsWith(".rtf")) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return "Rich Text Format (*.rtf)";
            }
        });
        jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else if (f.toString().endsWith(".txt")) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return "Plain Text (*.txt)";
            }
        });
        jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else if (f.toString().endsWith(".pdf")) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return "Portable Document Format (*.pdf)";
            }
        });
        jfc.setFileFilter(jfc.getChoosableFileFilters()[1]);

        screen.updateStatus("Loading misc...");
        new StyledEditorKit.AlignmentAction("", alignment).actionPerformed(null);
        new StyledEditorKit.ForegroundAction("", btnFontColor.getColor()).actionPerformed(null);
        setMinimumSize(new Dimension(542, 350));

        if (!filename.equals("")) {
            saverFile = new File(filename);
            try {
                InputStream frr = new FileInputStream(saverFile);
                kit.read(frr, doc, 0);
                saveAsFlag = false;
                setTitle(saverFile.getName() + " - NPad");
                fr.close();
                notModified = true;
                um = new UndoManager();

            } catch (FileNotFoundException e) {

                showErrorDialog("Unable to find the specified file.");

            } catch (Exception e) {

                showErrorDialog("Unable to read the specified file.");

            }
        }

        screen.updateStatus("Checking for updates...");
        try {
            screen.updateStatus("Registering System Fonts...");
            fontRegisterT.join();
            screen.updateStatus(dictionaryMsg);
            dictionaryT.join();
        } catch (InterruptedException ex) {
        }
        setVisible(true);
        screen.dispose();

        while (!inputField.hasFocus()) {
            inputField.requestFocus();
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                final double otherversion = checkForUpdates();
                if (otherversion > version) {
                    final JDialog dialog = new JDialog(THIS, "NPad - Update Available", true);
                    JLabel lbl = new JLabel("NPad v" + String.valueOf(otherversion) + " is now available.");
                    JLabel lbl2 = new JLabel("Please download it at http://www.geocities.com/nhnt website/index.html.", JLabel.CENTER);
                    dialog.setLayout(new FlowLayout());
                    dialog.add(lbl);
                    dialog.add(lbl2);
                    JButton kk = new JButton("OK");
                    kk.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            dialog.dispose();
                        }
                    });
                    dialog.add(kk);
                    Dimension dd = getSize();
                    dialog.setBounds((dd.width - 450) / 2 + getX(), (dd.height - 110) / 2 + getY(), 450, 110);
                    dialog.setVisible(true);
                }
            }
        }).start();

        if (unableToLoad) {
            checkDictionary();
        }
    }

    void saveFile(Object src) {

        int carPos = inputField.getCaretPosition();
        if (src == fileSave || src == btnSave) {
            if (saveAsFlag) {

                synchronized (this) {
                    statusFlag = false;
                }
                scTimer.stop();
                int i = jfc.showSaveDialog(this);

                if (i == JFileChooser.APPROVE_OPTION) {
                    if (jfc.getSelectedFile().getName().indexOf(".") != -1) {
                        saverFile = jfc.getSelectedFile();
                    } else if (jfc.getFileFilter().getDescription().endsWith("(*.txt)")) {
                        saverFile = new File(jfc.getSelectedFile().toString() + ".txt");
                    } else if (jfc.getFileFilter().getDescription().endsWith("(*.pdf)")) {
                        saverFile = new File(jfc.getSelectedFile().toString() + ".pdf");
                    } else {
                        saverFile = new File(jfc.getSelectedFile().toString() + ".rtf");
                    }
                    try {
                        boolean overwrite = checkOverwrite(saverFile);

                        if (overwrite) {
                            OutputStream fww = new FileOutputStream(saverFile);
                            if (saverFile.getName().endsWith(".rtf")) {
//                                kit.write(fww, doc, 0, doc.getLength());
                                int a = 0;
                                com.lowagie.text.Document d = new com.lowagie.text.Document();
                                RtfWriter2.getInstance(d, fww);
                                d.open();
                                Vector paras = new Vector();
                                Paragraph para = new Paragraph();
                                para.setSpacingBefore(0);
                                Element p = doc.getParagraphElement(0);
                                int align = 0;
                                while (a < doc.getLength()) {
                                    if (doc.getParagraphElement(a) != p && doc.getParagraphElement(a + 1) == doc.getParagraphElement(a)) {
                                        switch (align) {
                                            case StyleConstants.ALIGN_LEFT:
                                                para.setAlignment(Paragraph.ALIGN_LEFT);
                                                break;
                                            case StyleConstants.ALIGN_CENTER:
                                                para.setAlignment(Paragraph.ALIGN_CENTER);
                                                break;
                                            case StyleConstants.ALIGN_RIGHT:
                                                para.setAlignment(Paragraph.ALIGN_RIGHT);
                                                break;
                                            case StyleConstants.ALIGN_JUSTIFIED:
                                                para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                                break;
                                        }
                                        paras.add(para);
                                        para = new Paragraph();
                                        p = doc.getParagraphElement(a);
                                    }
                                    Chunk ch = new Chunk();
                                    String str = doc.getText(a, 1);
                                    if (doc.getParagraphElement(a + 1) != doc.getParagraphElement(a)) {
                                        a++;
                                        continue;
                                    }
                                    ch.append(str);
                                    inputField.setCaretPosition(a + 1);
                                    MutableAttributeSet m = inputField.getInputAttributes();
                                    int fontstyle = com.lowagie.text.Font.NORMAL;
                                    if (StyleConstants.isBold(m) && StyleConstants.isItalic(m)) {
                                        fontstyle = com.lowagie.text.Font.BOLDITALIC;
                                    } else if (StyleConstants.isBold(m)) {
                                        fontstyle = com.lowagie.text.Font.BOLD;
                                    } else if (StyleConstants.isItalic(m)) {
                                        fontstyle = com.lowagie.text.Font.ITALIC;
                                    }
                                    if (StyleConstants.isUnderline(m)) {
                                        fontstyle += com.lowagie.text.Font.UNDERLINE;
                                    }
                                    com.lowagie.text.Font font = FontFactory.getFont(fontName);
                                    font.setFamily(fontName);
                                    font.setSize(fontSize);
                                    font.setStyle(fontstyle);
                                    font.setColor(StyleConstants.getForeground(m));
                                    ch.setFont(font);
                                    para.add(ch);
                                    align = StyleConstants.getAlignment(m);
                                    a++;
                                }
                                switch (align) {
                                    case StyleConstants.ALIGN_LEFT:
                                        para.setAlignment(Paragraph.ALIGN_LEFT);
                                        break;
                                    case StyleConstants.ALIGN_CENTER:
                                        para.setAlignment(Paragraph.ALIGN_CENTER);
                                        break;
                                    case StyleConstants.ALIGN_RIGHT:
                                        para.setAlignment(Paragraph.ALIGN_RIGHT);
                                        break;
                                    case StyleConstants.ALIGN_JUSTIFIED:
                                        para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                        break;
                                }
                                if (!" \r\n\t\b".contains(para.getContent())) {
                                    paras.add(para);
                                }
                                Iterator it = paras.iterator();
                                while (it.hasNext()) {
                                    d.add((Paragraph) it.next());
                                }
                                d.close();
                            } else if (saverFile.getName().endsWith(".pdf")) {
                                int a = 0;
                                com.lowagie.text.Document d = new com.lowagie.text.Document();
                                PdfWriter.getInstance(d, fww);
                                d.open();
                                Vector paras = new Vector();
                                Paragraph para = new Paragraph();
                                Element p = doc.getParagraphElement(0);
                                int align = 0;
                                while (a < doc.getLength()) {
                                    if (doc.getParagraphElement(a) != p && doc.getParagraphElement(a + 1) == doc.getParagraphElement(a)) {
                                        switch (align) {
                                            case StyleConstants.ALIGN_LEFT:
                                                para.setAlignment(Paragraph.ALIGN_LEFT);
                                                break;
                                            case StyleConstants.ALIGN_CENTER:
                                                para.setAlignment(Paragraph.ALIGN_CENTER);
                                                break;
                                            case StyleConstants.ALIGN_RIGHT:
                                                para.setAlignment(Paragraph.ALIGN_RIGHT);
                                                break;
                                            case StyleConstants.ALIGN_JUSTIFIED:
                                                para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                                break;
                                        }
                                        paras.add(para);
                                        para = new Paragraph();
                                        p = doc.getParagraphElement(a);
                                    }
                                    Chunk ch = new Chunk();
                                    String str = doc.getText(a, 1);
                                    if (doc.getParagraphElement(a + 1) != doc.getParagraphElement(a)) {
                                        a++;
                                        continue;
                                    }
                                    ch.append(doc.getText(a, 1));
                                    inputField.setCaretPosition(a + 1);
                                    MutableAttributeSet m = inputField.getInputAttributes();
                                    int fontstyle = com.lowagie.text.Font.NORMAL;
                                    if (StyleConstants.isBold(m) && StyleConstants.isItalic(m)) {
                                        fontstyle = com.lowagie.text.Font.BOLDITALIC;
                                    } else if (StyleConstants.isBold(m)) {
                                        fontstyle = com.lowagie.text.Font.BOLD;
                                    } else if (StyleConstants.isItalic(m)) {
                                        fontstyle = com.lowagie.text.Font.ITALIC;
                                    }
                                    if (StyleConstants.isUnderline(m)) {
                                        fontstyle += com.lowagie.text.Font.UNDERLINE;
                                    }
                                    String fontfamily = StyleConstants.getFontFamily(m);
                                    int fontsize = StyleConstants.getFontSize(m);
                                    com.lowagie.text.Font font = FontFactory.getFont(fontfamily);
                                    font.setFamily(fontfamily);
                                    font.setSize(fontsize);
                                    font.setStyle(fontstyle);
                                    font.setColor(StyleConstants.getForeground(m));
                                    ch.setFont(font);
                                    para.add(ch);
                                    align = StyleConstants.getAlignment(m);
                                    a++;
                                }
                                switch (align) {
                                    case StyleConstants.ALIGN_LEFT:
                                        para.setAlignment(Paragraph.ALIGN_LEFT);
                                        break;
                                    case StyleConstants.ALIGN_CENTER:
                                        para.setAlignment(Paragraph.ALIGN_CENTER);
                                        break;
                                    case StyleConstants.ALIGN_RIGHT:
                                        para.setAlignment(Paragraph.ALIGN_RIGHT);
                                        break;
                                    case StyleConstants.ALIGN_JUSTIFIED:
                                        para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                        break;
                                }
                                paras.add(para);
                                Iterator it = paras.iterator();
                                while (it.hasNext()) {
                                    d.add((Paragraph) it.next());
                                }
                                d.close();
                            } else {
                                fw = new FileWriter(saverFile);
                                fw.write(doc.getText(0, doc.getLength()));
                                fw.flush();
                                fw.close();
                            }
                            saveAsFlag = false;
                            setTitle(saverFile.getName() + " - NPad");
                            notModified = true;
                            jfc.setCurrentDirectory(null);
                        } else {
                            jfc.setCurrentDirectory(saverFile);
                            saveFile(fileSaveAs);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        showErrorDialog("Unable to write to the specified file.");
                    }

                }

                synchronized (this) {
                    statusFlag = true;
                }
                new Thread(this).start();
                if (scFlag) {
                    scTimer.restart();
                }
            } else {

                try {

                    OutputStream fww = new FileOutputStream(saverFile);
                    if (saverFile.getName().endsWith(".rtf")) {
//                                kit.write(fww, doc, 0, doc.getLength());
                        int a = 0;
                        com.lowagie.text.Document d = new com.lowagie.text.Document();
                        RtfWriter2.getInstance(d, fww);
                        d.open();
                        Vector paras = new Vector();
                        Paragraph para = new Paragraph();
                        Element p = doc.getParagraphElement(0);
                        int align = 0;
                        while (a < doc.getLength()) {
                            if (doc.getParagraphElement(a) != p && doc.getParagraphElement(a + 1) == doc.getParagraphElement(a)) {
                                switch (align) {
                                    case StyleConstants.ALIGN_LEFT:
                                        para.setAlignment(Paragraph.ALIGN_LEFT);
                                        break;
                                    case StyleConstants.ALIGN_CENTER:
                                        para.setAlignment(Paragraph.ALIGN_CENTER);
                                        break;
                                    case StyleConstants.ALIGN_RIGHT:
                                        para.setAlignment(Paragraph.ALIGN_RIGHT);
                                        break;
                                    case StyleConstants.ALIGN_JUSTIFIED:
                                        para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                        break;
                                }
                                paras.add(para);
                                para = new Paragraph();
                                p = doc.getParagraphElement(a);
                            }
                            Chunk ch = new Chunk();
                            String str = doc.getText(a, 1);
                            if (doc.getParagraphElement(a + 1) != doc.getParagraphElement(a)) {
                                a++;
                                continue;
                            }
                            ch.append(doc.getText(a, 1));
                            inputField.setCaretPosition(a + 1);
                            MutableAttributeSet m = inputField.getInputAttributes();
                            int fontstyle = com.lowagie.text.Font.NORMAL;
                            if (StyleConstants.isBold(m) && StyleConstants.isItalic(m)) {
                                fontstyle = com.lowagie.text.Font.BOLDITALIC;
                            } else if (StyleConstants.isBold(m)) {
                                fontstyle = com.lowagie.text.Font.BOLD;
                            } else if (StyleConstants.isItalic(m)) {
                                fontstyle = com.lowagie.text.Font.ITALIC;
                            }
                            if (StyleConstants.isUnderline(m)) {
                                fontstyle += com.lowagie.text.Font.UNDERLINE;
                            }
                            String fontfamily = StyleConstants.getFontFamily(m);
                            int fontsize = StyleConstants.getFontSize(m);
                            com.lowagie.text.Font font = FontFactory.getFont(fontfamily);
                            font.setFamily(fontfamily);
                            font.setSize(fontsize);
                            font.setStyle(fontstyle);
                            font.setColor(StyleConstants.getForeground(m));
                            ch.setFont(font);
                            para.add(ch);
                            align = StyleConstants.getAlignment(m);
                            a++;
                        }
                        switch (align) {
                            case StyleConstants.ALIGN_LEFT:
                                para.setAlignment(Paragraph.ALIGN_LEFT);
                                break;
                            case StyleConstants.ALIGN_CENTER:
                                para.setAlignment(Paragraph.ALIGN_CENTER);
                                break;
                            case StyleConstants.ALIGN_RIGHT:
                                para.setAlignment(Paragraph.ALIGN_RIGHT);
                                break;
                            case StyleConstants.ALIGN_JUSTIFIED:
                                para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                break;
                        }
                        paras.add(para);
                        Iterator it = paras.iterator();
                        while (it.hasNext()) {
                            d.add((Paragraph) it.next());
                        }
                        d.close();
                    } else if (saverFile.getName().endsWith(".pdf")) {
                        int a = 0;
                        com.lowagie.text.Document d = new com.lowagie.text.Document();
                        PdfWriter.getInstance(d, fww);
                        d.open();
                        Vector paras = new Vector();
                        Paragraph para = new Paragraph();
                        Element p = doc.getParagraphElement(0);
                        int align = 0;
                        while (a < doc.getLength()) {
                            if (doc.getParagraphElement(a) != p && doc.getParagraphElement(a + 1) == doc.getParagraphElement(a)) {
                                switch (align) {
                                    case StyleConstants.ALIGN_LEFT:
                                        para.setAlignment(Paragraph.ALIGN_LEFT);
                                        break;
                                    case StyleConstants.ALIGN_CENTER:
                                        para.setAlignment(Paragraph.ALIGN_CENTER);
                                        break;
                                    case StyleConstants.ALIGN_RIGHT:
                                        para.setAlignment(Paragraph.ALIGN_RIGHT);
                                        break;
                                    case StyleConstants.ALIGN_JUSTIFIED:
                                        para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                        break;
                                }
                                paras.add(para);
                                para = new Paragraph();
                                p = doc.getParagraphElement(a);
                            }
                            Chunk ch = new Chunk();
                            String str = doc.getText(a, 1);
                            if (doc.getParagraphElement(a + 1) != doc.getParagraphElement(a)) {
                                a++;
                                continue;
                            }
                            ch.append(doc.getText(a, 1));
                            inputField.setCaretPosition(a + 1);
                            MutableAttributeSet m = inputField.getInputAttributes();
                            int fontstyle = com.lowagie.text.Font.NORMAL;
                            if (StyleConstants.isBold(m) && StyleConstants.isItalic(m)) {
                                fontstyle = com.lowagie.text.Font.BOLDITALIC;
                            } else if (StyleConstants.isBold(m)) {
                                fontstyle = com.lowagie.text.Font.BOLD;
                            } else if (StyleConstants.isItalic(m)) {
                                fontstyle = com.lowagie.text.Font.ITALIC;
                            }
                            if (StyleConstants.isUnderline(m)) {
                                fontstyle += com.lowagie.text.Font.UNDERLINE;
                            }
                            String fontfamily = StyleConstants.getFontFamily(m);
                            int fontsize = StyleConstants.getFontSize(m);
                            com.lowagie.text.Font font = FontFactory.getFont(fontfamily);
                            font.setFamily(fontfamily);
                            font.setSize(fontsize);
                            font.setStyle(fontstyle);
                            font.setColor(StyleConstants.getForeground(m));
                            ch.setFont(font);
                            para.add(ch);
                            align = StyleConstants.getAlignment(m);
                            a++;
                        }
                        switch (align) {
                            case StyleConstants.ALIGN_LEFT:
                                para.setAlignment(Paragraph.ALIGN_LEFT);
                                break;
                            case StyleConstants.ALIGN_CENTER:
                                para.setAlignment(Paragraph.ALIGN_CENTER);
                                break;
                            case StyleConstants.ALIGN_RIGHT:
                                para.setAlignment(Paragraph.ALIGN_RIGHT);
                                break;
                            case StyleConstants.ALIGN_JUSTIFIED:
                                para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                break;
                        }
                        paras.add(para);
                        Iterator it = paras.iterator();
                        while (it.hasNext()) {
                            d.add((Paragraph) it.next());
                        }
                        d.close();
                    } else {
                        fw = new FileWriter(saverFile);
                        fw.write(doc.getText(0, doc.getLength()));
                        fw.flush();
                        fw.close();
                    }
                    saveAsFlag = false;
                    setTitle(saverFile.getName() + " - NPad");
                    notModified = true;
                    jfc.setCurrentDirectory(null);
                } catch (Exception e) {
                    showErrorDialog("Unable to write to the specified file");
                }

            }

        } else if (src == fileSaveAs || src == btnSaveAs) {

            synchronized (this) {
                statusFlag = false;
            }
            scTimer.stop();
            int i = jfc.showSaveDialog(this);
            if (jfc.getSelectedFile().getName().indexOf(".") != -1) {
                saverFile = jfc.getSelectedFile();
            } else {
                saverFile = new File(jfc.getSelectedFile().toString() + ".rtf");
            }
            if (i == JFileChooser.APPROVE_OPTION) {

                try {

                    boolean overwrite = checkOverwrite(saverFile);

                    if (overwrite) {
                        OutputStream fww = new FileOutputStream(saverFile);
                        if (saverFile.getName().endsWith(".rtf")) {
//                                kit.write(fww, doc, 0, doc.getLength());
                            int a = 0;
                            com.lowagie.text.Document d = new com.lowagie.text.Document();
                            RtfWriter2.getInstance(d, fww);
                            d.open();
                            Vector paras = new Vector();
                            Paragraph para = new Paragraph();
                            Element p = doc.getParagraphElement(0);
                            int align = 0;
                            while (a < doc.getLength()) {
                                if (doc.getParagraphElement(a) != p && doc.getParagraphElement(a + 1) == doc.getParagraphElement(a)) {
                                    switch (align) {
                                        case StyleConstants.ALIGN_LEFT:
                                            para.setAlignment(Paragraph.ALIGN_LEFT);
                                            break;
                                        case StyleConstants.ALIGN_CENTER:
                                            para.setAlignment(Paragraph.ALIGN_CENTER);
                                            break;
                                        case StyleConstants.ALIGN_RIGHT:
                                            para.setAlignment(Paragraph.ALIGN_RIGHT);
                                            break;
                                        case StyleConstants.ALIGN_JUSTIFIED:
                                            para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                            break;
                                    }
                                    paras.add(para);
                                    para = new Paragraph();
                                    p = doc.getParagraphElement(a);
                                }
                                Chunk ch = new Chunk();
                                String str = doc.getText(a, 1);
                                if (doc.getParagraphElement(a + 1) != doc.getParagraphElement(a)) {
                                    a++;
                                    continue;
                                }
                                ch.append(doc.getText(a, 1));
                                inputField.setCaretPosition(a + 1);
                                MutableAttributeSet m = inputField.getInputAttributes();
                                int fontstyle = com.lowagie.text.Font.NORMAL;
                                if (StyleConstants.isBold(m) && StyleConstants.isItalic(m)) {
                                    fontstyle = com.lowagie.text.Font.BOLDITALIC;
                                } else if (StyleConstants.isBold(m)) {
                                    fontstyle = com.lowagie.text.Font.BOLD;
                                } else if (StyleConstants.isItalic(m)) {
                                    fontstyle = com.lowagie.text.Font.ITALIC;
                                }
                                if (StyleConstants.isUnderline(m)) {
                                    fontstyle += com.lowagie.text.Font.UNDERLINE;
                                }
                                String fontfamily = StyleConstants.getFontFamily(m);
                                int fontsize = StyleConstants.getFontSize(m);
                                com.lowagie.text.Font font = FontFactory.getFont(fontfamily);
                                font.setFamily(fontfamily);
                                font.setSize(fontsize);
                                font.setStyle(fontstyle);
                                font.setColor(StyleConstants.getForeground(m));
                                ch.setFont(font);
                                para.add(ch);
                                align = StyleConstants.getAlignment(m);
                                a++;
                            }
                            switch (align) {
                                case StyleConstants.ALIGN_LEFT:
                                    para.setAlignment(Paragraph.ALIGN_LEFT);
                                    break;
                                case StyleConstants.ALIGN_CENTER:
                                    para.setAlignment(Paragraph.ALIGN_CENTER);
                                    break;
                                case StyleConstants.ALIGN_RIGHT:
                                    para.setAlignment(Paragraph.ALIGN_RIGHT);
                                    break;
                                case StyleConstants.ALIGN_JUSTIFIED:
                                    para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                    break;
                            }
                            paras.add(para);
                            Iterator it = paras.iterator();
                            while (it.hasNext()) {
                                d.add((Paragraph) it.next());
                            }
                            d.close();
                        } else if (saverFile.getName().endsWith(".pdf")) {
                            int a = 0;
                            com.lowagie.text.Document d = new com.lowagie.text.Document();
                            PdfWriter.getInstance(d, fww);
                            d.open();
                            Vector paras = new Vector();
                            Paragraph para = new Paragraph();
                            Element p = doc.getParagraphElement(0);
                            int align = 0;
                            while (a < doc.getLength()) {
                                if (doc.getParagraphElement(a) != p && doc.getParagraphElement(a + 1) == doc.getParagraphElement(a)) {
                                    switch (align) {
                                        case StyleConstants.ALIGN_LEFT:
                                            para.setAlignment(Paragraph.ALIGN_LEFT);
                                            break;
                                        case StyleConstants.ALIGN_CENTER:
                                            para.setAlignment(Paragraph.ALIGN_CENTER);
                                            break;
                                        case StyleConstants.ALIGN_RIGHT:
                                            para.setAlignment(Paragraph.ALIGN_RIGHT);
                                            break;
                                        case StyleConstants.ALIGN_JUSTIFIED:
                                            para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                            break;
                                    }
                                    paras.add(para);
                                    para = new Paragraph();
                                    p = doc.getParagraphElement(a);
                                }
                                Chunk ch = new Chunk();
                                String str = doc.getText(a, 1);
                                if (doc.getParagraphElement(a + 1) != doc.getParagraphElement(a)) {
                                    a++;
                                    continue;
                                }
                                ch.append(doc.getText(a, 1));
                                inputField.setCaretPosition(a + 1);
                                MutableAttributeSet m = inputField.getInputAttributes();
                                int fontstyle = com.lowagie.text.Font.NORMAL;
                                if (StyleConstants.isBold(m) && StyleConstants.isItalic(m)) {
                                    fontstyle = com.lowagie.text.Font.BOLDITALIC;
                                } else if (StyleConstants.isBold(m)) {
                                    fontstyle = com.lowagie.text.Font.BOLD;
                                } else if (StyleConstants.isItalic(m)) {
                                    fontstyle = com.lowagie.text.Font.ITALIC;
                                }
                                if (StyleConstants.isUnderline(m)) {
                                    fontstyle += com.lowagie.text.Font.UNDERLINE;
                                }
                                String fontfamily = StyleConstants.getFontFamily(m);
                                int fontsize = StyleConstants.getFontSize(m);
                                com.lowagie.text.Font font = FontFactory.getFont(fontfamily);
                                font.setFamily(fontfamily);
                                font.setSize(fontsize);
                                font.setStyle(fontstyle);
                                font.setColor(StyleConstants.getForeground(m));
                                ch.setFont(font);
                                para.add(ch);
                                align = StyleConstants.getAlignment(m);
                                a++;
                            }
                            switch (align) {
                                case StyleConstants.ALIGN_LEFT:
                                    para.setAlignment(Paragraph.ALIGN_LEFT);
                                    break;
                                case StyleConstants.ALIGN_CENTER:
                                    para.setAlignment(Paragraph.ALIGN_CENTER);
                                    break;
                                case StyleConstants.ALIGN_RIGHT:
                                    para.setAlignment(Paragraph.ALIGN_RIGHT);
                                    break;
                                case StyleConstants.ALIGN_JUSTIFIED:
                                    para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                                    break;
                            }
                            paras.add(para);
                            Iterator it = paras.iterator();
                            while (it.hasNext()) {
                                d.add((Paragraph) it.next());
                            }
                            d.close();
                        } else {
                            fw = new FileWriter(saverFile);
                            fw.write(doc.getText(0, doc.getLength()));
                            fw.flush();
                            fw.close();
                        }
                        saveAsFlag = false;
                        setTitle(saverFile.getName() + " - NPad");
                        notModified = true;
                        jfc.setCurrentDirectory(null);
                    } else {
                        jfc.setCurrentDirectory(saverFile);
                        saveFile(fileSaveAs);
                    }

                } catch (Exception e) {
                    showErrorDialog("Unable to write to the specified file.");
                }

            }

            synchronized (this) {
                statusFlag = true;
            }
            new Thread(this).start();
            if (scFlag) {
                scTimer.restart();
            }
        }
        inputField.setCaretPosition(carPos);

    }

    void openFile() {

        synchronized (this) {
            statusFlag = false;
        }

        if (checkSaveAs() == null) {
            synchronized (this) {
                statusFlag = true;
            }
            new Thread(this).start();
            return;
        }

        scTimer.stop();
        javax.swing.filechooser.FileFilter filter = jfc.getChoosableFileFilters()[jfc.getChoosableFileFilters().length - 1];
        jfc.removeChoosableFileFilter(filter);
        int i = jfc.showOpenDialog(this);

        if (i == JFileChooser.APPROVE_OPTION) {

            try {

                saverFile = jfc.getSelectedFile();

                sc = new StyleContext();
                doc = new DefaultStyledDocument(sc);
                inputField.setDocument(new DefaultStyledDocument());
                InputStream frr = new FileInputStream(saverFile);
                if (saverFile.getName().endsWith(".rtf")) {
                    kit.read(frr, doc, 0);
                    inputField.setDocument(doc);
                    doc.remove(inputField.getText().length() - 3, 2);
                } else {
                    c = new char[(int) saverFile.length()];
                    fr = new FileReader(saverFile);
                    fr.read(c);
                    inputField.setDocument(doc);
                    inputField.setText(String.valueOf(c));
                }

                um = new UndoManager();
                doc.addUndoableEditListener(this);

                saveAsFlag = false;
                setTitle(saverFile.getName() + " - NPad");
                notModified = true;

            } catch (FileNotFoundException e) {

                showErrorDialog("Unable to find the specified file.");

            } catch (Exception e) {
                e.printStackTrace();
                showErrorDialog("Unable to read the specified file.");
            }
        }
        jfc.addChoosableFileFilter(filter);

        scTimer.start();
        synchronized (this) {
            statusFlag = true;
        }
        new Thread(this).start();

    }

    String checkSaveAs() {

        if (!notModified) {

            JPanel jp1 = new JPanel();
            JPanel jp2 = new JPanel();
            JLabel lbl;
            JLabel errorLabel = new JLabel(errorImg, JLabel.CENTER);
            JButton btnCheckSave;
            JButton btnDiscard;
            JButton btnCancel;

            if (saverFile != null) {
                lbl = new JLabel("Save changes to " + saverFile.getName() + "?");
            } else {
                lbl = new JLabel("Save changes to Untitled?");
            }
            btnCheckSave = new JButton("Yes");
            btnDiscard = new JButton("No");
            btnCancel = new JButton("Cancel");
            final JDialog dialog = new JDialog(this, "Save changes?", true);
            dialog.setLayout(new BorderLayout());
            jp1.add(errorLabel);
            jp1.add(lbl);
            btnCheckSave.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {

                    saveFile(fileSave);
                    return_val2 = "SAVE";
                    dialog.dispose();

                }
            });
            btnDiscard.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {

                    return_val2 = "CANCEL";
                    dialog.dispose();

                }
            });
            btnCancel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    return_val2 = null;
                    dialog.dispose();
                }
            });
            jp2.add(btnCheckSave);
            jp2.add(btnDiscard);
            jp2.add(btnCancel);
            dialog.add(jp1, BorderLayout.NORTH);
            dialog.add(jp2, BorderLayout.SOUTH);
            dialog.setBounds(((getSize().width - 275) / 2) + getX(),
                    ((getSize().height - 105) / 2) + getY(),
                    275, 105);
            dialog.setResizable(false);
            dialog.setVisible(true);
            return return_val2;
        } else {
            return "SAVED";
        }
    }

    boolean checkOverwrite(File f) {

        boolean overwrite;

        try {
            overwrite = f.createNewFile();
        } catch (Exception e) {
            return false;
        }

        if (!overwrite) {
            JPanel jp1 = new JPanel();
            JPanel jp2 = new JPanel();
            JLabel lbl;
            JLabel errorLabel = new JLabel(errorImg, JLabel.CENTER);
            JButton btnCheckSave;
            JButton btnDiscard;

            lbl = new JLabel(saverFile.getName() + " already exists. Overwrite it?");
            btnCheckSave = new JButton("Yes");
            btnDiscard = new JButton("No");
            final JDialog dialog = new JDialog(this, "Overwrite File?", true);
            dialog.setLayout(new BorderLayout());
            jp1.add(errorLabel);
            jp1.add(lbl);
            btnCheckSave.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    return_val = true;
                    dialog.dispose();
                }
            });
            btnDiscard.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {

                    return_val = false;
                    dialog.dispose();

                }
            });
            jp2.add(btnCheckSave);
            jp2.add(btnDiscard);
            dialog.add(jp1, BorderLayout.NORTH);
            dialog.add(jp2, BorderLayout.SOUTH);
            dialog.setBounds(((getSize().width - 275) / 2) + getX(),
                    ((getSize().height - 105) / 2) + getY(),
                    275, 105);
            dialog.setResizable(false);
            dialog.setVisible(true);
            return return_val;
        } else {
            return true;
        }
    }

    File openDictionaryFile() {
        synchronized (this) {
            statusFlag = false;
        }
        jfc.setAcceptAllFileFilterUsed(false);
        int i = jfc.showOpenDialog(this);
        jfc.setAcceptAllFileFilterUsed(true);
        synchronized (this) {
            statusFlag = true;
        }
        new Thread(this).start();
        if (i == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        } else {
            return null;
        }
    }

    void checkDictionary() {

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        final JLabel lbl = new JLabel("Unable to load the dictionary. Would you like to specify where it is?", JLabel.CENTER);
        lbl.setIcon(errorImg);
        final JButton btnCheckSave;
        final JButton btnDiscard;

        btnCheckSave = new JButton("Yes");
        btnDiscard = new JButton("No");
        final JDialog dialog = new JDialog(this, "Unable to load dictionary", true);
        dialog.setLayout(new BorderLayout());
        jp1.add(lbl);
        btnCheckSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                File temp = openDictionaryFile();
                if (temp != null) {
                    try {
                        dictionaryFile = temp;
                        c = new char[(int) dictionaryFile.length()];
                        fr = new FileReader(dictionaryFile);
                        fr.read(c);
                        fr.close();
                        dictionaryStr = String.valueOf(c);
                        dictionaryTokens = new StringTokenizer(dictionaryStr, "\r\n");
                        int y = dictionaryTokens.countTokens();
                        for (int i = 0; i < y; i++) {
                            String s = dictionaryTokens.nextToken();
                            String pro = DictionaryCompiler.getPronunciation(s);
                            dictionaryWords.put(s, "ok");
                            if (dictionary.get(pro) != null) {
                                String ss = (String) dictionary.get(pro);
                                dictionary.remove(pro);
                                dictionary.put(pro, ss + " " + s);
                            } else {
                                dictionary.put(pro, s);
                            }
                        }
                        scFlag = Boolean.parseBoolean(npaddat.getProperty("realtime", "true"));
                        unableToLoad = false;
                        toolsSpellCheck.setEnabled(true);
                        toolsSCRealTime.setState(scFlag);
                        toolsSCRealTime.setEnabled(true);
                        btnSC.setEnabled(true);
                        dialog.dispose();
                    } catch (Exception e) {
                        scFlag = false;
                        unableToLoad = true;
                        toolsSpellCheck.setEnabled(false);
                        toolsSCRealTime.setState(false);
                        toolsSCRealTime.setEnabled(false);
                        btnSC.setEnabled(false);
                        lbl.setText("Unable to load...");
                        dialog.remove(btnCheckSave);
                        dialog.validate();
                        btnDiscard.setText("OK");
                    }
                }

            }
        });
        btnDiscard.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                dialog.dispose();

            }
        });
        jp2.add(btnCheckSave);
        jp2.add(btnDiscard);
        dialog.add(jp1, BorderLayout.NORTH);
        dialog.add(jp2, BorderLayout.SOUTH);
        dialog.setBounds(((getSize().width - 420) / 2) + getX(),
                ((getSize().height - 105) / 2) + getY(),
                420, 105);
        dialog.setResizable(false);
        dialog.setVisible(true);

    }

    void doFindAndReplace() {

        far.setBounds((getSize().width - 350) / 2 + getX(),
                (getSize().height - 175) / 2 + getY(),
                350, 175);
        far.setVisible(true);

        findInput.addActionListener(this);
        replaceInput.addActionListener(this);
        btnCase.addItemListener(this);
        findBtn.addActionListener(this);
        replaceBtn.addActionListener(this);
        replaceAllBtn.addActionListener(this);
        closeBtn.addActionListener(this);

    }

    void find(String s) {

        try {

            inputField.select(0, 0);

            if (matchCase == false) {

                startIndex = doc.getText(0, doc.getLength()).toLowerCase().indexOf(s.toLowerCase(), endIndex);
                endIndex = startIndex + s.length();

            } else {

                startIndex = doc.getText(0, doc.getLength()).indexOf(s, endIndex);
                endIndex = startIndex + s.length();

            }

            if (endIndex != -1 && startIndex != -1) {

                inputField.setCaretPosition(startIndex);
                inputField.moveCaretPosition(endIndex);
                findError.setText("");

                if (doc.getText(0, doc.getLength()).toLowerCase().indexOf(s.toLowerCase(), endIndex) == -1 && doc.getText(0, doc.getLength()).indexOf(s, endIndex) == -1) {

                    startIndex = 0;
                    endIndex = 0;
                    findError.setText("Reached end of file");

                }

            } else {

                findError.setText("Unable to find \"" + s + "\"");
                startIndex = 0;
                endIndex = 0;

            }

        } catch (Exception e) {
        }

    }

    void replaceAll() {

        try {

            if (matchCase) {

                if (doc.getText(0, doc.getLength()).indexOf(findTxt) != -1) {

                    findError.setText("");
                    replaceWholeWords(findTxt, replaceTxt, 0, false);
                    findError.setText("Reached end of file");

                } else {

                    findError.setText("Unable to find \"" + findTxt + "\"");

                }

            } else {
                if (doc.getText(0, doc.getLength()).toLowerCase().indexOf(findTxt.toLowerCase()) != -1) {

                    findError.setText("");
                    replaceWholeWords(findTxt, replaceTxt, 0, false);
                    findError.setText("Reached end of file");

                } else {

                    findError.setText("Unable to find \"" + findTxt + "\"");

                }

            }

        } catch (Exception e) {
        }

        if (scFlag) {
            scTimer.restart();
        }
    }

    void wordCount() {

        try {

            int carPos = inputField.getCaretPosition();
            String splited = doc.getText(0, carPos);
            whichChar = splited.length();
            totChar = doc.getLength();
            StringTokenizer totalWords = new StringTokenizer(doc.getText(0, doc.getLength()), spChars);
            StringTokenizer splitedWords = new StringTokenizer(splited, spChars);
            whichWord = splitedWords.countTokens();
            totWord = totalWords.countTokens();
            JTextArea temp = new JTextArea(splited);
            whichLine = temp.getLineCount();
            JTextArea temp2 = new JTextArea(doc.getText(0, doc.getLength()));
            totLine = temp2.getLineCount();
            int start = doc.getText(0, doc.getLength()).lastIndexOf("\n", carPos - 1);
            start++;
            int end = doc.getText(0, doc.getLength()).indexOf("\n", start);
            if (end == -1) {
                end = doc.getLength();
            }
            String line = doc.getText(0, doc.getLength()).substring(start, end);
            totCol = line.length() + 1;
            int start2 = splited.lastIndexOf("\n") + 1;
            String line2 = splited.substring(start2, splited.length());
            whichCol = line2.length() + 1;

        } catch (Exception e) {

            return;

        }

    }

    void doSpellCheck() {

        dlm.clear();

        if (!misspeltWords.getText().equals("Unable to load dictionary")) {
            badWordTokens = null;
            try {
                badWordTokens = new StringTokenizer(doc.getText(0, doc.getLength()), spChars);
            } catch (Exception e) {
            }

            while (badWordTokens.hasMoreTokens()) {
                if (checkSpelling(badWordTokens.nextToken(), true)) {
                    break;
                } else {
                    misspeltWords.setText("There are no spelling mistakes");
                }

            }

        }

        if (!spellCheckDialog.isVisible()) {
            spellCheckDialog.setBounds((getSize().width - 395) / 2 + getX(),
                    (getSize().height - 265) / 2 + getY(),
                    395, 265);
            spellCheckDialog.setVisible(true);
        }

    }

    boolean checkSpelling(String misspelt, boolean b) {

        if (misspelt.equals(misspelt.toUpperCase())) {
            return false;
        }
        if (misspelt.length() == 1) {
            return false;
        }
        String s = misspelt.toLowerCase();
        String str = dictionary.getProperty(DictionaryCompiler.getPronunciation(misspelt), "");
        if (str.toLowerCase().indexOf(s + " ") == -1 && !str.toLowerCase().endsWith(" " + s) && !str.toLowerCase().equals(s)) {

            if (b) {
                misspeltWords.setText(misspelt);
                listSimilarWords(misspelt);
            }


            return true;

        } else {
//            if (str.toLowerCase().contains(misspelt) && misspelt.length() < 3) {
//                int i = str.toLowerCase().indexOf(misspelt);
//                if (i != -1 && !str.substring(i, i + misspelt.length()).equals(str.substring(i, i + misspelt.length()).toLowerCase())) {
//                    return false;
//                }
//            }
            return false;
        }
    }

    boolean addToDictionary(String s, boolean b) {

        String pro = DictionaryCompiler.getPronunciation(s.toLowerCase());
        if (dictionary.get(pro) != null) {
            String ss = (String) dictionary.get(pro);
            dictionary.remove(pro);
            dictionary.put(pro, ss + " " + s);
        } else {
            dictionary.put(pro, s);
        }
        if (scFlag && b) {
            scTimer.restart();
        }
        addedToDictionary = true;
        return true;

    }

    synchronized Vector listSimilarWords(String misspelt) {

        Vector temp = new Vector();
        temp.clear();
        temp.trimToSize();
        temp.ensureCapacity(1);

        dlm.clear();
        dlm.trimToSize();
        dlm.ensureCapacity(1);

        String misspelt1 = misspelt;
        misspelt = misspelt.toLowerCase();

        String returnString = DictionaryCompiler.getPronunciation(misspelt);

        for (int i = 0; i < misspelt.length(); i++) {
            String one = misspelt.substring(0, i + 1);
            String two = misspelt.substring(i + 1, misspelt.length());
            if (!checkSpelling(one, false) && !checkSpelling(two, false) && one.length() > 1 && two.length() > 1) {
                String finstr = "";
                if (one.equals(one.toLowerCase())) {
                    for (int pos = 0; pos < one.length(); pos++) {
                        if (Character.isUpperCase(misspelt.charAt(pos))) {
                            finstr += Character.toUpperCase(one.charAt(pos));
                        } else {
                            finstr += one.charAt(pos);
                        }
                    }
                } else {
                    finstr += one;
                }
                finstr += " ";
                if (two.equals(two.toLowerCase())) {
                    for (int pos = 0; pos < two.length(); pos++) {
                        if (Character.isUpperCase(misspelt.charAt(pos))) {
                            finstr += Character.toUpperCase(two.charAt(pos));
                        } else {
                            finstr += two.charAt(pos);
                        }
                    }
                } else {
                    finstr += two;
                }
                if (!temp.contains(finstr)) {
                    dlm.addElement(finstr);
                    temp.add(finstr);
                }
            }
        }

        if (dictionary.get(returnString) != null) {
            String s = (String) dictionary.get(returnString);
            StringTokenizer st = new StringTokenizer(s, " ");
            while (st.hasMoreTokens()) {
                String str = st.nextToken();
                String finstr = "";
                int i = 0;
                finstr += Character.isUpperCase(misspelt1.charAt(i)) ? Character.toUpperCase(str.charAt(i)) : Character.toLowerCase(str.charAt(i));
                finstr += str.substring(1, str.length());
                if (!temp.contains(finstr)) {
                    temp.add(finstr);
                    dlm.addElement(finstr);
                }
            }
        }

        Vector subsets = getAllSubsets(misspelt);
        Iterator it = subsets.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (dictionary.get(DictionaryCompiler.getPronunciation(str)) != null) {
                String s = (String) dictionary.get(DictionaryCompiler.getPronunciation(str));
                StringTokenizer st = new StringTokenizer(s, " ");
                while (st.hasMoreTokens()) {
                    String string = st.nextToken();
                    String finstr = "";
                    int i = 0;
                    finstr += Character.isUpperCase(misspelt1.charAt(i)) ? Character.toUpperCase(string.charAt(i)) : Character.toLowerCase(string.charAt(i));
                    finstr += string.substring(1, string.length());
                    if (finstr.length() >= 2 * misspelt.length() / 3 && !temp.contains(finstr)) {
                        temp.add(finstr);
                        dlm.addElement(finstr);
                    }
                }
            }
        }

// -----------------------------------------------------OLD ALGORITHM----------------------------------------------------
//        String s = (String) dictionaryWords.get("0");
//        for (int z = 0; (s = (String) dictionaryWords.get(String.valueOf(z))) != null; z++) {
//            int n = 0;
//            if (misspelt.length() > s.length()) {
//                n = s.length() * 2;
//            } else {
//                n = misspelt.length() * 2;
//            }
//            int x = n / 2;
//            for (int i = 0; i < x; i++) {
//                if (s.charAt(i) == misspelt.charAt(i)) {
//                    n--;
//                }
//                if (s.charAt((s.length() - 1) - i) == misspelt.charAt((misspelt.length() - 1) - i)) {
//                    n--;
//                }
//            }
//            int r = (int) Math.ceil((x + 2) / 2);
//            if (n <= r && n > -1 && s.length() - misspelt.length() > -2 && s.length() - misspelt.length() < 2) {
//                dlm.addElement(s);
//                temp.add(s);
//            } else if (s.indexOf(misspelt) != -1 && s.length() - misspelt.length() > -2 && s.length() - misspelt.length() < 2) {
//                dlm.addElement(s);
//                temp.add(s);
//            } else if (misspelt.indexOf(s) != -1 && s.length() - misspelt.length() > -2 && s.length() - misspelt.length() < 2) {
//                dlm.addElement(s);
//                temp.add(s);
//            } else if (misspelt.length() % 3 == 0) {
//                int third = misspelt.length() / 3;
//                int third2 = third * 2;
//                if ((s.startsWith(misspelt.substring(0, third2)) || s.endsWith(misspelt.substring(third, misspelt.length()))) && s.length() - misspelt.length() > -2 && s.length() - misspelt.length() < 2) {
//                    dlm.addElement(s);
//                    temp.add(s);
//                }
//            }
//        }
//
//
//
//        if (dlm.size() < 3) {
//            for (int z = 0; (s = (String) dictionaryWords.get(String.valueOf(z))) != null; z++) {
//                if (s.length() - misspelt.length() > -3 && s.length() - misspelt.length() < 3) {
//                    for (int i = 1; i < (misspelt.length() - 1); i++) {
//                        if (s.startsWith(misspelt.substring(0, i)) && s.endsWith(misspelt.substring(i + 1, misspelt.length()))) {
//                            dlm.addElement(s);
//                            temp.add(s);
//                            break;
//                        }
//                    }
//                }
//            }
//        }
// ---------------------------------------------------END OLD ALGORITHM--------------------------------------------------



        if (dlm.isEmpty()) {

            if (dlm.isEmpty()) {
                dlm.addElement("No matches were found");
                temp.add("No matches were found");
            }
        }
        temp.trimToSize();
        return temp;

    }

    Vector getAllSubsets(String superSet) {
        Vector returnVal = new Vector();
        int min = (int) Math.rint(2.0 * (double) superSet.length() / 3.0);
        for (int i = min; i <= superSet.length(); i++) {
            int j = 0;
            for (j = 0; j + i < superSet.length(); j++) {
                String str = superSet.substring(j, j + i);
                if (!returnVal.contains(str)) {
                    returnVal.addElement(str);
                }
            }
            String temp = "";
            for (int z = superSet.length() - i; z < superSet.length(); z++) {
                temp += superSet.charAt(z);
            }
            if (!returnVal.contains(temp)) {
                returnVal.add(temp);
            }
        }
        for (int i = 0; i < superSet.length() - 1; i++) {
            String temp = superSet.substring(0, i) + superSet.substring(i + 1, superSet.length());
            if (!returnVal.contains(temp)) {
                returnVal.add(temp);
            }
        }
        return returnVal;
    }

    void addListeners() {
        replaceWords.addActionListener(this);
        replaceMisspelt.addActionListener(this);
        ignoreMisspelt.addActionListener(this);
        addMisspeltToDictionary.addActionListener(this);
        scClose.addActionListener(this);
        fileNew.addActionListener(this);
        fileOpen.addActionListener(this);
        fileSave.addActionListener(this);
        fileSaveAs.addActionListener(this);
        filePrint.addActionListener(this);
        fileExit.addActionListener(this);
        editCut.addActionListener(this);
        editCopy.addActionListener(this);
        editPaste.addActionListener(this);
        editUndo.addActionListener(this);
        editRedo.addActionListener(this);
        editSelectAll.addActionListener(this);
        editStamp.addActionListener(this);
        styleBold.addActionListener(this);
        styleUnderline.addActionListener(this);
        styleItalics.addActionListener(this);
        styleAlignLeft.addActionListener(this);
        styleAlignRight.addActionListener(this);
        styleAlignCenter.addActionListener(this);
        styleAlignJustified.addActionListener(this);
        toolsFind.addActionListener(this);
        toolsGoto.addActionListener(this);
        toolsSpellCheck.addActionListener(this);
        toolsSCRealTime.addActionListener(this);
        toolsGC.addActionListener(this);
        fileNew0.addActionListener(this);
        fileOpen0.addActionListener(this);
        fileSave0.addActionListener(this);
        filePrint0.addActionListener(this);
        editCut0.addActionListener(this);
        editCopy0.addActionListener(this);
        editPaste0.addActionListener(this);
        editUndo0.addActionListener(this);
        editRedo0.addActionListener(this);
        editSelectAll0.addActionListener(this);
        editStamp0.addActionListener(this);
        toolsFind0.addActionListener(this);
        toolsGoto0.addActionListener(this);
        toolsSpellCheck0.addActionListener(this);
        viewStatusbar.addItemListener(this);
        viewToolbar.addItemListener(this);
        helpHelp.addActionListener(this);
        helpAbout.addActionListener(this);
        rcIgnore.addActionListener(this);
        rcAdd.addActionListener(this);
        btnNew.addActionListener(this);
        btnOpen.addActionListener(this);
        btnSave.addActionListener(this);
        btnSaveAs.addActionListener(this);
        btnPrint.addActionListener(this);
        btnCut.addActionListener(this);
        btnCopy.addActionListener(this);
        btnPaste.addActionListener(this);
        btnUndo.addActionListener(this);
        btnRedo.addActionListener(this);
        btnFind.addActionListener(this);
        btnSC.addActionListener(this);
        btnBold.addActionListener(this);
        btnItalics.addActionListener(this);
        btnUnderline.addActionListener(this);
        btnLeft.addActionListener(this);
        btnCenter.addActionListener(this);
        btnRight.addActionListener(this);
        btnJustified.addActionListener(this);
        btnFontColor.addActionListener(this);
        btnRaiseFont.addActionListener(this);
        btnLowerFont.addActionListener(this);
        doc.addUndoableEditListener(this);
        inputField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (" \r\n\t\b!?<>,.\"\':;/}{][|\\-()".indexOf(e.getKeyChar()) != -1 && scFlag) {
                    scTimer.restart();
                }
            }
        });
        inputField.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (inputField.getSelectedText() == null && e.getButton() == MouseEvent.BUTTON3) {
                    Point pt = new Point(e.getX(), e.getY());
                    Position.Bias[] biasRet = new Position.Bias[1];
                    int pos = inputField.getUI().viewToModel(inputField, pt, biasRet);
                    if (biasRet[0] == null) {
                        biasRet[0] = Position.Bias.Forward;
                    }
                    if (pos >= 0) {
                        inputField.getCaret().setDot(pos);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    rightClickMenu.removeAll();
                    try {
                        final String text = doc.getText(0, doc.getLength());
                        badWordTokens = new StringTokenizer(text, spChars);
                        int z = 0;
                        String string = "";
                        do {
                            string = badWordTokens.nextToken();
                            z++;
                        } while (badWordTokens.hasMoreTokens() && z < whichWord);
                        string = string.trim();
                        if (!string.equals("")) {
                            final String str = string;
                            final int start = text.lastIndexOf(str, inputField.getCaretPosition());
                            if (checkSpelling(str, false)) {
                                JMenu similarWords = new JMenu("Similar Matches");
                                Vector v = listSimilarWords(str);
                                for (int i = 0; i < 15 && i < v.size(); i++) {
                                    JMenuItem temp = new JMenuItem((String) v.get(i));
                                    temp.addActionListener(new ActionListener() {

                                        @Override
                                        public void actionPerformed(ActionEvent ae) {
                                            try {
                                                int carPos = inputField.getCaretPosition();
                                                int startPos = start;
                                                int mlength = str.length();
                                                String r = ae.getActionCommand();
                                                int rlength = r.length();
                                                int z = 0;
                                                carPos -= mlength - rlength;
                                                for (; z < mlength && z < rlength; z++) {
                                                    inputField.select(startPos + z, startPos + z + 1);
                                                    inputField.replaceSelection(String.valueOf(r.charAt(z)));
                                                }
                                                if (z < mlength) {
                                                    inputField.select(startPos + z, startPos + str.length());
                                                    inputField.replaceSelection("");
                                                } else if (z < rlength) {
                                                    inputField.replaceSelection(r.substring(z, r.length()));
                                                }
                                                if (scFlag) {
                                                    doSCHighlights();
                                                }
                                                inputField.setCaretPosition(carPos);
                                            } catch (Exception e) {
                                            }
                                        }
                                    });
                                    if (temp.getText().equals("No matches were found")) {
                                        temp.setEnabled(false);
                                    }
                                    similarWords.add(temp);
                                }
                                similarWords.addSeparator();
                                similarWords.add(rcIgnore);
                                rcIgnore.setActionCommand(str);
                                similarWords.add(rcAdd);
                                rcAdd.setActionCommand(str);
                                rightClickMenu.add(similarWords);
                                rightClickMenu.addSeparator();
                            }
                        }
                    } catch (Exception ex) {
                    }
                    rightClickMenu.add(fileNew0);
                    rightClickMenu.add(fileOpen0);
                    rightClickMenu.add(fileSave0);
                    rightClickMenu.add(filePrint0);
                    rightClickMenu.addSeparator();
                    rightClickMenu.add(editCut0);
                    rightClickMenu.add(editCopy0);
                    rightClickMenu.add(editPaste0);
                    rightClickMenu.addSeparator();
                    rightClickMenu.add(editUndo0);
                    rightClickMenu.add(editRedo0);
                    rightClickMenu.addSeparator();
                    rightClickMenu.add(editSelectAll0);
                    rightClickMenu.addSeparator();
                    rightClickMenu.add(editStamp0);
                    rightClickMenu.addSeparator();
                    rightClickMenu.add(toolsFind0);
                    rightClickMenu.addSeparator();
                    rightClickMenu.add(toolsGoto0);
                    rightClickMenu.addSeparator();
                    rightClickMenu.add(toolsSpellCheck0);
                    rightClickMenu.validate();
                    rightClickMenu.show(inputField, e.getX(), e.getY());
                }
            }
        });
    }

    void updateUI() {

        mbar.updateUI();
        file.updateUI();
        fileNew.updateUI();
        fileOpen.updateUI();
        fileSave.updateUI();
        fileSaveAs.updateUI();
        filePrint.updateUI();
        fileNew0.updateUI();
        fileOpen0.updateUI();
        fileSave0.updateUI();
        filePrint0.updateUI();
        fileExit.updateUI();
        edit.updateUI();
        editCut.updateUI();
        editCopy.updateUI();
        editPaste.updateUI();
        editUndo.updateUI();
        editRedo.updateUI();
        editSelectAll.updateUI();
        editStamp.updateUI();
        editCut0.updateUI();
        editCopy0.updateUI();
        editPaste0.updateUI();
        editUndo0.updateUI();
        editRedo0.updateUI();
        editSelectAll0.updateUI();
        editStamp0.updateUI();
        style.updateUI();
        styleBold.updateUI();
        styleItalics.updateUI();
        styleUnderline.updateUI();
        styleAlign.updateUI();
        styleAlignLeft.updateUI();
        styleAlignCenter.updateUI();
        styleAlignRight.updateUI();
        styleAlignJustified.updateUI();
        tools.updateUI();
        toolsFind.updateUI();
        toolsGoto.updateUI();
        toolsSpellCheck.updateUI();
        toolsFind0.updateUI();
        toolsGoto0.updateUI();
        toolsSpellCheck0.updateUI();
        toolsSCRealTime.updateUI();
        toolsGC.updateUI();
        view.updateUI();
        viewStatusbar.updateUI();
        viewToolbar.updateUI();
        help.updateUI();
        helpHelp.updateUI();
        helpAbout.updateUI();
        rightClickMenu.updateUI();
        rcAdd.updateUI();
        rcIgnore.updateUI();

        btnNew.updateUI();
        btnOpen.updateUI();
        btnSave.updateUI();
        btnSaveAs.updateUI();
        btnPrint.updateUI();
        btnCut.updateUI();
        btnCopy.updateUI();
        btnPaste.updateUI();
        btnUndo.updateUI();
        btnRedo.updateUI();
        btnSC.updateUI();
        btnFind.updateUI();
        btnBold.updateUI();
        btnItalics.updateUI();
        btnUnderline.updateUI();
        btnLeft.updateUI();
        btnCenter.updateUI();
        btnRight.updateUI();
        btnJustified.updateUI();
        btnFontColor.updateUI2();
        fontList.updateUI();
        fontSizes.updateUI();
        btnFontColor.updateUI();
        tbar.updateUI();
        tbarAlign.updateUI();
        tbarEdit.updateUI();
        tbarFile.updateUI();
        tbarFonts.updateUI();
        tbarNStyles.updateUI();
        tbarStyle.updateUI();
        tbarStyles.updateUI();
        tbarTools.updateUI();

        statusLbl.updateUI();

        misspeltPanel.updateUI();
        misspeltLbl.updateUI();
        listPanel.updateUI();
        misspeltWords.updateUI();
        similarLbl.updateUI();
        similarWordsList.updateUI();
        replacePanel.updateUI();
        misspeltButtons.updateUI();
        ignoreMisspelt.updateUI();
        replaceMisspelt.updateUI();
        addMisspeltToDictionary.updateUI();
        scClose.updateUI();

        findPanel.updateUI();
        findBtn.updateUI();
        findError.updateUI();
        findLbl.updateUI();
        findPanel.updateUI();
        replaceInput.updateUI();
        btnCase.updateUI();
        replaceLbl.updateUI();
        matchCasePanel.updateUI();
        combinedPanel.updateUI();
        buttonPanel.updateUI();
        findInput.updateUI();
        replaceInput.updateUI();
        findError.updateUI();
        combinedPanel.updateUI();
        findBtn.updateUI();
        replaceBtn.updateUI();
        replaceAllBtn.updateUI();
        closeBtn.updateUI();

        misspeltPanel.updateUI();
        misspeltLbl.updateUI();
        misspeltWords.updateUI();
        listPanel.updateUI();
        similarLbl.updateUI();
        replacePanel.updateUI();
        scReplaceLbl.updateUI();
        replaceWords.updateUI();
        misspeltButtons.updateUI();
        replaceMisspelt.updateUI();
        ignoreMisspelt.updateUI();
        addMisspeltToDictionary.updateUI();
        scClose.updateUI();
        spellCheckPanel.updateUI();

        fontList.updateUI();
        fontSizes.updateUI();

        inputField.updateUI();
        getContentPane().remove(jsp);
        validate();
        jsp = new JScrollPane(inputField);
        getContentPane().add(jsp, BorderLayout.CENTER);
        jfc.updateUI();

    }
    synchronized void doSCHighlights() {
        try {
            Vector x = new Vector();
            Vector y = new Vector();
            if (doc.getText(0, doc.getLength()).indexOf(" ") == -1 && doc.getText(0, doc.getLength()).indexOf("\r") == -1 && doc.getText(0, doc.getLength()).indexOf("\n") == -1) {
                return;
            }
            int one = inputField.getCaretPosition() - 5000;
            if (one < 0) {
                one = 0;
            }
            int two = inputField.getCaretPosition() + 5000;
            if (two > doc.getText(0, doc.getLength()).length()) {
                two = doc.getLength();
            }
            String string = doc.getText(one, two);
            int z = 0;
            StringTokenizer tokens = new StringTokenizer(string, spChars, true);
            while (tokens.hasMoreTokens()) {
                String s = tokens.nextToken();
                if (checkSpelling(s, false) && spChars.indexOf(s) == -1) {
                    x.add(new Integer(z));
                    y.add(new Integer(z + s.length()));
                }
                z += s.length();
            }
            int r = x.size();
            inputField.getHighlighter().removeAllHighlights();
            for (int i = 0; i < r; i++) {
                inputField.getHighlighter().addHighlight(Integer.parseInt(x.get(i).toString()), Integer.parseInt(y.get(i).toString()), underliner);
            }
        } catch (Exception e) {
            return;
        }
    }

    void doGoto() {
        final JDialog gotoDialog = new JDialog(this, "NPad - Go to Line", true);
        JLabel lbl = new JLabel("Enter line number: ", JLabel.RIGHT);
        final JLabel gotoError = new JLabel("", JLabel.CENTER);
        final JTextField gotoTF = new JTextField("1", 3);
        gotoTF.setDocument(new NumberDocument(gotoTF, "NO LIMIT", true));
        JButton gotoBtn = new JButton("OK");
        JButton gotoCancel = new JButton("Cancel");
        gotoBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (gotoTF.getText().equals("")) {
                    gotoError.setText("Type a line number");
                } else {
                    int line = Integer.parseInt(gotoTF.getText());
                    try {
                        inputField.setCaretPosition(new JTextArea(doc.getText(0, doc.getLength())).getLineStartOffset(line - 1));
                        gotoDialog.dispose();
                    } catch (Exception be) {
                        gotoError.setText("Bad line number");
                    }
                }
            }
        });
        gotoCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                gotoDialog.dispose();
            }
        });
        gotoDialog.setLayout(new BorderLayout());
        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.add(lbl);
        panel1.add(gotoTF);
        gotoDialog.add(panel1, BorderLayout.NORTH);
        gotoDialog.add(gotoError, BorderLayout.CENTER);
        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.add(gotoBtn);
        panel2.add(gotoCancel);
        gotoDialog.add(panel2, BorderLayout.SOUTH);
        Dimension d = getSize();
        gotoDialog.setBounds((d.width - 200) / 2 + getX(), (d.height - 120) / 2 + getY(), 200, 120);
        gotoDialog.setResizable(false);
        gotoDialog.setVisible(true);
    }

    double checkForUpdates() {
        try {
            URL url = new URL("http://www.geocities.com/nhnt website/index.html");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            br.read(c);
            String str = new String(c);
            int i = str.indexOf("<!--VERSION ");
            if (i == -1) {
                return 0;
            } else {
                i += 12;
                int x = str.indexOf(" ", i);
                return Double.parseDouble(str.substring(i, x));
            }
        } catch (Exception e) {
            return 0;
        }
    }

    void replaceWholeWords(String str, String replace, int index, boolean onlyOnce) {
        int carPos = inputField.getCaretPosition();
        try {
            String text = doc.getText(0, doc.getLength());
            int end = index;
            int z = text.indexOf(str, end);
            int first = z;
            if (z == -1) {
                findError.setText("Unable to find \"" + str + "\"");
                inputField.setCaretPosition(carPos);
                return;
            }
            end = z + str.length();
            inputField.select(z, end);
            inputField.replaceSelection(replace);
            end = z + replace.length();
            replaceIndex = end;
            if (!onlyOnce) {
                do {
                    text = doc.getText(0, doc.getLength());
                    z = text.indexOf(str, end);
                    end = z + str.length();
                    inputField.select(z, end);
                    inputField.replaceSelection(replace);
                    end = z + replace.length();
                } while (z > first && end < doc.getLength());
            }
            findError.setText("Unable to find \"" + str + "\"");
            inputField.setCaretPosition(carPos);
        } catch (Exception e) {
            showErrorDialog("Unable to replace text");
            inputField.setCaretPosition(carPos);
        }
    }

    void off() {
        synchronized (this) {
            statusFlag = false;
        }
    }

    void on() {
        statusFlag = true;
        new Thread(this).start();
        if (scFlag) {
            scTimer.restart();
        }
    }

    void showErrorDialog(String msg) {
        JLabel error = new JLabel(msg, JLabel.CENTER);
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        final JDialog errDisplay = new JDialog();
        errDisplay.setTitle("Error");
        errDisplay.setLayout(new BorderLayout());
        JLabel errorLabel = new JLabel(errorImg, JLabel.CENTER);
        jp1.add(errorLabel);
        jp1.add(error);
        JButton errButton = new JButton("OK");
        jp2.add(errButton);
        errDisplay.add(jp1, BorderLayout.NORTH);
        errDisplay.add(jp2, BorderLayout.SOUTH);
        Dimension dd = getSize();
        errDisplay.setBounds((dd.width - 250) / 2 + getX(), (dd.height - 100) / 2 + getY(), 250, 100);
        errDisplay.setVisible(true);
        errButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                errDisplay.dispose();

            }
        });
    }

    public static void main(String args[]) {

        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        if (args.length != 0) {
            String str = args[0];
            if (args.length > 1) {
                for (int i = 1; i < args.length; i++) {
                    str += " " + args[i];
                }
            }
            new NPad(str);
        } else {
            new NPad("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        Object src = ae.getSource();

        if (src == btnNew || src == fileNew || src == fileNew0) {

            if (checkSaveAs() == null) {
                return;
            }
            saverFile = null;
            saveAsFlag = true;
            inputField.setText("");
            notModified = true;
            um = new UndoManager();
            setTitle("Untitled - NPad");
            inputField.requestFocus();

        } else if (src == fileSave || src == fileSaveAs || src == btnSave || src == btnSaveAs || src == fileSave0) {
            saveFile(src);
        } else if (src == fileOpen || src == btnOpen || src == fileOpen0) {
            openFile();
        } else if (src == filePrint || src == btnPrint || src == filePrint0) {
            synchronized (this) {
                statusFlag = false;
            }
            scTimer.stop();
            try {
                inputField.print();
            } catch (PrinterException e) {
                showErrorDialog("Unable to print.");
            }
            synchronized (this) {
                statusFlag = true;
            }
            new Thread(this).start();
            if (scFlag) {
                scTimer.restart();
            }
            inputField.requestFocus();
        } else if (src == fileExit) {

            if (checkSaveAs() == null) {
                return;
            }
            System.exit(0);

        } else if (src == editCut || src == btnCut || src == editCut0) {
            inputField.cut();
            if (scFlag) {
                scTimer.restart();
            }
            inputField.requestFocus();
        } else if (src == editCopy || src == btnCopy || src == editCopy0) {
            inputField.copy();
            if (scFlag) {
                scTimer.restart();
            }
            inputField.requestFocus();
        } else if (src == editPaste || src == btnPaste || src == editPaste0) {
            inputField.paste();
            editPaste.setEnabled(false);
            editPaste.setEnabled(true);
            if (scFlag) {
                scTimer.restart();
            }
            inputField.requestFocus();
        } else if (src == editUndo || src == btnUndo || src == editUndo0) {
            undo();
        } else if (src == editRedo || src == btnRedo || src == editRedo0) {
            redo();
        } else if (src == editSelectAll || src == editSelectAll0) {
            inputField.selectAll();
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == editStamp || src == editStamp0) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            inputField.replaceSelection(sdf.format(date));
            editStamp.setEnabled(false);
            editStamp.setEnabled(true);
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == toolsFind || src == btnFind || src == toolsFind0) {
            doFindAndReplace();
        } else if (src == findInput || src == findBtn) {
            find(findInput.getText());
        } else if (src == replaceInput || src == replaceBtn) {

            replaceInput.removeActionListener(this);
            replaceBtn.removeActionListener(this);
            findTxt = findInput.getText();
            replaceTxt = replaceInput.getText();
            replaceWholeWords(findTxt, replaceTxt, replaceIndex, true);
            replaceInput.addActionListener(this);
            replaceBtn.addActionListener(this);
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == replaceAllBtn) {

            if (!replaceTxt.equals(replaceInput.getText()) || !replaceInput.getText().equals(findInput.getText())) {

                findTxt = findInput.getText();
                replaceTxt = replaceInput.getText();
                replaceAll();

            }

            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == closeBtn) {

            findInput.setText("");
            replaceInput.setText("");
            startIndex = 0;
            endIndex = 0;
            far.dispose();
            if (scFlag) {
                scTimer.restart();
            }
            replaceIndex = 0;
        } else if (src == toolsGoto || src == toolsGoto0) {
            doGoto();
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == toolsSpellCheck || src == btnSC || src == toolsSpellCheck0) {
            doSpellCheck();
        } else if (src == toolsSCRealTime) {
            scFlag = toolsSCRealTime.getState();
            if (scFlag) {
                if (scFlag) {
                    scTimer.restart();
                }
            } else {
                inputField.getHighlighter().removeAllHighlights();
            }
        } else if (src == replaceMisspelt || src == replaceWords) {

            if (!misspeltWords.getText().equals("") && !replaceWords.getText().equals("")) {

                replaceWholeWords(misspeltWords.getText(), replaceWords.getText(), replaceIndex, true);
                if (scFlag) {
                    scTimer.restart();
                }
                if (!badWordTokens.hasMoreTokens()) {

                    misspeltWords.setText("There are no spelling mistakes");
                    dlm.clear();
                    dlm.trimToSize();
                    replaceWords.setText("");

                }

                while (badWordTokens.hasMoreTokens()) {

                    if (checkSpelling(badWordTokens.nextToken(), true)) {
                        break;
                    }
                }

            } else {
                Toolkit.getDefaultToolkit().beep();
            }
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == ignoreMisspelt) {

            if (dictionary.get(DictionaryCompiler.getPronunciation(misspeltWords.getText().toLowerCase())) != null) {
                String ss = (String) dictionary.get(DictionaryCompiler.getPronunciation(misspeltWords.getText().toLowerCase()));
                dictionary.remove(DictionaryCompiler.getPronunciation(misspeltWords.getText().toLowerCase()));
                dictionary.put(DictionaryCompiler.getPronunciation(misspeltWords.getText().toLowerCase()), ss + " " + misspeltWords.getText());
            } else {
                dictionary.put(DictionaryCompiler.getPronunciation(misspeltWords.getText().toLowerCase()), misspeltWords.getText());
            }

            dictionaryWords.put(misspeltWords.getText().toLowerCase(), "ok");

            if (scFlag) {
                scTimer.restart();
            }
            if (!badWordTokens.hasMoreTokens()) {
                misspeltWords.setText("There are no spelling mistakes");
                dlm.clear();
                dlm.trimToSize();
                replaceWords.setText("");
            }

            while (badWordTokens.hasMoreTokens()) {

                if (checkSpelling(badWordTokens.nextToken(), true)) {
                    break;
                }
            }
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == addMisspeltToDictionary) {

            if (misspeltWords.getText().indexOf(" ") == -1) {
                if (!addToDictionary(misspeltWords.getText(), true)) {
                    showErrorDialog("Unable to add to the dictionary.");
                }
                if (!badWordTokens.hasMoreTokens()) {
                    misspeltWords.setText("There are no spelling mistakes");
                    dlm.clear();
                    dlm.trimToSize();
                    replaceWords.setText("");
                }
                while (badWordTokens.hasMoreTokens()) {

                    if (checkSpelling(badWordTokens.nextToken(), true)) {
                        break;
                    }
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == scClose) {

            badWordTokens = null;
            misspeltWords.setText("");
            replaceWords.setText("");
            dlm.clear();
            dlm.trimToSize();
            findTxt = "";
            replaceTxt = "";
            spellCheckDialog.dispose();
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == toolsGC) {
            System.runFinalization();
            System.gc();
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == helpHelp) {
            try {
                URL helpurl = this.getClass().getClassLoader().getResource("npadhelp.txt");
                InputStream is = helpurl.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                char cc[] = new char[is.available()];
                br.read(cc);
                br.close();
                final JDialog helpd = new JDialog(this, "NPad - Help", false);
                JTextArea helpArea = new JTextArea(String.valueOf(cc));
                helpArea.setEditable(false);
                helpArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
                helpArea.setTabSize(3);
                helpArea.setForeground(Color.black);
                helpArea.setBackground(Color.white);
                helpd.setLayout(new BorderLayout());
                helpd.add(new JScrollPane(helpArea), BorderLayout.CENTER);
                JButton helpClose = new JButton("Close");
                helpd.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent e) {
                        helpHelp.setEnabled(true);
                        helpd.dispose();
                    }
                });
                helpClose.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        helpHelp.setEnabled(true);
                        helpd.dispose();
                    }
                });
                helpd.add(helpClose, BorderLayout.SOUTH);
                Dimension dd = getSize();
                helpd.setBounds((dd.width - 400) / 2 + getX(), (dd.height - 400) / 2 + getY(), 400, 400);
                helpd.setVisible(true);
                helpHelp.setEnabled(false);
            } catch (Exception e) {
                showErrorDialog("Unable to load the help file");
            }
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == helpAbout) {

            final JDialog aboutDialog = new JDialog(this, "About NPad", true);
            aboutDialog.setLayout(new FlowLayout());
            JLabel aboutLabel = new JLabel("NPad v 3.0", JLabel.CENTER);
            JLabel aboutLabel2 = new JLabel("2007 - 2008 By Nihanth S", JLabel.CENTER);
            JLabel aboutLabel3 = new JLabel("This program uses the", JLabel.CENTER);
            JLabel aboutLabel4 = new JLabel("IText RTF and PDF APIs.", JLabel.CENTER);
            JPanel aboutPanel1 = new JPanel(new GridLayout(4, 1, 0, 0));
            JButton aboutClose = new JButton("Close");
            JPanel aboutPanel2 = new JPanel(new FlowLayout());
            aboutDialog.setLayout(new FlowLayout());
            aboutLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
            aboutLabel2.setFont(new Font("SansSerif", Font.BOLD, 14));
            aboutLabel3.setFont(new Font("SansSerif", Font.BOLD, 12));
            aboutLabel4.setFont(new Font("SansSerif", Font.BOLD, 12));
            aboutPanel1.add(aboutLabel);
            aboutPanel1.add(aboutLabel2);
            aboutPanel1.add(aboutLabel3);
            aboutPanel1.add(aboutLabel4);
            aboutClose.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    aboutDialog.dispose();
                }
            });
            aboutPanel2.add(aboutClose);
            aboutDialog.add(aboutPanel1);
            aboutDialog.add(aboutPanel2);
            aboutDialog.setBounds((getSize().width - 225) / 2 + getX(),
                    (getSize().height - 175) / 2 + getY(),
                    225, 175);
            aboutDialog.setResizable(false);
            aboutDialog.setVisible(true);
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == scTimer) {
            doSCHighlights();
        } else if (src == rcIgnore) {
            dictionaryWords.put(rcIgnore.getActionCommand().toLowerCase(), "ok");
            if (dictionary.get(DictionaryCompiler.getPronunciation(rcIgnore.getActionCommand())) != null) {
                String ss = (String) dictionary.get(DictionaryCompiler.getPronunciation(rcIgnore.getActionCommand()));
                dictionary.remove(DictionaryCompiler.getPronunciation(rcIgnore.getActionCommand()));
                dictionary.put(DictionaryCompiler.getPronunciation(rcIgnore.getActionCommand()), ss + " " + rcIgnore.getActionCommand());
            } else {
                dictionary.put(DictionaryCompiler.getPronunciation(rcIgnore.getActionCommand()), rcIgnore.getActionCommand());
            }
            if (scFlag) {
                scTimer.restart();
            }
        } else if (src == rcAdd) {
            if (!addToDictionary(rcAdd.getActionCommand(), true)) {
                showErrorDialog("Unable to add to the dictionary.");
            }
        } else if (src == btnBold || src == styleBold) {
            new StyledEditorKit.BoldAction().actionPerformed(ae);
            inputField.requestFocus();
        } else if (src == btnItalics || src == styleItalics) {
            new StyledEditorKit.ItalicAction().actionPerformed(ae);
            inputField.requestFocus();
        } else if (src == btnUnderline || src == styleUnderline) {
            new StyledEditorKit.UnderlineAction().actionPerformed(ae);
            inputField.requestFocus();
        } else if (src == btnLeft || src == styleAlignLeft) {
            new StyledEditorKit.AlignmentAction("", StyleConstants.ALIGN_LEFT).actionPerformed(ae);
            inputField.requestFocus();
        } else if (src == btnCenter || src == styleAlignCenter) {
            new StyledEditorKit.AlignmentAction("", StyleConstants.ALIGN_CENTER).actionPerformed(ae);
            inputField.requestFocus();
        } else if (src == btnRight || src == styleAlignRight) {
            new StyledEditorKit.AlignmentAction("", StyleConstants.ALIGN_RIGHT).actionPerformed(ae);
            inputField.requestFocus();
        } else if (src == btnJustified || src == styleAlignJustified) {
            new StyledEditorKit.AlignmentAction("", StyleConstants.ALIGN_JUSTIFIED).actionPerformed(ae);
            inputField.requestFocus();
        } else if (ae.getActionCommand().equals("fontcolor")) {
            new StyledEditorKit.ForegroundAction("", btnFontColor.getColor()).actionPerformed(ae);
            inputField.requestFocus();
        } else if (src == fontList) {
            fontName = list.getText();
            if (!fontName.equals(" ")) {
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attr, fontName);
                int p0 = inputField.getSelectionStart();
                int p1 = inputField.getSelectionEnd();
                if (p1 != p0) {
                    doc.setCharacterAttributes(p0, p1 - p0, attr, false);
                } else {
                    StyleConstants.setFontFamily(inputField.getInputAttributes(), fontName);
                }
                inputField.requestFocus();
            }
        } else if (src == fontSizes) {
            if (!sizes.getText().trim().equals("")) {
                fontSize = Integer.parseInt(sizes.getText().trim());
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontSize(attr, fontSize);
                int p0 = inputField.getSelectionStart();
                int p1 = inputField.getSelectionEnd();
                if (p1 != p0) {
                    doc.setCharacterAttributes(p0, p1 - p0, attr, false);
                } else {
                    StyleConstants.setFontSize(inputField.getInputAttributes(), fontSize);
                }
                inputField.requestFocus();
            }
        } else if (src == sizes) {
            if (!sizes.getText().trim().equals("")) {
                fontSize = Integer.parseInt(sizes.getText().trim());
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontSize(attr, fontSize);
                int p0 = inputField.getSelectionStart();
                int p1 = inputField.getSelectionEnd();
                if (p1 != p0) {
                    doc.setCharacterAttributes(p0, p1 - p0, attr, false);
                } else {
                    StyleConstants.setFontSize(inputField.getInputAttributes(), fontSize);
                }
                inputField.requestFocus();
            }
        } else if (src == list) {
            fontName = list.getText();
            if (!fontName.equals(" ")) {
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attr, fontName);
                int p0 = inputField.getSelectionStart();
                int p1 = inputField.getSelectionEnd();
                if (p1 != p0) {
                    doc.setCharacterAttributes(p0, p1 - p0, attr, false);
                } else {
                    StyleConstants.setFontFamily(inputField.getInputAttributes(), fontName);
                }
                inputField.requestFocus();
            }
        }

    }

    @Override
    public void itemStateChanged(ItemEvent ie) {

        Object src = ie.getSource();

        if (src == viewStatusbar) {

            if (viewStatusbar.getState()) {

                getContentPane().add(statusLbl, BorderLayout.SOUTH);
                getContentPane().validate();
                bStat = true;

            } else {

                getContentPane().remove(statusLbl);
                getContentPane().validate();
                bStat = false;

            }

        } else if (src == viewToolbar) {

            if (viewToolbar.getState()) {
                getContentPane().add(tbar, BorderLayout.NORTH);
                getContentPane().validate();
                bTbar = true;

            } else {

                getContentPane().remove(tbar);
                getContentPane().validate();
                bTbar = false;

            }

        } else if (src == fontList) {
            fontName = list.getText();
            if (!fontName.equals(" ")) {
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attr, fontName);
                int p0 = inputField.getSelectionStart();
                int p1 = inputField.getSelectionEnd();
                if (p1 != p0) {
                    doc.setCharacterAttributes(p0, p1 - p0, attr, false);
                } else {
                    StyleConstants.setFontFamily(inputField.getInputAttributes(), fontName);
                }
                inputField.requestFocus();
            }
        } else if (src == fontSizes) {
            if (!sizes.getText().trim().equals("")) {
                fontSize = Integer.parseInt(sizes.getText().trim());
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontSize(attr, fontSize);
                int p0 = inputField.getSelectionStart();
                int p1 = inputField.getSelectionEnd();
                if (p1 != p0) {
                    doc.setCharacterAttributes(p0, p1 - p0, attr, false);
                } else {
                    StyleConstants.setFontSize(inputField.getInputAttributes(), fontSize);
                }
                inputField.requestFocus();
            }
        } else {
            matchCase = !matchCase;
        }
    }

    @Override
    public void undoableEditHappened(UndoableEditEvent e) {

        if (notModified) {

            if (saverFile != null) {
                setTitle(saverFile.getName() + "* - NPad");
            } else {
                setTitle("Untitled* - NPad");
            }
            notModified = false;

        }
        um.addEdit(e.getEdit());

    }

    @Override
    public void run() {
        while (statusFlag) {
            try {
                int whichWordOld = whichWord;
                wordCount();
                if (inputField.hasFocus()) {
                    fontList.removeActionListener(this);
                    fontSizes.removeActionListener(this);
                    ((JTextField) fontList.getEditor().getEditorComponent()).setActionCommand(" ");
                    ((JTextField) fontSizes.getEditor().getEditorComponent()).setActionCommand(" ");
                    JTextField listf = ((JTextField) fontList.getEditor().getEditorComponent());
                    JTextField sizesf = ((JTextField) fontSizes.getEditor().getEditorComponent());
                    if (inputField.getSelectedText() == null) {
                        if (!fontList.hasFocus()) {
                            listf.setText(StyleConstants.getFontFamily(inputField.getInputAttributes()));
                        }
                        if (!fontSizes.hasFocus()) {
                            sizesf.setText(String.valueOf(StyleConstants.getFontSize(inputField.getInputAttributes())));
                        }
                    } else {
                        if (!fontList.hasFocus()) {
                            listf.setText("");
                        }
                        if (!fontSizes.hasFocus()) {
                            sizesf.setText("");
                        }
                    }
                    ((JTextField) fontList.getEditor().getEditorComponent()).setActionCommand("list");
                    ((JTextField) fontSizes.getEditor().getEditorComponent()).setActionCommand("sizes");
                }
                statusLbl.setText("Chars: " + String.valueOf(whichChar) + "/" + String.valueOf(totChar) +
                        "     Lines: " + String.valueOf(whichLine) + "/" + String.valueOf(totLine) +
                        "     Columns: " + String.valueOf(whichCol) + "/" + String.valueOf(totCol) +
                        "     Words: " + String.valueOf(whichWord) + "/" + String.valueOf(totWord));
                btnBold.setSelected(StyleConstants.isBold(inputField.getInputAttributes()));
                btnItalics.setSelected(StyleConstants.isItalic(inputField.getInputAttributes()));
                btnUnderline.setSelected(StyleConstants.isUnderline(inputField.getInputAttributes()));
                btnLeft.setSelected(StyleConstants.getAlignment(inputField.getInputAttributes()) == StyleConstants.ALIGN_LEFT);
                btnRight.setSelected(StyleConstants.getAlignment(inputField.getInputAttributes()) == StyleConstants.ALIGN_RIGHT);
                btnCenter.setSelected(StyleConstants.getAlignment(inputField.getInputAttributes()) == StyleConstants.ALIGN_CENTER);
                btnJustified.setSelected(StyleConstants.getAlignment(inputField.getInputAttributes()) == StyleConstants.ALIGN_JUSTIFIED);
                btnFontColor.setColor(StyleConstants.getForeground(inputField.getInputAttributes()));
                if (whichWordOld != whichWord && scFlag && inputField.getSelectedText() == null) {
                    scTimer.restart();
                }
            } catch (Exception e) {
                continue;
            }
        }

    }

    synchronized void undo() {
        editUndo.setEnabled(false);
        doc.removeUndoableEditListener(this);
        if (um.canUndo()) {
            um.undo();
        }
        editUndo.setEnabled(true);
        doc.addUndoableEditListener(this);
        if (scFlag) {
            scTimer.restart();
        }
    }

    synchronized void redo() {
        editUndo.setEnabled(false);
        doc.removeUndoableEditListener(this);
        if (um.canRedo()) {
            um.redo();
        }
        editUndo.setEnabled(true);
        doc.addUndoableEditListener(this);
        if (scFlag) {
            scTimer.restart();
        }
    }
}

class NPadSplashScreen extends JWindow {

    private JLabel status = new JLabel("", JLabel.CENTER);
    private ImageIcon image = new ImageIcon(this.getClass().getClassLoader().getResource("images/LoadGraphic.PNG"));
    private java.awt.Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("images/npad.PNG"));
    private JFrame jframe = new JFrame("Starting NPad v 3.0");

    public NPadSplashScreen(JFrame frame) {
        super(frame);
        JLabel imageLbl = new JLabel(image, JLabel.CENTER);
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLbl, BorderLayout.CENTER);
        imagePanel.add(status, BorderLayout.SOUTH);
        imagePanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        add(imagePanel);
        pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dd = getSize();
        setBounds((d.width - dd.width) / 2, (d.height - dd.height) / 2, dd.width, dd.height);
        jframe.setBounds(-100, -100, 0, 0);
        jframe.setResizable(false);
        jframe.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setVisible(false);
            }
        });
        jframe.setIconImage(icon);
    }

    @Override
    public void dispose() {
        super.dispose();
        jframe.dispose();
    }

    void updateStatus(String status) {
        this.status.setText(status);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        jframe.setVisible(b);
    }

}

class NumberDocument extends PlainDocument {

    boolean limit = false;
    JTextComponent field;
    boolean beep = true;

    public NumberDocument(JTextComponent jtc, String islimit, boolean beep) {
        if (islimit.equals("LIMIT")) {
            limit = true;
        }
        field = jtc;
        this.beep = beep;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        char[] source = str.toCharArray();
        char[] dest = new char[source.length];
        int destIndex = 0;
        for (int i = 0; i < source.length; i++) {
            if (Character.isDigit(source[i]) && (!limit || field.getText().length() < 2)) {
                dest[destIndex++] = source[i];
            } else if (beep) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        String insert = new String(dest, 0, destIndex);
        super.insertString(offs, insert, a);
    }
}