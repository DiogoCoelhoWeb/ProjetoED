package demos;

import events.EnigmaEvent;
import events.GoBackEvent;
import events.LoseTurnEvent;
import graph.Graph;
import player.Player;
import rooms.MapLocations;
import rooms.Room;

import java.util.Iterator;

public class GraphDemo {
    public static void main(String[] args) {
        System.out.println("--- Graph Demo ---");



        // 1. Create the graph
        Graph<MapLocations> labyrinth = new Graph<>();

        // 2. Create rooms (which are a type of MapLocations)
        Room entrance = new Room("Entrance Hall", null);
        Room library = new Room("Library", null);
        Room armory = new Room("Armory", new EnigmaEvent("ooh",1)); // Event inside a room
        Room exit = new Room("Exit", null);

        Player pl = new Player("Player",entrance);
        // 3. Add rooms as vertices
        labyrinth.addVertex(entrance);
        labyrinth.addVertex(library);
        labyrinth.addVertex(armory);
        labyrinth.addVertex(exit);

        System.out.println("Initial rooms (vertices) added. Graph size: " + labyrinth.size());

        // 4. Connect rooms. This will create Corridor vertices implicitly.
        // Path 1: Entrance -> Library (no event corridor)
        labyrinth.addEdge(entrance, library);
        
        // Path 2: Library -> Armory (corridor with a GoBackEvent)
        labyrinth.addEdge(library, armory, new GoBackEvent());
        
        // Path 3: Armory -> Exit
        labyrinth.addEdge(armory, exit, new GoBackEvent());

        // Path 4: A direct but longer path from Entrance to Exit
        labyrinth.addEdge(entrance, exit);

        System.out.println("Corridors added. Graph size now includes rooms and corridors: " + labyrinth.size());
        System.out.println("Is the graph connected? " + labyrinth.isConnected());

        // 5. Demonstrate Traversals from the Entrance
        System.out.println("\n--- BFS Traversal from Entrance ---");
        Iterator<MapLocations> bfs = labyrinth.iteratorBFS(entrance);
        while (bfs.hasNext()) {
            MapLocations location = bfs.next();

            System.out.println("Visited: " + location.getName() + " (Type: " + location.getType() + ")");
        }

        System.out.println("\n--- DFS Traversal from Entrance ---");
        Iterator<MapLocations> dfs = labyrinth.iteratorDFS(entrance);
        while (dfs.hasNext()) {
            MapLocations location = dfs.next();
            pl.moveTo(location);
            try {
                location.getEvent().execute(pl);
            } catch (Exception e) {

            }
            System.out.println("Visited: " + location.getName() + " (Type: " + location.getType() + ")");
        }

        // 6. Demonstrate Shortest Path
        System.out.println("\n--- Shortest Path from Entrance to Exit ---");
        Iterator<MapLocations> path = labyrinth.iteratorShortestPath(entrance, exit);
        if (!path.hasNext()) {
            System.out.println("No path found.");
        } else {
            System.out.print("Path: ");
            while (path.hasNext()) {
                System.out.print(path.next().getName());
                if (path.hasNext()) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }
}
