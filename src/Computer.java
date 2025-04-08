import java.util.ArrayList;
import java.util.Random;

// Imported from Player.java
public class Computer extends Player {
    private int difficulty;

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

    public int getDifficulty() {
        return difficulty;
    }

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
        Random random = new Random();
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
        for (int i = parade.size() - 2; i >= 0; i--) {
            if (i < parade.size() - 1 - card.getValue()) {
                if (parade.get(i).getColour().equals(card.getColour()) || parade.get(i).getValue() <= card.getValue()) {
                    count++;
                }
            }
        }
        return count;
    }

    // Simulate the penalty incurred by playing this card
    private int simulatePenalty(Card card, ArrayList<Card> parade) {
        int penalty = 0;
        for (int i = parade.size() - 2; i >= 0; i--) {
            if (i < parade.size() - 1 - card.getValue()) {
                if (parade.get(i).getColour().equals(card.getColour()) || parade.get(i).getValue() <= card.getValue()) {
                    penalty += parade.get(i).getValue();
                }
            }
        }
        return penalty;
    }

    public static int getRandomInt(int range) {
        Random random = new Random();
        return random.nextInt(range + 1); // Generates number within range (inclusive)
    }

    public int discardCard(ArrayList<Card> parade, ArrayList<Player> players) {
        int cardIndex = -1;
        switch (difficulty) {
            case 1:
                cardIndex = getRandomInt(getHandSize() - 1); // random int from 0 to handsize - 1.
                break;
            case 2:
                int max = 0;
                int cardIndexMedium = 0; 
                for (Card c : getHand()) {
                    int value = c.getValue();
                    if (value >= max) {
                        max = value;
                        cardIndex = cardIndexMedium;
                    }
                    cardIndexMedium++;
                }
                break;
            case 3:
                int minPoints = Integer.MAX_VALUE;
                int cardIndexHard = 0;
                for (Card c : getHand()) {
                    ArrayList<Card> temp = getCollectedCards();
                    temp.add(c);
                    int score = calculateScore(players);
                    if (score < minPoints) {
                        minPoints = score;
                        cardIndex = cardIndexHard;
                    }
                    cardIndexHard++;
                }
                break;
            default:
                cardIndex = getRandomInt(getHandSize() - 1);
        }
        return cardIndex;
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