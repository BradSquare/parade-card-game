import java.util.ArrayList;
import java.util.Collections;

public class ParadeGame {
    private ArrayList<Card> deck;
    private ArrayList<Player> players;
    private ArrayList<Card> parade;
    private int currentPlayerIndex;

    // Constructor to set up the game
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

    // Deal 5 cards to each player
    private void dealCards() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.addCardToHand(deck.remove(0));
            }
        }
    }

    // Start the game
    public void startGame() {
        while (!isGameOver()) {
            Player currentPlayer = players.get(currentPlayerIndex);

            // Print the game state for the current player
            printGameState();

            // Simulate player playing a card (here we just play the first card in hand)
            Card playedCard = currentPlayer.playCard(0);
            System.out.println(currentPlayer.getName() + " played: " + playedCard);

            // Add the card to the parade
            parade.add(playedCard);

            // Remove cards from the parade if necessary
            handleCardRemoval(playedCard);

            // Draw a new card from the deck
            if (!deck.isEmpty()) {
                currentPlayer.addCardToHand(deck.remove(0));
            }

            // Move to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        // End the game and show results
        endGame();
    }

    // Check if the game is over (when the deck is empty and all players have had their turn)
    private boolean isGameOver() {
        return deck.isEmpty();
    }

    // Handle card removal from the parade (as per the rules)
    private void handleCardRemoval(Card playedCard) {
        int numCardsInParade = parade.size();

        if (numCardsInParade <= playedCard.getValue()) {
            // No removal needed if the parade has fewer or equal cards than the played card
            return;
        }

        // Remove cards from the parade
        for (int i = numCardsInParade - 1; i >= 0; i--) {
            Card cardInParade = parade.get(i);

            if (cardInParade.getValue() <= playedCard.getValue() && cardInParade.getColour().equals(playedCard.getColour())) {
                // Move the card to the player's collected cards
                players.get(currentPlayerIndex).addToCollectedCards(parade.remove(i));
            }
        }
    }

    // Print the current game state (parade and player hands)
    private void printGameState() {
        System.out.println("Current Parade: " + parade);
        for (Player player : players) {
            System.out.println(player);
        }
    }

    // End the game and print final scores
    private void endGame() {
        System.out.println("\nGame Over! Final Scores:");
        for (Player player : players) {
            System.out.println(player.getName() + " Score: " + player.calculateScore());
        }
    }
}