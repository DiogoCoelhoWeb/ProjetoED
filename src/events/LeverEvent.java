package events;

import player.Player;

public class LeverEvent extends EnigmaEvent{

    /**
     * Constructs a LeverEvent with the specified description and correct choice index.
     * Extends the functionality of EnigmaEvent, representing a lever-based decision
     * with multiple choices.
     *
     * @param description the text description of the event
     * @param correctChoice the index of the correct choice for the lever-based event
     */
    public LeverEvent(String description, int correctChoice) {
        super(description, correctChoice);
    }

    /**
     * Adds a new choice to the list of available choices for this event.
     *
     * @param choice the new choice to be added to the list of choices
     *               for this event
     * @throws IllegalArgumentException if the choice is null or empty
     */
    @Override
    public void addChoice(String choice) {
        super.addChoice(choice);
    }


    /**
     * Executes the event logic associated with the lever action for the given player.
     *
     * @param player the player participating in the event
     * @return a string representing the outcome or result of the event execution
     */
    @Override
    public String execute(Player player) {
        return "";
    }

    /**
     * Executes the specified action based on the player's interaction with the lever event.
     *
     * @param player the player who is triggering the event
     * @param choice the choice made by the player during the event
     * @return a string representing the outcome or result of the action based on the player's choice
     */
    @Override
    public String execute(Player player, int choice) {
        return "";
    }
}
