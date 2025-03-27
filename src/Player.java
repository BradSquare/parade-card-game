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

    private void updateColorCount(int[] colorCount, Card card) {
        int index = getColorIndex(card.getColour());
        if (index != -1) {
            colorCount[index]++;
        }
    }
    
    private int getColorIndex(String color) {
        switch (color) {
            case "Red": return 0;
            case "Blue": return 1;
            case "Yellow": return 2;
            case "Green": return 3;
            case "Purple": return 4;
            case "Orange": return 5;
            default: return -1;
        }
    }

    // Calculate the player's score by summing the values of all collected cards
    public int calculateScore(ArrayList<Player> allPlayers) {
        int score = 0;
    
        // Count this player's cards by color
        int[] playerColorCount = new int[6];
        for (Card card : collectedCards) {
            updateColorCount(playerColorCount, card);
        }
    
        // Determine the max color count per color among all players
        int[] maxColorCount = new int[6];
        for (Player p : allPlayers) {
            int[] colorCount = new int[6];
            for (Card c : p.getCollectedCards()) {
                updateColorCount(colorCount, c);
            }
            for (int i = 0; i < 6; i++) {
                maxColorCount[i] = Math.max(maxColorCount[i], colorCount[i]);
            }
        }
    
        // Calculate score based on majority rules
        for (Card card : collectedCards) {
            int colorIndex = getColorIndex(card.getColour());
            if (colorIndex != -1 && playerColorCount[colorIndex] == maxColorCount[colorIndex] && playerColorCount[colorIndex] > 0) {
                // This player has majority in this color → card counts as 1 point
                score += 1;
            } else {
                // No majority → use printed value
                score += card.getValue();
            }
        }
    
        return score;
    }

    @Override
    public String toString() {
        return name + " (Hand: " + hand + ")";
    }
}