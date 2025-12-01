package events;

import lists.ArrayUnorderedList;
import player.Player;

public abstract class ChoiceEvent extends Event{

   protected ArrayUnorderedList<String> choices;

    /**
     * Constructs a ChoiceEvent with the specified description.
     *
     * @param description the text description of the event
     */
    public ChoiceEvent(String description) {
        super(description);
        this.choices = new ArrayUnorderedList<>(); // Initialize the choices list
    }

    /**
     * Adds a new choice to the list of available choices for this event.
     * Throws an IllegalArgumentException if the provided choice is null
     * or an empty string.
     *
     * @param choice the new choice to be added to the list of choices
     *               for this event
     * @throws IllegalArgumentException if the choice is null or empty
     */
    public void addChoice(String choice){
        if(choice == null || choice.trim().isEmpty()){
            throw new IllegalArgumentException("Choice cannot be null or empty.");
        }
        this.choices.addToRear(choice);
    }

    /**
     * Retrieves the list of choices associated with this event.
     *
     * @return an ArrayUnorderedList containing the choices as strings
     */
    public ArrayUnorderedList<String> getChoices(){
        return this.choices;
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
