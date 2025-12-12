package demos;

import events.BuffDebuffEvent;
import events.LoseTurnEvent;
import files.MapSave;
import map.Map;
import rooms.Room;
import events.EnigmaEvent;
import rooms.Treasure;

public class SaveMapDemo {
    public static void main(String[] args) {
        System.out.println("Starting SaveMapDemo...");

        // 1. Create a Map
        Map demoMap = new Map("SaveDemoMap");

        // 2. Create Rooms
        // Entrance (Start)
        Room entrance = new Room("Entrance", null, true);
        
        // Hall with an Enigma
        EnigmaEvent mathEnigma = new EnigmaEvent("What is 2+2?", 1); // 1 is index of correct choice
        mathEnigma.addChoice("3");
        mathEnigma.addChoice("4");
        mathEnigma.addChoice("5");
        
        Room hall = new Room("Hall", mathEnigma);
        
        // Kitchen
        Room kitchen = new Room("Kitchen", null);

        Treasure treasureRoom = new Treasure("Treasure Room", null);

        BuffDebuffEvent event = new LoseTurnEvent();

        // 3. Add locations to map
        // Note: The order might matter for ID generation, but MapSave uses internal IDs.
        demoMap.addLocation(entrance);
        demoMap.addLocation(hall);
        demoMap.addLocation(kitchen);
        demoMap.addLocation(treasureRoom);

        // 4. Connect rooms
        demoMap.addConnection(entrance, hall);
        demoMap.addConnection(hall, kitchen,event);
        demoMap.addConnection(kitchen, treasureRoom);

        // 5. Save the map
        MapSave mapSave = new MapSave();

        
        System.out.println("Saving map to: " + demoMap.getName() + ".json");
        mapSave.saveMap(demoMap );
        
        System.out.println("Map saved successfully!");
    }
}
