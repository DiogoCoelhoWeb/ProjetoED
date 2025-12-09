package demos;

import events.ChoiceEventManager;
import events.LoseTurnEvent;
import files.EnigmaLoader;
import game.GameLoop;
import map.Map;
import player.Bot;
import player.Player;
import rooms.Room;
import rooms.Treasure;

public class GameLoopBotDemo {
    public static void main(String[] args) {
        // 1. Create the Map
        Map dungeonMap = new Map("Dungeon of Doom (Bot Edition)");
        ChoiceEventManager choiceEventManager = EnigmaLoader.loadEnigmas();

        // 2. Create Locations
        Room entrance = new Room("Entrance Hall", null, true);
        Room library = new Room("Ancient Library", choiceEventManager.getRandomChoiceEvent()); 
        Room study = new Room("Wizard's Study", null);
        Room darkTunnel = new Room("Dark Tunnel", null);
        Room armory = new Room("Abandoned Armory", null);
        Room prisonCell = new Room("Damp Prison Cell", null);
        Treasure treasureRoom = new Treasure("Treasure Chamber", choiceEventManager.getRandomChoiceEvent());

        // 3. Add Locations
        dungeonMap.addLocation(entrance);
        dungeonMap.addLocation(library);
        dungeonMap.addLocation(study);
        dungeonMap.addLocation(darkTunnel);
        dungeonMap.addLocation(armory);
        dungeonMap.addLocation(prisonCell);
        dungeonMap.addLocation(treasureRoom);

        // 4. Connect Locations
        dungeonMap.addConnection(entrance, library);
        dungeonMap.addConnection(library, study);
        dungeonMap.addConnection(study, treasureRoom);
        dungeonMap.addConnection(entrance, darkTunnel, new LoseTurnEvent());
        dungeonMap.addConnection(darkTunnel, armory);
        dungeonMap.addConnection(armory, prisonCell);
        dungeonMap.addConnection(armory, treasureRoom, new LoseTurnEvent());

        // 5. Initialize GameLoop
        GameLoop gameLoop = new GameLoop(dungeonMap);

        // 6. Add Players (Human and Bot)
        Player human = new Player("HumanHero", entrance);
        Bot bot = new Bot("RoboExplorer", entrance);
        
        gameLoop.getPlayerManager().addPlayer(human);
        gameLoop.getPlayerManager().addPlayer(bot);

        // 7. Start Game
        System.out.println("Starting GameLoop Bot Demo...");
        System.out.println("You are 'HumanHero'. 'RoboExplorer' is the bot.");
        gameLoop.start();
    }
}
