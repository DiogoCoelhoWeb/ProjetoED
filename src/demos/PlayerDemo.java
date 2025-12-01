package demos;

import player.Player;
import rooms.Room;

public class PlayerDemo {
    public static void main(String[] args) {
        System.out.println("--- Player Demo ---");

        // 1. Create some rooms for the player to move between
        Room startRoom = new Room("Starting Room", null);
        Room nextRoom = new Room("Next Room", null);
        Room finalRoom = new Room("Final Room", null);

        // 2. Create a player
        Player player = new Player("Aris", startRoom);
        System.out.println(player.getUsername() + " has been created in " + player.getCurrentLocation().getName());

        // 3. Demonstrate movement
        System.out.println("\n--- Movement Demo ---");
        System.out.println("Current room: " + player.getCurrentLocation().getName());

        System.out.println("Moving to 'Next Room'...");
        player.moveTo(nextRoom);
        System.out.println("Current room: " + player.getCurrentLocation().getName());

        System.out.println("Moving to 'Final Room'...");
        player.moveTo(finalRoom);
        System.out.println("Current room: " + player.getCurrentLocation().getName());
        
        System.out.println("Using goBack()...");
        player.goBack();
        System.out.println("Current room after goBack(): " + player.getCurrentLocation().getName());

        System.out.println("Using goBack() again...");
        player.goBack();
        System.out.println("Current room after second goBack(): " + player.getCurrentLocation().getName());
        
        System.out.println("Trying to go back from the starting room...");
        player.goBack(); // Should not change the room

        // 4. Demonstrate turn management
        System.out.println("\n--- Turn Management Demo ---");
        System.out.println("Initial turns: " + player.getTurns());
        
        System.out.println("Adding 3 turns...");
        player.addTurn(3);
        System.out.println("Turns after addTurn(3): " + player.getTurns());

        System.out.println("Using a turn...");
        player.useTurn();
        System.out.println("Turns after useTurn(): " + player.getTurns());

        System.out.println("Resetting turns...");
        player.resetTurns();
        System.out.println("Turns after resetTurns(): " + player.getTurns());
        
        // 5. Demonstrate blocking
        System.out.println("\n--- Blocking Demo ---");
        System.out.println("Is player blocked? " + player.isBlocked());

        System.out.println("Blocking player for 2 turns...");
        player.block(2);
        System.out.println("Is player blocked now? " + player.isBlocked());

        System.out.println("Ending turn 1 while blocked...");
        player.endTurn();
        System.out.println("Is player still blocked? " + player.isBlocked());

        System.out.println("Ending turn 2 while blocked...");
        player.endTurn();
        System.out.println("Is player still blocked? " + player.isBlocked());

        System.out.println("\nPlayer demo finished.");
    }
}
