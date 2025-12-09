package events;

import player.Player;
import java.util.Random;

public class EnigmaEvent extends ChoiceEvent{

    private int correctChoice;

    public EnigmaEvent(String description, int correctChoice) {
        super(description);
        this.correctChoice = correctChoice;
    }

    public int getCorrectChoice() {
        return correctChoice;
    }

    @Override
    public String execute(Player player) {
        // For now, simulate a player choice
        int playerChoice = (int) (Math.random() * choices.size());

        if (playerChoice == this.correctChoice) {
            return player.getUsername() + " solved the enigma! " + getDescription();
        } else {
            player.addTurn(-1); // Penalty for wrong answer, e.g., lose a turn
            return player.getUsername() + " failed to solve the enigma and lost a turn! " + getDescription();
        }
    }

    public String execute(Player player, int choice) {
        if (choice == this.correctChoice) {
            return player.getUsername() + " solved the enigma! " ;
        } else {
            player.block(1); // Penalty for wrong answer, e.g., lose a turn
            return player.getUsername() + " failed to solve the enigma and lost a turn! ";
        }
    }
}
