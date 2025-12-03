package map;

import events.BuffDebuffEvent;
import graph.NetworkGraph;
import lists.ArrayUnorderedList;
import rooms.MapLocations;

import java.util.Iterator;

public class Map {

    private NetworkGraph<MapLocations> graph;
    private String name;
    private ArrayUnorderedList<MapLocations> startLocation; // Adicionado para saber onde começar a iterar

    public Map(String name) {
        this.name = name;
        this.graph = new NetworkGraph<>();

    }

    public String getName() {
        return this.name;
    }
    
    public NetworkGraph<MapLocations> getGraph() {
        return this.graph;
    }
    
    public void addLocation(MapLocations location) {
        if (location.isStart() ) {
            this.startLocation.addToRear(location);
        }
        this.graph.addVertex(location);
    }
    
    public void addConnection(MapLocations from, MapLocations to) {
        this.graph.addEdge(from, to);
    }
    public void addConnection(MapLocations from, MapLocations to, BuffDebuffEvent event) {
        this.graph.addEdge(from, to, event);
    }
    
    public boolean veifyToVertex(MapLocations from, MapLocations to) {
        return this.graph.veifyToVertex(from, to);
    }
    
    public void printGraph() {
        Iterator<MapLocations> it = graph.iteratorDFS(this.startLocation.get(0));
        while (it.hasNext()) {
            System.out.println(it.next().getName()); // Assumindo que MapLocations tem getName()
        }
    }


}
