import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int numPlayers;
        int numComputers = 0;
        while (true) {  // Keep asking for input until a valid one is given
            System.out.print("Enter number of players: ");
            if (scanner.hasNextInt()) {
                numPlayers = scanner.nextInt();
                if (numPlayers >= 2 && numPlayers <= 6) {
                    break;  // Exit loop when a valid number of players is provided
                } else if (numPlayers == 1) {
                    System.out.print("Enter number of computers: ");
                    if (scanner.hasNextInt()) {
                        numComputers = scanner.nextInt();
                        if (numComputers >= 1 && numComputers <= 5) {
                            break;  // Exit loop when a valid number of computers is provided
                        }
                    } else {
                        scanner.next(); // Clear invalid input
                    }
                }
            } else {
                scanner.next();  // Clear invalid input
            }
            System.out.println("Game only supports 2-6 players.");
        }

        int computerDifficulty = 0;
        // Prompt for difficulty if there are computers
        if (numComputers > 0) {
            while (true) {
                System.out.println("\nComputer Difficulty:");
                System.out.println("1. Easy");
                System.out.println("2. Medium");
                System.out.println("3. Hard");
                System.out.print("Select difficulty (1-3): ");

                if (scanner.hasNextInt()) {
                    computerDifficulty = scanner.nextInt();
                    if (computerDifficulty >= 1 && computerDifficulty <= 3) {
                        break;
                    }
                } else {
                    scanner.next(); // Clear invalid input
                }
                System.out.println("Invalid selection. Please enter 1, 2, or 3.");
            }
        }
        
        ParadeGame game = new ParadeGame(numPlayers, numComputers, computerDifficulty);
        game.startGame(); 

        scanner.close();
    }
}
