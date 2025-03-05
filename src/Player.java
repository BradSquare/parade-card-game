import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private ArrayList<Card> collectedCards;

    // Constructor
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.collectedCards = new ArrayList<>();
    }

    // Add a card to the player's hand
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    // Play a card from hand
    public Card playCard(int index) {
        return hand.remove(index);  // Remove and return the card at the given index
    }

    // Add a card to collected cards (cards removed from the parade)
    public void addToCollectedCards(Card card) {
        collectedCards.add(card);
    }

    // Get the total score of this player (sum of card values in collected cards)
    public int calculateScore() {
        int score = 0;
        for (Card card : collectedCards) {
            score += card.getValue();
        }
        return score;
    }

    // Get the player's name
    public String getName() {
        return name;
    }

    // Get the player's hand size
    public int getHandSize() {
        return hand.size();
    }

    // Get all collected cards (for display purposes)
    public ArrayList<Card> getCollectedCards() {
        return collectedCards;
    }

    @Override
    public String toString() {
        return name + " has " + hand.size() + " cards.";
    }
}