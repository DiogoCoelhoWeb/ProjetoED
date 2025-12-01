package menus;

import java.io.IOException;

public class MainMenu extends AbstractMenu {

    private static final int START_GAME = 1;
    private static final int LOAD_GAME = 2;
    private static final int CREATE_MAP = 3;
    private static final int EXIT = 0;

    public MainMenu() {
        super("Main Menu");
        addOption(START_GAME, "Start New Game");
        addOption(LOAD_GAME, "Load Game");
        addOption(CREATE_MAP, "Create map");
        addOption(EXIT, "Exit");
    }

    @Override
    protected void executeOption(int option) {
        switch (option) {
            case START_GAME:
                System.out.println("Starting a new game...");
                // Here you would typically instantiate and run your game logic
                // For now, just a message
                break;
            case LOAD_GAME:
                System.out.println("Loading game...");

                break;
            case CREATE_MAP:
                System.out.println("Create map...");

                break;
            case EXIT:
                System.out.println("Exiting game. Goodbye!");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    @Override
    protected int getExitOptionId() {
        return EXIT;
    }
}
