/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nihanth
 */
public class tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (int i = 0; i < 32; i++) {
            int diag = 0;
            for (int j = 1, k = 0; k < i; j += i + 1, k++) {
                diag += j;
                if (diag == 5335) {
                    System.out.println(i * i);
                    System.exit(0);
                }
            }
        }
    }
}