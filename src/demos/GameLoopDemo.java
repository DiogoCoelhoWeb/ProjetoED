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
        

        Room corridor = new Room("Dark Corridor", choiceEventManager.getRandomChoiceEvent()); // Answer: Piano


        // Treasure Room
        Treasure treasureRoom = new Treasure("Treasure Chamber", choiceEventManager.getRandomChoiceEvent());

        // 3. Add Locations to Map
        dungeonMap.addLocation(entrance);
        dungeonMap.addLocation(corridor);
        dungeonMap.addLocation(treasureRoom);

        // 4. Connect Locations
        dungeonMap.addConnection(entrance, corridor,new LoseTurnEvent());
        dungeonMap.addConnection(corridor, treasureRoom);

        // 5. Initialize GameLoop
        GameLoop gameLoop = new GameLoop(dungeonMap);

        // 6. Add Player
        Player hero = new Player("Hero", entrance);
        Player hero2 = new Player("Hero2", entrance);
        gameLoop.getPlayerManager().addPlayer(hero);
        gameLoop.getPlayerManager().addPlayer(hero2);

        // 7. Start Game
        System.out.println("Starting GameLoop Demo...");
        gameLoop.start();
    }
}
