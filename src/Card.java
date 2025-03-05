public class Card {

    private int value;
    private String colour;

    // Constructor to initialize the card's value and colour
    public Card(int value, String colour) {
        this.value = value;
        this.colour = colour;
    }

    // Get the value of the card
    public int getValue() {
        return value;
    }

    // Get the colour of the card
    public String getColour() {
        return colour;
    }

    // Represent the card as a string
    @Override
    public String toString() {
        return colour + " " + value;
    }
}
