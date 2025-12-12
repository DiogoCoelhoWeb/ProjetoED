package demos;

import events.ChoiceEventManager;
import files.EnigmaLoader;
import files.MapLoader;
import map.Map;
import rooms.MapLocations;

import java.util.Iterator;

public class MapLoaderDemo {

    public static void main(String[] args) {
        System.out.println("===  Map Loader Demo ===");

        // 1. Load Events
        ChoiceEventManager eventManager = EnigmaLoader.loadEnigmas();

        // 2. Load Map
        MapLoader loader = new MapLoader();
        Map map = loader.loadMap("data/maps/UserMap.json", eventManager);

        if (map != null) {
            System.out.println("\n Map loaded successfully!");
            System.out.println("Map Name: " + map.getName());
            
            System.out.println("\n--- Rooms ---");
            
            Object[] vertices = map.getGraph().getVertices();
            for (Object obj : vertices) {
                if (obj instanceof MapLocations) {
                    MapLocations loc = (MapLocations) obj;
                    if (loc.getEvent() != null) {
                         System.out.println("   Room Event: " + loc.getEvent().getDescription());
                    }
                    
                    // Print connections
                    try {
                        lists.ArrayUnorderedList<MapLocations> neighbors = map.getGraph().getNeighbors(loc);
                        Iterator<MapLocations> nit = neighbors.iterator();
                        while(nit.hasNext()) {
                            MapLocations neighbor = nit.next();
                            events.Event edgeEvent = map.getGraph().getEdgeWeight(loc, neighbor);
                            String edgeInfo = (edgeEvent != null) ? " [Event: " + edgeEvent.getDescription() + "]" : "";
                            System.out.println("     -> " + neighbor.getName() + edgeInfo);
                        }
                    } catch (Exception e) {
                        System.out.println("     (Error getting neighbors)");
                    }
                }
            }

        } else {
            System.out.println("Failed to load map.");
        }
    }
}
