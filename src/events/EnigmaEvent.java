package events;

import player.Bot;
import player.Player;
import java.util.Random;

public class EnigmaEvent extends ChoiceEvent{

    private int correctChoice;

    /**
     * Constructs an EnigmaEvent with a specific description and the correct choice index.
     *
     * @param description the text description of the enigma event
     * @param correctChoice the index of the correct choice for solving the enigma
     */
    public EnigmaEvent(String description, int correctChoice) {
        super(description);
        this.correctChoice = correctChoice;
    }

    /**
     * Retrieves the correct choice for the enigma event.
     *
     * @return the index of the correct choice as an integer
     */
    public int getCorrectChoice() {
        return correctChoice;
    }

    /**
     * Executes the enigma event for the given bot, determining whether the bot
     * solved the enigma or failed based on a random choice.
     *
     * @param bot the player attempting to solve the enigma
     * @return a message indicating whether the player successfully solved the enigma
     *         or failed, along with the enigma's description
     */
    @Override
    public String execute(Player bot) {
        int playerChoice = (int) (Math.random() * choices.size());

        return execute(bot, playerChoice);
    }

    /**
     * Executes the enigma-solving attempt for the specified player based on their choice.
     * Determines whether the player's choice matches the correct choice and applies consequences accordingly.
     *
     * @param player the player attempting to solve the enigma
     * @param choice the player's selected choice for solving the enigma
     * @return a descriptive message indicating whether the player solved the enigma or failed and received a penalty
     */
    public String execute(Player player, int choice) {
        if (choice == this.correctChoice) {
            return player.getUsername() + " solved the enigma! " ;
        } else {
            player.block(1);
            return player.getUsername() + " failed to solve the enigma and lost a turn! ";
        }
    }
}
