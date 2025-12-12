/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 */

package menus;

import lists.ArrayUnorderedList;
import rooms.MapLocations;

public class SelectRoomMenu extends AbstractMenu{
    ArrayUnorderedList<MapLocations> locations = new ArrayUnorderedList<>();
    MapLocations selectedLocation;

    /**
     * Constructs a SelectRoomMenu instance with the given list of map locations.
     * This menu allows the user to select a room from the provided list of locations.
     *
     * @param locations the list of MapLocations instances available for selection
     */
    public SelectRoomMenu(ArrayUnorderedList<MapLocations> locations) {
        this.locations = locations;
    }

    /**
     * Prompts the user to select a room from the available locations by running the menu.
     * The method invokes the menu display and selection process, ultimately returning
     * the selected map location.
     *
     * @return the selected MapLocation instance chosen by the user during the menu interaction
     */
    public MapLocations selectRoom(){
        runMenu();
        return this.selectedLocation;
    }

    /**
     * Displays a menu where all available room locations are listed,
     * allowing the user to visually see and select a room.
     * The menu displays each location in a numbered format for readability.
     *
     *
     * Each location is retrieved from the `locations` list and displayed by its
     * index and name. The index starts at 1 for user-friendliness.
     */
    @Override
    protected void displayMenu() {
        int index = 1;

        System.out.println("Select Room Menu");
        for (MapLocations location : locations) {
            System.out.println(index + ". " + location.getName());
            index++;
        }
    }

    /**
     * Handles the execution of the menu loop for the current menu instance.
     * Displays menu options and processes user input to execute the selected action.
     * The method ensures that only valid input within the range of menu options is accepted.
     * If an invalid option is selected, the user is prompted to try again.
     * The loop continues until a valid option is selected and processed.
     */
    @Override
    public void runMenu() {
        boolean isValid = false;

        do {
            printSeparator();
            displayMenu();
            String input = readInput("Please select an option: ");
            try {
                int option = Integer.parseInt(input);
                if (option > 0 && option <= locations.size()) {
                    isValid = true;
                    executeOption(option);
                } else {
                    System.out.println();
                    System.out.println("Invalid option. Please try again");
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println("Invalid option selected. Please try again");
            }
        } while (!isValid);
    }

    /**
     * Executes the option selected by the user and updates the selected location.
     * This method iterates through the list of locations and assigns the selected
     * location based on the provided option. If the option is invalid, an
     * IllegalArgumentException is thrown.
     *
     * @param option the user's selected option, corresponding to a specific location
     *               in the list. The value should be greater than 0 and not exceed
     *               the number of locations available.
     * @throws IllegalArgumentException if the provided option does not match any
     *                                  valid location.
     */
    @Override
    protected void executeOption(int option) {
        for (MapLocations location : locations) {
            if (--option == 0) {
                this.selectedLocation = location;
                return;
            }
        }
        throw new IllegalArgumentException("Invalid option selected");
    }
}
