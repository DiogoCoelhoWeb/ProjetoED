/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 */

package menus;

import rooms.RoomType;

import java.awt.desktop.SystemEventListener;

public class RoomTypeMenu extends AbstractMenu{

    private boolean hasTreasureRoom;
    private RoomType type;

    /**
     * Constructs a RoomTypeMenu instance with the specified configuration
     * for whether a treasure room is included in the menu options.
     *
     * @param hasTreasureRoom a boolean indicating whether the menu should include the treasure room type
     */
    public RoomTypeMenu(boolean hasTreasureRoom) {
        this.hasTreasureRoom = hasTreasureRoom;
    }

    /**
     * Allows the user to select a room type from the available options provided in the menu.
     * The selection is confirmed by the user before being returned.
     *
     * @return The selected RoomType based on user input and confirmation.
     */
    public RoomType selectRoomType(){
        runMenu();
        return this.type;
    }

    /**
     * Displays the Room Type menu to the console.
     *
     * The menu lists all available `RoomType` enumeration values with their corresponding
     * ordinal numbers. If the `RoomType` is `TREASURE_ROOM` and the `RoomTypeMenu` instance
     * has the `hasTreasureRoom` flag set to true, that specific option is excluded from
     * the displayed menu.
     */
    @Override
    protected void displayMenu() {
        System.out.println("Room Type Menu");
        for (RoomType t: RoomType.values()){
            if(t == RoomType.TREASURE_ROOM && hasTreasureRoom){
                continue;
            }

            System.out.println((t.ordinal() + 1) + ". " + t.toString());
        }
    }

    /**
     * Executes the menu flow, allowing the user to select an option
     * from the available room types and confirm their selection.
     * The menu will keep running until a valid and confirmed option
     * is chosen by the user.
     */
    @Override
    public void runMenu() {
        boolean isValid = false;
        boolean isConfirmed = false;

        do{
            printSeparator();
            displayMenu();
            String input = readInput("Please select an option: ");
            try{
                int option = Integer.parseInt(input);

                if(option > 0 && option <= RoomType.values().length){
                    this.type = RoomType.values()[option - 1];
                    isValid = true;

                    printSeparator();
                    System.out.println("You have selected: " + this.type.toString());
                    String confirm = readInput("Confirm selection? (y/n): ");

                    if(confirm.equalsIgnoreCase("y")) {
                        isConfirmed = true;
                    }
                } else {
                    System.out.println();
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e){
                System.out.println();
                System.out.println("Invalid input. Please enter a number.");
            }
        } while(!isValid || !isConfirmed);
    }

    /**
     * Executes the specific action associated with the given menu option.
     * This method determines the behavior of the menu based on the user-selected option
     * and should be implemented by subclasses with context-specific logic.
     *
     * @param option the numeric identifier of the menu option selected by the user
     */
    @Override
    protected void executeOption(int option) {}

}
