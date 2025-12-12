package rooms;

import events.ChoiceEvent;
import events.Event;

public abstract class  MapLocations {

    private static int idCounter = 0;

    protected String name;
    protected ChoiceEvent event;
    protected int id;

    /**
     * Constructs a MapLocations object with the specified name and associated event.
     * This constructor initializes the name and event properties and assigns
     * a unique identifier to the object.
     *
     * @param name the name of the map location
     * @param event the event associated with this map location
     */
    public MapLocations(String name, ChoiceEvent event) {
        this.name = name;
        this.event = event;
        this.id = idCounter++;
    }


    /**
     * Retrieves the ChoiceEvent associated with this map location.
     *
     * @return the ChoiceEvent object linked to this map location, or null if no event is associated
     */
    public ChoiceEvent getEvent() {
        return this.event;
    }

    /**
     * Constructs a MapLocations instance with the specified name.
     *
     * @param name the name of the map location
     */
    public MapLocations(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public abstract boolean isStart();

    /**
     * Retrieves the type of the map location as a string. This method is
     * intended to be implemented by subclasses to specify their respective types.
     *
     * @return the type of the map location as a string
     */
    public abstract RoomType getType();
}
