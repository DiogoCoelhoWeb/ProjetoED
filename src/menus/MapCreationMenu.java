package menus;

import events.BuffDebuffEvent;
import events.ChoiceEvent;
import lists.ArrayUnorderedList;
import lists.BSTOrderedList;
import map.Map;
import rooms.MapLocations;
import rooms.Room;
import rooms.RoomType;
import rooms.Treasure;

import java.lang.reflect.Type;
import java.util.List;

public class MapCreationMenu extends AbstractMenu{

    private boolean hasTreasureRoom = false;
    private boolean hasStartRoom = false;

    private Map map;
    private ArrayUnorderedList<MapLocations> locations = new ArrayUnorderedList<>();

    public MapCreationMenu(String mapName) {
        this.map = new Map(mapName);
    }

    protected Map createMap(){
        runMenu();
        return this.map;
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
        RoomTypeMenu roomTypeMenu = new RoomTypeMenu(this.hasTreasureRoom);
        ChoiceEventMenu choiceEventMenu = new ChoiceEventMenu();

        printSeparator();
        String name = readInput("Enter room name: ");
        RoomType type = roomTypeMenu.selectRoomType();

        if (type == RoomType.ENTRANCE_HALL) {
            isStart = true;
            this.hasStartRoom = true;
        }

        event = choiceEventMenu.createChoiceEvent();

        if (type == RoomType.TREASURE_ROOM) {
            Treasure treasureRoom = new Treasure(name, event);
            map.addLocation(treasureRoom);
            this.locations.addToRear(treasureRoom);
            this.hasTreasureRoom = true;
            return;
        }

        Room newRoom = new Room(name, event, isStart);
        map.addLocation(newRoom);
        this.locations.addToRear(newRoom);
        System.out.println("Room created successfully");
    }

    private void linkRooms() {
        if(this.locations.isEmpty() || this.locations.size() < 2){
            System.out.println("Not enough rooms to link. Please create more rooms first.");
            return;
        }

        SelectRoomMenu selectRoomMenu = new SelectRoomMenu(this.locations);

        System.out.println("Select the first room to link:");
        MapLocations firstRoom = selectRoomMenu.selectRoom();

        System.out.println("Select the second room to link:");
        MapLocations secondRoom = selectRoomMenu.selectRoom();

        String buffDebuff = readInput("Do you want an event on this connection? (y/n): ");

        if(buffDebuff.equalsIgnoreCase("y")){
            BuffDebuffEvent event = new BuffDebuffEventMenu().createBuffDebuffEvent();
        }

        map.addConnection(firstRoom, secondRoom);
        System.out.println("Rooms linked successfully");
    }

    //private void visualizeMap() {}
}

