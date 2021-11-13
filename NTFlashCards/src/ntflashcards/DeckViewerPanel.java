package ntflashcards;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.*;
import java.io.*;

public class DeckViewerPanel extends JPanel implements ActionListener {

    Deck deck;
    int counter = 0;
    Card currentCard;
    JLabel title = new JLabel("Card #1", JLabel.CENTER);
    JPanel cardPanel = new JPanel(new BorderLayout());
    CardLabel cardLbl = new CardLabel("");
    JPanel controlPanel = new JPanel(new GridLayout(1, 4, 0, 0));
    JButton firstCard = new JButton("First Card");
    JButton lastCard = new JButton("Last Card");
    JButton toggleSide = new JButton("Show Answer");
    JButton prevCard = new JButton("Previous Card");
    JButton nextCard = new JButton("Next Card");
    File saverFile = null;
    boolean front = true;

    public DeckViewerPanel(Deck deck) {
        setLayout(new BorderLayout());
        this.deck = deck;
        if (deck.getPath() != null) {
            saverFile = new File(deck.getPath());
        }
        add(title, BorderLayout.NORTH);
        cardLbl.setBackground(Color.WHITE);
        cardLbl.setOpaque(true);
        cardLbl.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.GRAY));
        cardPanel.add(cardLbl, BorderLayout.CENTER);
        currentCard = deck.getCardAt(0);
        cardLbl.setText(currentCard.getQ());
        add(cardPanel, BorderLayout.CENTER);
        prevCard.setEnabled(false);
        if (deck.countCards() <= 1) {
            nextCard.setEnabled(false);
        }
        controlPanel.add(firstCard);
        controlPanel.add(prevCard);
        controlPanel.add(toggleSide);
        controlPanel.add(nextCard);
        controlPanel.add(lastCard);
        add(controlPanel, BorderLayout.SOUTH);
        addListeners();
    }

    void addListeners() {
        firstCard.addActionListener(this);
        prevCard.addActionListener(this);
        nextCard.addActionListener(this);
        lastCard.addActionListener(this);
        toggleSide.addActionListener(this);
    }

    Deck getDeck() {
        return deck;
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 5, 5, 5);
    }

    void resetQandA() {
        cardLbl.setText(currentCard.getQ());
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src == prevCard && counter > 0) {
            currentCard = deck.getCardAt(--counter);
            nextCard.setEnabled(true);
            if (counter == 0) {
                prevCard.setEnabled(false);
            }
            resetQandA();
        } else if (src == nextCard && counter < deck.lastPos()) {
            currentCard = deck.getCardAt(++counter);
            prevCard.setEnabled(true);
            if (counter == deck.lastPos()) {
                nextCard.setEnabled(false);
            }
            resetQandA();
        } else if (src == firstCard) {
            currentCard = deck.firstCard();
            prevCard.setEnabled(false);
            nextCard.setEnabled(true);
            resetQandA();
            counter = 0;
        } else if (src == lastCard) {
            currentCard = deck.lastCard();
            prevCard.setEnabled(true);
            nextCard.setEnabled(false);
            resetQandA();
            counter = deck.lastPos();
        } else if (src == toggleSide) {
            if (front) {
                cardLbl.setText(currentCard.getA());
                toggleSide.setText("Show Question");
            }
            else {
                cardLbl.setText(currentCard.getQ());
                toggleSide.setText("Show Answer");
            }
            front = !front;
        }
        if (deck.countCards() == 0 || deck.countCards() == 1) {
            prevCard.setEnabled(false);
            nextCard.setEnabled(false);
        }
        title.setText("Card #" + (counter + 1));
    }
}