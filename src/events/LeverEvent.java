package events;

import player.Player;

public class LeverEvent extends EnigmaEvent{

    public LeverEvent(String description, int correctChoice) {
        super(description, correctChoice);
    }

    @Override
    public void addChoice(String choice) {
        super.addChoice(choice);
    }


    @Override
    public String execute(Player player) {
        return "";
    }

    @Override
    public String execute(Player player, int choice) {
        return "";
    }
}
