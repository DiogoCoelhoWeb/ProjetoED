package menus;

import events.ChoiceEvent;
import map.Map;
import rooms.Room;
import rooms.RoomType;
import rooms.Treasure;

import java.lang.reflect.Type;

public class MapCreationMenu extends AbstractMenu{

    private Map map;

    public MapCreationMenu(String mapName) {
        this.map = new Map(mapName);
    }

    protected void displayMenu() {
        System.out.println("Map Creation Menu");
        System.out.println("1. Create Room");
        System.out.println("2. Link Rooms");
        //System.out.println("3. Visualize Map");
        System.out.println("3. Save");
    }

    public void runMenu() {
        boolean isValid = false;
        boolean hasSaved = false;

        do {
            printSeparator();
            displayMenu();
            String input = readInput("Please select an option: ");
            try {
                int option = Integer.parseInt(input);
                if (option >= 1 && option <= 3) {
                    isValid = true;
                    if (option == 3) {
                        hasSaved = true;
                        System.out.println();
                        System.out.println("Map saved successfully");
                        break;
                    }
                    executeOption(option);
                } else {
                    System.out.println();
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (!isValid || !hasSaved);
    }

    protected void executeOption(int option) {
        switch (option) {
            case 1:
                createRoom();
                break;
            case 2:
                linkRooms();
                break;
            //case 3:
                //visualizeMap();
                //break;
        }
    }

    private void createRoom() {
        boolean isStart = false;
        ChoiceEvent event;
        RoomTypeMenu roomTypeMenu = new RoomTypeMenu();
        ChoiceEventMenu choiceEventMenu = new ChoiceEventMenu();

        printSeparator();
        String name = readInput("Enter room name: ");
        RoomType type = roomTypeMenu.selectRoomType();

        if (type == RoomType.ENTRANCE_HALL) {
            isStart = true;
        }
        event = choiceEventMenu.createChoiceEvent();

        if (type == RoomType.TREASURE_ROOM) {
            map.addLocation(new Treasure(name, event));
            return;
        }

        map.addLocation(new Room(name, event, isStart));
        System.out.println("Room created successfully");
    }

    private void linkRooms() {

    }

    //private void visualizeMap() {}
}

