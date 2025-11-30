package events;

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


}
