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
     * Constructs a new instance of the BuffDebuffEventMenu class.
     * This menu allows users to configure and create various Buff/Debuff events
     * by providing options for specific event types (e.g., losing turns, going back).
     * It extends the AbstractMenu class and overrides its methods to provide
     * a custom menu interface for event creation.
     */
    public BuffDebuffEventMenu() {}

    /**
     * Creates and returns a BuffDebuffEvent based on user input. The method
     * first runs a menu for user interaction, executes the chosen menu option,
     * and then sets the corresponding event. This method ensures that the
     * returned event is configured according to user choices.
     *
     * @return a BuffDebuffEvent instance derived from the user's menu selection
     */
    public BuffDebuffEvent createBuffDebuffEvent(){
        runMenu();
        return this.event;
    }

    /**
     * Displays the menu options specific to the Buff/Debuff Event Menu.
     * This method outputs the available options for the user to select,
     * such as "Lose Turns Event" or "Go Back Event".
     * It is intended to provide the user with the contextual choices
     * related to buff and debuff events.
     */
    @Override
    protected void displayMenu() {
        System.out.println("Buff/Debuff Event Menu");
        System.out.println("1. Lose Turns Event");
        System.out.println("2. Go Back Event");
    }

    /**
     * Executes the menu loop for the BuffDebuffEventMenu.
     * This method displays the menu, reads user input, validates the input,
     * and executes the corresponding option based on the input. The loop
     * continues until the user provides a valid selection.
     *
     * If an invalid input or invalid option is provided, an error message
     * is displayed, and the user is prompted to try again. Input must be
     * a numeric value corresponding to one of the menu options.
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
     * Executes an option selected by the user in the BuffDebuffEventMenu.
     * Based on the selected option, the method initializes the appropriate event.
     *
     * @param option the option selected by the user. Accepts the following values:
     *               1 - Initializes a LoseTurnEvent.
     *               2 - Initializes a GoBackEvent with a custom description provided by the user.
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
                this.event = new GoBackEvent(description);
                break;
        }
    }
}
