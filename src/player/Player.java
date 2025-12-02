package player;

import graph.Graph;
import rooms.MapLocations;
import stack.LinkedStack;

public class Player {

    private static int idCounter = 0;

    private final int id;
    private final String username;
    private int turns;

    private MapLocations currentLocation;
    private final LinkedStack<MapLocations> pathHistory;
    private int blockedTurns;


    /**
     * Constructs a Player object with a specified username and starting room.
     * This constructor initializes the player's unique ID, username, initial number of turns,
     * blocked turns (set to zero), the starting room as the current room, and their path history.
     *
     * @param username the username of the player
     * @param startingLocation the initial Room where the player starts
     */
    public Player(String username, MapLocations startingLocation) {
        this.id = idCounter++;
        this.username = username;
        this.turns = 1;
        this.blockedTurns = 0;

        this.currentLocation = startingLocation;
        this.pathHistory = new LinkedStack<>();
        this.pathHistory.push(startingLocation);
    }

    /**
     * Retrieves the unique identifier of the player.
     *
     * @return the unique ID of the player as an integer
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the username of the player.
     *
     * @return the player's username as a string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the current number of turns available to the player.
     *
     * @return the total number of turns the player currently has
     */
    public int getTurns() {
        return turns;
    }

    /**
     * Adds the specified number of turns to the player's current turn count.
     *
     * @param numTurns the number of turns to be added; can be a positive or negative integer
     */
    public void addTurn(int numTurns) {
        this.turns = this.turns + numTurns;
    }

    /**
     * Decreases the number of turns available to the player by one, if the player has
     * at least one turn remaining. If the player has zero turns, this method does nothing.
     *
     * This method ensures that the player's turn count does not drop below zero.
     */
    public void useTurn() {
        if (this.turns > 0) {
            this.turns--;
        }
    }

    /**
     * Resets the player's turn count to the initial default value of 1.
     *
     * This method is typically invoked at the end of a turn cycle or when
     * the player's turns need to be reset for consistent gameplay mechanics.
     */
    public void resetTurns() {
        this.turns = 1;
    }

    /**
     * Retrieves the current room where the player is located.
     *
     * @return the current room of the player
     */
    public MapLocations getCurrentLocation() {
        return currentLocation;
    }


    /**
     * Moves the player to the specified new room. This method updates the player's
     * current room, adds the new room to the player's path history, and notifies
     * the player of the room change through a message.
     *
     * @param newLocation the new room the player is moving to
     */
    public void moveTo(MapLocations newLocation, Graph<MapLocations> graph) {
        if(graph.veifyToVertex(this.currentLocation, newLocation)){
            this.currentLocation = newLocation;
            this.pathHistory.push(this.currentLocation);
            System.out.println(this.username + " moved to " + newLocation.getName());
        }
        else {
            System.out.print( this.username + " cant move to " + newLocation.getName());
        }
    }

    /**
     * Navigates the player back to the previous room in their movement history, if possible.
     * Updates the current room to the previous room and notifies the player.
     * If the player cannot go back (e.g., they are in the starting room), an appropriate message is displayed.
     *
     * @return the current room of the player after attempting to go back
     */
    public MapLocations goBack() {
        if (pathHistory.size() > 1) {
            pathHistory.pop();
            this.currentLocation = pathHistory.peek();
            System.out.println(username + ": " +  this.currentLocation.getName());
        } else {
            System.out.println(username + " cant go back");
        }
        return this.currentLocation;
    }


    /**
     * Checks whether the player is currently blocked from taking actions.
     *
     * @return true if the player has remaining blocked turns; false otherwise
     */
    public boolean isBlocked() {
        return this.blockedTurns > 0;
    }


    /**
     * Blocks the player from taking actions for a specified number of turns.
     * Updates the player's `blockedTurns` and displays a message indicating the
     * username and the number of turns they are blocked for.
     *
     * @param numTurns the number of turns the player will be blocked
     */
    public void block(int numTurns) {
        this.blockedTurns = numTurns;
        System.out.println(username + " its blocked for" + numTurns + " turns!");
    }


    /**
     * Ends the player's current turn and manages the blocked turn status if applicable.
     * If the player is currently blocked, the number of blocked turns is decremented.
     * A message is displayed informing the player how many turns remain until they can play again.
     * Resets the player's turn count at the end of the method.
     */
    public void endTurn() {
        if (this.blockedTurns > 0) {
            this.blockedTurns--;
            System.out.println("Faltam " + this.blockedTurns + " turnos para " + username + " poder jogar.");
        }

        resetTurns();
    }

}