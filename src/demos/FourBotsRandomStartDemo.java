package demos;

import events.ChoiceEventManager;
import files.EnigmaLoader;
import files.MapLoader;
import game.GameLoop;
import lists.ArrayUnorderedList;
import map.Map;
import player.Bot;
import rooms.MapLocations;


import java.util.Iterator;


public class FourBotsRandomStartDemo {
    public static void main(String[] args) {
        System.out.println("=== Demo: 4 Bots with Random Start Locations ===");

        ChoiceEventManager eventManager = EnigmaLoader.loadEnigmas();

        MapLoader loader = new MapLoader();
        Map arenaMap = loader.loadMap("data/maps/UserMap.json");

        // 5. Initialize GameLoop
        GameLoop gameLoop = new GameLoop(arenaMap);

        ArrayUnorderedList<MapLocations> startLocations = arenaMap.getStartLocation();

        int count = startLocations.size();
        System.out.println("Found " + count + " start locations.");
        if (count < 4){
            System.out.println("Not enough start locations to create 4 bots.");
            return;
        }
        Iterator<MapLocations> it;

        MapLocations[] locationsArray = new MapLocations[count];
        it = startLocations.iterator();
        for (int i = 0; i < count; i++) {
            locationsArray[i] = it.next();
        }

        //  Fisher-Yates Shuffle
        for (int i = locationsArray.length - 1; i > 0; i--) {
            int index = (int) (Math.random() * (i + 1));

            MapLocations temp = locationsArray[index];
            locationsArray[index] = locationsArray[i];
            locationsArray[i] = temp;
        }


        // 7. Create 4 Bots assigning random starts
        Bot bot1 = new Bot("Alpha", locationsArray[0]);
        Bot bot2 = new Bot("Bravo", locationsArray[1]);
        Bot bot3 = new Bot("Charlie", locationsArray[2]);
        Bot bot4 = new Bot("Delta", locationsArray[3]);

        System.out.println("Start Assignments:");
        System.out.println("Bot Alpha starts at: " + locationsArray[0].getName());
        System.out.println("Bot Bravo starts at: " + locationsArray[1].getName());
        System.out.println("Bot Charlie starts at: " + locationsArray[2].getName());
        System.out.println("Bot Delta starts at: " + locationsArray[3].getName());
        System.out.println("------------------------------------------------");

        // 8. Add Bots to Game
        gameLoop.getPlayerManager().addPlayer(bot1);
        gameLoop.getPlayerManager().addPlayer(bot2);
        gameLoop.getPlayerManager().addPlayer(bot3);
        gameLoop.getPlayerManager().addPlayer(bot4);

        // 9. Start
        gameLoop.start();
    }
}
