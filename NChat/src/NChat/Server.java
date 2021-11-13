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
import java.net.*;
import java.util.*;
import javax.swing.*;

class Server implements Runnable {

    private int port = 6564;
    private Hashtable idcon = new Hashtable();
    private int id = 0;
    static final String CRLF = "\r\n";
    private Hashtable names = new Hashtable();
    JFrame consoleWindow = new JFrame("NChat server console window");
    JTextArea area = new JTextArea();
    JScrollPane pane = new JScrollPane(area);

    synchronized void addConnection(Socket s) {
        new ClientConnection(this, s, id);
        id++;
    }

    synchronized Enumeration getNames() {
        return names.keys();
    }

    synchronized boolean set(String the_id, ClientConnection con) {
        if (names.containsKey(con.getName())) {
            con.write("idtaken " + CRLF);
            return false;
        }
        con.write("success " + CRLF);
        idcon.remove(the_id);
        Enumeration e = idcon.keys();
        while (e.hasMoreElements()) {
            String id = (String) e.nextElement();
            ClientConnection other = (ClientConnection) idcon.get(id);
            con.write("add " + other + CRLF);
        }
        idcon.put(the_id, con);
        names.put(con.getName(), the_id);
        broadcast(the_id, "add " + con);
        return true;
    }

    synchronized String lookup(String name) {
        return (String) names.get(name);
    }

    synchronized void sendto(String dest, String body) {
        ClientConnection con = (ClientConnection) idcon.get(dest);
        if (con != null) {
            con.write(body + CRLF);
        }
    }

    synchronized void broadcast(String exclude, String body) {
        Enumeration e = idcon.keys();
        while (e.hasMoreElements()) {
            String id = (String) e.nextElement();
            if (!exclude.equals(id)) {
                ClientConnection con = (ClientConnection) idcon.get(id);
                con.write(body + CRLF);
            }
        }
    }

    synchronized void delete(String the_id) {
        broadcast(the_id, "delete " + the_id);
    }

    synchronized void kill(ClientConnection c) {
        if (idcon.remove(c.getId()) == c) {
            delete(c.getId());
            names.remove(c.getName());
        }
    }

    void printToConsole(String text) {
        area.append(text + CRLF);
    }

    @Override
    public void run() {
        try {
            try {
                consoleWindow.setLayout(new BorderLayout());
                consoleWindow.add(pane, BorderLayout.CENTER);
                consoleWindow.pack();
                consoleWindow.setSize(480, 360);
                area.setEditable(false);
                if (SystemTray.isSupported()) {
                    SystemTray tray = SystemTray.getSystemTray();
                    TrayIcon icon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("images/icon.png")));
                    PopupMenu menu = new PopupMenu();
                    final MenuItem one = new MenuItem("Show console...");
                    final MenuItem two = new MenuItem("Stop server...");
                    ActionListener listener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            Object src = ae.getSource();
                            if (src == one) {
                                consoleWindow.setVisible(true);
                            } else if (src == two) {
                                System.exit(0);
                            }
                        }
                    };
                    one.addActionListener(listener);
                    two.addActionListener(listener);
                    menu.add(one);
                    menu.add(two);
                    icon.setPopupMenu(menu);
                    tray.add(icon);
                }
            } catch (AWTException ex) {
                printToConsole("Unable to initialize tray icon");
            }
            ServerSocket acceptSocket = new ServerSocket(port);
            printToConsole("Server listening on port " + port);
            while (true) {
                Socket s = acceptSocket.accept();
                addConnection(s);
            }
        } catch (IOException e) {
            printToConsole("accept loop IOException: " + e);
        }
    }

    public static void main(String[] args) {
        new Thread(new Server()).start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
        }
    }
}