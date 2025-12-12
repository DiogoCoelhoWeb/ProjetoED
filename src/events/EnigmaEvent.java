package events;

import player.Player;
import java.util.Random;

public class EnigmaEvent extends ChoiceEvent{

    private int correctChoice;

    /**
     * Constructs an EnigmaEvent with the specified description and correct choice index.
     *
     * @param description the text description of the event
     * @param correctChoice the index of the correct choice for the enigma
     */
    public EnigmaEvent(String description, int correctChoice) {
        super(description);
        this.correctChoice = correctChoice;
    }

    /**
     * Retrieves the correct choice for the event.
     *
     * @return the integer representing the correct choice for the event
     */
    public int getCorrectChoice() {
        return correctChoice;
    }

    /**
     * Executes the enigma event and determines the outcome based on the player's random choice.
     *
     * @param player the player attempting to solve the enigma
     * @return a message indicating whether the player successfully solved the enigma or failed
     */
    @Override
    public String execute(Player player) {
        int playerChoice = (int) (Math.random() * choices.size());

        if (playerChoice == this.correctChoice) {
            return player.getUsername() + " solved the enigma! " + getDescription();
        } else {
            player.addTurn(-1);
            return player.getUsername() + " failed to solve the enigma and lost a turn! " + getDescription();
        }
    }

    /**
     * Executes the enigma-solving logic based on the player's choice and determines whether
     * the player solves the enigma or faces a penalty for an incorrect answer. Returns a message
     * indicating the outcome of the player's action.
     *
     * @param player the player attempting to solve the enigma
     * @param choice the player's chosen answer to the enigma
     * @return a string message that either congratulates the player or informs them of
     *         the penalty for an incorrect answer
     */
    public String execute(Player player, int choice) {
        if (choice == this.correctChoice) {
            return player.getUsername() + " solved the enigma! " ;
        } else {
            player.block(1); // Penalty for wrong answer, e.g., lose a turn
            return player.getUsername() + " failed to solve the enigma and lost a turn! ";
        }
    }
}
