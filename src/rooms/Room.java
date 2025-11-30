package rooms;

import events.ChoiceEvent;

public class Room extends MapLocations{


    public Room(String name, ChoiceEvent event) {
        super(name, event);
    }

    @Override
    public String getType() {
        return "Room";
    }
}
