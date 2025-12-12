package player;

import events.ChoiceEvent;
import events.EnigmaEvent;
import graph.NetworkGraph;
import lists.ArrayUnorderedList;
import rooms.MapLocations;

import java.util.Iterator;

public class Bot extends Player {

    private MapLocations previousLocation;
    private final double SHORTEST_PATH_PROBABILITY = 0.20;
    private final double ENIGMA_SUCCESS_PROBABILITY = 0.45;

    /**
     * Constructs a Bot object with a specified username and starting location.
     * A Bot is a type of player with additional capabilities for automated
     * decision-making during gameplay.
     *
     * @param username the username of the bot
     * @param startingLocation the initial location where the bot starts
     */
    public Bot(String username, MapLocations startingLocation) {
        super(username, startingLocation);
        this.previousLocation = null;
    }


    /**
     * Decides the next move for the bot based on available connections.
     * With 20% probability, it tries to move towards the Treasure Room using the shortest path.
     * Otherwise, prioritizes exploring new rooms over returning to the previous one using random logic.
     *
     * @param graph The game map
     * @return The target MapLocations to move to
     */
    public MapLocations chooseMove(NetworkGraph<MapLocations> graph) {
        // 20% chance to use Shortest Path to Treasure
        if (Math.random() < SHORTEST_PATH_PROBABILITY) {
            MapLocations treasure = findTreasure(graph);
            if (treasure != null) {
                Iterator<MapLocations> pathIt = graph.iteratorShortestPath(this.getCurrentLocation(), treasure);
                if (pathIt.hasNext()) {
                    pathIt.next(); // Skip current location (start of path)
                    if (pathIt.hasNext()) {
                        return pathIt.next(); // Return the next step
                    }
                }
            }
        }

        // Fallback to existing logic (Random / Exploration)
        ArrayUnorderedList<MapLocations> allNeighbors = new ArrayUnorderedList<>();

        // Use BFS to find connected nodes.
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

    /**
     * Finds and returns the location of the treasure room in the given network graph.
     * This method uses a breadth-first search (BFS) strategy starting from the current location
     * to look for a map location of type "Treasure Room".
     *
     * @param graph the network graph representing the game map, containing map locations as vertices
     * @return the MapLocations object representing the treasure room if found, or null if no treasure room exists in the graph
     */
    private MapLocations findTreasure(NetworkGraph<MapLocations> graph) {
        // Note: This relies on accessing vertices. Since we only have the graph object,
        // we can iterate via BFS/DFS from start to find "Treasure Room" or assume we can access an iterator.
        // Assuming we can iterate over all reachable nodes.
        Iterator<MapLocations> it = graph.iteratorBFS(this.getCurrentLocation()); 
        // Or better, just BFS the whole graph if connected.
        
        while(it.hasNext()){
            MapLocations loc = it.next();
            if("Treasure Room".equals(loc.getType())){
                return loc;
            }
        }
        return null;
    }

    /**
     * Solves an enigma with a fixed success probability.
     * @param event The event to solve.
     * @return The chosen answer index.
     */
    public int solveEnigma(ChoiceEvent event) {
        if (event instanceof EnigmaEvent) {
             if (Math.random() < ENIGMA_SUCCESS_PROBABILITY) {
                 return ((EnigmaEvent) event).getCorrectChoice();
             }
        }

        // Random choice if not EnigmaEvent or probability failed
        // Avoid division by zero if choices is empty (though should not happen for events requiring choice)
        if (event.getChoices().isEmpty()) return -1;
        
        return (int) (Math.random() * event.getChoices().size());
    }
}
