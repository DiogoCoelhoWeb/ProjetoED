package demos;

import events.ChoiceEventManager;
import events.LoseTurnEvent;
import files.EnigmaLoader;
import game.GameLoop;
import map.Map;
import player.Player;
import rooms.Room;
import rooms.Treasure;
import events.EnigmaEvent;

public class GameLoopDemo {
    public static void main(String[] args) {
        // 1. Create the Map
        Map dungeonMap = new Map("Dungeon of Doom");
        ChoiceEventManager choiceEventManager = EnigmaLoader.loadEnigmas();

        // 2. Create Locations
        // Entry (Start)
        Room entrance = new Room("Entrance Hall", null, true);

        // Path A: The Intellectual Path (Enigmas)
        Room library = new Room("Ancient Library", choiceEventManager.getRandomChoiceEvent()); // Enigma Room
        Room study = new Room("Wizard's Study", null);

        // Path B: The Physical Path (Traps/Corridor Events)
        Room darkTunnel = new Room("Dark Tunnel", null);
        Room armory = new Room("Abandoned Armory", null);

        // Dead End
        Room prisonCell = new Room("Damp Prison Cell", null);

        // Treasure Room
        Treasure treasureRoom = new Treasure("Treasure Chamber", choiceEventManager.getRandomChoiceEvent());

        // 3. Add Locations to Map
        dungeonMap.addLocation(entrance);
        dungeonMap.addLocation(library);
        dungeonMap.addLocation(study);
        dungeonMap.addLocation(darkTunnel);
        dungeonMap.addLocation(armory);
        dungeonMap.addLocation(prisonCell);
        dungeonMap.addLocation(treasureRoom);

        // 4. Connect Locations
        // Entrance -> Library (Normal)
        dungeonMap.addConnection(entrance, library);
        
        // Library -> Study (Normal)
        dungeonMap.addConnection(library, study);
        
        // Study -> Treasure (Win)
        dungeonMap.addConnection(study, treasureRoom);

        // Entrance -> Dark Tunnel (Corridor Event: Lose Turn)
        dungeonMap.addConnection(entrance, darkTunnel, new LoseTurnEvent());

        // Dark Tunnel -> Armory (Normal)
        dungeonMap.addConnection(darkTunnel, armory);

        // Armory -> Prison Cell (Dead End)
        dungeonMap.addConnection(armory, prisonCell);

        // Armory -> Treasure (Corridor Event: Lose Turn - High Risk Shortcut)
        dungeonMap.addConnection(armory, treasureRoom, new LoseTurnEvent());

        // 5. Initialize GameLoop
        GameLoop gameLoop = new GameLoop(dungeonMap);

        // 6. Add Player
        Player hero = new Player("Hero", entrance);
        Player hero2 = new Player("Sidekick", entrance);
        gameLoop.getPlayerManager().addPlayer(hero);
        gameLoop.getPlayerManager().addPlayer(hero2);

        // 7. Start Game
        System.out.println("Starting GameLoop Demo...");
        gameLoop.start();
    }
}
