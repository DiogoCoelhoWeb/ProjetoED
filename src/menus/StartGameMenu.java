package menus;

import files.MapLoader;
import game.GameLoop; // Assuming GameLoop is the class to start a new game
import lists.ArrayUnorderedList;
import map.Map;
import player.Bot;
import player.Player;
import rooms.MapLocations;
import utils.Shuffle;

import java.io.File;


public class StartGameMenu extends AbstractMenu {
    public StartGameMenu() {
    }

    /**
     * Displays the menu options specific to the StartGameMenu.
     * This method presents the user with choices related to starting a new game or returning
     * to the main menu. It also prints a visual separator for better readability.
     */
    @Override
    protected void displayMenu() {
        System.out.println("Start Game Menu");
        System.out.println("1. Start New Game");
        System.out.println("2. Back to Main Menu");
        printSeparator();
    }

    /**
     * Executes the menu flow for the Start Game Menu. This method continuously
     * displays the menu options, prompts the user for input, validates the input,
     * and executes the corresponding action until the user opts to return to the
     * Main Menu.
     *
     * This method continues running in a loop until the user selects the
     * option to return to the Main Menu.
     */
    @Override
    public void runMenu() {
        boolean returningToMainMenu = false;
        do {
            displayMenu();
            String input = readInput("Please select an option: ");
            try {
                int option = Integer.parseInt(input);
                if (option >= 1 && option <= 2) {
                    if (option == 2) {
                        returningToMainMenu = true;
                        System.out.println("Returning to Main Menu...");
                    }
                    executeOption(option);
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (!returningToMainMenu);
    }

    /**
     * Executes the selected menu option based on the provided option number.
     *
     * @param option the option selected by the user.
     *
     */
    @Override
    protected void executeOption(int option) {
        switch (option) {
            case 1:
                startNewGame();
                break;
            case 2:
                break;
            default:
                System.out.println("Invalid option. This should not happen.");
                break;
        }
    }

    /**
     * Starts a new game by loading a map, setting up players or bots,
     * and initiating the game loop. This method allows the player to
     * choose between manual or automated gameplay modes. It also checks
     * for valid starting locations and manages errors like empty player
     * lists or failed map loading.
     *
     * If any of the required conditions are not met (e.g., missing map
     * or insufficient starting locations), the method will terminate
     * and display an error message.
     */
    private void startNewGame() {
        printSeparator();
        System.out.println("Starting a new game...");

        Map map = loadMap();

        if (map == null) {
            System.out.println("Failed to load map. Aborting.");
            return;
        }

        ArrayUnorderedList<MapLocations> startLocs = map.getStartLocation();
        int count = startLocs.size();

        if (startLocs == null) {
            System.out.println("Error: Map has no Start locations!");
            return;
        }

        GameLoop gameLoop = new GameLoop(map);
        Shuffle<MapLocations> shuffle = new Shuffle<>();
        MapLocations[] locationsArray = shuffle.shuffle(startLocs);

        String modeInput = readInput("Modo Automatico? (s/n): ");
        if (modeInput != null && modeInput.trim().equalsIgnoreCase("s")) {

            if (count < 4) {
                System.out.println("Not enough start locations to create 4 bots.");
                return;
            }
            setUpAutoMode(locationsArray, gameLoop);

        } else {
            setUpPlayers(gameLoop, locationsArray);
        }
        if (!gameLoop.getPlayerManager().getPlayers().isEmpty()) {
            gameLoop.start();
        } else {
            System.out.println("NO players found. Aborting.");
        }
    }


    /**
     * Sets up players for a new game by allowing the user to add human players
     * and optionally create bot players to fill in until a minimum number of players
     * is reached. Each player is added to the game loop's player manager.
     *
     * @param gameLoop       the game loop instance that manages the game state and player manager
     * @param locationsArray an array of MapLocations specifying possible starting positions for players
     */
    private void setUpPlayers(GameLoop gameLoop, MapLocations[] locationsArray) {
        int numHumanPlayers = readIntengerInput("Number of Human Players: ");

        for (int i = 1; i <= numHumanPlayers; i++) {
            String name = readInput("Nome do Jogador " + i + ": ");

            if (name == null || name.trim().isEmpty()) {
                name = "Player " + i;
            }
            gameLoop.getPlayerManager().addPlayer(new Player(name, locationsArray[0]));
        }

        int currentPlayers = gameLoop.getPlayerManager().getPlayers().size();
        if (currentPlayers < 4) {
            String wantBots = readInput("Quer bots? (s/n): ");
            if (wantBots != null && wantBots.trim().equalsIgnoreCase("s")) {
                int botsToAdd = 4 - currentPlayers;
                System.out.println("Added " + botsToAdd);
                for (int i = 1; i <= botsToAdd; i++) {
                    gameLoop.getPlayerManager().addPlayer(new Bot("Bot " + i, locationsArray[i]));
                }
            }
        }
    }

    /**
     * Sets up automated game mode by initializing bot players and assigning them
     * predefined locations. The bots are then added to the player manager within
     * the game loop.
     *
     * @param locationsArray an array of MapLocations representing the initial
     *                       positions for the bot players
     * @param gameLoop the game loop instance, which manages the state and
     *                 progression of the game
     */
    private void setUpAutoMode(MapLocations[] locationsArray, GameLoop gameLoop) {

        Bot bot1 = new Bot("Alpha", locationsArray[0]);
        Bot bot2 = new Bot("Bravo", locationsArray[1]);
        Bot bot3 = new Bot("Charlie", locationsArray[2]);
        Bot bot4 = new Bot("Delta", locationsArray[3]);

        gameLoop.getPlayerManager().addPlayer(bot1);
        gameLoop.getPlayerManager().addPlayer(bot2);
        gameLoop.getPlayerManager().addPlayer(bot3);
        gameLoop.getPlayerManager().addPlayer(bot4);
    }

    /**
     * This method loads a map selected by the user from a predefined directory of maps.
     * It lists all available maps in the directory, prompts the user to select one by inputting its ID,
     * and then loads the chosen map using a MapLoader utility.
     *
     * @return a Map instance representing the selected map
     * @throws RuntimeException if no maps are found in the directory
     */
    private Map loadMap() {
        MapLoader loader = new MapLoader();

        File[] maps = new File("data/maps").listFiles();
        System.out.println("Available maps:");
        if (maps == null) {
            System.out.println("No maps found.");
            throw new RuntimeException("No maps found.");
        }
        int mapId;
        do {
            for (int i = 0; i < maps.length; i++) {
                System.out.println((i + 1) + ": " + maps[i].getName().replace(".json", ""));
            }
            mapId = readIntengerInput("Please enter the id of the map to load: ");

        } while (mapId - 1 < 0 || mapId - 1 >= maps.length);

        return loader.loadMap(maps[mapId - 1].getName().replace(".json", ""));
    }
}