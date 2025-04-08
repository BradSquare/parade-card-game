public class Card {

// Instance variables
    private final int value;
    private final String colour;

// Constructor
    public Card(int value, String colour) {
        this.value = value;
        this.colour = colour;
    }

// Instance methods
    public int getValue() {
        return value;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return colour + " " + value;
    }
}
