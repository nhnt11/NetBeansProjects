/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lamelamelame;

/**
 *
 * @author Nihanth
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        final String str = "donkey";
        for (int i = str.length(); i > 0; i--) {
            System.out.println("Substrings of length " + i);
            for (int j = 0; j <= str.length() - i; j++) {
                System.out.println(" " + str.substring(j, j + i));
            }
        }
    }

}
