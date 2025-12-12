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

    public BuffDebuffEventMenu() {}

    public BuffDebuffEvent createBuffDebuffEvent(){
        runMenu();
        return this.event;
    }

    @Override
    protected void displayMenu() {
        System.out.println("Buff/Debuff Event Menu");
        System.out.println("1. Lose Turns Event");
        System.out.println("2. Go Back Event");
    }

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
