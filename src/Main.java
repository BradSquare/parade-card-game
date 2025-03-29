import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int numHumans;
        int numComputers = 0;

        // Keep asking for number of players / computers until a valid one is given
        outerLoop:
        while (true) {  
            System.out.print("Enter number of human(s): ");

            if (scanner.hasNextInt()) { // if an integer is entered
                numHumans = scanner.nextInt();

                if (numHumans >= 2 && numHumans <= 6) { // if human vs human(s)
                    break outerLoop;  // Exit loop when a valid number of players is provided
                } else if (numHumans == 1) { // if human vs computer(s)
                    
                    while (true) {
                        System.out.print("Enter number of computer(s): ");

                        if (scanner.hasNextInt()) {
                            numComputers = scanner.nextInt();
                            if (numComputers >= 1 && numComputers <= 5) {
                                break outerLoop;  // Exit both loops when a valid number of computers is provided
                            } else { // invalid number of computers are entered
                                System.out.println("Game only supports 2-6 players. Please enter a valid integer value!");
                                continue;
                            }
                        } else {
                            scanner.next();  // Clear invalid input
                            System.out.println("Please enter a valid integer value!"); // if an integer is not entered
                        }
                    }
                } else { // invalid number of humans is entered
                    System.out.println("Game only supports 2-6 players. Please enter a valid integer value!");
                    continue;
                }
            }
            scanner.next();  // Clear invalid input
            System.out.println("Please enter a valid integer value!"); // if an integer is not entered
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
        
        ParadeGame game = new ParadeGame(numHumans, numComputers, computerDifficulty);
        game.startGame(); 

        scanner.close();
    }
}
