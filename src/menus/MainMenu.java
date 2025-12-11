package menus;

import files.MapLoader;
import game.GameLoop;
import map.Map;
import player.Bot;
import player.Player;
import events.ChoiceEventManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMenu extends AbstractMenu {

    private BufferedReader reader;

    public MainMenu() {
        super("");
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    protected void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Start New Game");
        System.out.println("2. Load Saved Game (Not Implemented)");
        System.out.println("3. Create/Edit Map");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    public void runMenu() {
        boolean running = true;
        while (running) {
            displayMenu();
            try {
                String input = reader.readLine();
                if (input == null) break; // Handle EOF

                int option;
                try {
                    option = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                switch (option) {
                    case 1:
                        startNewGame();
                        break;
                    case 2:
                        loadSavedGame();
                        break;
                    case 3:
                        new MapCreationMenu("New Map").runMenu();
                        break;
                    case 0:
                        System.out.println("Exiting... Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
            }
        }
    }

    private void loadSavedGame() throws IOException {

    }

    private void startNewGame() throws IOException {
        String filePath = selectMap();
        if (filePath == null) {
            System.out.print("Enter map file path (default: data/maps/UserMap.json): ");
            filePath = reader.readLine();
            if (filePath == null || filePath.trim().isEmpty()) {
                filePath = "data/maps/UserMap.json";
            }
        }

        ChoiceEventManager eventManager = new ChoiceEventManager();
        MapLoader loader = new MapLoader();
        Map map = loader.loadMap(filePath, eventManager);

        if (map == null) {
            System.out.println("Failed to load map. Aborting.");
            return;
        }

        rooms.MapLocations startLoc = getStartLocation(map);
        if (startLoc == null) {
            System.out.println("Error: Map has no locations!");
            return;
        }

        GameLoop gameLoop = new GameLoop(map);

        System.out.print("Modo Automatico? (s/n): ");
        String modeInput = reader.readLine();

        if (modeInput != null && modeInput.trim().equalsIgnoreCase("s")) {
            System.out.println("Iniciando Modo Automatico com 4 Bots.");
            for (int i = 1; i <= 4; i++) {
                gameLoop.getPlayerManager().addPlayer(new Bot("Bot " + i, startLoc));
            }
        } else {
            System.out.print("Quantos jogadores humanos? ");
            int numHumanPlayers = 0;
            try {
                String input = reader.readLine();
                if (input != null && !input.trim().isEmpty()) {
                    numHumanPlayers = Integer.parseInt(input);
                } else {
                     numHumanPlayers = 1; // Default
                }
            } catch (NumberFormatException e) {
                System.out.println("Numero invalido. Assumindo 1.");
                numHumanPlayers = 1;
            }

            for (int i = 1; i <= numHumanPlayers; i++) {
                System.out.print("Nome do Jogador " + i + ": ");
                String name = reader.readLine();
                if (name == null || name.trim().isEmpty()) name = "Player " + i;
                gameLoop.getPlayerManager().addPlayer(new Player(name, startLoc));
            }

            int currentPlayers = gameLoop.getPlayerManager().getPlayers().size();
            if (currentPlayers < 4) {
                System.out.print("Quer bots? (s/n): ");
                String wantBots = reader.readLine();
                if (wantBots != null && wantBots.trim().equalsIgnoreCase("s")) {
                    int botsToAdd = 4 - currentPlayers;
                    System.out.println("Adicionando " + botsToAdd + " bots para completar 4 jogadores.");
                    for (int i = 1; i <= botsToAdd; i++) {
                        gameLoop.getPlayerManager().addPlayer(new Bot("Bot " + i, startLoc));
                    }
                }
            }
        }

        if (!gameLoop.getPlayerManager().getPlayers().isEmpty()) {
            gameLoop.start();
        } else {
            System.out.println("Nenhum jogador configurado. Jogo cancelado.");
        }
    }

    private String selectMap() {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        try (java.io.FileReader fr = new java.io.FileReader("data/maps.json")) {
            org.json.simple.JSONObject root = (org.json.simple.JSONObject) parser.parse(fr);
            org.json.simple.JSONArray maps = (org.json.simple.JSONArray) root.get("maps");
            
            if (maps == null || maps.isEmpty()) return null;

            System.out.println("\nSelect a Map:");
            for (int i = 0; i < maps.size(); i++) {
                org.json.simple.JSONObject map = (org.json.simple.JSONObject) maps.get(i);
                System.out.println((i + 1) + ". " + map.get("map_name"));
            }
            System.out.println("0. Enter path manually");

            System.out.print("> ");
            String input = reader.readLine();
            int choice = Integer.parseInt(input);

            if (choice == 0) return null;
            if (choice > 0 && choice <= maps.size()) {
                 org.json.simple.JSONObject selected = (org.json.simple.JSONObject) maps.get(choice - 1);
                 return (String) selected.get("path");
            }
        } catch (Exception e) {
            System.out.println("Could not load map list: " + e.getMessage());
        }
        return null;
    }

    private rooms.MapLocations getStartLocation(Map map) {
        rooms.MapLocations startLoc = null;
        java.util.Iterator<rooms.MapLocations> it = map.getStartLocation().iterator();
        
        if (it.hasNext()) {
            startLoc = it.next();
        }
        
        if (startLoc == null && map.getGraph().getNumVertices() > 0) {
             Object[] vertices = map.getGraph().getVertices();
             if (vertices.length > 0) {
                startLoc = (rooms.MapLocations) vertices[0];
             }
        }
        return startLoc;
    }

    @Override
    protected void executeOption(int option) {
        // Deprecated by runMenu logic
    }
}
