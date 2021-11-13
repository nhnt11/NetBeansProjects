/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beep;

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
        int times = 1;
        if (args.length > 0) {
            try {
                times = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
            }
        }
        for (int i = 0; i < times; i++) {
            java.awt.Toolkit.getDefaultToolkit().beep();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }
}
