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
        
        ParadeGame game = new ParadeGame(numPlayers, numComputers);
        game.startGame(); 

        scanner.close();
    }
}
