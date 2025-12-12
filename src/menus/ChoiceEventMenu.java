/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 */

package menus;

import events.ChoiceEvent;
import events.ChoiceEventManager;
import events.EnigmaEvent;
import files.EnigmaLoader;
import lists.ArrayUnorderedList;
import lists.BSTOrderedList;
import player.Player;

public class ChoiceEventMenu extends AbstractMenu{

    private BSTOrderedList<String> choices = new BSTOrderedList<>();

    public ChoiceEventMenu() {}

    @Override
    protected void displayMenu() {
        System.out.println("Choice Event Menu");
        System.out.println("1. Add Choice");
        System.out.println("2. Remove Choice");
        System.out.println("3. View Choices");
        System.out.println("5. Random Event");
        System.out.println("4. Save");
    }

    public ChoiceEvent createChoiceEvent(){
        int correctChoice;
        ChoiceEventManager choiceEventManager = EnigmaLoader.loadEnigmas();

        printSeparator();
        System.out.println("Create Room Event");
        System.out.println();

        String hasEvent = readInput("Do you want to have an event? (y/n): ");
        if(hasEvent.equalsIgnoreCase("n")){
            return null;
        }

        String randomEvent = readInput("Do you want a random event? (y/n): ");
        if(randomEvent.equalsIgnoreCase("y")){
            return choiceEventManager.getRandomChoiceEvent();
        }


        String eventName = readInput("Enter the question of the event: ");
        runMenu();
        correctChoice = selectCorrectChoice();

        EnigmaEvent enigmaEvent = new EnigmaEvent(eventName, correctChoice);

        for(String choice : choices){
            enigmaEvent.addChoice(choice);
        }

        return enigmaEvent;
    }

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
                if(option >= 1 && option <= 5){
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

    private void addChoice() {
        printSeparator();
        String newChoice = readInput("Enter the new choice: ");

        this.choices.add(newChoice);

        System.out.println();
        System.out.println("Choice added");
    }

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

    private void viewChoices() {
        int index = 1;
        for (String choice : choices) {
            System.out.println(index + ". " + choice);
            index++;
        }
    }

    private String choiceAtIndex(int index) {
        for (String choice : choices) {
            if (index == 0) {
                return choice;
            }
            index--;
        }
        throw new IndexOutOfBoundsException("Index out of bounds");
    }

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

        return correctIndex - 1; // Convert to zero-based index
    }
}
