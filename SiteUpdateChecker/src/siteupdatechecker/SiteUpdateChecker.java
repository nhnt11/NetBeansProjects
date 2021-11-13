/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siteupdatechecker;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author nihanth
 */
public class SiteUpdateChecker {

    private String mURLString;
    private URL mURL;
    private MessageDigest md = MessageDigest.getInstance("SHA1");
    private String sha1 = "";
    private String sha1_;
    private Timer mTimer = new Timer();
    private InputStream mIS;

    public SiteUpdateChecker() throws Exception {
        System.out.println("Enter site URL:");
        mURLString = new Scanner(System.in).nextLine();
        mURL = new URL(mURLString);
    }

    private void start() throws Exception {
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    mIS = mURL.openStream();
                    byte[] buf = new byte[1024];
                    while ((mIS.read(buf)) != -1) {
                        md.update(buf);
                    }
                    sha1_ = toHexString(md.digest());
                    if (!sha1.equals("") && !sha1_.equals(sha1)) {
                        mTimer.scheduleAtFixedRate(new TimerTask() {

                            @Override
                            public void run() {
                                Toolkit.getDefaultToolkit().beep();
                            }
                        }, 1000, 500);
                        System.out.println("Site updated. Check it!");
                        Runtime.getRuntime().exec("open " + mURLString);
                    }
                    sha1 = sha1_.toString();
                    md.reset();
                } catch (IOException e) {
                }
            }
        }, 1000, 30000);
    }

    public static void main(String[] args) throws Exception {
        new SiteUpdateChecker().start();
    }

    public static String toHexString(byte[] bytes) {
        char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v / 16];
            hexChars[j * 2 + 1] = hexArray[v % 16];
        }
        return new String(hexChars);
    }
}
