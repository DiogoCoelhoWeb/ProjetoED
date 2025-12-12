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
     * Executes the specific logic of this event based on the player provided.
     *
     * @param player the player participating in the event
     * @return a string result based on the execution of the event
     */
    public abstract String execute(Player player);

    /**
     * Executes the event with a specific choice for the given player.
     * Performs actions based on the player state and the provided choice index.
     *
     * @param player the player interacting with the event
     * @param choice the index of the choice selected by the player
     * @return a string representing the outcome or description of the event
     */
    public abstract String execute(Player player,int choice);
}
