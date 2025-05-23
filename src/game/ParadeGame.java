package game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import entity.Card;
import entity.Computer;
import entity.Player;
import utility.CardDisplayUtil;
import utility.LoadingUtil;

public class ParadeGame {

// Instance variables

    /** The deck of cards used in the game. */
    private ArrayList<Card> deck;

    /** List of players in the game. */
    private ArrayList<Player> players;

    /** The parade where played cards are placed. */
    private ArrayList<Card> parade;

    private int currentPlayerIndex;
    private boolean lastRoundTriggered = false;
    private int lastRoundTurnsTaken = 0;
    private Scanner scanner;

// Constructor

    /**
     * Constructs a new Parade game with the specified number of players.
     * 
     * @param numPlayers The number of players participating in the game.
     * @param numComputers The number of computer players participating in the game.
     * @param computerDifficulty The computer difficulty level (1: Easy, 2: Medium, 3: Hard).
    */
    public ParadeGame(int numPlayers, int numComputers, int computerDifficulty) {
        deck = new ArrayList<>();
        players = new ArrayList<>();
        parade = new ArrayList<>();
        currentPlayerIndex = 0;

        // Create deck of cards, with 6 colours * 11 numbers (from 0 - 10)
        for (int value = 0; value <= 10; value++) {
            for (String colour : new String[]{"Red", "Blue", "Grey", "Green", "Purple", "Orange"}) {
                deck.add(new Card(value, colour));
            }
        }

        // Randomise cards in the initialised deck
        Collections.shuffle(deck);

        // Create players and computers and get human player names
        this.scanner = new Scanner(System.in);
        ArrayList<String> playerNames = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            String name;
            while (true) {
                System.out.print("Enter name for Player " + i + ": ");
                name = scanner.nextLine().trim();
        
                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty!");
                } else if (playerNames.contains(name)) { // No duplicate names
                    System.out.println("Name already taken!");
                } else {
                    break; // Valid name
                }
            }
            playerNames.add(name);
        }

        // Create human players
        for (String name : playerNames) {
            players.add(new Player(name));
        }

        int creationIndex = 1;
        for (int i = 0; i < numComputers; i++) {
            Computer computer = new Computer("Computer " + (creationIndex), computerDifficulty);
            players.add(computer);
            creationIndex++;
        }

        // Deal cards to players and computers.
        dealCards();

        // Set up the initial parade (6 cards)
        for (int i = 0; i < 6; i++) {
            parade.add(deck.remove(0)); // Remove the top card from the deck and add it to the parade
        }

        // Set random starting player (reusing instance method from Computer)
        currentPlayerIndex = Computer.getRandomInt(players.size() - 1);
    }

    /**
     * Deal 5 cards to all the players
    */
    private void dealCards() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.addCardToHand(deck.remove(0)); // Remove the top card from the deck and add it to the player's hand
            }
        }
    }

    /**
     * Starts the game by dealing initial hands and running the game loop.
    */
    public void startGame() {
        // Game loop
        while (!isGameOver()) {
            // Define the current player
            Player currentPlayer = players.get(currentPlayerIndex);
    
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
            displayParade();
            System.out.println("Cards left in the deck: " + deck.size());
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
            LoadingUtil.Delay();
            LoadingUtil.Delay();

            System.out.println(currentPlayer.getName() + ", your turn!\n");

            displayHand(currentPlayer);
    
            // Display player's hand for player to select card to play
            for (int i = 0; i < currentPlayer.getHandSize(); i++) {
                System.out.println(i + ": " + currentPlayer.getHand().get(i));
            }
    
            int cardIndex;
            Card playedCard;
            if (currentPlayer instanceof Computer) { // Current player is a computer
                Computer computer = (Computer) currentPlayer;
                playedCard = computer.playCard(parade);
                System.out.print("\n" + currentPlayer.getName() + " is thinking");
                LoadingUtil.Ellipsis();
            } else { // Current player is an actual person playing.
                while (true) {  // Keep asking for input until a valid one is given
                    System.out.print("Choose a card index to play: ");

                    String input = scanner.nextLine().trim(); // get input and trim spaces

                    // If user just pressed Enter without typing anything
                    if (input.isEmpty()) {
                        System.out.println("Please enter a valid integer value!");
                        continue;
                    }

                    try {
                        cardIndex = Integer.parseInt(input);
                        if (cardIndex >= 0 && cardIndex < currentPlayer.getHandSize()) {
                            break; // valid input
                        } else {
                            System.out.println("Invalid index! Please enter a valid card index.");
                        }
                    } catch (NumberFormatException e) {
                        if (!input.isEmpty()) {
                            // Print error message
                            System.out.println("Please enter a valid integer value!");
                        }
                    } 

                }
                System.out.println();
                
                playedCard = currentPlayer.playCard(cardIndex);
    
            }

            System.out.println(currentPlayer.getName() + " played:");
            displaySingleCard(playedCard);
    
            // Add the card to the parade
            parade.add(playedCard);
    
            // Remove cards from the parade if necessary (based on the played card)
            handleCardRemoval(playedCard, currentPlayer);

            displayCollectedCards(currentPlayer);
    
            // Draw a new card from the deck if available
            if (!deck.isEmpty()) {
                currentPlayer.addCardToHand(deck.remove(0));
            }
    
            // Move to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            
            LoadingUtil.Delay();
            LoadingUtil.Delay();
            LoadingUtil.Delay();
            LoadingUtil.Delay();
        }
        
        // End the game and show results
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
        endGame();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Determines whether the game has ended based on game state conditions.
     * The game ends when either:
     * <ul>
     *   <li>A player has collected at least one card of each color (triggers final round)</li>
     *   <li>The deck is empty (triggers final round)</li>
     * </ul>
     * 
     * <p>When a game-ending condition is detected, appropriate status messages are displayed.
     * This method also manages the final round trigger state and turn counting.
     *
     * @return {@code true} if the game has ended, {@code false} if the game should continue
     * @see #hasCollectedAllColours(Player)
    */
    private boolean isGameOver() {
        if (lastRoundTriggered) {
            lastRoundTurnsTaken++;
    
            if (lastRoundTurnsTaken >= players.size()) {
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Final round is completed. Game Over!");
                return true;
            }
            return false;  // Continue next round
        }
    
        // If a player has collected all 6 colours
        for (Player player : players) {
            if (hasCollectedAllColours(player)) {
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(player.getName() + " has collected all six colours! Last round begins.");
                LoadingUtil.Delay();
                LoadingUtil.Delay();
                lastRoundTriggered = true;
                lastRoundTurnsTaken = 0; // Reset counter for final turns
                return false;
            }
        }
    
        // If the deck is empty and the last round has not been played
        if (deck.isEmpty() && !lastRoundTriggered) {
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Deck is empty! Last round begins.");
            LoadingUtil.Delay();
            LoadingUtil.Delay();
            lastRoundTriggered = true;
            lastRoundTurnsTaken = 0; // Reset counter for final turns
            return false;
        }
    
        return false; 
    }
    
    /**
     * Determines whether the specified player has collected at least one card of each color.
     * The colors checked are: Red, Blue, Grey, Green, Purple, and Orange.
     *
     * @param player The player whose collected cards will be checked. Must not be null.
     * @return {@code true} if the player has collected at least one card of each color,
     *         {@code false} otherwise.
     * @throws NullPointerException if the player parameter is null.
     * @see Card
    */
    private boolean hasCollectedAllColours(Player player) {
        boolean[] coloursPresent = new boolean[6];  // Array to track colours (Red, Blue, Grey, Green, Purple, Orange)
        
        for (Card card : player.getCollectedCards()) {
            switch (card.getColour()) {
                case "Red": coloursPresent[0] = true; break;
                case "Blue": coloursPresent[1] = true; break;
                case "Grey": coloursPresent[2] = true; break;
                case "Green": coloursPresent[3] = true; break;
                case "Purple": coloursPresent[4] = true; break;
                case "Orange": coloursPresent[5] = true; break;
            }
        }
    
        // Check if all colours are collected
        for (boolean colour : coloursPresent) {
            if (!colour) {
                return false;
            }
        }
        return true;
    }

    /**
     * Handles the removal of cards from the parade based on the played card.
     *
     * @param playedCard The card that was played and triggers the removal. Must not be null.
     * @param currentPlayer The player who played the card. Must not be null.
     * @throws NullPointerException if either playedCard or currentPlayer is null.
    */
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

    /**
     * Displays the current parade of cards in a visual format.
     * The parade represents the central line of cards that players interact with.
     * Each card is displayed with its color and value in a formatted layout.
    */
    private void displayParade() {
        System.out.println("Current Parade:");
        CardDisplayUtil.displayCards(parade);
    }

    /**
     * Displays a player's hand of cards in a visual format.
     * 
     * @param player The player whose hand will be displayed. Must not be null.
     * @throws NullPointerException if the player parameter is null.
    */
    private void displayHand(Player player) {
        System.out.println(player.getName() + "'s Hand:");
        CardDisplayUtil.displayCards(player.getHand());
    }

    /**
     * Displays the cards collected by the specified player.
     * If no cards have been collected, displays an appropriate message.
     * 
     * @param currentPlayer The player whose collected cards will be displayed.
     *                      Must not be null.
     * @throws NullPointerException if currentPlayer parameter is null.
    */
    private void displayCollectedCards(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + "'s Collected Cards: ");
        if (currentPlayer.getCollectedCards().size() == 0) {
            System.out.println("No Cards Collected!\n");
            return;
        }
        CardDisplayUtil.displayCards(currentPlayer.getCollectedCards());
    }

    /**
     * Displays a single card in a visual format.
     * 
     * @param card The card to display. Must not be null.
     * @throws NullPointerException if card parameter is null.
    */
    private void displaySingleCard(Card card) {
        ArrayList<Card> cardAsList = new ArrayList<Card>();
        cardAsList.add(card);
        CardDisplayUtil.displayCards(cardAsList);
    }
    
    /**
     * Ends the game by handling final card discards and calculating scores.
     */
    private void endGame() {        
        // Display each player's collected cards
        for (Player player : players) {
            displayCollectedCards(player);

            // Display the player's hand
            displayHand(player);
            for (int i = 0; i < player.getHandSize(); i++) {
                System.out.println(i + ": " + player.getHand().get(i));
            }

            // Choose the 1st card to discard
            int cardIndex1 = -1;
            if (player instanceof Computer) { // if player is a computer, computer discards cards based on difficulty
                Computer computer = (Computer) player;
                cardIndex1 = computer.discardCard(parade, players);
                System.out.println("Choose 1st card to discard: " + cardIndex1);
                System.out.println();
            } else { // player is an actual human playing, allow player to choose which card to discard
                while (true) {
                    System.out.print("Choose 1st card to discard: ");
                    String input = scanner.nextLine().trim();
                
                    if (input.isEmpty()) {
                        System.out.println("Please enter a valid integer value!");
                        continue;
                    }
                
                    try {
                        cardIndex1 = Integer.parseInt(input);
                
                        if (cardIndex1 >= 0 && cardIndex1 < player.getHandSize()) {
                            break; // Valid index
                        } else {
                            System.out.println("Invalid index! Please choose a valid card index.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid integer value!");
                    }
                }
                System.out.println();
            }

            // Discard the 1st card
            Card discardedCard1 = player.getHand().get(cardIndex1);
            player.getHand().remove(discardedCard1);
            System.out.println(player.getName() + " discarded:");
            displaySingleCard(discardedCard1);

            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");

            // Display the updated hand
            System.out.print("After discarding the 1st card, ");
            displayHand(player);
            for (int i = 0; i < player.getHandSize(); i++) {
                System.out.println(i + ": " + player.getHand().get(i));
            }

            // Choose the 2nd card to discard
            int cardIndex2 = -1;
            if (player instanceof Computer) {
                Computer computer = (Computer) player;
                cardIndex2 = computer.discardCard(parade, players);
                System.out.println("Choose 2nd card to discard: " + cardIndex2);
                System.out.println();
            } else {
                while (true) {
                    System.out.print("Choose 2nd card to discard: ");
                    String input = scanner.nextLine().trim();
                
                    if (input.isEmpty()) {
                        System.out.println("Please enter a valid integer value!");
                        continue;
                    }
                
                    try {
                        cardIndex2 = Integer.parseInt(input);
                
                        if (cardIndex2 >= 0 && cardIndex2 < player.getHandSize()) {
                            break; // Valid input
                        } else {
                            System.out.println("Invalid index! Please choose a valid card index.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid integer value!");
                    }
                }
            }            

            // Discard the 2nd card
            Card discardedCard2 = player.getHand().get(cardIndex2);
            player.getHand().remove(discardedCard2);
            System.out.println(player.getName() + " discarded:");
            displaySingleCard(discardedCard2);

            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");

            // Add the remaining cards in hand to collected cards
            for (Card card : player.getHand()) {
                player.addToCollectedCards(card);
            }
    
        }

        // Calculate scores for each player and display each player's collected cards
        for (Player player : players) {
            int score = player.calculateScore(players);
            System.out.print("At the end of the game, ");
            displayCollectedCards(player);
            System.out.println(player.getName() + "'s Score: " + score);
            System.out.println();
        }
    
        // Determine the winner
        ArrayList<Player> winners = determineWinner();
        if (winners.size() == 1) {
            System.out.println("The winner is " + winners.get(0).getName() + "!");
        } else {
            System.out.println("It's a tie between: ");
            for (Player p : winners) {
                System.out.println("- " + p.getName());
            }
        }

        if (scanner != null) {
            scanner.close();
        }
    }

    /**
     * Determines the winner(s) of the game based on players' scores and collected cards.
     * <p>
     * The winner is the player with the lowest score. If multiple players have the same
     * lowest score, the player with the fewest collected cards is preferred. If there's still
     * a tie, all such players are considered winners.
     * </p>
     *
     * @return an {@code ArrayList} of {@code Player} objects representing the winner(s).
    */
    private ArrayList<Player> determineWinner() {
        int lowestScore = Integer.MAX_VALUE;
        int fewestCards = Integer.MAX_VALUE;

        ArrayList<Player> winnersList = new ArrayList<>();
    
        for (Player player : players) {
            int score = player.calculateScore(players);
            int cardCount = player.getCollectedCards().size();
            
            // Player with the lowest score wins
            if (score < lowestScore) {
                winnersList.clear();
                winnersList.add(player);
                lowestScore = score;
                fewestCards = cardCount;
            } else if (score == lowestScore) { // If players are tied with lowest score,
                if (cardCount < fewestCards) { // player with fewer cards will win instead
                    winnersList.clear();
                    winnersList.add(player);
                    fewestCards = cardCount;
                } else if (cardCount == fewestCards) { // Multiple winners if players have the same score and number of cards
                    winnersList.add(player);
                }
            }
        }
    
        return winnersList;
    }
    

}