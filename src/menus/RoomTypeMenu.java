/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 */

package menus;

import rooms.RoomType;

import java.awt.desktop.SystemEventListener;

public class RoomTypeMenu extends AbstractMenu{

    private RoomType type;

    public RoomTypeMenu() {}

    public RoomType selectRoomType(){
        runMenu();
        return this.type;
    }

    @Override
    protected void displayMenu() {
        System.out.println("Room Type Menu");
        for (RoomType t: RoomType.values()){
            System.out.println((t.ordinal() + 1) + ". " + t.toString());
        }
    }

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

    @Override
    protected void executeOption(int option) {}

}
