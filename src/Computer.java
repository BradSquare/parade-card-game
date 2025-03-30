import java.util.ArrayList;
import java.util.Random;

// Imported from Player.java
public class Computer extends Player {
    private int difficulty;
    private Random random;

    public Computer(String name, int difficulty) {
        super(name);
        this.difficulty = difficulty;
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

    // public Card playCard(int index) {
    //     return super.playCard(index);
    // }

    public Card playCard(ArrayList<Card> parade) {
        switch (difficulty) {
            case 1:
                return playEasy();
            case 2:
                return playMedium(parade);
            case 3:
                return playHard(parade);
            default:
                return playEasy();
        }
    }

    // Plays a random card
    private Card playEasy() {
        int index = random.nextInt(getHandSize()); // Pick a random card
        return super.playCard(index);
    }

    // Plays a card that will remove the least number of cards
    private Card playMedium(ArrayList<Card> parade) {
        ArrayList<Card> hand = getHand();
        Card bestCard = hand.get(0);
        int minRemoval = Integer.MAX_VALUE;

        for (Card card : hand) {
            int removed = simulateCardEffect(card, parade);
            if (removed < minRemoval) {
                minRemoval = removed;
                bestCard = card;
            }
        }
        return super.playCard(hand.indexOf(bestCard));
    }

    // Plays the card that removes cards that adds the least value
    private Card playHard(ArrayList<Card> parade) {
        ArrayList<Card> hand = getHand();
        Card bestCard = hand.get(0);
        int minPenalty = Integer.MAX_VALUE;

        for (Card card : hand) {
            int penalty = simulatePenalty(card, parade);
            if (penalty < minPenalty) {
                minPenalty = penalty;
                bestCard = card;
            }
        }
        return super.playCard(hand.indexOf(bestCard));
    }

    // Simulate how many cards will be removed when this card is played
    private int simulateCardEffect(Card card, ArrayList<Card> parade) {
        int count = 0;
        for (Card c : parade) {
            if (c.getColour() == card.getColour() || c.getValue() <= card.getValue()) {
                count++;
            }
        }
        return count;
    }

    // Simulate the penalty incurred by playing this card
    private int simulatePenalty(Card card, ArrayList<Card> parade) {
        int penalty = 0;
        for (Card c : parade) {
            if (c.getColour() == card.getColour() || c.getValue() <= card.getValue()) {
                penalty += c.getValue(); // Example penalty calculation
            }
        }
        return penalty;
    }

    public ArrayList<Card> getCollectedCards() {
        return super.getCollectedCards();
    }

    public void addToCollectedCards(Card card) {
        super.addToCollectedCards(card);
    }

    // Calculate the player's score by summing the values of all collected cards
    public int calculateScore(ArrayList<Player> allPlayers) {
        return super.calculateScore(allPlayers);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}