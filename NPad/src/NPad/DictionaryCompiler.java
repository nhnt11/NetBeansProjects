package NPad;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import java.util.regex.*;

public class DictionaryCompiler extends JFrame {

    static Properties dictionary = new Properties();
    static final int SUCCESS = 0;
    static final int FAILED = 1;

    static void compile(File dictionaryFile, File newFile) throws Exception {
        char c[] = new char[(int) dictionaryFile.length()];
        BufferedReader dictionaryBR = new BufferedReader(new FileReader(dictionaryFile));
        dictionaryBR.read(c);
        String dictionaryStr = String.valueOf(c);
        Pattern pat = Pattern.compile(".*?\r\n");
        Matcher mat = pat.matcher(dictionaryStr);
        while (mat.find()) {
            String str = mat.group().trim();
            String pro = getPronunciation(str);
            String s;
            if ((s = (String) dictionary.get(pro)) != null) {
                s += " " + str;
            } else {
                s = str;
            }
            dictionary.put(pro, s);
        }
        dictionary.store(new FileWriter(newFile), "NPad - Dictionary");
    }

    static void compile(URL dictionaryURL, File newFile) throws Exception {
        InputStream is = dictionaryURL.openStream();
        char c[] = new char[is.available()];
        BufferedReader dictionaryBR = new BufferedReader(new InputStreamReader(is));
        dictionaryBR.read(c);
        String dictionaryStr = String.valueOf(c);
        FileWriter fw = new FileWriter(newFile);
        fw.write(dictionaryStr);
        fw.flush();
        fw.close();
        compile(newFile, newFile);
    }

