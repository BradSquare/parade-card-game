import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private ArrayList<Card> collectedCards;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.collectedCards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getHandSize() {
        return hand.size();
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public Card playCard(int index) {
        if (index >= 0 && index < hand.size()) {
            return hand.remove(index);
        }
        return null; // Invalid index
    }

    public ArrayList<Card> getCollectedCards() {
        return collectedCards;
    }

    public void addToCollectedCards(Card card) {
        collectedCards.add(card);
    }

    // Calculate the player's score by summing the values of all collected cards
    public int calculateScore() {
        int score = 0;
        for (Card card : collectedCards) {
            score += card.getValue();
        }
        return score;
    }

    @Override
    public String toString() {
        return name + " (Hand: " + hand + ")";
    }
}