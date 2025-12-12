/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 */

package menus;

import lists.ArrayUnorderedList;
import lists.BSTOrderedList;
import rooms.MapLocations;

public class SelectRoomMenu extends AbstractMenu{
    ArrayUnorderedList<MapLocations> locations = new ArrayUnorderedList<>();
    MapLocations selectedLocation;

    public SelectRoomMenu(ArrayUnorderedList<MapLocations> locations) {
        this.locations = locations;
    }

    public MapLocations selectRoom(){
        runMenu();
        return this.selectedLocation;
    }

    @Override
    protected void displayMenu() {
        int index = 1;

        System.out.println("Select Room Menu");
        for (MapLocations location : locations) {
            System.out.println(index + ". " + location.getName());
            index++;
        }
    }

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
