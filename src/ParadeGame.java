import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ParadeGame {
    private ArrayList<Card> deck;
    private ArrayList<Player> players;
    private ArrayList<Card> parade;
    private int currentPlayerIndex;

    public ParadeGame(int numPlayers) {
        deck = new ArrayList<>();
        players = new ArrayList<>();
        parade = new ArrayList<>();
        currentPlayerIndex = 0;

        // Create deck of cards
        for (int value = 0; value <= 10; value++) {
            for (String color : new String[]{"Red", "Blue", "Yellow", "Green", "Purple", "Orange"}) {
                deck.add(new Card(value, color));
            }
        }
        Collections.shuffle(deck);

        // Create players
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player("Player " + (i + 1)));
        }

        // Deal cards to players
        dealCards();

        // Set up the initial parade (6 cards)
        for (int i = 0; i < 6; i++) {
            parade.add(deck.remove(0));
        }
    }

    private void dealCards() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.addCardToHand(deck.remove(0));
            }
        }
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        
        while (!isGameOver()) {
            Player currentPlayer = players.get(currentPlayerIndex);

            // Print the game state for the current player
            printGameState();

            System.out.println(currentPlayer.getName() + ", your turn!");

            // Display player's hand
            System.out.println("Your Hand:");
            for (int i = 0; i < currentPlayer.getHandSize(); i++) {
                System.out.println(i + ": " + currentPlayer.getHand().get(i));
            }

            // Prompt the player to choose a card to play
            System.out.print("Choose a card index to play: ");
            int cardIndex = scanner.nextInt();

            // Validate the input and play the chosen card
            if (cardIndex >= 0 && cardIndex < currentPlayer.getHandSize()) {
                Card playedCard = currentPlayer.playCard(cardIndex);
                System.out.println(currentPlayer.getName() + " played: " + playedCard);

                // Add the card to the parade
                parade.add(playedCard);

                // Remove cards from the parade if necessary
                handleCardRemoval(playedCard);

                // Draw a new card from the deck if available
                if (!deck.isEmpty()) {
                    currentPlayer.addCardToHand(deck.remove(0));
                }
            } else {
                System.out.println("Invalid choice! You lost your turn.");
            }

            // Move to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        // End the game and show results
        endGame();
    }

    private boolean isGameOver() {
        for (Player player : players) {
            if (player.getCollectedCards().size() >= 6) {
                System.out.println(player.getName() + " has collected all colors! Last round starts.");
                return true;
            }
        }

        return deck.isEmpty();
    }

    private void handleCardRemoval(Card playedCard) {
        int numCardsInParade = parade.size();

        if (numCardsInParade <= playedCard.getValue()) {
            return;
        }

        for (int i = numCardsInParade - 1; i >= 0; i--) {
            Card cardInParade = parade.get(i);

            if (cardInParade.getValue() <= playedCard.getValue() && cardInParade.getColour().equals(playedCard.getColour())) {
                players.get(currentPlayerIndex).addToCollectedCards(parade.remove(i));
            }
        }
    }

    private void printGameState() {
        System.out.println("Current Parade: " + parade);
        for (Player player : players) {
            System.out.println(player);
        }
    }

    private void endGame() {
        System.out.println("\nGame Over! Final Scores:");

        for (Player player : players) {
            System.out.println(player.getName() + "'s final hand:");
            System.out.println("Collected Cards: " + player.getCollectedCards());

            System.out.println("Discard two cards:");
            for (int i = 0; i < 2; i++) {
                if (player.getHandSize() > 0) {
                    Card discardedCard = player.playCard(0); 
                    System.out.println("Discarded: " + discardedCard);
                }
            }

            int score = player.calculateScore();
            score += calculateMajorityPoints(player);
            System.out.println(player.getName() + " Score: " + score);
        }

        Player winner = determineWinner();
        System.out.println("\nThe winner is: " + winner.getName());
    }

    private int calculateMajorityPoints(Player player) {
        int majorityPoints = 0;
        ArrayList<Card> collectedCards = player.getCollectedCards();
        int[] colorCount = new int[6];

        for (Card card : collectedCards) {
            switch (card.getColour()) {
                case "Red":
                    colorCount[0]++;
                    break;
                case "Blue":
                    colorCount[1]++;
                    break;
                case "Yellow":
                    colorCount[2]++;
                    break;
                case "Green":
                    colorCount[3]++;
                    break;
                case "Purple":
                    colorCount[4]++;
                    break;
                case "Orange":
                    colorCount[5]++;
                    break;
            }
        }

        for (int count : colorCount) {
            if (count > 1) {
                majorityPoints += count;
            }
        }

        return majorityPoints;
    }

    private Player determineWinner() {
        Player winner = players.get(0);
        int lowestScore = winner.calculateScore();

        for (Player player : players) {
            int score = player.calculateScore();
            if (score < lowestScore) {
                winner = player;
                lowestScore = score;
            }
        }

        return winner;
    }
}