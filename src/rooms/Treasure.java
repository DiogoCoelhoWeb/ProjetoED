package rooms;

import events.ChoiceEvent;

public class Treasure extends MapLocations{

    /**
     * Constructs a Treasure instance representing a treasure location on the map.
     * A Treasure is a specific type of map location that can trigger a choice-based
     * event when interacted with by players.
     *
     * @param name the name of the treasure location
     * @param event the ChoiceEvent associated with the treasure location
     */
    public Treasure(String name, ChoiceEvent event) {
        super(name, event);
    }

    /**
     * Determines if this location is the starting point in the map.
     *
     * @return false, as this location is not the starting point.
     */
    @Override
    public boolean isStart() {
        return false;
    }

    /**
     * Retrieves the type of the map location. This implementation returns
     * the specific type of the location as "Treasure Room".
     *
     * @return the string "Treasure Room", indicating the type of this map location
     */
    @Override
    public RoomType getType() {
        return RoomType.TREASURE_ROOM;
    }
}
