package events;

public class LoseTurnEvent extends BuffDebuffEvent{

    private int numTurns ;

    /**
     * Constructs a LoseTurnEvent, which represents an event that causes a player
     * to lose a randomized number of turns. This class extends BuffDebuffEvent
     * and initializes with a predefined description.
     *
     * The constructor randomizes the number of turns a player loses and appends
     * this information to the event's description.
     */
    public LoseTurnEvent() {
        super("You recived a strategic pause for a few turns.");
        randomizeTurns();
        setDescription(this.description + " You will lose " + this.numTurns + " turns.");
    }

    /**
     * Randomly assigns a value to the number of turns for the event,
     * within the range of 1 to 4 inclusive.
     * This helps determine the duration of the "Lose Turn" effect.
     */
    private void randomizeTurns() {
       this.numTurns= (int) (Math.random() * 4) + 1;
    }

}
