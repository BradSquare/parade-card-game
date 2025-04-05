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
            case "Grey": return 2;
            case "Green": return 3;
            case "Purple": return 4;
            case "Orange": return 5;
            default: return -1;
        }
    }

    // Calculate the player's score by summing the values of all collected cards
    public int calculateScore(ArrayList<Player> allPlayers) {
        int score = 0;
        int numPlayers = allPlayers.size();
        
        // Count this player's cards by color
        int[] playerColorCount = new int[6];
        for (Card card : collectedCards) {
            updateColorCount(playerColorCount, card);
        }
        
        // Determine the highest color count per color among all players
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
        
        // Special rule for 2-player games (majority requires at least 2 more cards than opponent)
        boolean twoPlayerMode = (numPlayers == 2);
        
        // Calculate score based on majority rules
        for (Card card : collectedCards) {
            int colorIndex = getColorIndex(card.getColour());
            if (colorIndex != -1) {
                boolean hasMajority = playerColorCount[colorIndex] == maxColorCount[colorIndex] && playerColorCount[colorIndex] > 0;
                
                if (twoPlayerMode) {
                    // Check if the majority rule for 2 players is met (2 more than the opponent)
                    int opponentCount = 0;
                    for (Player p : allPlayers) {
                        if (p != this) {
                            for (Card c : p.getCollectedCards()) {
                                if (getColorIndex(c.getColour()) == colorIndex) {
                                    opponentCount++;
                                }
                            }
                        }
                    }
                    hasMajority = (playerColorCount[colorIndex] >= opponentCount + 2);
                }
                
                if (hasMajority) {
                    score += 1; // Majority cards are worth 1 point each
                } else {
                    score += card.getValue(); // Non-majority cards count as their printed value
                }
            }
        }
        
        return score;
    }

    @Override
    public String toString() {
        return name + " (Hand: " + hand + ")";
    }
}