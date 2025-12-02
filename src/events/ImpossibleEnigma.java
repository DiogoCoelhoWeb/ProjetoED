package events;

import player.Player;

public class ImpossibleEnigma extends ChoiceEvent{

    public ImpossibleEnigma(String description) {
        super(description);
    }

    @Override
    public String execute(Player player) {
        // No penalties for an impossible enigma, player just stays in the current room.
        // The game loop will naturally proceed to the next player or turn.
        return player.getUsername() + " encountered an impossible enigma! No correct choice could be made, so they remain in their current location. " + getDescription();
    }
    public  String execute(Player player,int choice){
        return execute(player);
    }
}
