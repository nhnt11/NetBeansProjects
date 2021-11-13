package ntflashcards;

import java.util.*;

public class Card {

    String q;
    String a;

    public Card(String q, String a) {
        this.q = q;
        this.a = a;
    }

    protected String getQ() {
        return q;
    }

    protected String getA() {
        return a;
    }

    protected void setQ(String q) {
        this.q = q;
    }

    protected void setA(String a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return q + "\r\n" + a;
    }
}
