package ntflashcards;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import ntflashcards.*;

public class DeckEditorPanel extends JPanel implements ActionListener {

    Deck deck;
    int counter = 0;
    Card currentCard;
    JPanel titles = new JPanel(new BorderLayout());
    JLabel title = new JLabel("Card #1", JLabel.CENTER);
    JLabel qTitle = new JLabel("Question", JLabel.CENTER);
    JLabel aTitle = new JLabel("Answer", JLabel.CENTER);
    JPanel cardPanel = new JPanel(new GridLayout(1, 2, 5, 5));
    CardLabel cardQLbl = new CardLabel("");
    CardLabel cardALbl = new CardLabel("");
    JPanel controlPanel = new JPanel(new GridLayout(1, 8, 0, 0));
    JButton firstCard = new JButton("First Card");
    JButton lastCard = new JButton("Last Card");
    JButton prevCard = new JButton("Previous Card");
    JButton nextCard = new JButton("Next Card");
    JButton editCard = new JButton("Edit...");
    JButton newCard = new JButton("New Card...");
    JButton delCard = new JButton("Delete Card...");
    JButton saveDeck = new JButton("Save Deck...");
    JButton selCard = new JButton("Go to card...");
    File saverFile = null;

    public DeckEditorPanel(Deck deck) {
        this.deck = deck;
        if (deck.getPath() != null) {
            saverFile = new File(deck.getPath());
        }
        setLayout(new BorderLayout(5, 5));
        JPanel p1 = new JPanel(new GridLayout(1, 2, 5, 5));
        titles.add(title, BorderLayout.NORTH);
        p1.add(qTitle);
        p1.add(aTitle);
        titles.add(p1, BorderLayout.SOUTH);
        add(titles, BorderLayout.NORTH);
        cardQLbl.setBackground(Color.WHITE);
        cardQLbl.setOpaque(true);
        cardALbl.setBackground(Color.WHITE);
        cardALbl.setOpaque(true);
        cardQLbl.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.GRAY));
        cardALbl.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.GRAY));
        cardPanel.add(cardQLbl);
        cardPanel.add(cardALbl);
        currentCard = deck.getCardAt(0);
        cardQLbl.setText(currentCard.getQ());
        cardALbl.setText(currentCard.getA());
        add(cardPanel, BorderLayout.CENTER);
        prevCard.setEnabled(false);
        if (deck.countCards() <= 1) {
            nextCard.setEnabled(false);
        }
        controlPanel.add(firstCard);
        controlPanel.add(prevCard);
        controlPanel.add(editCard);
        controlPanel.add(newCard);
        controlPanel.add(delCard);
        controlPanel.add(saveDeck);
        controlPanel.add(selCard);
        controlPanel.add(nextCard);
        controlPanel.add(lastCard);
        add(controlPanel, BorderLayout.SOUTH);
        addListeners();
    }

    void addListeners() {
        firstCard.addActionListener(this);
        prevCard.addActionListener(this);
        nextCard.addActionListener(this);
        editCard.addActionListener(this);
        newCard.addActionListener(this);
        delCard.addActionListener(this);
        saveDeck.addActionListener(this);
        lastCard.addActionListener(this);
        selCard.addActionListener(this);
    }

    Deck getDeck() {
        return deck;
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 5, 5, 5);
    }

    void resetQandA() {
        cardQLbl.setText(currentCard.getQ());
        cardALbl.setText(currentCard.getA());
    }

    void saveDeck() throws IOException {
        if (saverFile == null) {
            JFileChooser jfc = new JFileChooser();
            jfc.addChoosableFileFilter(new FileFilter() {

                public String getDescription() {
                    return "Folders";
                }

                public boolean accept(File file) {
                    if (file.isDirectory()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setAcceptAllFileFilterUsed(false);
            int i = jfc.showSaveDialog(this);
            if (i == JFileChooser.APPROVE_OPTION) {
                saverFile = new File(jfc.getSelectedFile().getPath() + "/" + deck.getName() + ".deck");
            }
        }
        try {
            deck.deckToFile(saverFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }

    }

    void saveDeck(String delim) throws IOException {
        JFileChooser jfc = new JFileChooser();
        jfc.addChoosableFileFilter(new FileFilter() {

            public String getDescription() {
                return "Folders";
            }

            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        int i = jfc.showSaveDialog(this);
        if (i == JFileChooser.APPROVE_OPTION) {
            saverFile = new File(jfc.getSelectedFile().getPath() + "/" + deck.getName() + ".txt");
        }
        try {
            deck.deckToFile(saverFile, delim);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        } else if (src == saveDeck) {
            try {
                saveDeck();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (src == delCard) {
            int i = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this card?", "Confirm card deletion", JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.OK_OPTION) {
                deck.removeCard(currentCard);
                if (nextCard.isEnabled()) {
                    currentCard = deck.getCardAt(counter);
                    nextCard.setEnabled(true);
                    if (counter == 0) {
                        prevCard.setEnabled(false);
                    }
                    resetQandA();
                } else {
                    currentCard = deck.getCardAt(--counter);
                    prevCard.setEnabled(true);
                    if (counter == deck.lastPos()) {
                        nextCard.setEnabled(false);
                    }
                    resetQandA();
                }
            }
        } else if (src == newCard) {
            JLabel qLbl = new JLabel("Enter Question: ", JLabel.RIGHT);
            JLabel aLbl = new JLabel("Enter Answer: ", JLabel.RIGHT);
            final JTextArea qArea = new JTextArea(5, 25);
            final JTextArea aArea = new JTextArea(5, 25);
            JButton OK = new JButton("OK");
            JButton cancel = new JButton("Cancel");
            final JDialog d = new JDialog();
            d.setLayout(new FlowLayout());
            JPanel p1 = new JPanel();
            JPanel p2 = new JPanel();
            JPanel p3 = new JPanel();
            p1.add(qLbl);
            p1.add(new JScrollPane(qArea));
            p2.add(aLbl);
            p2.add(new JScrollPane(aArea));
            p3.add(OK);
            p3.add(cancel);
            d.add(p1);
            d.add(p2);
            d.add(p3);
            d.setSize(480, 300);
            OK.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    d.dispose();
                    deck.addCard((currentCard = new Card(qArea.getText(), aArea.getText())));
                    resetQandA();
                    counter = deck.lastPos();
                    nextCard.setEnabled(false);
                    if (deck.countCards() > 1) {
                        prevCard.setEnabled(true);
                    }
                }
            });
            cancel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    d.dispose();
                }
            });
            d.setVisible(true);
            counter = deck.positionOf(currentCard);
        } else if (src == editCard) {
            JLabel qLbl = new JLabel("Enter Question: ", JLabel.RIGHT);
            JLabel aLbl = new JLabel("Enter Answer: ", JLabel.RIGHT);
            final JTextArea qArea = new JTextArea(currentCard.getQ(), 5, 25);
            final JTextArea aArea = new JTextArea(currentCard.getA(), 5, 25);
            qArea.selectAll();
            aArea.selectAll();
            JButton OK = new JButton("OK");
            JButton cancel = new JButton("Cancel");
            final JDialog d = new JDialog();
            d.setLayout(new FlowLayout());
            JPanel p1 = new JPanel();
            JPanel p2 = new JPanel();
            JPanel p3 = new JPanel();
            p1.add(qLbl);
            p1.add(new JScrollPane(qArea));
            p2.add(aLbl);
            p2.add(new JScrollPane(aArea));
            p3.add(OK);
            p3.add(cancel);
            d.add(p1);
            d.add(p2);
            d.add(p3);
            d.setSize(480, 300);
            OK.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    d.dispose();
                    currentCard.setQ(qArea.getText());
                    currentCard.setA(aArea.getText());
                    resetQandA();
                }
            });
            cancel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    d.dispose();
                }
            });
            d.setVisible(true);
        } else if (src == selCard) {
            String s = JOptionPane.showInputDialog(this, "Enter card number: ", "Go to card...", JOptionPane.OK_CANCEL_OPTION);
            if (s != null) {
                try {
                    int i = Integer.parseInt(s);
                    if (i > 0 && i <= deck.countCards()) {
                        counter = i - 1;
                        currentCard = deck.getCardAt(counter);
                        resetQandA();
                        if (counter == 0) {
                            prevCard.setEnabled(false);
                        } else {
                            prevCard.setEnabled(true);
                        }
                        if (counter == deck.lastPos()) {
                            nextCard.setEnabled(false);
                        } else {
                            nextCard.setEnabled(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Either you didn't type a natural number, or there aren't that many cards.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number.");
                }
            }
        }
        if (deck.countCards() == 0 || deck.countCards() == 1) {
            prevCard.setEnabled(false);
            nextCard.setEnabled(false);
        }
        title.setText("Card #" + (counter + 1));
    }
}