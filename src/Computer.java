import java.util.ArrayList;

// Imported from Player.java
public class Computer extends Player {

    public Computer(String name) {
        super(name);
    }

    public String getName() {
        return super.getName();
    }

    public ArrayList<Card> getHand() {
        return super.getHand();
    }

    public int getHandSize() {
        return super.getHandSize();
    }

    public void addCardToHand(Card card) {
        super.addCardToHand(card);
    }

    public Card playCard(int index) {
        return super.playCard(index);
    }

    public ArrayList<Card> getCollectedCards() {
        return super.getCollectedCards();
    }

    public void addToCollectedCards(Card card) {
        super.addToCollectedCards(card);
    }

    // Calculate the player's score by summing the values of all collected cards
    public int calculateScore() {
        return super.calculateScore();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}