/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 */

package menus;

import events.ChoiceEvent;
import events.EnigmaEvent;
import lists.BSTOrderedList;


public class ChoiceEventMenu extends AbstractMenu{

    private BSTOrderedList<String> choices = new BSTOrderedList<>();

    /**
     * Constructs a new instance of the ChoiceEventMenu class.
     */
    public ChoiceEventMenu() {}

    /**
     * Displays the menu options for the choice event management system.
     * This method outputs a list of available actions related to managing
     * choices in the event, including adding, removing, viewing, and saving.
     *
     * This method is an implementation of the abstract method {@code displayMenu}
     * from the superclass and is designed for customizing the menu presentation
     * specific to managing choices in an event.
     */
    @Override
    protected void displayMenu() {
        System.out.println("Choice Event Menu");
        System.out.println("1. Add Choice");
        System.out.println("2. Remove Choice");
        System.out.println("3. View Choices");
        System.out.println("4. Save");
    }

    /**
     * Creates a new ChoiceEvent with a user-defined question, a list of choices,
     * and a specified correct choice. The method interacts with the user to gather
     * the necessary inputs, including the question, available choices, and the
     * correct choice index. It validates the inputs and constructs a fully
     * initialized ChoiceEvent.
     *
     * @return the newly created ChoiceEvent instance containing the question, list
     *         of choices, and the correct choice index
     */
    public ChoiceEvent createChoiceEvent(){
        int correctChoice;

        printSeparator();
        System.out.println("Create Room Event");
        System.out.println();
        String eventName = readInput("Enter the question of the event: ");
        runMenu();
        correctChoice = selectCorrectChoice();

        EnigmaEvent enigmaEvent = new EnigmaEvent(eventName, correctChoice);

        for(String choice : choices){
            enigmaEvent.addChoice(choice);
        }

        return enigmaEvent;
    }

    /**
     * Executes and manages the user interaction for the Choice Event Menu.
     * This method runs a loop that displays the menu options, validates user input,
     * and executes the desired menu option based on the user's choice.
     *
     * The menu includes options such as adding choices, removing choices, viewing the current list
     * of choices, and saving the selections. The method ensures that the "Save" option cannot
     * be successfully executed unless at least two choices are present.
     *
     */
    @Override
    public void runMenu() {
        boolean isValid = false;
        boolean hasSaved = false;

        do{
            printSeparator();
            displayMenu();
            String input = readInput("Please select an option: ");
            try{
                int option = Integer.parseInt(input);
                if(option >= 1 && option <= 4){
                    isValid = true;
                    executeOption(option);

                    if(option == 4){
                        hasSaved = true;
                        if (this.choices.size() < 2) {
                            System.out.println();
                            System.out.println("You must have at least two choices before saving.");
                            hasSaved = false;
                            isValid = false;
                            continue;
                        }
                        System.out.println();
                        System.out.println("Choices saved successfully");
                    }
                } else {
                    System.out.println();
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e){
                System.out.println();
                System.out.println("Invalid input. Please enter a number.");
            }
        } while(!isValid || !hasSaved);
    }

    /**
     * Executes the action corresponding to the given menu option.
     * This method handles the logic for each menu option, invoking specific actions
     * such as adding a choice, removing a choice, viewing all choices, or exiting the menu.
     * Invalid options are handled with an error message.
     *
     * @param option the selected menu option that determines the action to execute.
     */
    @Override
    protected void executeOption(int option) {
        switch (option){
            case 1:
                addChoice();
                break;
            case 2:
                removeChoice();
                break;
            case 3:
                printSeparator();
                System.out.println("Current Choices:");
                viewChoices();
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    /**
     * Adds a new choice to the list of choices maintained by the menu.
     * This method prompts the user to input a new choice, adds it to the
     * internal list of choices, and provides feedback to the user upon
     * successful addition. It also prints a separator for visual clarity.
     */
    private void addChoice() {
        printSeparator();
        String newChoice = readInput("Enter the new choice: ");

        this.choices.add(newChoice);

        System.out.println();
        System.out.println("Choice added");
    }

    /**
     * Removes a choice from the list of available choices.
     *
     * Invalid inputs and out-of-range indices are handled with appropriate console messages
     * to guide the user.
     */
    private void removeChoice() {
        printSeparator();
        System.out.println("Remove Choice");
        viewChoices();
        String removeChoice = readInput("Enter the choice to remove: ");

        try {
            int index = Integer.parseInt(removeChoice);
            if (index < 1 || index > choices.size()) {
                System.out.println();
                System.out.println("Invalid choice number");
            } else {
                String choiceToRemove = choiceAtIndex(index - 1);
                this.choices.remove(choiceToRemove);
                System.out.println();
                System.out.println("Choice removed");
            }
        } catch (Exception e){
            System.out.println();
            System.out.println("Choice not found");
        }
    }

    /**
     * Displays a list of available choices to the user in a numbered format.
     * Each choice from the collection of choices is printed to the console,
     * prefixed with its corresponding index number starting from 1.
     *
     * This method iterates through all the items in the collection of choices
     * and outputs them sequentially. It is intended to provide a descriptive
     * view of the current options available within the menu.
     *
     * It relies on the existence and accessibility of the `choices` field,
     * which is expected to contain the list of options that need to be displayed.
     */
    private void viewChoices() {
        int index = 1;
        for (String choice : choices) {
            System.out.println(index + ". " + choice);
            index++;
        }
    }

    /**
     * Retrieves the choice at the specified index from the list of choices.
     * The method iterates through the collection of choices and returns the
     * choice located at the specified index. If the index is out of the valid
     * range, an IndexOutOfBoundsException is thrown.
     *
     * @param index the zero-based index of the desired choice in the list
     * @return the choice at the specified index
     * @throws IndexOutOfBoundsException if the index is less than 0 or exceeds
     *                                   the size of the list
     */
    private String choiceAtIndex(int index) {
        for (String choice : choices) {
            if (index == 0) {
                return choice;
            }
            index--;
        }
        throw new IndexOutOfBoundsException("Index out of bounds");
    }

    /**
     * Prompts the user to select the correct choice from a list of available options.
     * Displays the list of choices, validates user input, and ensures a valid selection is made.
     *
     * @return The zero-based index of the user's selected choice from the list.
     */
    private int selectCorrectChoice() {
        int index = 1;

        printSeparator();
        System.out.println("Choose the Correct Choice");

        for (String choice : choices) {
            System.out.println(index + ". " + choice);
            index++;
        }

        int correctIndex = -1;

        do {
            String input = readInput("Enter the number of the correct choice: ");
            try {
                correctIndex = Integer.parseInt(input);
                if (correctIndex < 1 || correctIndex > choices.size()) {
                    System.out.println();
                    System.out.println("Invalid choice number. Please try again");
                    correctIndex = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("Invalid input. Please enter a number");
            }
        } while (correctIndex == -1);

        return correctIndex - 1;
    }
}
