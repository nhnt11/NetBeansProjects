package ntflashcards;

import java.util.*;
import java.io.*;

public class Deck {

    Vector cards = new Vector();
    String name = "";
    String path = null;

    public Deck(Vector cards, String name) {
        this.cards = cards;
        this.name = name;
    }

    public Deck(Vector cards) {
        this.cards = cards;
    }

    public Deck(String name) {
        this.name = name;
    }

    public Deck() {
    }

    void setCards(Vector cards) {
        this.cards = cards;
    }

    void addCard(Card card) {
        cards.addElement(card);
    }

    void removeCard(Card card) {
        cards.remove(card);
    }

    String getName() {
        return name;
    }

    int positionOf(Card card) {
        return cards.indexOf(card);
    }

    void setName(String name) {
        this.name = name;
    }

    Card getCardAt(int i) {
        if (cards.size() > i && cards.size() != 0) {
            return (Card) cards.elementAt(i);
        } else {
            return new Card("No question set", "No answer set");
        }
    }

    Card firstCard() {
        return getCardAt(0);
    }

    Card lastCard() {
        return getCardAt(countCards() - 1);
    }

    int lastPos() {
        return countCards() - 1;
    }

    int countCards() {
        return cards.size();
    }

    void setPath(String path) {
        this.path = path;
    }

    String getPath() {
        return path;
    }

    static Deck deckFromFile(File file) throws IOException {
        FileReader f = new FileReader(file);
        BufferedReader br = new BufferedReader(f);
        Vector tempCards = new Vector();
        Deck tempDeck = new Deck(tempCards, file.getName().substring(0, file.getName().lastIndexOf(".")));
        while (br.ready()) {
            StringTokenizer st = new StringTokenizer(br.readLine(), "\b");
            if (st.countTokens() == 2) {
                tempDeck.addCard(new Card(st.nextToken(), st.nextToken()));
            }
        }
        br.close();
        tempDeck.setPath(file.getPath());
        return tempDeck;
    }

    static Deck deckFromFile(File file, String delim) throws IOException {
        FileReader f = new FileReader(file);
        BufferedReader br = new BufferedReader(f);
        Vector tempCards = new Vector();
        Deck tempDeck = new Deck(tempCards, file.getName().substring(0, file.getName().lastIndexOf(".")));
        while (br.ready()) {
            StringTokenizer st = new StringTokenizer(br.readLine(), delim);
            if (st.countTokens() == 2) {
                tempDeck.addCard(new Card(st.nextToken(), st.nextToken()));
            }
        }
        br.close();
        tempDeck.setPath(file.getPath());
        return tempDeck;
    }

    void deckToFile(File file) throws IOException {
        String txt = toString();
        FileWriter fw = new FileWriter(file);
        fw.write(txt);
        fw.flush();
        fw.close();
    }

    void deckToFile(File file, String delim) throws IOException {
        String txt = toString(delim);
        FileWriter fw = new FileWriter(file);
        fw.write(txt);
        fw.flush();
        fw.close();
    }

    @Override
    public String toString() {
        Iterator it = cards.iterator();
        String txt = name + "\r\n";
        while (it.hasNext()) {
            Card c = (Card) it.next();
            txt += c.getQ() + "\b" + c.getA() + "\r\n";
        }
        return txt;
    }

    public String toString(String delim) {
        Iterator it = cards.iterator();
        String txt = name + "\r\n";
        while (it.hasNext()) {
            Card c = (Card) it.next();
            txt += c.getQ() + delim + c.getA() + "\r\n";
        }
        return txt;
    }
}
