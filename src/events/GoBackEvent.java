package events;

import player.Player;
import utils.Quotes;

import java.util.Random; // Keep Random for now, will replace with Math.random() if user requests.

public class GoBackEvent extends BuffDebuffEvent {



    public GoBackEvent() {
        super(Quotes.getRandomGoBackQuote());
    }

    public GoBackEvent(String description) {
        super(description);
    }

    @Override
    public String execute(Player player) {
        player.goBack();
        return player.getUsername() +": " + description;
    }
}
