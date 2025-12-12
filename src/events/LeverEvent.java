package events;

import player.Player;

public class LeverEvent extends EnigmaEvent{

    /**
     * Constructs a LeverEvent with a specific description and the correct choice index.
     *
     * @param description the text description of the lever event
     * @param correctChoice the index of the correct choice for solving the lever event
     */
    public LeverEvent(String description, int correctChoice) {
        super(description, correctChoice);
    }

    /**
     * Adds a new choice to the list of available choices for this LeverEvent.
     * The provided choice must not be null or empty.
     *
     * @param choice the new choice to be added to the list of choices
     * @throws IllegalArgumentException if the choice is null or empty
     */
    @Override
    public void addChoice(String choice) {
        super.addChoice(choice);
    }


    /**
     * Executes the logic associated with a lever event involving the provided player.
     *
     * @param player the player interacting with the lever event
     * @return the outcome of the lever event as a string
     */
    @Override
    public String execute(Player player) {
        return "";
    }

    /**
     * Executes the event logic based on the specified player and their choice.
     *
     * @param player the player interacting with the event
     * @param choice the player's choice in the context of the event
     * @return a string representing the outcome of the event based on the player's choice
     */
    @Override
    public String execute(Player player, int choice) {
        return "";
    }
}
