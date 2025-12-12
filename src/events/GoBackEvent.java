package events;

import player.Player;
import utils.Quotes;

import java.util.Random; // Keep Random for now, will replace with Math.random() if user requests.

public class GoBackEvent extends BuffDebuffEvent {

    /**
     * Constructs a GoBackEvent with a randomly selected "go back" description.
     * The event is designed to force the player to move back in the game.
     * Utilizes a quote from the Quotes class to provide immersive text for the event.
     */
    public GoBackEvent() {
        super(Quotes.getRandomGoBackQuote());
    }

    /**
     * Constructs a GoBackEvent with the specified description.
     *
     * @param description the text description of the event
     */
    public GoBackEvent(String description) {
        super(description);
    }

    /**
     * Executes the "go back" action for the specified player and returns a message.
     *
     * @param player the player who will perform the go back action
     * @return a string containing the player's username and the description of the event
     */
    @Override
    public String execute(Player player) {
        player.goBack();
        return player.getUsername() +": " + description;
    }
}
