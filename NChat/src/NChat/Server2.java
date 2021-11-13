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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

class NChatConstants {
    public static final int PORT = 24278;
    public static final String CRLF = "\r\n";
    public static final String IDTAKEN = "idtaken ";
    public static final String SUCCESS = "success ";
    public static final String ADD = "add ";
    public static final String DELETE = "delete ";
    public static final String ID = "id ";
}

public final class Server2 implements Runnable {

    //Constants
    //Tables
    private HashMap<String, ClientConnection2> mIdsConnsMap = new HashMap();
    private HashMap<String, String> mNamesIdsMap = new HashMap();
    //Use a simple int to keep ID on connections. Iterated for every connection.
    private int mCurrentId = 0;

    synchronized void addConn(Socket s) {
        new ClientConnection2(this, s, "" + mCurrentId++);
    }

    synchronized Set getIds() {
        return mIdsConnsMap.keySet();
    }

    synchronized boolean setConn(String id, ClientConnection2 conn) {
        if (mNamesIdsMap.containsKey(conn.getName())) {
            conn.write(NChatConstants.IDTAKEN + NChatConstants.CRLF);
            return false;
        }
        conn.write(NChatConstants.SUCCESS + NChatConstants.CRLF);
        mIdsConnsMap.put(id, conn);
        mNamesIdsMap.put(conn.getName(), id);
        broadcast(id, NChatConstants.ADD + conn + NChatConstants.CRLF);
        return true;
    }

    synchronized void broadcast(String excludeId, String msg) {
        for (ClientConnection2 conn : mIdsConnsMap.values()) {
            if (!conn.getId().equals(excludeId)) {
                conn.write(msg + NChatConstants.CRLF);
            }
        }
    }

    synchronized void sendTo(String toId, String msg) {
        mIdsConnsMap.get(toId).write(msg + NChatConstants.CRLF);
    }

    synchronized String getIdFromName(String name) {
        return mNamesIdsMap.get(name);
    }

    synchronized void delete(String id) {
        broadcast(id, NChatConstants.DELETE + id);
    }

    synchronized void kill(ClientConnection2 conn) {
        if (mIdsConnsMap.remove(conn.getId()) == conn) {
            delete(conn.getId());
            mNamesIdsMap.remove(conn.getName());
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket sock = new ServerSocket(NChatConstants.PORT);
            while (true) {
                addConn(sock.accept());
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}

final class ClientConnection2 implements Runnable {

    
    private Socket mSocket;
    private Server2 mServer;
    private String mName;
    private String mId;
    private BufferedWriter mOut;
    private BufferedReader mIn;

    ClientConnection2(Server2 server, Socket s, String id) {
        try {
            mServer = server;
            mSocket = s;
            mId = id;
            mOut = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            mIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            write(NChatConstants.ID + mId + NChatConstants.CRLF);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    String getName() {
        return mName;
    }

    String getId() {
        return mId;
    }

    void write(String msg) {
        try {
            mOut.write(msg);
            mOut.flush();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public String toString() {
        return mId + " " + mName;
    }
    
    private String readLine() {
        try {
            return mIn.readLine();
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    @Override
    public void run() {
        String line;
        while ((line = readLine()) != null) {
            
        }
    }
}