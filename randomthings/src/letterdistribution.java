import java.awt.BorderLayout;
import java.io.*;
import java.util.regex.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class letterdistribution extends JFrame {

    private static final char badchars[] = " \r\n\t\b\f`1234567890-–=[]\\;'/.,~!@#$%^&*()_+{}|:\"<>?‘’“”…".toCharArray();
    private static final char descendingorder[] = "etaoinshrdlcumwfgypbvkjxqz".toCharArray();
    private JTextArea area = new JTextArea();
    private Hashtable freq_dist = new Hashtable();

    public letterdistribution() {
        area.setEditable(false);
        setLayout(new BorderLayout());
        add(new JScrollPane(area), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(480, 320);
        JFileChooser fc = new JFileChooser();
        area.setTabSize(3);
        fc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "Text Files (*.txt)";
            }
        });
        fc.setAcceptAllFileFilterUsed(true);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File f = fc.getSelectedFile();
                FileReader fr = new FileReader(f);
                char cc[] = new char[(int)f.length()];
                fr.read(cc);
                fr.close();
                String text = String.valueOf(cc);
                printToTextArea("Calculating the distribution of letters in: " + f.getPath() + "(" + f.length() + " bytes). Please wait...");
                printToTextArea();
                text = text.toLowerCase();
                for (char s : badchars) {
                    try {
                        text = text.replaceAll("\\" + s, "");
                    } catch (PatternSyntaxException e) {
                        text = text.replaceAll("" + s, "");
                    }
                }
                printToTextArea("Number of letters: " + text.length());
                for (char c = 'a'; c <= 'z'; c++) {
                    int num = getNumberOf(c, text);
                    double pc = getPercentOf(num, text);
                    String percent = String.valueOf(pc) + "0000";
                    percent = percent.substring(0, percent.indexOf(".")) + percent.substring(percent.indexOf("."), percent.indexOf(".") + 3);
                    printToTextArea("\t" + c + ": " + num + "/" + text.length() + " (" + percent + "%)");
                    freq_dist.put(new Character(c), new Double(pc));
                }
                File ff = new File(f.getPath().substring(0, f.getPath().indexOf(f.getName())) + f.getName().substring(0, f.getName().indexOf(".")) + "_codified" + f.getName().substring(f.getName().indexOf(".")));
                FileWriter fw = new FileWriter(ff);
                fw.write(decode(text, freq_dist));
                fw.flush();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    String decode(String codetext, Hashtable freq_dist) {
        codetext = codetext.toLowerCase();
        Enumeration keys = freq_dist.keys();
        Enumeration values = freq_dist.elements();
        Hashtable dist_freq = new Hashtable();
        while (keys.hasMoreElements() && values.hasMoreElements()) {
            dist_freq.put(values.nextElement(), keys.nextElement());
        }
        double rankings[] = new double[26];
        for (char c = 'a', i = 0; c <= 'z'; c++, i++) {
            rankings[i] = ((Double)freq_dist.get(new Character(c))).doubleValue();
        }
        Arrays.sort(rankings);
        for (int i = 0; i < 26; i++) {
            double d = rankings[i];
            codetext.replaceAll(((Character)dist_freq.get(new Double(d))).toString(), String.valueOf(descendingorder[i]));
        }
        return codetext;
    }

    int getNumberOf(char c, String txt) {
        return txt.split(String.valueOf(c)).length - 1;
    }

    double getPercentOf(int num, String txt) {
        return (double)((double)num * 100 / (double)txt.length());
    }

    void printToTextArea(String txt) {
        area.append(txt + System.getProperty("line.separator"));
    }

    void printToTextArea() {
        area.append(System.getProperty("line.separator"));
    }

    static letterdistribution l;

    public static void main(String args[]) {
        l = new letterdistribution();
        JButton b = new JButton("Do it again!");
        l.add(b, BorderLayout.SOUTH);
        l.validate();
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                l = new letterdistribution();
            }
        });
    }
}
