package menus;

public class MainMenu extends AbstractMenu {

    public MainMenu() {}

    protected void displayMenu() {
        System.out.println("Main Menu");
        System.out.println("1. Start Game");
        System.out.println("2. Load Game");
        System.out.println("3. Create Map");
        System.out.println("4. Exit");
    }

    public void runMenu() {
        boolean isValid = false;
        boolean hasExited = false;

        do {
            displayMenu();
            String input = readInput("Please select an option: ");
            try {
                int option = Integer.parseInt(input);
                if (option >= 1 && option <= 4) {
                    isValid = true;
                    if (option == 4) {
                        hasExited = true;
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    executeOption(option);
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (!isValid || !hasExited);
    }

    protected void executeOption(int option) {
        do {
            switch (option) {
                case 1:
                    StartGameMenu startGameMenu = new StartGameMenu();
                    startGameMenu.runMenu();
                    break;
                case 2:
                    System.out.println("Loading game...");
                    break;
                case 3:
                    createMap();
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

    private void createMap() {
        printSeparator();

        String mapName = readInput("Enter map name: ");

        MapCreationMenu mapCreationMenu = new MapCreationMenu(mapName);
        mapCreationMenu.runMenu();
    }
}
