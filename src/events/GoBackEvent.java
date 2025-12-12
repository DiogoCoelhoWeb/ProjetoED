package events;

import player.Player;
import utils.Quotes;

import java.util.Random; // Keep Random for now, will replace with Math.random() if user requests.

public class GoBackEvent extends BuffDebuffEvent {

    /**
     * Represents an event where the player is required to go back by one step.
     * This event is initialized with a random "go back" quote from the Quotes class
     * to serve as its description.
     */
    public GoBackEvent() {
        super(Quotes.getRandomGoBackQuote());
    }

    /**
     * Constructs a GoBackEvent with a specified description of the event.
     *
     * @param description the text description of the event
     */
    public GoBackEvent(String description) {
        super(description);
    }

    /**
     * Executes the go-back action for the provided player and returns a descriptive message.
     *
     * @param player The player object on which the go-back action is performed.
     * @return A string containing the player's username and the description of the event.
     */
    @Override
    public String execute(Player player) {
        player.goBack();
        return player.getUsername() +": " + description;
    }
}
