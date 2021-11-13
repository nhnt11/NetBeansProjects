/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pottermore;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Security;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Nihanth
 */
public class Main extends JFrame implements ActionListener {

    Timer timer;

    public Main() {
        timer = new Timer(60000, this);
        timer.setRepeats(true);
        timer.setInitialDelay(0);
        timer.start();
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            checkForWebChange();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    void checkForWebChange() throws IOException {
        URL url = new URL("http://www.pottermore.com/");
        InputStream is = url.openStream();
        byte buf[] = new byte[1024 * 1024];
        int b;
        for (int i = 0; (b = is.read()) != -1; i++) {
            buf[i] = (byte) b;
        }
        String html = new String(buf);
        if (html.indexOf("Sorry, Day 6 registration is now closed") == -1) {
            trigger();
        }
    }

    void trigger() {
        timer.stop();
        timer = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Toolkit.getDefaultToolkit().beep();
            }
        });
        timer.setRepeats(true);
        timer.start();
        while (true) {
            try {
                sendEmail();
                break;
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }

    void sendEmail() throws AddressException, MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", 465);
        Session session = Session.getInstance(props);
        session.setDebug(false);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("nhnt11@gmail.com"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress("nhnt11@gmail.com"));
        msg.setSubject("Pottermore open!!");
        msg.setContent("Pottermore registration open! GO GET IT", "text/plain");
        Transport transport = session.getTransport("smtps");
        transport.connect("smtp.gmail.com", 465, "nhnt11@gmail.com", "@betalip0pr0teinemi@");
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Main main = new Main();
        main.setVisible(true);
    }
}
