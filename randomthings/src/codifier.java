
import javax.swing.*;
import java.io.*;
import java.util.regex.PatternSyntaxException;

public class codifier {

    private static final char badchars[] = " \r\n\t\b\f`1234567890-–=[]\\;'/.,~!@#$%^&*()_+{}|:\"<>?‘’“”…".toCharArray();

    public static void main(String args[]) {
        JFileChooser jfc = new JFileChooser();
        if (jfc.showOpenDialog(jfc) == JFileChooser.APPROVE_OPTION) {
            try {
                File f = jfc.getSelectedFile();
                FileReader fr = new FileReader(f);
                char c[] = new char[(int) f.length()];
                fr.read(c);
                fr.close();
                String text = String.valueOf(c);
                File ff = new File(f.getPath().substring(0, f.getPath().indexOf(f.getName())) + f.getName().substring(0, f.getName().indexOf(".")) + "_codified" + f.getName().substring(f.getName().indexOf(".")));
                FileWriter fw = new FileWriter(ff);
                fw.write(codify(text));
                fw.flush();
                fw.close();
            } catch (Exception e) {
            }
        }
    }

    static String codify(String clrtext) {
        clrtext = clrtext.toLowerCase();
        for (char s : badchars) {
            try {
                clrtext = clrtext.replaceAll("\\" + s, "");
            } catch (PatternSyntaxException e) {
                clrtext = clrtext.replaceAll("" + s, "");
            }
        }
        char c[] = clrtext.toCharArray();
        char f[] = new char[c.length];
        for (int i = 0; i < c.length; i++) {
            f[i] = (char) ('a' + 'z' - c[i]);
        }
        return String.valueOf(f);
    }
}
