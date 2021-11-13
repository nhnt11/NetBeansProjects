/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jump;

import java.util.regex.Pattern;

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
        System.out.println(Pattern.quote("+918026620951"));
        if ("+918026620951".matches("\\+91802662.*")) System.out.println("wtf");
//        JFrame frame = new JFrame();
//        frame.add(new World());
//        frame.setSize(500, 500);
//        frame.setVisible(true);
    }

}
