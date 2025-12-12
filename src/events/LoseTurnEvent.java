package events;

import player.Player;
import utils.Quotes;

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
        super(Quotes.getRandomLoseTurnQuote());
        randomizeTurns();
        setDescription(this.description + " You will lose " + this.numTurns + " turns.");
    }

    /**
     * Constructs a LoseTurnEvent with a specific number of turns to lose.
     * @param turns The number of turns the player will lose.
     */
    public LoseTurnEvent(int turns) {
        super(Quotes.getRandomLoseTurnQuote());
        this.numTurns = turns;
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



    /**
     * Executes the "Lose Turn" event for the specified player. This method applies
     * the effect of the event, causing the player to lose a defined number of turns,
     * and returns a string including the player's username and a description of
     * the event.
     *
     * @param player The player affected by the event. The specified player will
     *               experience the effect of losing turns as defined by the event.
     * @return A string containing the player's username followed by the event's
     *         description, indicating the player has been affected by this event.
     */
    @Override
    public String execute(Player player) {
        player.block(this.numTurns);
        return player.getUsername() + " : " + description;
    }
}
