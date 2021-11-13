/*
 * NChat  Copyright (C) 2012  Nihanth Subramanya
 * 
 * NChat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * NChat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with NChat.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package NChat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.text.*;

public class NChat extends JFrame implements ActionListener, Runnable {

    private String name;
    private JLabel nameLbl = new JLabel("Enter your ID: ");
    private JTextField nameTF = new JTextField(10);
    private JLabel ipLbl = new JLabel("Enter IP:");
    private JTextField ipField = new JTextField("localhost", 10);
    private JButton nameButton = new JButton("OK");
    private JLabel statusLbl = new JLabel("Enter your ID");
    private JPanel namePanel = new JPanel();
    private JPanel ipPanel = new JPanel();
    private JPanel nameipPanel = new JPanel(new GridLayout(2, 1));
    private JTextPane chatArea = new JTextPane();
    private JScrollPane chatSP = new JScrollPane(chatArea);
    private JTextField inputArea = new JTextField(30);
    private JButton sendButton = new JButton("Send");
    private JPanel chatPanel = new JPanel();
    private JPanel inputPanel = new JPanel();
    private JPanel totalChat = new JPanel();
    private ServerConnection server;
    StyleContext sc = new StyleContext();
    StyledDocument doc = new DefaultStyledDocument(sc);

    void initialize() {
        chatArea.setDocument(doc);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        namePanel.add(nameLbl);
        nameTF.addActionListener(this);
        namePanel.add(nameTF);
        namePanel.add(nameTF);
        nameButton.addActionListener(this);
        namePanel.add(nameButton);
        ipPanel.add(ipLbl);
        ipPanel.add(ipField);
        nameipPanel.add(namePanel);
        nameipPanel.add(ipPanel);
        statusLbl.setForeground(Color.red);
        add(nameipPanel, BorderLayout.CENTER);
        add(statusLbl, BorderLayout.SOUTH);
        chatArea.setEditable(false);
        chatPanel.setLayout(new BorderLayout());
        chatPanel.add(chatSP, BorderLayout.CENTER);
        inputArea.addActionListener(this);
        inputPanel.add(inputArea);
        sendButton.addActionListener(this);
        inputPanel.add(sendButton);
        totalChat.setLayout(new BorderLayout());
        totalChat.add(chatPanel, BorderLayout.CENTER);
        totalChat.add(inputPanel, BorderLayout.SOUTH);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((d.width - 450) / 2, (d.height - 300) / 2, 450, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        if (args != null && args.length > 0 && args[0].equals("server")) Server.main(null);
        else new NChat().initialize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == sendButton || src == inputArea) {
            if (!inputArea.getText().equals("")) {
                chat(getClientName(), " " + inputArea.getText());
                server.chat(inputArea.getText());
                inputArea.setText("");
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        } else if (src == nameTF || src == nameButton) {
            if (!nameTF.getText().equals("") && nameTF.isEditable()) {
                if (nameTF.getText().indexOf(" ") > -1) {
                    Toolkit.getDefaultToolkit().beep();
                    showStatus("No spaces, please");
                } else if (nameTF.getText().indexOf("]") > -1 || nameTF.getText().indexOf("[") > -1) {
                    Toolkit.getDefaultToolkit().beep();
                    showStatus("No square brackets, please");
                } else if (nameTF.getText().length() > 12) {
                    Toolkit.getDefaultToolkit().beep();
                    showStatus("That ID is too long! Please confine it to twelve characters");
                } else {
                    try {
                        showStatus("Connecting, please wait...");
                        server = new ServerConnection(this, ipField.getText());
                        showStatus("Connected");
                    } catch (IOException ex) {
                        Toolkit.getDefaultToolkit().beep();
                        showStatus("Enter a valid IP Address");
                        JOptionPane.showMessageDialog(rootPane, "Either the IP Address is invalid, or there is no NChat server running on it. Please enter a valid IP Address.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    name = nameTF.getText();
                    server.setName(name);
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    void add(String name) {
        try {
            chatArea.getStyledDocument().insertString(chatArea.getStyledDocument().getLength(), name + " has joined the chat.\r\n\r\n", null);
        } catch (Exception e) {
        }
        chatArea.setCaretPosition(chatArea.getStyledDocument().getLength());
    }

    void delete(String string) {
        try {
            chatArea.getStyledDocument().insertString(chatArea.getStyledDocument().getLength(), string + " has left the chat.\r\n\r\n", null);
        } catch (Exception e) {
        }
        chatArea.setCaretPosition(chatArea.getStyledDocument().getLength());
    }

    void chat(String from, String string) {
        StyledDocument lDoc = chatArea.getStyledDocument();
        javax.swing.text.Style style = lDoc.addStyle("color", null);
        if (string.trim().startsWith("[" + getClientName() + "]")) {
            StyleConstants.setForeground(style, Color.BLUE);
        }
        try {
            lDoc.insertString(lDoc.getLength(), from + "> " + string + "\r\n\r\n", style);
        } catch (BadLocationException ex) {
        }
        chatArea.setCaretPosition(chatArea.getStyledDocument().getLength());
    }

    String getClientName() {
        return name;
    }

    void showStatus(String str) {
        statusLbl.setText(str);
    }

    void idtaken() {
        showStatus("That ID is already taken!");
    }

    void success() {
        remove(nameipPanel);
        add(totalChat, BorderLayout.CENTER);
        validate();
        try {
            chatArea.getStyledDocument().insertString(chatArea.getStyledDocument().getLength(), "Welcome to NChat, " + name + "!\r\n\r\n", null);
        } catch (Exception e) {
        }
        showStatus("Welcome to NChat!");
        new Thread(this).start();
        setTitle("You are: " + name);
    }

    @Override
    public void run() {
        while (true) {
            int i = server.getHowManyPeople();
            String s;
            switch (i) {
                case 0:
                    s = "is nobody";
                    break;
                case 1:
                    s = "is 1 person";
                    break;
                default:
                    s = "are " + String.valueOf(i) + " people";
            }
            showStatus("There " + s + " online!");
        }
    }
}

class ServerConnection implements Runnable {

    private NChat nchat;
    private static final int port = 6564;
    private static final String CRLF = "\r\n";
    private BufferedReader in;
    private PrintWriter out;
    private String id = null;
    private Thread t;
    private int howManyPeople = 0;
    private static final int ID = 1;
    private static final int ADD = 2;
    private static final int DELETE = 3;
    private static final int CHAT = 4;
    private static final int IDTAKEN = 5;
    private static final int SUCCESS = 6;
    private final static HashMap keys = new HashMap();

    static {
        keys.put("id", ID);
        keys.put("add", ADD);
        keys.put("delete", DELETE);
        keys.put("chat", CHAT);
        keys.put("idtaken", IDTAKEN);
        keys.put("success", SUCCESS);
    }
    private static HashMap idcon = new HashMap();

    public ServerConnection(NChat nchat, String site) throws IOException {
        this.nchat = nchat;
        Socket server = new Socket(site, port);
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        out = new PrintWriter(server.getOutputStream(), true);
        start();
    }

    private String readline() {
        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    void setName(String s) {
        out.println("name " + s);
    }

    void delete() {
        out.println("delete " + nchat.getClientName());
    }

    void send(String s) {
        out.println("send " + s);
    }

    void chat(String s) {
        send("chat " + nchat.getClientName() + " " + s);
    }

    void quit() {
        send("quit " + nchat.getClientName());
        out.println("quit");
    }

    String getID() {
        return String.valueOf(Integer.parseInt(id) + 1);
    }

    void start() {
        t = new Thread(this);
        t.start();
    }

    int getHowManyPeople() {
        return howManyPeople;
    }

    private int lookup(String s) {
        Integer i = (Integer) keys.get(s);
        return i == null ? -1 : i.intValue();
    }

    @Override
    public void run() {
        String s;
        StringTokenizer st;
        while ((s = readline()) != null) {
            st = new StringTokenizer(s);
            String keyword = st.nextToken();
            switch (lookup(keyword)) {
                case ID:
                    id = st.nextToken();
                    break;
                case ADD: {
                    String id = st.nextToken();
                    String host = st.nextToken();
                    String name = st.nextToken();
                    idcon.put(id, name);
                    nchat.add(name);
                    howManyPeople++;
                }
                break;
                case DELETE: {
                    String who = st.nextToken();
                    nchat.delete((String) idcon.get(who));
                    howManyPeople--;
                }
                break;
                case CHAT: {
                    String from = st.nextToken();
                    nchat.chat(from, st.nextToken(CRLF));
                }
                break;
                case IDTAKEN: {
                    nchat.idtaken();
                }
                break;
                case SUCCESS: {
                    nchat.success();
                }
                break;
            }
        }
    }
}

class ClientConnection implements Runnable {

    private Socket sock;
    private BufferedReader in;
    private OutputStream out;
    private String host;
    private Server server;
    private static final String CRLF = "\r\n";
    private String name = null;
    private String id;
    static private final int NAME = 1;
    static private final int DELETE = 2;
    static private final int SEND = 3;
    static private final int QUIT = 4;
    static private final HashMap keys = new HashMap();

    static {
        keys.put("name", NAME);
        keys.put("delete", DELETE);
        keys.put("send", SEND);
        keys.put("quit", QUIT);
    }

    public ClientConnection(Server srv, Socket s, int i) {
        try {
            server = srv;
            sock = s;
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = s.getOutputStream();
            host = s.getInetAddress().getHostName();
            id = "" + i;
            write("id " + id + CRLF);
            new Thread(this).start();
        } catch (IOException e) {
            server.printToConsole("failed ClientConnection " + e);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + " " + host + " " + name;
    }

    public String getHost() {
        return host;
    }

    public String getId() {
        return id;
    }

    public void close() {
        server.kill(this);
        try {
            sock.close();
        } catch (IOException e) {
        }
    }

    public void write(String string) {
        byte buf[] = string.getBytes();
        try {
            out.write(buf, 0, buf.length);
        } catch (IOException e) {
            close();
        }
    }

    public String readline() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private int lookup(String s) {
        Integer i = (Integer) keys.get(s);
        return i == null ? -1 : i;
    }

    @Override
    public void run() {
        String s;
        StringTokenizer st;
        while ((s = readline()) != null) {
            st = new StringTokenizer(s);
            String keyword = st.nextToken();
            outer:
            switch (lookup(keyword)) {
                case NAME:
                    name = st.nextToken() + (st.hasMoreTokens() ? " " + st.nextToken(CRLF) : "");
                    if (server.set(id, this)) {
                        server.printToConsole(" [" + new Date() + "] " + this);
                    }
                    break;
                case DELETE:
                    server.delete(id);
                    break;
                case SEND:
                    String body = st.nextToken(CRLF);
                    StringTokenizer token = new StringTokenizer(body, " ");
                    token.nextToken();
                    String whosent = token.nextToken();
                    String body_ = token.nextToken();
                    Enumeration e = server.getNames();
                    while (e.hasMoreElements()) {
                        String string = (String) e.nextElement();
                        if (body_.startsWith("[" + string + "]")) {
                            server.sendto(server.lookup(string), body);
                            break outer;
                        }
                    }
                    server.broadcast(server.lookup(whosent), body);
                    break;
                case QUIT:
                    close();
                    return;
            }
        }
        close();
    }
}
