package rooms;

import events.ChoiceEvent;

public class Room extends MapLocations {

    private boolean isStart;

    public Room(String name, ChoiceEvent event) {
        this(name, event, false);
    }

    public Room(String name, ChoiceEvent event, boolean isStart) {
        super(name, event);
        this.isStart = isStart;
    }

    public boolean isStart() {
        return this.isStart;
    }

    @Override
    public String getType() {
        return "Room";
    }
}
