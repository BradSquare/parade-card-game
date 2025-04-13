import java.util.Scanner;

import game.ParadeGame;
import utility.LoadingUtil;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int numHumans = 1;
        int numComputers = 0;

        LoadingUtil.startScreen();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Select Game Mode:");
        System.out.println("1. Human vs Computer(s)");
        System.out.println("2. Human vs Human(s)");
        while (true) {
            System.out.print("Select 1 or 2: ");
            String gameModeString = scanner.nextLine().trim();

            if (gameModeString.isEmpty()) {
                System.out.println("Please enter a valid integer value!");
                continue;
            }

            try{
                int gameMode = Integer.parseInt(gameModeString);

                if (gameMode == 1) { // If Human vs Computer(s)
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
                    while (true) {
                        System.out.print("Enter number of computer(s) (1-5): ");
                        String numComputersString = scanner.nextLine().trim();

                        if (numComputersString.isEmpty()) {
                            System.out.println("Please enter a valid integer value!");
                            continue;
                        }

                        try {
                            numComputers = Integer.parseInt(numComputersString);
                            if (numComputers >= 1 && numComputers <= 5) {
                                break;  // Valid number of computers
                            } else {
                                System.out.println("Please enter a valid number of computers (1-5).");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid integer value!");
                        }
                    }
                    break;  // Exit the loop after valid selection
                } else if (gameMode == 2) { // If Human vs Human(s)
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
                    while (true) {
                        System.out.print("Enter total number of player(s) (2-6): ");
                        String numHumansString = scanner.nextLine().trim();

                        if (numHumansString.isEmpty()) {
                            System.out.println("Please enter a valid integer value!");
                            continue;
                        }

                        try {
                            numHumans = Integer.parseInt(numHumansString);
                            if (numHumans >= 2 && numHumans <= 6) {
                                break;  // Valid number of humans
                            } else {
                                System.out.println("Please enter a valid number of humans (2-6).");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid integer value");
                        }
                    }
                    break;  // Exit the loop after valid selection
                } else {
                    System.out.println("Invalid selection. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer value!");
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");

        int computerDifficulty = 0;

        // Prompt for difficulty if there are computers
        if (numComputers > 0) {
            System.out.println("Computer Difficulty:");
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Hard");
            while (true) {
                System.out.print("Select difficulty (1-3): ");

                String computerDifficultyString = scanner.nextLine().trim();

                if (computerDifficultyString.isEmpty()) {
                    System.out.println("Please enter a valid integer value!");
                    continue;
                }

                try {
                    computerDifficulty = Integer.parseInt(computerDifficultyString);
                    if (computerDifficulty >= 1 && computerDifficulty <= 3) {
                        break;
                    } else {
                        System.out.println("Invalid difficulty. Please select a number from 1 and 3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid integer value!");
                }
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
        }

        // Start the game
        ParadeGame game = new ParadeGame(numHumans, numComputers, computerDifficulty);
        game.startGame(); 

        scanner.close();
    }
}
