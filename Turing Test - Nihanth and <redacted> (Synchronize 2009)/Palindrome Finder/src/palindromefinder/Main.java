package palindromefinder;

import javax.swing.*;
import java.util.regex.*;
import java.util.*;


public class Main {

    public Main() {
        Hashtable table = checkForPalindromes(JOptionPane.showInputDialog("Enter a sentence"));
        Enumeration it = table.keys();
        while (it.hasMoreElements()) {
            String word = (String) it.nextElement();
            System.out.println(word + " - " + (String) table.get(word));
        }
    }
    
    Hashtable checkForPalindromes(String line) {
        Hashtable table = new Hashtable();
        StringTokenizer st = new StringTokenizer(line, ", .");
        while (st.hasMoreTokens()) {
            char word[] = st.nextToken().toCharArray();
            String totalPalindromes = "";
            for (int i = 3; i <= word.length; i++) {
                for (int j = 0; j <= word.length - i; j++) {
                    String s = "";
                    for (int k = 0; k < i; k++) {
                        s += word[j + k];
                    }
                    if (s.toLowerCase().equals(reverseString(s).toLowerCase())) totalPalindromes += s + " ";
                }
            }
            if (!totalPalindromes.equals("")) table.put(String.valueOf(word), totalPalindromes);
        }
        return table;
    }
    
    String reverseString(String str) {
        char c[] = str.toCharArray();
        String returnstr = "";
        for (int i = 0, j = c.length - 1; i < c.length; i++, j--) {
            returnstr += c[j];
        }
        return returnstr;
    }
    
    public static void main(String[] args) {
        new Main();
    }

}
