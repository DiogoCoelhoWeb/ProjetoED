package menus;

import events.BuffDebuffEvent;
import events.ChoiceEvent;
import files.MapSave;
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

    /**
     * Constructs a new MapCreationMenu with the specified map name and initializes
     * the map object.
     *
     * @param mapName the name of the map to be created
     */
    public MapCreationMenu(String mapName) {
        this.map = new Map(mapName);
    }

    /**
     * Creates and initializes a new map within the execution context.
     * This method invokes the runMenu process to allow the user to interact
     * with the creation menu, configure the map, and save its configuration.
     *
     * @return the newly created and configured Map instance
     */
    protected Map createMap(){
        runMenu();
        return this.map;
    }

    /**
     * Displays the Map Creation Menu options to the user.
     *
     * The method overrides the {@code displayMenu} method in the AbstractMenu
     * superclass to provide a custom implementation specific to the map creation context.
     */
    protected void displayMenu() {
        System.out.println("Map Creation Menu");
        System.out.println("1. Create Room");
        System.out.println("2. Link Rooms");
        System.out.println("3. Save");
    }

    /**
     * Runs the menu for map creation, providing users with options to create rooms, link rooms,
     * or save the map. It continuously displays the menu until a valid option is selected
     * and the user chooses to save the map.
     *
     * The method ensures that the map is saved before exiting the menu.
     */
    public void runMenu() {
        boolean isValid = false;
        boolean hasSaved = false;
        MapSave save = new MapSave();
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
                        save.saveMap(this.map);
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

    /**
     * Executes the selected option from the menu.
     *
     * Depending on the provided option, this method triggers specific actions
     * such as creating a new room or linking existing rooms in the context of
     * the map creation menu.
     *
     * @param option the menu option selected by the user. Expected values are:
     *               1 - to create a new room within the map,
     *               2 - to link two rooms in the map.
     */
    protected void executeOption(int option) {
        switch (option) {
            case 1:
                createRoom();
                break;
            case 2:
                linkRooms();
                break;

        }
    }

    /**
     * Creates a new room within the current map.
     *
     * The method allows defining a room's name, type, and optional choice event. It supports the creation
     * of an entrance hall, treasure room, or other types of rooms. The method also ensures that only one
     * entrance hall and one treasure room can exist within the map. If the selected room type is a
     * treasure room, it is created and flagged as such. For regular rooms, including entrance halls
     * */
    private void createRoom() {
        boolean isStart = false;
        ChoiceEvent event = null;
        RoomTypeMenu roomTypeMenu = new RoomTypeMenu(this.hasTreasureRoom);
        ChoiceEventMenu choiceEventMenu = new ChoiceEventMenu();

        printSeparator();
        String name = readInput("Enter room name: ");
        RoomType type = roomTypeMenu.selectRoomType();

        if (type == RoomType.ENTRANCE_HALL) {
            isStart = true;
            this.hasStartRoom = true;
        }

        String wantEvent = readInput("Do you want to add a choice event to this room? (y/n): ");

        if (wantEvent.equalsIgnoreCase("y")) {
            event = choiceEventMenu.createChoiceEvent();
        }

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

    /**
     * Links two rooms in the map by establishing a connection between them.
     * This method first verifies that there are at least two rooms created,
     * and if not, it displays a message prompting the user to create more rooms.
     *
     * The user is then prompted to select two rooms that they wish to link.
     * A menu is displayed for selecting each room, allowing the user to choose
     * from the available rooms*/
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

}

