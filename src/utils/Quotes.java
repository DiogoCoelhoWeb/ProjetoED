package utils;

public class Quotes {

    private static final String[] GO_BACK_QUOTES = {
            "Behold the Sphere! It laughs at thy retreat. Unworthy is this path.",
            "The walls themselves reject your advance. Turn back, traveler.",
            "A spectral wind pushes you back. This is not your destined path.",
            "The air grows heavy, forcing you to retreat from whence you came.",
            "An ancient curse binds this corridor. You are repelled.",
            "The labyrinth whispers: 'Not this way.' You are compelled to return."
    };

    private static final String[] LOSE_TURN_QUOTES = {
            "Time's sands flow backward, stealing your next move.",
            "A sudden fatigue overtakes you, halting your progress.",
            "The spirits of the labyrinth demand tribute: a pause in your journey.",
            "Your path darkens, forcing a moment of unwelcome reflection.",
            "A fleeting illusion, and your momentum is lost for a time."
    };


    /**
     * Returns a random quote for a "go back" event.
     * @return A random "go back" quote.
     */
    public static String getRandomGoBackQuote() {
        return GO_BACK_QUOTES[(int) (Math.random() * GO_BACK_QUOTES.length)];
    }

    /**
     * Returns a random quote for a "lose turn" event.
     * @return A random "lose turn" quote.
     */
    public static String getRandomLoseTurnQuote() {
        return LOSE_TURN_QUOTES[(int) (Math.random() * LOSE_TURN_QUOTES.length)];
    }
}
