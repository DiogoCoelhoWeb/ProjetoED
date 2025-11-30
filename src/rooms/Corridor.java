package rooms;

import events.BuffDebuffEvent;

public class Corridor extends MapLocations {

    /**
     * Constructs a Corridor instance with the specified name and associated BuffDebuffEvent.
     * This constructor initializes the name and event properties for the Corridor object.
     *
     * @param name the name of the corridor
     * @param event the BuffDebuffEvent associated with this corridor
     */
    public Corridor(String name, BuffDebuffEvent event) {
        super(name,event);
    }

    /**
     * Constructs a Corridor instance with the specified name. This constructor
     * initializes the name property of the Corridor, representing its unique identifier or label.
     *
     * @param name the name of the corridor
     */
    public Corridor(String name) {
        super(name);
    }

    /**
     * Retrieves the type of this map location.
     *
     * @return the type of the map location as the string "Corridor"
     */
    @Override
    public String getType() {
        return "Corridor";
    }
}
