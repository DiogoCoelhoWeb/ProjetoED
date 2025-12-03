package rooms;

import events.ChoiceEvent;

public class Room extends MapLocations {

    private boolean isStart;

    /**
     * Constructs a Room instance with a specified name and associated ChoiceEvent.
     * By default, the Room is not set as a starting location.
     *
     * @param name the name of the room
     * @param event the ChoiceEvent associated with this room, which defines
     *              an interactive event occurring in the room
     */
    public Room(String name, ChoiceEvent event) {
        this(name, event, false);
    }

    /**
     * Constructs a Room instance, which is a specific type of map location
     * that may act as the starting point of the map and is associated with
     * a choice-based event.
     *
     * @param name the name of the room
     * @param event the ChoiceEvent associated with the room
     * @param isStart a boolean value indicating whether this room is the starting point
     */
    public Room(String name, ChoiceEvent event, boolean isStart) {
        super(name, event);
        this.isStart = isStart;
    }

    /**
     * Checks if this room is the starting point on the map.
     *
     * @return true if this room is the starting point, false otherwise
     */
    public boolean isStart() {
        return this.isStart;
    }

    /**
     * Retrieves the type of the map location. This implementation returns
     * the specific type of the location as "Room".
     *
     * @return the string "Room", indicating the type of this map location
     */
    @Override
    public String getType() {
        return "Room";
    }
}
