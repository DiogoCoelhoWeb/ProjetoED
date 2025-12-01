package events;

import player.Player;

public abstract class BuffDebuffEvent extends Event{

    public BuffDebuffEvent(String description) {
        super(description);
    }

    /**
     * Executes the event, applying its effects to the specified player.
     * This method must be implemented by subclasses to define their specific event logic.
     *
     * @param player the player affected by the event
     * @return a String message describing the outcome of the event
     */
    public abstract String execute(Player player);
}
