package demos;

import events.EnigmaEvent;
import events.ImpossibleEnigma;
import graph.NetworkGraph;
import rooms.MapLocations;
import rooms.Room;

import java.util.Iterator;

public class MapDemo {
    public static void main(String[] args) {
        NetworkGraph<MapLocations> map = new NetworkGraph<>();

        Room entery = new Room("Entrance", null);
        Room armory = new Room("Armory", new EnigmaEvent("what is1+1",3));
        Room hall = new Room("Hall", new EnigmaEvent("what is1+1",3));
        Room kitchen = new Room("Kitchen", null);
        Room diningRoom = new Room("Dining Room", null);
        Room bedroom = new Room("Bedroom", null);
        Room bathroom = new Room("Bathroom", new ImpossibleEnigma("chuck norris can solve math problems"," "));
        Room tresure = new Room("tresure", null);

        map.addVertex(entery);
        map.addVertex(armory);
        map.addVertex(hall);
        map.addVertex(kitchen);
        map.addVertex(diningRoom);
        map.addVertex(bedroom);
        map.addVertex(bathroom);
        map.addVertex(tresure);

        map.addEdge(entery,hall);
        map.addEdge(hall,armory);
        map.addEdge(hall,kitchen);
        map.addEdge(kitchen,diningRoom);
        map.addEdge(diningRoom,bedroom);
        map.addEdge(bedroom,bathroom);
        map.addEdge(bedroom,tresure);


        Iterator<MapLocations> it = map.iteratorDFS(entery);
       while(it.hasNext()){
            System.out.println(it.next().getName());
            System.out.println("\n****** Events *****");
            try {
                System.out.println(it.next().getEvent().getDescription());
            } catch (NullPointerException e) {
                System.out.println("No event");
            }
        }
    }
}
