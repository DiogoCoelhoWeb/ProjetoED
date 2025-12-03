package player;

import lists.ArrayUnorderedList;

public class PlayerManager {
    private ArrayUnorderedList<Player> players;

    /**
     * Constructs a new PlayerManager instance.
     * Initializes an empty unordered list to hold Player objects.
     */
    public PlayerManager() {
        this.players = new ArrayUnorderedList<>();
    }

    /**
     * Adds a player to the list of players. This method ensures that no more than
     * four players can be added. If the limit is exceeded, an exception is thrown.
     *
     * @param player the player to be added to the list
     * @throws IllegalArgumentException if the number of players exceeds four
     */
    public void addPlayer(Player player) {
        if (this.players.size() == 4){
            throw new IllegalArgumentException("Cannot add more than 4 players.");
        }
        this.players.addToRear(player);
    }

    /**
     * Retrieves the list of players managed by this instance.
     *
     * @return an ArrayUnorderedList of Player objects currently managed
     */
    public ArrayUnorderedList<Player> getPlayers() {
        return this.players;
    }
}