    static String getPronunciation(String word) {
        word = word.toLowerCase().trim();
        String pronunciation = word;
        for (char c = 'a'; c <= 'z'; c++) {
            Pattern p = Pattern.compile(c + "+");
            Matcher m = p.matcher(pronunciation);
            while (m.find()) {
                if (m.group().length() < 2) continue;
                pronunciation = pronunciation.replaceAll(m.group(), m.group().substring(0, 1));
            }
        }
        Pattern p = Pattern.compile("[a, e, i, u].e");
        Matcher m = p.matcher(pronunciation);
        while (m.find()) {
            String s = m.group();
            pronunciation = pronunciation.replaceAll(s, s.substring(0, 2));
        }
        p = Pattern.compile("o.e");
        m = p.matcher(pronunciation);
        while (m.find()) {
            String s = m.group();
            pronunciation = pronunciation.replaceAll(s, "u" + s.charAt(1));
        }
        p = Pattern.compile("[b, c, d, f, g, j, k, l, m, n, q, r, t, v, w, x, z]h");
        m = p.matcher(pronunciation);
        while (m.find()) {
            pronunciation = pronunciation.replaceAll(m.group(), m.group().substring(0, 1));
        }
        p = Pattern.compile("[b, c, d, f, g, j, k, l, m, n, q, r, t, v, w, x, z]eight");
        m = p.matcher(pronunciation);
        while (m.find()) {
            pronunciation = pronunciation.replaceAll(m.group(), m.group().substring(0, 1) + "at");
        }
        pronunciation = pronunciation.replaceAll("eigh", "a");
        pronunciation = pronunciation.replaceAll("eigh", "a");
        pronunciation = pronunciation.replaceAll("igh", "i");
        pronunciation = pronunciation.replaceAll("tion", "shun");
        pronunciation = pronunciation.replaceAll("sion", "shun");
        pronunciation = pronunciation.replaceAll("cion", "shun");
        pronunciation = pronunciation.replaceAll("qu", "cw");
        pronunciation = pronunciation.replaceAll("s", "c");
        pronunciation = pronunciation.replaceAll("ck", "c");
        pronunciation = pronunciation.replaceAll("k", "c");
        pronunciation = pronunciation.replaceAll("eau", "u");
        pronunciation = pronunciation.replaceAll("a[a, e, i, o, u, w, y]", "a");
        pronunciation = pronunciation.replaceAll("e[a, e, i, o]", "e");
        pronunciation = pronunciation.replaceAll("ey", "a");
        pronunciation = pronunciation.replaceAll("eu", "u");
        pronunciation = pronunciation.replaceAll("i[e, i]", "i");
        pronunciation = pronunciation.replaceAll("ia", "a");
        pronunciation = pronunciation.replaceAll("io", "o");
        pronunciation = pronunciation.replaceAll("iu", "u");
        pronunciation = pronunciation.replaceAll("o[a, e]", "o");
        pronunciation = pronunciation.replaceAll("ou", "ow");
        pronunciation = pronunciation.replaceAll("oo", "u");
        pronunciation = pronunciation.replaceAll("u[e, u]", "u");
        pronunciation = pronunciation.replaceAll("ui", "wi");
        pronunciation = pronunciation.replaceAll("u[a, o]", "wa");
        pronunciation = pronunciation.replaceAll("y", "i");
        pronunciation = pronunciation.replaceAll("j", "g");
        pronunciation = pronunciation.replaceAll("ph", "f");
        pronunciation = pronunciation.replaceAll("x", "c");
        pronunciation = pronunciation.replaceAll("z", "c");
//        pronunciation = pronunciation.replaceAll("", "");
//        for (int i = 0; i < word.length(); i++) {
//            String letter = String.valueOf(word.charAt(i));
//            if ("eo".indexOf(letter) == -1) {
//                if (word.indexOf(letter + letter, i) == i) {
//                     word = word.substring(0, i) + word.substring(i + 1, word.length());
//                }
//            }
//            if (letter.equals("e")) {
//                if (word.length() > i + 2 && word.charAt(i + 1) == 'a' && word.charAt(i + 2) == 'u') {
//                    pronunciation += "u";
//                    i += 2;
//                } else if (word.length() > i + 1 && (word.charAt(i + 1) == 'a' || word.charAt(i + 1) == 'e' || word.charAt(i + 1) == 'i')) {
//                    pronunciation += "ee";
//                    i++;
//                } else if (i > 1 && "aeiou".indexOf(String.valueOf(word.charAt(i - 2))) == -1) {
//                    pronunciation += "e";
//                } else if (word.length() > i + 1 && word.charAt(i + 1) == 'y') {
//                    pronunciation += "i";
//                    i++;
//                } else {
//                    pronunciation += "e";
//                }
//                continue;
//            } else if (letter.equals("a")) {
//                if ((word.length() > i + 2 && "aeiouy".indexOf(word.charAt(i + 2)) != -1)) {
//                    pronunciation += "A";
//                } else if (word.length() > i + 1 && (word.charAt(i + 1) == 'e' || word.charAt(i + 1) == 'y')) {
//                    pronunciation += "ay";
//                    i++;
//                } else if (word.length() > i + 2 && word.charAt(i + 1) == 'i' && "bcdfghjklmnpqrstvwxz".indexOf(word.charAt(i + 2)) != -1) {
//                    pronunciation += "ay";
//                    i++;
//                } else if (word.length() > i + 1 && (word.charAt(i + 1) == 'u' || word.charAt(i + 1) == 'a')) {
//                    pronunciation += "o";
//                    i++;
//                } else if (word.length() == 1) {
//                    pronunciation += "u";
//                } else {
//                    pronunciation += "a";
//                }
//                continue;
//            } else if (letter.equals("i")) {
//                if (word.equals("i") || (word.length() > i + 2 && word.charAt(i + 2) == 'e')) {
//                    pronunciation += "i";
//                } else if (word.length() > i + 1 && word.charAt(i + 1) == 'i') {
//                    pronunciation += "i";
//                    i++;
//                } else {
//                    pronunciation += "i";
//                }
//                continue;
//            } else if (letter.equals("o")) {
//                if (word.length() > i + 1 && word.charAt(i + 1) == 'o') {
//                    pronunciation += "u";
//                    i++;
//                } else if (word.length() > i + 2 && word.charAt(i + 2) == 'e') {
//                    pronunciation += "o";
//                } else if (word.length() > i + 1 && word.charAt(i + 1) == 'u') {
//                    pronunciation += "u";
//                    i++;
//                } else if (word.length() > i + 1 && word.charAt(i + 1) == 'a') {
//                    pronunciation += "o";
//                    i++;
//                } else {
//                    pronunciation += "e";
//                }
//                continue;
//            } else if (letter.equals("u")) {
//                if (word.length() > i + 2 && word.charAt(i + 2) == 'e') {
//                    pronunciation += "u";
//                } else {
//                    pronunciation += "e";
//                }
//                continue;
//            } else if (letter.equals("c")) {
//                if (word.indexOf("cion", i) == i) {
//                    pronunciation += "shen";
//                    i += 3;
//                } else if (word.length() > i + 1 && word.charAt(i + 1) == 'k') {
//                    pronunciation += "c";
//                    i++;
//                } else {
//                    pronunciation += "c";
//                }
//                continue;
//            } else if (letter.equals("j")) {
//                pronunciation += "g";
//                continue;
//            } else if (letter.equals("k")) {
//                pronunciation += "c";
//                continue;
//            }
//            else if (letter.equals("t")) {
//                if (word.indexOf("tion", i) == i) {
//                    pronunciation += "shen";
//                    i += 3;
//                } else {
//                    pronunciation += "t";
//                }
//                continue;
//            } else if (letter.equals("s")) {
//                if (word.indexOf("sion", i) == i) {
//                    pronunciation += "shen";
//                    i += 3;
//                } else if (word.length() > i + 1 && word.charAt(i + 1) == 'c') {
//                    pronunciation += "s";
//                    i++;
//                } else {
//                    pronunciation += "s";
//                }
//                continue;
//            } else if (letter.equals("x")) {
//                if (i == 0) {
//                    pronunciation += "s";
//                } else {
//                    pronunciation += "cs";
//                }
//                continue;
//            } else if (letter.equals("q")) {
//                if (word.length() > i + 1 && word.charAt(i + 1) == 'u') {
//                    pronunciation += "cw";
//                    i++;
//                } else {
//                    pronunciation += "c";
//                }
//                continue;
//            } else if (letter.equals("y")) {
//                pronunciation += "i";
//                continue;
//            } else if (letter.equals("z")) {
//                pronunciation += "s";
//                continue;
//            }
//            pronunciation += letter;
//        }
//        if ("aeiouy".indexOf(pronunciation.charAt(pronunciation.length() - 1)) == -1) pronunciation += "e";
//        System.out.println(pronunciation);
        return pronunciation;
    }
}