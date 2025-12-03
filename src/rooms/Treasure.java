package rooms;

import events.ChoiceEvent;

public class Treasure extends MapLocations{

    public Treasure(String name, ChoiceEvent event) {
        super(name, event);
    }

    @Override
    public boolean isStart() {
        return false;
    }

    @Override
    public String getType() {
        return "Treasure Room";
    }
}
