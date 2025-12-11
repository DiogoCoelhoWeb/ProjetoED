package events;

import player.Player;

public class SwapEvent extends ChoiceEvent {

    public SwapEvent() {
        super("You have stepped into a dimensional rift! You will be swapped with another player.");
    }

    @Override
    public String execute(Player player) {
        return "The world spins around you...";
    }

    @Override
    public String execute(Player player, int choice) {
        return execute(player);
    }
}
