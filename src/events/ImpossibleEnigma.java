package events;

import player.Player;

public class ImpossibleEnigma extends ChoiceEvent{

    private String response;

    /**
     * Constructs an ImpossibleEnigma event with the specified description
     * and response message. This event represents a scenario where no
     * correct solution can be found, leaving the player unaffected and
     * in their current location.
     *
     * @param description the textual description of the event
     * @param response the response associated with the event when encountered by a player
     */
    public ImpossibleEnigma(String description,String response) {
        super(description);
        this.response=response;
    }

    /**
     * Retrieves the response associated with the event.
     *
     * @return the response as a String
     */
    public String getResponse() {
        return response;
    }

    /**
     * Executes the logic associated with an impossible enigma event where no correct choice is available.
     * The player remains in their current location without penalties, and the game proceeds as usual.
     *
     * @param player the player encountering the impossible enigma
     * @return a string describing the event and confirming the player's continuation in their current location
     */
    @Override
    public String execute(Player player) {
        // No penalties for an impossible enigma, player just stays in the current room.
        // The game loop will naturally proceed to the next player or turn.
        return player.getUsername() + " encountered an impossible enigma! No correct choice could be made, so they remain in their current location. " + getDescription();
    }

    /**
     * Executes the provided choice for the player encountering the impossible enigma.
     * This method effectively calls {@link #execute(Player)} as the choice does not impact the outcome.
     *
     * @param player the player encountering the enigma
     * @param choice the choice made by the player, which does not affect the result
     * @return a message describing the player's encounter with the impossible enigma
     */
    public  String execute(Player player,int choice){
        return execute(player);
    }
}
