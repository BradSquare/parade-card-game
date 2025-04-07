import java.util.ArrayList;

public class CardDisplayUtil {
    // ANSI colour codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";  // used for Orange fallback
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String GREY = "\u001B[37m";    // Light grey
    public static final String ORANGE = "\u001B[38;5;208m"; // Extended ANSI colour for orange

    public static void displayCards(ArrayList<Card> cards) {
        int totalCards = cards.size();
        int cardsPerRow = 8;

        for (int start = 0; start < totalCards; start += cardsPerRow) {
            int end = Math.min(start + cardsPerRow, totalCards);
            ArrayList<Card> row = new ArrayList<>(cards.subList(start, end));
            displayRow(row);
        }
    }

    public static void displayRow(ArrayList<Card> row) {
        // List to store the lines for each card
        ArrayList<String[]> cardLinesList = new ArrayList<>();

        // Get each card's art and split it into lines
        for (Card card : row) {
            String cardArt = getCardArt(card.getColour(), card.getValue());
            String[] lines = cardArt.split("\n");
            cardLinesList.add(lines);
        }

        // Assuming all cards have the same number of lines (7 for the card, 1 for RESET, etc.)
        int numLines = cardLinesList.get(0).length;

        // Print line by line across all cards
        for (int i = 0; i < numLines; i++) {
            for (String[] lines : cardLinesList) {
                System.out.print(lines[i] + "  "); // Space between cards
            }
            System.out.println();
        }
    }

    // Get colour code from colour string
    public static String getColourCode(String colour) {
        return switch (colour.toLowerCase()) {
            case "red" -> RED;
            case "blue" -> BLUE;
            case "grey" -> GREY;
            case "green" -> GREEN;
            case "purple" -> PURPLE;
            case "orange" -> ORANGE;
            default -> RESET;
        };
    }

    // Return a string of the card in ASCII format with colour
    public static String getCardArt(String colour, int number) {
        String colourCode = getColourCode(colour);
        String numStr = String.valueOf(number);
    
        // Card width inside borders is 10 (| + 10 chars + |)
        int innerWidth = 8;
    
        // Format number lines
        String spaceNumLeft = numStr + " ".repeat(innerWidth - numStr.length() - 1);
        String spaceNumRight = " ".repeat(innerWidth - numStr.length() - 1) + numStr;
    
        // Center the color label
        int colourLength = colour.length();
        int totalPadding = innerWidth - colourLength;
        int paddingLeft = totalPadding / 2;
        int paddingRight = totalPadding - paddingLeft;
        String centeredColour = " ".repeat(paddingLeft) + colour + " ".repeat(paddingRight);
    
        return 
        colourCode + "+--------+\n" + 
        colourCode + "| " + spaceNumLeft + "|\n" +
        colourCode + "|        |\n" +
        colourCode + "|" + centeredColour + "|\n" +
        colourCode + "|        |\n" +
        colourCode + "|" + spaceNumRight + " |\n" +
        colourCode + "+--------+\n" +
        RESET;
    }
    
}

