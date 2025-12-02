package player;

import graph.NetworkGraph;
import lists.ArrayUnorderedList;
import rooms.MapLocations;

import java.util.Iterator;

public class Bot extends Player {

    private MapLocations previousLocation;

    public Bot(String username, MapLocations startingLocation) {
        super(username, startingLocation);
        this.previousLocation = null;
    }


    public void moveTo(MapLocations newLocation, NetworkGraph<MapLocations> graph) {
        // Store current as previous before moving
        if (this.getCurrentLocation() != newLocation) {
            this.previousLocation = this.getCurrentLocation();
        }
        super.moveTo(newLocation, graph);
    }

    /**
     * Decides the next move for the bot based on available connections.
     * Prioritizes exploring new rooms over returning to the previous one.
     *
     * @param graph The game map
     * @return The target MapLocations to move to
     */
    public MapLocations chooseMove(NetworkGraph<MapLocations> graph) {
        ArrayUnorderedList<MapLocations> allNeighbors = new ArrayUnorderedList<>();

        // Use BFS to find connected nodes.
        // The iterator returns the start node first, then its neighbors.
        // We filter using veifyToVertex to ensure they are direct neighbors.
        Iterator<MapLocations> it = graph.iteratorBFS(this.getCurrentLocation());

        if (it.hasNext()) it.next(); // Skip self

        while (it.hasNext()) {
            MapLocations candidate = it.next();
            // Since BFS traverses the whole graph, we check if it's directly connected
            if (graph.veifyToVertex(this.getCurrentLocation(), candidate)) {
                allNeighbors.addToRear(candidate);
            }
        }

        ArrayUnorderedList<MapLocations> sensibleNeighbors = new ArrayUnorderedList<>();

        // Filter out going back
        Iterator<MapLocations> neighborIt = allNeighbors.iterator();
        while (neighborIt.hasNext()) {
            MapLocations neighbor = neighborIt.next();
            if (neighbor != previousLocation) {
                sensibleNeighbors.addToRear(neighbor);
            }
        }

        ArrayUnorderedList<MapLocations> pool = sensibleNeighbors.isEmpty() ? allNeighbors : sensibleNeighbors;

        if (pool.isEmpty()) return null;

        int count = pool.size();
        int randomIndex = (int) (Math.random() * count);

        neighborIt = pool.iterator();
        MapLocations chosen = null;
        for(int i=0; i<=randomIndex; i++) {
            chosen = neighborIt.next();
        }

        return chosen;
    }
}
