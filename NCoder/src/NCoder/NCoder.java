package NCoder;

import java.io.*;

public class NCoder {

    static char table[][] = null;

    static String encode(String clearTxt, String key) {
        if (table == null) {
            generateTable();
        }
        while (key.length() < clearTxt.length()) {
            key += key;
        }
        String codeTxt = "";
        for (int i = 0; i < clearTxt.length(); i++) {
            if ("\r\n".contains(String.valueOf(clearTxt.charAt(i)))) {
                codeTxt += "\r\n";
                if (clearTxt.length() > i + 1 && "\r\n".contains(String.valueOf(clearTxt.charAt(i + 1)))) i++;
            } else if ("\t\b\f".contains(String.valueOf(clearTxt.charAt(i)))) {
                codeTxt += clearTxt.charAt(i);
            } else codeTxt += table[clearTxt.charAt(i) - 32][key.charAt(i) - 32];
        }
        return codeTxt;
    }

    static String decode(String codeTxt, String key) {
        if (table == null) {
            generateTable();
        }
        while (key.length() < codeTxt.length()) {
            key += key;
        }
        String clearTxt = "";
        for (int i = 0; i < codeTxt.length(); i++) {
            if ("\r\n".contains(String.valueOf(codeTxt.charAt(i)))) {
                clearTxt += "\r\n";
                if (codeTxt.length() > i + 1 && "\r\n".contains(String.valueOf(codeTxt.charAt(i + 1)))) i++;
                continue;
            }
            char c[] = table[key.charAt(i) - 32];
            for (int j = 0; j < c.length; j++) {
                if (c[j] == codeTxt.charAt(i)) {
                    clearTxt += table[0][j];
                    break;
                }
            }
        }
        return clearTxt;
    }

    static int getIndexOfLetter(char letter) {
        return Character.getNumericValue(letter);
    }

    static char[][] generateTable() {
        table = new char[96][96];
        for (int i = 0; i < 96; i++) {
            for (int j = 0; j < 96; j++) {
                int k = i + j;
                if (k > 96) k -= 96;
                table[i][j] = (char)(k + 32);
            }
        }
        return table;
    }

    void writeObject(Object o) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File(System.getProperty("user.home") + "/table.dat")));
        os.writeObject(o);
        os.flush();
        os.close();
    }
}
