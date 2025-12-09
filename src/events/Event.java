package events;

import player.Player;

public abstract class Event {
    private static int idCounter = 0;
    protected int id;
    protected String description;

    /**
     * Constructs an Event with the specified description.
     *
     * @param description the text description of the event
     */
    public Event(String description) {
        this.description = description;
        this.id = idCounter++;
    }

    /**
     * Retrieves the unique identifier of the event.
     * @return The unique ID of the event.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the event. This method is primarily
     * for use during deserialization to restore the original ID.
     * @param id The ID to set for the event.
     */
    public void setId(int id) {
        this.id = id;
        if (id >= idCounter) { // Ensure idCounter keeps track of the highest ID
            idCounter = id + 1;
        }
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

