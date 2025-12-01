package events;

import player.Player;

public abstract class Event {
    protected String description;

    /**
     * Constructs an Event with the specified description.
     *
     * @param description the text description of the event
     */
    public Event(String description) {
        this.description = description;
    }

    /**
     * Retrieves the description of the event.
     *
     * @return the description of the event as a String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the event.
     *
     * @param description the new text description of the event
     */
    public void setDescription(String description){
        this.description = description;
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

