package map;

import events.BuffDebuffEvent;
import graph.NetworkGraph;
import lists.ArrayUnorderedList;
import rooms.MapLocations;


public class Map {

    private NetworkGraph<MapLocations> graph;
    private String name;
    private ArrayUnorderedList<MapLocations> startLocation; // Adicionado para saber onde começar a iterar

    /**
     * Constructs a Map object with the specified name and initializes the
     * associated graph and start location list.
     *
     * @param name the name of the map
     */
    public Map(String name) {
        this.name = name;
        this.graph = new NetworkGraph<>();
        this.startLocation = new ArrayUnorderedList<>();
    }

    /**
     * Retrieves the name of the map.
     *
     * @return a string representing the name of the map
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Retrieves the graph representing the network of map locations.
     *
     * @return a NetworkGraph instance containing the map locations and their connections
     */
    public NetworkGraph<MapLocations> getGraph() {
        return this.graph;
    }
    
    /**
     * Adds a location to the map. If the location is marked as a start location,
     * it is added to the list of starting locations. Additionally, the location
     * is added as a vertex in the underlying graph structure of the map.
     *
     * @param location the location to be added to the map
     */
    public void addLocation(MapLocations location) {
        if (location.isStart() ) {
            this.startLocation.addToRear(location);
        }
        this.graph.addVertex(location);
    }
    
    /**
     * Adds a connection (edge) between two map locations in the graph.
     *
     * @param from the starting map location of the connection
     * @param to the destination map location of the connection
     */
    public void addConnection(MapLocations from, MapLocations to) {
        this.graph.addEdge(from, to);
    }
    /**
     * Adds a connection between two map locations in the graph with a specified event.
     *
     * @param from  the starting map location of the connection
     * @param to    the destination map location of the connection
     * @param event the BuffDebuffEvent associated with the connection, providing
     *              specific effects or properties for the connection
     * @throws IllegalArgumentException if the provided event is not an instance
     *                                  of BuffDebuffEvent
     */
    public void addConnection(MapLocations from, MapLocations to, BuffDebuffEvent event) {
        this.graph.addEdge(from, to, event);
    }
    
    /**
     * Determines if there is a connection (edge) between two map locations in the graph.
     *
     * @param from the starting map location
     * @param to the target map location
     * @return true if there is a connection between the specified locations, false otherwise
     */
    public boolean veifyToVertex(MapLocations from, MapLocations to) {
        return this.graph.veifyToVertex(from, to);
    }

    public ArrayUnorderedList<MapLocations> getStartLocation() {
        return this.startLocation;
    }

}
