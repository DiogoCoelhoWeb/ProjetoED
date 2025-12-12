/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 */

package menus;

import events.BuffDebuffEvent;
import events.GoBackEvent;
import events.LoseTurnEvent;

public class BuffDebuffEventMenu extends AbstractMenu{

    private BuffDebuffEvent event;

    /**
     * Constructs a new instance of BuffDebuffEventMenu.
     *
     * This menu allows the user to create and select between different Buff or Debuff events,
     * such as a "Lose Turns" event or a "Go Back" event. The menu interacts with the user through
     * textual prompts to define the specifics of the chosen event, where applicable.
     *
     * The created BuffDebuffEvent is stored internally and can be accessed or processed further
     * once the menu operation is completed.
     */
    public BuffDebuffEventMenu() {}

    /**
     * Creates and initializes a BuffDebuffEvent based on user input from the associated menu.
     * This method executes the menu where the user selects a specific buff or debuff event to create.
     *
     * @return The created BuffDebuffEvent object based on the user's selection.
     */
    public BuffDebuffEvent createBuffDebuffEvent(){
        runMenu();
        return this.event;
    }

    /**
     * Displays the options for the Buff/Debuff Event Menu in the console.
     *
     * This method provides two menu options:
     * 1. "Lose Turns Event" - Represents an event where players lose turns.
     * 2. "Go Back Event" - Represents an event allowing players to go back or undo an action.
     *
     * The implementation provides a textual interface to guide the user in selecting an event.
     * It is intended to be called during the operation of the menu to present the choices.
     */
    @Override
    protected void displayMenu() {
        System.out.println("Buff/Debuff Event Menu");
        System.out.println("1. Lose Turns Event");
        System.out.println("2. Go Back Event");
    }

    /**
     * Runs the menu loop for the Buff/Debuff Event Menu, allowing the user to interact
     * and select specific options until a valid input is provided. The menu will repeatedly
     * prompt the user until a valid option is chosen.
     *
     * If the user enters an invalid value (non-numeric or out of range), an error message
     * is displayed and the loop continues until a valid input is received.
     *
     * This method serves as the main control loop for managing user interactions with the
     * Buff/Debuff Event Menu.
     *
     * @throws NumberFormatException if the input for the menu option is not a valid integer.
     */
    @Override
    public void runMenu() {
        boolean isValid = false;

        do{
            printSeparator();
            displayMenu();
            String input = readInput("Please select an option: ");
            try{
                int option = Integer.parseInt(input);
                if(option >= 1 && option <= 2){
                    isValid = true;
                    executeOption(option);
                } else {
                    System.out.println();
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e){
                System.out.println();
                System.out.println("Invalid input. Please enter a number.");
            }
        } while(!isValid);
    }

    /**
     * Executes the selected option for the Buff/Debuff Event Menu and sets the corresponding event.
     *
     * @param option The option selected by the user.
     *               Possible values:
     *               1: Creates a LoseTurnEvent.
     *               2: Prompts the user for a description and creates a GoBackEvent.
     *                  If the description is empty, a default GoBackEvent is created.
     */
    @Override
    protected void executeOption(int option) {
        switch (option){
            case 1:
                this.event = new LoseTurnEvent();
                break;
            case 2:
                System.out.println();
                String description = readInput("Enter the description for the Go Back Event: ");
                if(description.isEmpty()){
                    this.event = new GoBackEvent();
                }
                this.event = new GoBackEvent(description);
                break;
        }
    }


}
