package game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import entity.Card;
import entity.Computer;
import entity.Player;
import utility.CardDisplayUtil;

public class ParadeGame {

// Instance variables
    private ArrayList<Card> deck;
    private ArrayList<Player> players;
    private ArrayList<Card> parade;
    private int currentPlayerIndex;
    private boolean lastRoundTriggered = false;
    private int lastRoundTurnsTaken = 0;
    private Scanner scanner;

// Constructor
    // Game Setup
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
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine().trim();
            while (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                System.out.print("Enter name for Player " + i + ": ");
                name = scanner.nextLine().trim();
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
            parade.add(deck.remove(0)); // remove the top card from the deck and add it to the parade
        }

        // Set random starting player (reusing instance method from Computer)
        currentPlayerIndex = Computer.getRandomInt(players.size() - 1);
    }

    // Deal 5 cards to all the players
    private void dealCards() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.addCardToHand(deck.remove(0)); // Remove the top card from the deck and add it to the player's hand
            }
        }
    }

    // Gameplay
    public void startGame() {
    
        // Game loop
        while (!isGameOver()) {
            // Define the current player
            Player currentPlayer = players.get(currentPlayerIndex);
    
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            displayParade();
            System.out.println("Cards left in the deck: " + deck.size());
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            
            System.out.println(currentPlayer.getName() + ", your turn!");
            System.out.println();
            displayCollectedCards(currentPlayer);
            System.out.println();

            // Display the player's hand
            displayHand(currentPlayer);
    
            // Display player's hand
            System.out.println("Your Hand:");
            for (int i = 0; i < currentPlayer.getHandSize(); i++) {
                System.out.println(i + ": " + currentPlayer.getHand().get(i));
            }
    
            int cardIndex;
            Card playedCard;
            if (currentPlayer instanceof Computer) { // if current player is a computer
                Computer computer = (Computer) currentPlayer;
                playedCard = computer.playCard(parade);
            } else { // current player is an actual person playing.
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
                System.out.println();
                
                playedCard = currentPlayer.playCard(cardIndex);
    
            }

            System.out.println(currentPlayer.getName() + " played:");
            displaySingleCard(playedCard);
    
            // Add the card to the parade
            parade.add(playedCard);
    
            // Remove cards from the parade if necessary (based on the played card)
            handleCardRemoval(playedCard, currentPlayer);
    
            // Draw a new card from the deck if available
            if (!deck.isEmpty()) {
                currentPlayer.addCardToHand(deck.remove(0));
            }
    
            // Move to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            
        }
        
        // End the game and show results
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        endGame();
        System.out.println("--------------------------------------------------------------------------------------------------------------");

    }

    private boolean isGameOver() {
        if (lastRoundTriggered) {
            lastRoundTurnsTaken++;
    
            if (lastRoundTurnsTaken >= players.size()) {
                System.out.println("--------------------------------------------------------------------------------------------------------------");
                System.out.println("Final round is completed. Game Over!");
                return true;
            }
            return false;  // Continue next round
        }
    
        for (Player player : players) {
            if (hasCollectedAllColours(player)) {
                System.out.println("--------------------------------------------------------------------------------------------------------------");
                System.out.println(player.getName() + " has collected all six colours! Last round begins.");
                lastRoundTriggered = true;
                lastRoundTurnsTaken = 0; // Reset counter for final turns
                return false;
            }
        }
    
        if (deck.isEmpty() && !lastRoundTriggered) {
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            System.out.println("Deck is empty! Last round begins.");
            lastRoundTriggered = true;
            lastRoundTurnsTaken = 0; // Reset counter for final turns
            return false;
        }
    
        return false; 
    }
    
    // Check if a player has collected at least one card of each colour
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

    // Visually display the parade
    private void displayParade() {
        System.out.println("Current Parade:");
        CardDisplayUtil.displayCards(parade);
    }

    // Visually display the player's hand
    private void displayHand(Player player) {
        System.out.println(player.getName() + "'s Hand:");
        CardDisplayUtil.displayCards(player.getHand());
    }

    // Visually display the collected cards
    private void displayCollectedCards(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + "'s Collected Cards: ");
        if (currentPlayer.getCollectedCards().size() == 0) {
            System.out.println("No Cards Collected!");
            return;
        }
        CardDisplayUtil.displayCards(currentPlayer.getCollectedCards());
    }

    // Visually display a single card
    private void displaySingleCard(Card card) {
        ArrayList<Card> cardAsList = new ArrayList<Card>();
        cardAsList.add(card);
        CardDisplayUtil.displayCards(cardAsList);
    }
    
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
                while (cardIndex1 < 0 || cardIndex1 >= player.getHandSize()) {
                    System.out.print("Choose 1st card to discard: ");
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
                System.out.println();
            }

            // Discard the 1st card
            Card discardedCard1 = player.getHand().get(cardIndex1);
            player.getHand().remove(discardedCard1);
            System.out.println(player.getName() + " discarded:");
            displaySingleCard(discardedCard1);


            System.out.println("--------------------------------------------------------------------------------------------------------------");

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
                while (cardIndex2 < 0 || cardIndex2 >= player.getHandSize()) {
                    System.out.print("Choose 2nd card to discard: ");
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
                System.out.println();
            }            

            // Discard the 2nd card
            Card discardedCard2 = player.getHand().get(cardIndex2);
            player.getHand().remove(discardedCard2);
            System.out.println(player.getName() + " discarded:");
            displaySingleCard(discardedCard2);

            System.out.println("--------------------------------------------------------------------------------------------------------------");

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
            } else if (score == lowestScore) { // if players are tied with lowest score,
                if (cardCount < fewestCards) { // player with fewer cards will win instead
                    winnersList.clear();
                    winnersList.add(player);
                    fewestCards = cardCount;
                } else if (cardCount == fewestCards) { // multiple winners if players are tied in score and number of cards
                    winnersList.add(player);
                }
            }
        }
    
        return winnersList;
    }
}