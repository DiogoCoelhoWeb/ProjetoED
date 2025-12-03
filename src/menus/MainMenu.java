package menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMenu extends AbstractMenu {

    protected void displayMenu() {
        System.out.println("Main Menu");
        System.out.println("1. Start Game");
        System.out.println("2. Load Game");
        System.out.println("3. Create Map");
        System.out.println("4. Exit");
    }

    public void runMenu() {
        displayMenu();
        System.out.println("Please select an option");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input = br.readLine();
            int option = Integer.parseInt(input);
            executeOption(option);
        } catch (IOException | NumberFormatException ignored) {

        }
    }

    protected void executeOption(int option) {
        do {
            switch (option) {
                case 1:
                    System.out.println("Starting game...");
                    break;
                case 2:
                    System.out.println("Loading game...");
                    break;
                case 3:
                    System.out.println("Creating map...");
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (option < 1 || option > 4);
    }
}
