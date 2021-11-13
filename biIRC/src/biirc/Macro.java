/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package biirc;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Macro {

    private String mKeyword;
    private String mSyntax;
    private ArrayList<String> mArgs;
    private ArrayList<String> mCommands;

    public Macro(String keyword, String syntax, ArrayList<String> args, ArrayList<String> commands) {
        mKeyword = keyword;
        mSyntax = syntax;
        mArgs = args;
        mCommands = commands;
    }

    static Macro loadFromFile(File f) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
        String keyword = f.getName().replaceFirst("[.][^.]+$", "");
        String syntax = doc.getElementsByTagName("syntax").item(0).getTextContent();
        NodeList argNodes = doc.getElementsByTagName("args").item(0).getChildNodes();
        ArrayList<String> args = new ArrayList();
        for (int i = 0; i < argNodes.getLength(); i++) {
            Node node = argNodes.item(i);
            if (!node.getNodeName().equals("arg")) continue;
            args.add(node.getTextContent());
        }
        NodeList cmdNodes = doc.getElementsByTagName("commands").item(0).getChildNodes();
        ArrayList<String> commands = new ArrayList();
        for (int i = 0; i < cmdNodes.getLength(); i++) {
            Node node = cmdNodes.item(i);
            if (!node.getNodeName().equals("cmd")) continue;
            commands.add(node.getTextContent());
        }
        return new Macro(keyword, syntax, args, commands);
    }

    String getKeyword() {
        return mKeyword;
    }

    String getSyntax() {
        return mSyntax;
    }

    ArrayList<String> getArgs() {
        return mArgs;
    }

    ArrayList<String> getCommands() {
        return mCommands;
    }
}
