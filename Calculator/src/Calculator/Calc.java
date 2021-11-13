package Calculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Calc extends JFrame implements ActionListener {

    int Counter;
    double Result;
    double Operand;
    double Mem;
    boolean DecimalFlag;
    boolean SignFlag;
    boolean OperatorKey;
    boolean FunctionKey;
    boolean SHIFT;
    boolean radiansFlag = false;
    boolean permRadFlag;
    int Operator;
    int ranNumLimit = 100;
    int permNumLimit;
    char[] c = new char[35];
    String dataStr = "";
    String Status;
    JLabel statusLbl = new JLabel(" ", JLabel.CENTER);
    JLabel display = new JLabel("0", JLabel.RIGHT);
    JButton one = new JButton("1");
    JButton two = new JButton("2");
    JButton three = new JButton("3");
    JButton four = new JButton("4");
    JButton five = new JButton("5");
    JButton six = new JButton("6");
    JButton seven = new JButton("7");
    JButton eight = new JButton("8");
    JButton nine = new JButton("9");
    JButton zero = new JButton("0");
    JButton sub = new JButton("-");
    JButton mul = new JButton("x");
    JButton plus = new JButton("+");
    JButton eql = new JButton("=");
    JButton div = new JButton("÷");
    JButton clr = new JButton("C");
    JButton decimal = new JButton(".");
    JButton sign = new JButton("±");
    JButton m = new JButton("M");
    JButton mClr = new JButton("MC");
    JButton mR = new JButton("MR");
    JButton shft = new JButton("sft");
    JButton perPi = new JButton("%");
    JButton sqr1ByX = new JButton("x²");
    JButton sqrtFact = new JButton("\u221Ax");
    JButton powLog = new JButton("^");
    JButton modSin = new JButton("mod");
    JButton binCos = new JButton("bin");
    JButton hexTan = new JButton("hex");
    JButton del = new JButton("del");
    File memoryFile;
    FileReader fr;
    FileWriter fw;
    JMenuBar mbar;
    JMenu file;
    JMenu options;
    JMenu help;
    JMenuItem close;
    JMenuItem ranNum;
    JMenu setRanNum;
    JMenuItem ranNumLimitCurrent;
    JMenuItem defaultOptions;
    JMenuItem optionsSaver;
    JMenuItem Degrees;
    JMenuItem Radians;
    JMenuItem ranNumLimit1;
    JMenuItem ranNumLimit2;
    JMenuItem ranNumLimit3;
    JMenuItem ranNumLimit4;
    JMenuItem ranNumLimit5;
    JMenuItem about;
    AboutDialog aboutDialog;
    Color clr1 = new Color(0, 0, 200);
    Color brown = new Color(128, 64, 0);
    Color gre = new Color(0, 200, 0);
    boolean current = true;

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Calc calc = new Calc();
        calc.setResizable(false);
        calc.setTitle("NCalc");
        calc.setIconImage(Toolkit.getDefaultToolkit().getImage("calc.gif"));
        calc.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - 412) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - 412) / 2,
                412, 412);
        calc.setVisible(true);
    }

    public Calc() {

        getContentPane().setLayout(null);

        getContentPane().setFont(new Font("dialog", Font.BOLD,13));

        display.setBounds(42, 15, 328, 30);
        display.setFont(new Font("dialog", Font.BOLD, 20));
        display.setBackground(Color.white);
        display.setForeground(clr1);
        display.setToolTipText("");
        display.setOpaque(true);
        display.setBorder(new javax.swing.border.LineBorder(Color.black));
        getContentPane().add(display);

        one.addActionListener(this);
        one.setBounds(42, 65, 60, 34);
        one.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(one);

        two.addActionListener(this);
        two.setBounds(106, 65, 60, 34);
        two.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(two);

        three.addActionListener(this);
        three.setBounds(170, 65, 60, 34);
        three.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(three);

        four.addActionListener(this);
        four.setBounds(42, 104, 60, 34);
        four.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(four);

        five.addActionListener(this);
        five.setBounds(106, 104, 60, 34);
        five.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(five);

        six.addActionListener(this);
        six.setBounds(170, 104, 60, 34);
        six.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(six);

        seven.addActionListener(this);
        seven.setBounds(42,142, 60, 34);
        seven.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(seven);

        eight.addActionListener(this);
        eight.setBounds(106,142, 60, 34);
        eight.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(eight);

        nine.addActionListener(this);
        nine.setBounds(170,142, 60, 34);
        nine.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(nine);

        decimal.addActionListener(this);
        decimal.setBounds(42, 180, 60, 34);
        decimal.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(decimal);

        zero.addActionListener(this);
        zero.setBounds(106, 180, 60, 34);
        zero.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(zero);

        sign.addActionListener(this);
        sign.setBounds(170, 180, 60, 34);
        sign.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(sign);

        sub.addActionListener(this);
        sub.setBounds(234, 104, 60, 34);
        sub.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(sub);

        mul.addActionListener(this);
        mul.setBounds(234,142, 60, 34);
        mul.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(mul);

        plus.addActionListener(this);
        plus.setBounds(234, 65, 60, 34);
        plus.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(plus);

        shft.addActionListener(this);
        shft.setBounds(42, 230, 60, 34);
        shft.setBackground(Color.orange);
        shft.setForeground(brown);
        shft.setFont(new Font("dialog", Font.BOLD,13));
        shft.setToolTipText("Shift buttons");
        getContentPane().add(shft);

        perPi.addActionListener(this);
        perPi.setBounds(106, 230, 60, 34);
        perPi.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(perPi);

        sqr1ByX.addActionListener(this);
        sqr1ByX.setBounds(170, 230, 60, 34);
        sqr1ByX.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(sqr1ByX);

        sqrtFact.addActionListener(this);
        sqrtFact.setBounds(234, 230, 60, 34);
        sqrtFact.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(sqrtFact);

        eql.addActionListener(this);
        eql.setBounds(309, 230, 60, 34);
        eql.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(eql);

        div.addActionListener(this);
        div.setBounds(234, 180, 60, 34);
        div.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(div);

        clr.addActionListener(this);
        clr.setBounds(309, 65, 60, 34);
        clr.setFont(new Font("dialog", Font.BOLD,13));
        clr.setForeground(new Color(255, 50, 0));
        clr.setBackground(Color.gray);
        clr.setToolTipText("Clear display");
        getContentPane().add(clr);

        m.addActionListener(this);
        m.setBounds(309, 104, 60, 34);
        m.setFont(new Font("dialog", Font.BOLD,13));
        m.setForeground(gre);
        m.setToolTipText("Add to memory");
        getContentPane().add(m);

        mClr.addActionListener(this);
        mClr.setBounds(309,142, 60, 34);
        mClr.setFont(new Font("dialog", Font.BOLD,13));
        mClr.setForeground(gre);
        mClr.setToolTipText("Clear memory");
        getContentPane().add(mClr);

        mR.addActionListener(this);
        mR.setBounds(309, 180, 60, 34);
        mR.setFont(new Font("dialog", Font.BOLD,13));
        mR.setForeground(gre);
        mR.setToolTipText("Recall from memory");
        getContentPane().add(mR);

        statusLbl.setBounds(42, 330, 329, 15);
        statusLbl.setFont(new Font("dialog", Font.BOLD, 12));
        statusLbl.setBackground(Color.black);
        statusLbl.setForeground(Color.red);
        getContentPane().add(statusLbl);

        powLog.addActionListener(this);
        powLog.setBounds(42, 269, 60, 34);
        powLog.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(powLog);

        modSin.addActionListener(this);
        modSin.setBounds(106, 269, 60, 34);
        modSin.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(modSin);

        binCos.addActionListener(this);
        binCos.setBounds(170, 269, 60, 34);
        binCos.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(binCos);

        hexTan.addActionListener(this);
        hexTan.setBounds(234, 269, 60, 34);
        hexTan.setFont(new Font("dialog", Font.BOLD,13));
        getContentPane().add(hexTan);

        del.addActionListener(this);
        del.setBounds(309, 269, 60, 34);
        del.setFont(new Font("dialog", Font.BOLD,13));
        del.setToolTipText("Delete");
        getContentPane().add(del);

        mbar = new JMenuBar();
        file = new JMenu("File");
        close = new JMenuItem("Close");
        file.add(close);
        mbar.add(file);
        options = new JMenu("Options");
        ranNum = new JMenuItem("Random Number");
        optionsSaver = new JMenuItem("Save Options");
        Degrees = new JMenuItem("Degrees");
        Radians = new JMenuItem("Radians");
        setRanNum = new JMenu("Random Number Limit");
        ranNumLimit1 = new JMenuItem("10");
        ranNumLimit2 = new JMenuItem("100");
        ranNumLimit3 = new JMenuItem("1,000");
        ranNumLimit4 = new JMenuItem("10,000");
        ranNumLimit5 = new JMenuItem("100,000");
        ranNumLimitCurrent = new JMenuItem("Current");
        defaultOptions = new JMenuItem("Load Default");
        setRanNum.add(ranNumLimit1);
        setRanNum.add(ranNumLimit2);
        setRanNum.add(ranNumLimit3);
        setRanNum.add(ranNumLimit4);
        setRanNum.add(ranNumLimit5);
        setRanNum.addSeparator();
        options.add(ranNum);
        options.addSeparator();
        options.add(Degrees);
        options.add(Radians);
        options.addSeparator();
        options.add(setRanNum);
        options.addSeparator();
        options.add(defaultOptions);
        options.add(optionsSaver);
        mbar.add(options);
        help = new JMenu("Help");
        about = new JMenuItem("About");
        help.add(about);
        mbar.add(help);
        defaultOptions.addActionListener(this);
        close.addActionListener(this);
        ranNum.addActionListener(this);
        Degrees.addActionListener(this);
        Radians.addActionListener(this);
        ranNumLimit1.addActionListener(this);
        ranNumLimit2.addActionListener(this);
        ranNumLimit3.addActionListener(this);
        ranNumLimit4.addActionListener(this);
        ranNumLimit5.addActionListener(this);
        ranNumLimitCurrent.addActionListener(this);
        optionsSaver.addActionListener(this);
        about.addActionListener(this);
        file.setMnemonic('f');
        options.setMnemonic('o');
        help.setMnemonic('h');
        defaultOptions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        ranNum.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        Degrees.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        Radians.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        optionsSaver.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        ranNumLimit1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
        ranNumLimit2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
        ranNumLimit3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
        ranNumLimit4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.CTRL_MASK));
        ranNumLimit5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.CTRL_MASK));
        ranNumLimitCurrent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        setJMenuBar(mbar);

        Clear();

        getSavedData();

        if (ranNumLimit == 10) {
            ranNumLimit1.setEnabled(false);
        } else if (ranNumLimit == 100) {
            ranNumLimit2.setEnabled(false);
        } else if (ranNumLimit == 1000) {
            ranNumLimit3.setEnabled(false);
        } else if (ranNumLimit == 10000) {
            ranNumLimit4.setEnabled(false);
        } else if (ranNumLimit == 100000) {
            ranNumLimit5.setEnabled(false);
        }
        if (radiansFlag) {
            Radians.setEnabled(false);
        } else {
            Degrees.setEnabled(false);
        }

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }

            @Override
            public void windowDeiconified(WindowEvent we) {
                getContentPane().repaint();
            }
        });
        getContentPane().repaint();

    }
    public final static int OpMinus = 11,  OpMultiply = 12,  OpPlus = 13,  OpDivide = 15,  OpMPlus = 19,  OpMClear = 20,  OpMR = 21,  OpMod = 22,  OpPow = 23;

    void displayStatus(String err_msg) {
        add(statusLbl);
        statusLbl.setText(err_msg);
    }

    void getSavedData() {
        try {
            memoryFile = new File("calcMemory.txt");
            fr = new FileReader(memoryFile);
            for (int i = 0; i < memoryFile.length(); i++) {
                fr.read(c, i, 1);
                dataStr += String.valueOf(c[i]);
            }
            StringTokenizer st = new StringTokenizer(dataStr.trim(), ",");
            ranNumLimit = Integer.parseInt(st.nextToken());
            if (st.nextToken().equals("degrees")) {
                radiansFlag = false;
            } else {
                radiansFlag = true;
            }
            fr.close();
            displayStatus("Loaded successfully.");
        } catch (Exception e) {
            displayStatus("Unable to read saved data");
            System.out.println(String.valueOf(e));
        }
    }

    void writeFileData(Boolean b) {
        String wfdstr = String.valueOf(ranNumLimit);
        wfdstr += ",";
        if (radiansFlag) {
            wfdstr += "radians";
        } else {
            wfdstr += "degrees";
        }
        try {
            memoryFile = new File("calcMemory.txt");
            memoryFile.delete();
            memoryFile = new File("calcMemory.txt");
            fw = new FileWriter(memoryFile);
            fw.write(wfdstr, 0, wfdstr.length());
            fw.flush();
            fw.close();
            memoryFile.setReadOnly();
            displayStatus("Saved successfully.");
        } catch (Exception e) {
            displayStatus("Unable to save data.");
            System.out.println(String.valueOf(e));
        }
    }

    void addNumber(int i) {
        displayStatus(" ");

        changeButtons(false);

        String Display = display.getText();

        if (OperatorKey == true) {
            Counter = 0;
        }

        Counter = Counter + 1;

        if ((Display.equals("0")) || (Status.equals("FIRST"))) {
            Display = "";
        }

        if (Counter < 21) {
            Display = Display + String.valueOf(i);
        } else {
            displayStatus("ERROR: 20 digit limit exceeded");
        }

        display.setText(Display);
        if (!Display.equals("0")) ranNumLimitCurrent.setText(Display);
        if (current) {
            setRanNum.add(ranNumLimitCurrent);
            current = false;
        }


        Status = "VALID";
        OperatorKey = false;
        FunctionKey = false;
    }

    void addOperator(int i) {
        displayStatus(" ");
        changeButtons(false);
        Result = (new Double(display.getText())).doubleValue();

        if ((OperatorKey == false) || (FunctionKey = true)) {
            switch (Operator) {
                case OpPlus:
                    Result = Operand + Result;
                    break;

                case OpMinus:
                    Result = Operand - Result;
                    break;

                case OpMultiply:
                    Result = Result * Operand;
                    break;

                case OpDivide:
                    if (Result == 0) {
                        Status = "ERROR";
                        display.setText("ERROR");
                        displayStatus("ERROR: Divide by zero");
                    } else {
                        Result = Operand / Result;
                    }
                    break;

                case OpMod:
                    if (Result != 0) {
                        Result = Operand % Result;
                    } else {
                        Result *= 1;
                    }
                    break;

                case OpPow:
                    if (Operand == 0 && Result == 0) {
                        display.setText("ERROR");
                        displayStatus("ERROR: Zero cannot be raised to the 0th power");
                    } else {
                        Result = Math.pow(Operand, Result);
                    }
                    break;
            }

            if (!Status.equals("ERROR")) {
                Status = "FIRST";

                Operand = Result;

                Operator = i;

                display.setText(String.valueOf(Result));

                DecimalFlag = false;

                SignFlag = false;
                OperatorKey = true;
                FunctionKey = false;
                displayStatus(" ");
            }

        }

    }

    void addDecimal() {
        displayStatus(" ");

        changeButtons(false);

        String Display = display.getText();

        if (Status.equals("FIRST")) {
            Display = "0";
        }

        if (!DecimalFlag) {
            Display = Display + ".";
        } else {
            displayStatus("The number already has a decimal point!");
        }

        display.setText(Display);
        DecimalFlag = true;
        Status = "VALID";
        OperatorKey = false;

    }

    void doPercent() {
        displayStatus(" ");

        changeButtons(false);

        String Display = display.getText();

        if ((!Status.equals("FIRST")) || (!Display.equals("0"))) {
            Result = (new Double(display.getText())).doubleValue();

            Result = Result / 100;

            display.setText(String.valueOf(Result));
            Status = "FIRST";
            OperatorKey = true;
            FunctionKey = true;
        }
    }

    void Clear() {
        Counter = 0;
        Status = "FIRST";
        Operand = 0;
        Result = 0;
        Operator = 0;
        changeButtons(false);
        DecimalFlag = false;

        SignFlag = false;

        OperatorKey = false;

        FunctionKey = false;

        display.setText("0");
        displayStatus(" ");
    }

    void switchSign() {
        displayStatus(" ");

        changeButtons(false);

        String Display = display.getText();

        if ((!Status.equals("FIRST")) || (!Display.equals("0"))) {
            Result = (new Double(display.getText())).doubleValue();

            Result = -Result;
            display.setText(String.valueOf(Result));
            Status = "VALID";
            SignFlag = true;
            DecimalFlag = true;
        }
    }

    void doSqr() {
        displayStatus(" ");

        changeButtons(false);

        String Display = display.getText();

        if ((Status.equals("FIRST")) || (!Display.equals("0"))) {
            Result = (new Double(display.getText())).doubleValue();

            Result *= Result;

            display.setText(String.valueOf(Result));

            Status = "FIRST";
            OperatorKey = true;
            FunctionKey = true;
        }
    }

    void doSqrRoot() {
        displayStatus(" ");

        changeButtons(false);

        String Display = display.getText();

        if ((!Status.equals("FIRST")) || (!Display.equals("0"))) {
            Result = (new Double(display.getText())).doubleValue();

            Result = Math.sqrt(Result);
            display.setText(String.valueOf(Result));

            Status = "FIRST";
            OperatorKey = true;
            FunctionKey = true;
        }
    }

    void addPi() {
        displayStatus(" ");

        changeButtons(false);

        String Display = display.getText();

        Result = new Double(Math.PI);

        display.setText(String.valueOf(Result));

        Status = "FIRST";

        OperatorKey = true;
        FunctionKey = true;


    }

    void addToMemory(int i) {
        displayStatus(" ");
        changeButtons(false);
        String Display = display.getText();
        switch (i) {
            case OpMPlus:
                if (((Display.equals("0.")) || (Display.equals("0"))) && (Mem == 0)) {
                    m.setBackground(plus.getBackground());
                } else {
                    Mem = (new Double(display.getText())).doubleValue();
                    m.setBackground(Color.green);
                }
                break;
            case OpMR:
                display.setText(String.valueOf(Mem));
                break;
            case OpMClear:
                Mem = 0;
                m.setBackground(plus.getBackground());
                break;
        }
        Status = "FIRST";
        OperatorKey = true;
        if (Mem == 0) {
            m.setBackground(plus.getBackground());
        }
    }

    double doFactorial(double Result) {
        changeButtons(false);
        if (!Status.equals("FIRST")) {
            if (Result == 0) {
                Result = 1;
                return Result;
            }
            if (Result == 1) {
                return 1;
            } else {
                Result = Result * doFactorial(Result - 1);
            }
            return Result;
        }
        return Result;
    }

    void do1ByX() {
        changeButtons(false);
        if (!Status.equals("FIRST")) {
            Result = (new Double(display.getText())).doubleValue();
            if (Result == 0) {
                display.setText("ERROR");
                displayStatus("ERROR: Divide by zero");
            } else {
                Result = 1 / Result;
                display.setText(String.valueOf(Result));
            }
        }
    }

    void addRandomNumber(int i) {
        changeButtons(false);
        Result = (new Double(Math.rint(i * Math.random())));
        display.setText(String.valueOf(Result));
        Status = "FIRST";
    }

    void doLog() {
        changeButtons(false);
        if (!Status.equals("FIRST")) {
            Result = (new Double(display.getText())).doubleValue();
            if (Result == 0) {
                display.setText("ERROR");
                displayStatus("ERROR: You can't find the logarithm of zero");
            } else {
                Result = Math.log(Result);
                display.setText(String.valueOf(Result));
            }
        }
    }

    void doSin() {
        changeButtons(false);
        Result = (new Double(display.getText())).doubleValue();
        if (radiansFlag) {
            Result = Math.sin(Result);
        } else {
            Result = Math.sin(Math.toRadians(Result));
        }
        String str = String.valueOf(Result);
        while (str.indexOf("E") != -1) {
            Result /= 10;
            String str2 = String.valueOf(Result);
            if (str2.indexOf("E") == -1) {
                break;
            }
        }
        display.setText(String.valueOf(Result));
    }

    void convertToBinary() {
        changeButtons(false);
        if (!Status.equals("FIRST")) {
            String str = display.getText();
            int i = Integer.parseInt(str);
            String str2 = Integer.toBinaryString(i);
            display.setText(String.valueOf(str2));
        }
    }

    void doCos() {
        changeButtons(false);
        Result = (new Double(display.getText())).doubleValue();
        if (radiansFlag) {
            Result = Math.cos(Result);
        } else {
            Result = Math.cos(Math.toRadians(Result));
        }
        String str = String.valueOf(Result);
        while (str.indexOf("E") != -1) {
            Result /= 10;
            String str2 = String.valueOf(Result);
            if (str2.indexOf("E") == -1) {
                break;
            }
        }
        display.setText(String.valueOf(Result));
    }

    void convertToHex() {
        changeButtons(false);
        if (!Status.equals("FIRST")) {
            String str = display.getText();
            int i = Integer.parseInt(str);
            String str2 = Integer.toHexString(i);
            display.setText(String.valueOf(str2));
        }
    }

    void doTan() {
        changeButtons(false);
        Result = (new Double(display.getText())).doubleValue();
        if (radiansFlag) {
            Result = Math.tan(Result);
        } else {
            Result = Math.tan(Math.toRadians(Result));
        }
        String str = String.valueOf(Result);
        while (str.indexOf("E") != -1) {
            Result /= 10;
            String str2 = String.valueOf(Result);
            if (str2.indexOf("E") == -1) {
                break;
            }
        }
        display.setText(String.valueOf(Result));
    }

    void doDelete() {
        changeButtons(false);
        if (!Status.equals("FIRST")) {
            String str = display.getText();
            int i = str.length();
            if (i != 1) {
                i -= 1;
                str = str.substring(0, i);
                display.setText(str);
            } else {
                display.setText("0");
            }
        }
    }

    void changeButtons(Boolean b) {
        if (b == true) {
            perPi.setText("pi");
            sqr1ByX.setText("1/x");
            sqrtFact.setText("!");
            powLog.setText("log");
            modSin.setText("sin");
            binCos.setText("cos");
            hexTan.setText("tan");
            SHIFT = true;
        } else if (b == false) {
            perPi.setText("%");
            sqr1ByX.setText("x²");
            sqrtFact.setText("\u221Ax");
            powLog.setText("^");
            modSin.setText("mod");
            binCos.setText("bin");
            hexTan.setText("hex");
            SHIFT = false;
        }
    }

    public void actionPerformed(ActionEvent event) {

        Object src = event.getSource();

        String arg = event.getActionCommand();

        if (!Status.equals("ERROR") || src == clr) {
            if (src == one) {
                addNumber(1);
            }
            if (src == two) {
                addNumber(2);
            }
            if (src == three) {
                addNumber(3);
            }
            if (src == four) {
                addNumber(4);
            }
            if (src == five) {
                addNumber(5);
            }
            if (src == six) {
                addNumber(6);
            }
            if (src == seven) {
                addNumber(7);
            }
            if (src == eight) {
                addNumber(8);
            }
            if (src == nine) {
                addNumber(9);
            }
            if (src == zero) {
                addNumber(0);
            }
            if (src == sub) {
                addOperator(11);
            }
            if (src == mul) {
                addOperator(12);
            }
            if (src == plus) {
                addOperator(13);
            }
            if (src == eql) {
                addOperator(14);
            }
            if (src == div) {
                addOperator(15);
            }
            if (src == decimal) {
                addDecimal();
            }
            if (src == shft) {
                if (!SHIFT) {
                    changeButtons(true);
                } else {
                    changeButtons(false);
                }
            }
            if (src == clr) {
                Clear();
            }
            if (src == sign) {
                switchSign();
            }
            if (src == sqr1ByX) {
                if (!SHIFT) {
                    doSqr();
                } else {
                    do1ByX();
                }
            }
            if (src == sqrtFact) {
                if (!SHIFT) {
                    doSqrRoot();
                } else {
                    Result = (new Double(display.getText())).doubleValue();
                    if (Result > 20) {
                        display.setText("ERROR");
                        displayStatus("That number is greater than 20");
                    } else {
                        display.setText(String.valueOf(doFactorial(Result)));
                    }
                }
            }
            if (src == perPi) {
                if (SHIFT) {
                    addPi();
                } else {
                    doPercent();
                }
            }
            if (src == powLog) {
                if (SHIFT) {
                    doLog();
                } else {
                    addOperator(23);
                }
            }
            if (src == modSin) {
                if (SHIFT) {
                    doSin();
                } else {
                    addOperator(22);
                }
            }
            if (src == binCos) {
                if (SHIFT) {
                    doCos();
                } else {
                    convertToBinary();
                }
            }
            if (src == hexTan) {
                if (SHIFT) {
                    doTan();
                } else {
                    convertToHex();
                }
            }
            if (src == m) {
                addToMemory(19);
            }
            if (src == mClr) {
                addToMemory(20);
            }
            if (src == mR) {
                addToMemory(21);
            }
            if (src == del) {
                doDelete();
            }
            if (arg.equals("Close")) {
                System.exit(0);
            } else if (arg.equals("Random Number")) {
                displayStatus(" ");
                addRandomNumber(ranNumLimit);
            }
            if (arg.equals("Current")) {
                displayStatus(" ");
                if (Status != "FIRST" && display.getText() != "0" && display.getText().indexOf(".") == -1) {
                    String sss = display.getText();
                    if (sss.length() > 6) {
                        displayStatus("That number has more than 6 digits.");
                    } else {
                        ranNumLimit2.setEnabled(true);
                        ranNumLimit3.setEnabled(true);
                        ranNumLimit4.setEnabled(true);
                        ranNumLimit1.setEnabled(true);
                        ranNumLimit5.setEnabled(true);
                        ranNumLimit = Integer.parseInt(sss);
                    }
                } else {
                    displayStatus("That number isn't valid.");
                }
            }
            if (arg.equals("Save Options")) {
                displayStatus(" ");
                writeFileData(false);
            }
            if (arg.equals("Load Default")) {
                displayStatus(" ");
                radiansFlag = false;
                ranNumLimit = 100;
                Degrees.setEnabled(false);
                Radians.setEnabled(true);
                ranNumLimit1.setEnabled(true);
                ranNumLimit2.setEnabled(false);
                ranNumLimit3.setEnabled(true);
                ranNumLimit4.setEnabled(true);
                ranNumLimit5.setEnabled(true);
            }
            if (src == Degrees) {
                displayStatus(" ");
                radiansFlag = false;
                Degrees.setEnabled(false);
                Radians.setEnabled(true);
            }
            if (src == Radians) {
                displayStatus(" ");
                radiansFlag = true;
                Radians.setEnabled(false);
                Degrees.setEnabled(true);
            }
            if (src == ranNumLimit1) {
                displayStatus(" ");
                ranNumLimit = 10;
                ranNumLimit2.setEnabled(true);
                ranNumLimit3.setEnabled(true);
                ranNumLimit4.setEnabled(true);
                ranNumLimit5.setEnabled(true);
                ranNumLimit1.setEnabled(false);
            }
            if (src == ranNumLimit2) {
                displayStatus(" ");
                ranNumLimit = 100;
                ranNumLimit1.setEnabled(true);
                ranNumLimit3.setEnabled(true);
                ranNumLimit4.setEnabled(true);
                ranNumLimit5.setEnabled(true);
                ranNumLimit2.setEnabled(false);
            }
            if (src == ranNumLimit3) {
                displayStatus(" ");
                ranNumLimit = 1000;
                ranNumLimit2.setEnabled(true);
                ranNumLimit1.setEnabled(true);
                ranNumLimit4.setEnabled(true);
                ranNumLimit5.setEnabled(true);
                ranNumLimit3.setEnabled(false);
            }
            if (src == ranNumLimit4) {
                displayStatus(" ");
                ranNumLimit = 10000;
                ranNumLimit2.setEnabled(true);
                ranNumLimit3.setEnabled(true);
                ranNumLimit1.setEnabled(true);
                ranNumLimit5.setEnabled(true);
                ranNumLimit4.setEnabled(false);
            }
            if (src == ranNumLimit5) {
                displayStatus(" ");
                ranNumLimit = 100000;
                ranNumLimit2.setEnabled(true);
                ranNumLimit3.setEnabled(true);
                ranNumLimit4.setEnabled(true);
                ranNumLimit1.setEnabled(true);
                ranNumLimit5.setEnabled(false);
            }
            if (src == about) {
                aboutDialog = new AboutDialog();
            }
        }
    }
}

class AboutDialog extends JDialog implements ActionListener {

    JLabel aboutLbl = new JLabel("NCalc v3.6", JLabel.CENTER);
    JLabel aboutLbl2 = new JLabel("2009 By Nihanth S", JLabel.CENTER);
    JButton aboutClose = new JButton("Close");

    public AboutDialog() {
        setTitle("About NCalc");
        setLayout(new FlowLayout());
        aboutLbl.setFont(new Font("dialog", Font.BOLD, 24));
        aboutLbl2.setFont(new Font("dialog", Font.BOLD, 20));
        aboutClose.setFont(new Font("dialog", Font.BOLD, 16));
        aboutClose.addActionListener(this);
        add(aboutLbl);
        add(aboutLbl2);
        add(aboutClose);
        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - 225) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - 135) / 2,
                225, 135);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}