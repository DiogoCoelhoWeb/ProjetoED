package events;

import player.Player;

public class ImpossibleEnigma extends ChoiceEvent{

    private String response;

    /**
     * Constructs an ImpossibleEnigma with a specific description and a predefined response.
     *
     * @param description the text description of the enigma event
     * @param response the response associated with the impossible enigma
     */
    public ImpossibleEnigma(String description,String response) {
        super(description);
        this.response=response;
    }

    /**
     * Retrieves the response associated with the event.
     *
     * @return the response of the event as a String
     */
    public String getResponse() {
        return response;
    }

    /**
     * Executes the event logic for an impossible enigma encountered by the player.
     * This method constructs a message indicating that the player remains in their
     * current location due to the impossibility of the enigma.
     *
     * @param player the player encountering the impossible enigma
     * @return a message detailing the outcome of the encounter
     */
    @Override
    public String execute(Player player) {
        // No penalties for an impossible enigma, player just stays in the current room.
        // The game loop will naturally proceed to the next player or turn.
        return player.getUsername() + " encountered an impossible enigma!  " + getDescription();
    }

    /**
     * Executes the event logic for the given player. This method resolves the outcome
     * of the event based on the player's interaction and returns a string that
     * describes the result of the execution.
     *
     * @param player The player interacting with the event.
     * @param choice An integer representing the choice made by the player.
     *               This parameter is not used within the method.
     * @return A string describing the result of the event execution for the player.
     */
    public  String execute(Player player,int choice){
        return execute(player);
    }
}
