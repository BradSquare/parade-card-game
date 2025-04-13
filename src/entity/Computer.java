package entity;

import java.util.ArrayList;
import java.util.Random;

public class Computer extends Player {

// Instance variable
    private int difficulty;

// Constructor
    public Computer(String name, int difficulty) {
        super(name);
        this.difficulty = difficulty;
    }

// Instance methods
    public int getDifficulty() {
        return difficulty;
    }

    // Chooses which card to play based on computer difficulty
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
        int index = random.nextInt(getHandSize());
        return super.playCard(index);
    }

    // Plays the card that will remove the least number of cards from parade
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

    // Plays the card that minimizes the point value of cards collected from the parade
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

    // Simulate how many cards will be removed from the parade when this card is played
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

    // Simulate the total point value of the cards removed from the parade when this card is played
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

    /*
     * Generates a random integer for the computer player's turn.
     * 
     * @param range The upper bound (inclusive) for the random integer.
     * @return A random integer between 0 and range.
    */
    public static int getRandomInt(int range) {
        Random random = new Random();
        return random.nextInt(range + 1);
    }

    // Chooses which card index to discard based on computer difficulty
    public int discardCard(ArrayList<Card> parade, ArrayList<Player> players) {
        int cardIndex = -1;
        switch (difficulty) {
            // Discards a random card
            case 1: // Easy difficulty
                cardIndex = getRandomInt(getHandSize() - 1);
                break;

            // Discards the card with the highest point value
            case 2: // Medium difficulty
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
            
            // Discards the card that will give the computer the lowest final score
            case 3: // Hard difficulty
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

}