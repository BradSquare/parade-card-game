package entity;
import java.util.ArrayList;

public class Player {

// Instance variables
    private String name;
    private ArrayList<Card> hand;
    private ArrayList<Card> collectedCards;

// Constructor
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.collectedCards = new ArrayList<>();
    }

// Instance methods
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

    // Updates the player's list of card colours collected
    private void updateColourCount(int[] colourCount, Card card) {
        int index = getColourIndex(card.getColour());
        if (index != -1) {
            colourCount[index]++;
        }
    }
    
    private int getColourIndex(String colour) {
        switch (colour) {
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
        
        // Count this player's cards by colour
        int[] playerColourCount = new int[6];
        for (Card card : collectedCards) {
            updateColourCount(playerColourCount, card);
        }
        
        // Determine the highest colour count per colour among all players
        int[] maxColourCount = new int[6];
        for (Player p : allPlayers) {
            int[] colourCount = new int[6];
            for (Card c : p.getCollectedCards()) {
                updateColourCount(colourCount, c);
            }
            for (int i = 0; i < 6; i++) {
                maxColourCount[i] = Math.max(maxColourCount[i], colourCount[i]);
            }
        }
        
        // Special rule for 2-player games (majority requires at least 2 more cards than opponent)
        boolean twoPlayerMode = (numPlayers == 2);
        
        // Calculate score based on majority rules
        for (Card card : collectedCards) {
            int colourIndex = getColourIndex(card.getColour());
            if (colourIndex != -1) {
                boolean hasMajority = playerColourCount[colourIndex] == maxColourCount[colourIndex] && playerColourCount[colourIndex] > 0;
                
                if (twoPlayerMode) {
                    // Check if the majority rule for 2 players is met (2 more than the opponent)
                    int opponentCount = 0;
                    for (Player p : allPlayers) {
                        if (p != this) {
                            for (Card c : p.getCollectedCards()) {
                                if (getColourIndex(c.getColour()) == colourIndex) {
                                    opponentCount++;
                                }
                            }
                        }
                    }
                    hasMajority = (playerColourCount[colourIndex] >= opponentCount + 2);
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