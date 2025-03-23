import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class ParadeGame {
    private ArrayList<Card> deck;
    private ArrayList<Player> players;
    private ArrayList<Card> parade;
    private int currentPlayerIndex;
    private boolean lastRoundTriggered = false;
    private int lastRoundTurnsTaken = 0;     

    public ParadeGame(int numPlayers, int numComputers) {
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

        // Create players and computers
        int creationIndex = 1;
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player("Player " + (creationIndex)));
            creationIndex++;
        }
        for (int i = 0; i < numComputers; i++) {
            players.add(new Computer("(Computer) Player " + (creationIndex)));
            creationIndex++;
        }

        // Deal cards to players and computers.
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
    
        // Game loop
        while (!isGameOver()) {
            Player currentPlayer = players.get(currentPlayerIndex);  // Ensure currentPlayer is defined
    
            // Print the game state for the current player
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
            printGameState();
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
            
            // Display the number of cards left in the deck
            System.out.println("Cards left in the deck: " + deck.size());

            System.out.println(currentPlayer.getName() + ", your turn!");
    
            // Display player's hand
            System.out.println("Your Hand:");
            for (int i = 0; i < currentPlayer.getHandSize(); i++) {
                System.out.println(i + ": " + currentPlayer.getHand().get(i));
            }
    
            int cardIndex;
            if (currentPlayer instanceof Computer) { // currentPlayer is a computer.
                cardIndex = getRandomInt(currentPlayer.getHandSize() - 1); // Random number from 0 to handsize - 1.
            } else { // currentPlayer is an actual person playing.
                while (true) {  // Keep asking for input until a valid one is given
                    System.out.print("Choose a card index to play: ");
                    if (scanner.hasNextInt()) {
                        cardIndex = scanner.nextInt();
                        if (cardIndex >= 0 && cardIndex < currentPlayer.getHandSize()) {
                            break;  // Exit loop when a valid index is provided
                        }
                    } else {
                        scanner.next();  // Clear invalid input
                    }
                    System.out.println("Invalid choice! Please enter a valid card index.");
                }
    
            }

            // Play the chosen card
            Card playedCard = currentPlayer.playCard(cardIndex);
            System.out.println(currentPlayer.getName() + " played: " + playedCard);
    
            // Add the card to the parade
            parade.add(playedCard);
    
            // Remove cards from the parade if necessary (based on the played card)
            handleCardRemoval(playedCard, currentPlayer);
    
            // Draw a new card from the deck if available
            if (!deck.isEmpty()) {
                currentPlayer.addCardToHand(deck.remove(0));
            }
    
            // Display the player's collected cards after they play their turn
            System.out.println(currentPlayer.getName() + "'s Collected Cards: " + currentPlayer.getCollectedCards());
    
            // Move to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            
        }
        
        // End the game and show results
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        endGame();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        scanner.close();
    }

    private boolean isGameOver() {
        if (lastRoundTriggered) {
            lastRoundTurnsTaken++;
    
            if (lastRoundTurnsTaken >= players.size()) {
                System.out.println("Final round is completed. Game Over!");
                return true;
            }
            return false;  // Continue last round
        }
    
        for (Player player : players) {
            if (hasCollectedAllColors(player)) {
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(player.getName() + " has collected all six colors! Last round begins.");
                lastRoundTriggered = true;
                lastRoundTurnsTaken = 0; // Reset counter for final turns
                return false;
            }
        }
    
        if (deck.isEmpty() && !lastRoundTriggered) {
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Deck is empty! Last round begins.");
            lastRoundTriggered = true;
            lastRoundTurnsTaken = 0; // Reset counter for final turns
            return false;
        }
    
        return false; 
    }

    // Generate a random int for computer's turn.
    public static int getRandomInt(int max) {
        Random random = new Random();
        return random.nextInt(max + 1); // Generates number from 0 to max (inclusive)
    }
    
    
    // Check if a player has collected at least one card of each color
    private boolean hasCollectedAllColors(Player player) {
        boolean[] colorsPresent = new boolean[6];  // Array to track colors (Red, Blue, Yellow, Green, Purple, Orange)
        
        for (Card card : player.getCollectedCards()) {
            switch (card.getColour()) {
                case "Red": colorsPresent[0] = true; break;
                case "Blue": colorsPresent[1] = true; break;
                case "Yellow": colorsPresent[2] = true; break;
                case "Green": colorsPresent[3] = true; break;
                case "Purple": colorsPresent[4] = true; break;
                case "Orange": colorsPresent[5] = true; break;
            }
        }
    
        // Check if all colors are collected
        for (boolean color : colorsPresent) {
            if (!color) {
                return false;
            }
        }
        return true;
    }

    private void handleCardRemoval(Card playedCard, Player currentPlayer) {
        // Get the number of cards in the parade
        int paradeSize = parade.size();
    
        // If the parade size is less than or equal to the value of the played card, no removal is necessary
        if (paradeSize - 1 <= playedCard.getValue()) {
            return;
        }
    
        // If the value of the played card is 0, all cards in the parade enter removal mode
        if (playedCard.getValue() == 0) {
            for (int i = paradeSize - 2; i >= 0; i--) {
                Card card = parade.get(i);
                if (card.getColour().equals(playedCard.getColour()) || card.getValue() == 0) {
                    parade.remove(i);
                    currentPlayer.addToCollectedCards(card);
                }
            }
            return;
        }
    
        // Loop through the parade from the back (end) to the front, excluding the card just played
        for (int i = paradeSize - 2; i >= 0; i--) {
    
            // Get the current card in the parade
            Card cardInParade = parade.get(i);
    
            // Check if this card is in removal mode
            if (i < paradeSize - 1 - playedCard.getValue()) {
                // This card is in removal mode, now check the conditions
                if (cardInParade.getColour().equals(playedCard.getColour()) || 
                    cardInParade.getValue() <= playedCard.getValue()) {
                    // Add to the removal list if the card matches removal conditions
                    parade.remove(i);
                    currentPlayer.addToCollectedCards(cardInParade);
                }
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
        System.out.println("Game Over!");
        
        // Initialize the Scanner object (only once for the entire loop)
        Scanner scanner = new Scanner(System.in);

        // Display each player's collected cards
        for (Player player : players) {
            System.out.println(player.getName() + "'s Collected Cards: " + player.getCollectedCards());

            // Display the player's hand
            for (int i = 0; i < player.getHandSize(); i++) {
                System.out.println(i + ": " + player.getHand().get(i));
            }

            // Choose the 1st card to discard
            int cardIndex1 = -1;
            while (cardIndex1 < 0 || cardIndex1 >= player.getHandSize()) {
                System.out.print("Choose 1st card to discard:");
                if (scanner.hasNextInt()) {
                    cardIndex1 = scanner.nextInt();
                        if (cardIndex1 < 0 || cardIndex1 >= player.getHandSize()) {
                            System.out.println("Invalid index! Please choose a valid index.");
                        }
                } else {
                    scanner.next();  // Clear the invalid input
                    System.out.println("Invalid input! Please enter a valid number.");
                }
            }

            // Discard the 1st card
            Card discardedCard1 = player.getHand().get(cardIndex1);
            player.getHand().remove(discardedCard1);

            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");

            // Display the updated hand
            System.out.println("Updated hand after discarding the 1st card:");
            for (int i = 0; i < player.getHandSize(); i++) {
                System.out.println(i + ": " + player.getHand().get(i));
            }

            // Choose the 2nd card to discard
            int cardIndex2 = -1;
            while (cardIndex2 < 0 || cardIndex2 >= player.getHandSize()) {
                System.out.print("Choose 2nd card to discard:");
                if (scanner.hasNextInt()) {
                    cardIndex2 = scanner.nextInt();
                    if (cardIndex2 < 0 || cardIndex2 >= player.getHandSize()) {
                        System.out.println("Invalid index! Please choose a valid index.");
                    }
                } else {
                    scanner.next();  // Clear the invalid input
                    System.out.println("Invalid input! Please enter a valid number.");
                }
            }

            // Discard the 2nd card
            Card discardedCard2 = player.getHand().get(cardIndex2);
            player.getHand().remove(discardedCard2);

            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");

            // Add the remaining cards in hand to collected cards
            for (Card card : player.getHand()) {
                player.addToCollectedCards(card);
            }
    
        }

        scanner.close();

        for (Player player : players) {
            // Calculate score: sum of values of all collected cards + majority points
            int score = player.calculateScore();
    
            // Count majority points per color
            score += calculateMajorityPoints(player);
    
            System.out.println(player.getName() + " Score: " + score);
        }
    
        // Determine the winner (player with the lowest score)
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