package game;

import events.ChoiceEvent;
import events.Event;
import lists.ArrayUnorderedList;
import map.Map;
import rooms.RoomType;
import player.Player;
import player.PlayerManager;
import rooms.MapLocations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;


public class GameLoop {
    private Map map;
    private PlayerManager playerManager;
    private boolean gameRunning;
    private BufferedReader reader; // Declare BufferedReader as a field

    public GameLoop(Map map) {
        this.map = map;
        this.playerManager = new PlayerManager();
        this.gameRunning = true;
        this.reader = new BufferedReader(new InputStreamReader(System.in)); // Initialize once
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    /**
     * Initializes and starts the game loop. This method ensures the game only begins if there are players present.
     * If no players are added, the game exits with a notification.
     *
     * During each game cycle, players take turns sequentially until the game ends. The turn progression follows the
     * order of players managed by the `PlayerManager`. Each player's turn is handled by the `playTurn` method.
     *
     * If an I/O exception occurs during a player's turn, the game logs the error and terminates to prevent further issues.
     *
     * This method is the primary entry point for controlling the game's execution.
     */
    public void start() {
        if (playerManager.getPlayers().isEmpty()) {
            System.out.println("Nenhum jogador adicionado. Encerrando o jogo.");
            return;
        }

        System.out.println("Jogo Iniciado!");

        while (gameRunning) {
            Iterator<Player> playerIterator = playerManager.getPlayers().iterator();
            while (playerIterator.hasNext()) {
                Player currentPlayer = playerIterator.next();

                // Check if game was stopped during another player's turn (unlikely but safe)
                if (!gameRunning) break;

                try {
                    playTurn(currentPlayer);
                } catch (IOException e) {
                    System.err.println("Erro de I/O durante o turno do jogador " + currentPlayer.getUsername() + ": " + e.getMessage());
                    gameRunning = false; // Critical error, stop game
                }
            }
        }
    }

    /**
     * Manages and executes the actions of a single turn for a specified player in the game.
     * This includes presenting the turn options, processing player input, and determining the outcome
     * of their choice. If a player has no turns remaining or the game is no longer running, their turn ends.
     *
     * @param player The player whose turn is being processed.
     * @throws IOException If an input or output exception occurs during the player's turn processing.
     */
    private void playTurn(Player player) throws IOException {
        if (player.isBlocked()) {
            player.endTurn();
            return;
        }

        while (player.getTurns() > 0 && gameRunning) {
            displayTurnMenu(player);

            System.out.print("> ");
            String input = reader.readLine();

            try {
                int choice = Integer.parseInt(input);
                processTurnChoice(player, choice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        player.endTurn();
    }

    /**
     * Displays the turn menu for the specified player. Includes information about
     * the player's username, current location, and remaining turns. The menu
     * offers options for the player to move, skip their turn, or exit the game.
     *
     * @param player The player whose turn menu is being displayed. This is used
     *               to retrieve the player's username, current location, and
     *               remaining turn count.
     */
    private void displayTurnMenu(Player player) {
        System.out.println("\n------------------------------------------------");
        System.out.println("Turno de: " + player.getUsername());
        System.out.println("Localizacao Atual: " + player.getCurrentLocation().getName());
        System.out.println("Turnos Restantes: " + player.getTurns());

        System.out.println("Chose:");
        System.out.println("1. Move");
        System.out.println("0. GIve up Turn");
        System.out.println("99. Exit");
    }

    /**
     * Processes the player's turn choice by executing the corresponding game action.
     * Depending on the player's input, this method handles movement, skips the player's
     * remaining turns, processes the exit confirmation, or notifies the player of
     * invalid input.
     *
     * @param player The player whose turn choice is being processed. This parameter
     *               provides the context for executing actions such as movement or
     *               skipping turns.
     * @param choice The numerical input representing the player's turn action.
     *               Acceptable values include:
     *               - 1: Executes the player's move action.
     *               - 0: Skips all remaining turns for the player.
     *               - 99: Asks for exit confirmation and terminates the game if confirmed.
     * @throws IOException If an input or output error occurs during player interaction
     *                     (e.g., handling movement or confirming exit).
     */
    private void processTurnChoice(Player player, int choice) throws IOException {
        switch (choice) {
            case 1:
                handleMove(player);
                break;
            case 0:
                skipRemainingTurns(player);
                break;
            case 99:
                if (confirmExit()) {
                    gameRunning = false;
                }
                break;
            default:
                System.out.println("Invalid input.");
        }
    }

    /**
     * Skips all remaining turns for the specified player. This method reduces the player's
     * remaining turns to zero by iteratively consuming each turn until none are left.
     *
     * @param player The player whose remaining turns are to be skipped.
     */
    private void skipRemainingTurns(Player player) {
        System.out.println("next turn...");
        while (player.getTurns() > 0) {
            player.useTurn();
        }
    }

    /**
     * Prompts the user to confirm if they want to exit the game.
     * Reads input from the user and evaluates their response.
     *
     * @return true if the user confirms exit by entering "y" (ignoring case),
     *         false otherwise.
     * @throws IOException If an input or output error occurs while reading user input.
     */
    private boolean confirmExit() throws IOException {
        System.out.print("You sure? (y/n): ");
        String confirm = reader.readLine();
        return confirm.equalsIgnoreCase("y");
    }


    /**
     * Handles the player's movement to a new location during their turn in the game.
     * This method determines the available neighboring locations, allows the player
     * to select a target location, and processes any room entry requirements before
     * finalizing the move. If there are no neighboring locations, the method indicates
     * a dead-end and terminates early.
     *
     * @param player The player attempting to move. This is used to retrieve
     *               their current location and update their position upon a successful move.
     * @throws IOException If an input or output error occurs during the movement process.
     */
    private void handleMove(Player player) throws IOException {
        ArrayUnorderedList<MapLocations> neighbors = map.getGraph().getNeighbors(player.getCurrentLocation());

        if (neighbors.isEmpty()) {
            System.out.println("DeadEnd");
            return;
        }

        MapLocations target = selectTargetLocation(neighbors);
        if (target == null) {
            return; // Usuário cancelou ou entrada inválida
        }

        // Verifica se há enigmas ou bloqueios antes de entrar
        if (processRoomEntryRequirements(player, target)) {
            performMove(player, target);
        }
    }

    /**
     * Allows the user to select a target location from a list of neighboring locations by providing a numbered menu.
     * The user can also cancel the selection by entering 0.
     *
     * @param neighbors an unordered list of neighboring map locations for the user to choose from
     * @return the selected MapLocations object if a valid selection is made, or null if the selection is canceled
     * @throws IOException if an I/O error occurs during user input
     */
    private MapLocations selectTargetLocation(ArrayUnorderedList<MapLocations> neighbors) throws IOException {
        System.out.println("Select your path");
        Iterator<MapLocations> it = neighbors.iterator();
        int i = 1;
        while (it.hasNext()) {
            System.out.println(i + ". " + it.next().getName());
            i++;
        }
        System.out.println("0. cancel");
        System.out.print("> ");

        try {
            int choice = Integer.parseInt(reader.readLine());
            if (choice == 0) return null;

            if (choice > 0 && choice < i) {
                it = neighbors.iterator();
                for (int k = 0; k < choice; k++) {
                    MapLocations temp = it.next();
                    if (k == choice - 1) return temp;
                }
            } else {
                System.out.println("Opcao invalida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
        }
        return null;
    }

    /**
     * Processes the requirements for a player to enter a target room in the game. If the target
     * room has an interactive event (e.g., an enigma), the player must successfully interact
     * with it to proceed. If the player fails the interaction, their turn is consumed, and they
     * cannot enter the room. If there are no interactive events, the player is allowed to enter
     * the room directly.
     *
     * @param player The player attempting to enter the target room. This is used to handle
     *               their interaction with any events and to check their status.
     * @param target The target location the player is attempting to enter. This is used to
     *               retrieve information about any events associated with the location.
     * @return true if the player successfully meets the entry requirements and can enter
     *         the room, false if they fail and cannot enter.
     * @throws IOException If an input or output error occurs during the event interaction.
     */
    private boolean processRoomEntryRequirements(Player player, MapLocations target) throws IOException {
        ChoiceEvent targetEvent = target.getEvent();

        // Se não há evento interativo, pode entrar
        if (targetEvent == null || targetEvent.getChoices().isEmpty()) {
            return true;
        }

        System.out.println("Evento na sala de destino: " + targetEvent.getDescription());
        handleEnigmaInteraction(player, targetEvent);

        if (player.isBlocked()) {
            System.out.println("Voce falhou no enigma e nao pode entrar na sala.");
            player.useTurn(); // Consome turno por falha
            return false;
        }

        return true;
    }

    /**
     * Handles the interaction between a player and an enigma (choice-based event).
     * This method presents the player with a list of available choices, allows
     * them to input their selection, and executes the corresponding action.
     *
     * @param player The player engaging with the enigma event. This parameter is
     *               used to determine the context of the interaction and execute
     *               event-specific actions affecting the player.
     * @param targetEvent The choice-based event to be presented to the player.
     *                    This contains the list of choices and the logic to
     *                    execute upon the player's selection.
     * @throws IOException If an input or output error occurs during player
     *                     interaction with the event.
     */
    private void handleEnigmaInteraction(Player player, ChoiceEvent targetEvent) throws IOException {
        ArrayUnorderedList<String> choices = targetEvent.getChoices();
        int answer = -1;
        int numChoices = choices.size();

        while (answer < 1 || answer > numChoices) {
            Iterator<String> choiceIt = choices.iterator();
            int c = 1;
            while (choiceIt.hasNext()) {
                System.out.println(c + ". " + choiceIt.next());
                c++;
            }
            System.out.print("Escolha sua resposta: ");
            try {
                answer = Integer.parseInt(reader.readLine());
                if (answer < 1 || answer > numChoices) {
                    System.out.println("Opcao invalida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida.");
            }
        }

        System.out.println(targetEvent.execute(player, answer - 1));
    }

    /**
     * Handles the movement of a player from their current location to a target location,
     * processes any associated events, and checks win conditions.
     *
     * @param player the player performing the move
     * @param target the target location the player is moving to
     */
    private void performMove(Player player, MapLocations target) {
        // Verifica Evento de Caminho (Aresta)
        Event edgeEvent = map.getGraph().getEdgeWeight(player.getCurrentLocation(), target);

        player.moveTo(target, map.getGraph());
        player.useTurn();

        if (edgeEvent != null) {
            System.out.println("Evento de Caminho: " + edgeEvent.execute(player));
        }

        // Executa evento da sala se não for interativo (já processado)
        ChoiceEvent targetEvent = target.getEvent();
        if (targetEvent != null && targetEvent.getChoices().isEmpty()) {
            System.out.println("Evento da Sala: " + targetEvent.execute(player));
        }

        checkWinCondition(player, target);
    }

    /**
     * Checks if the win condition for the game has been met. This method evaluates if the
     * specified target location is the "Treasure Room" and, if so, declares the player as the
     * winner and updates the game state to indicate that the game has ended.
     *
     * @param player The player currently attempting to check the location. This is used to
     *               retrieve the player's username for displaying the victory message.
     * @param target The location being checked by the player. If the location type is "Treasure Room",
     *               it triggers the win condition.
     */
    private void checkWinCondition(Player player, MapLocations target) {
        if (target.getType() == RoomType.TREASURE_ROOM) {
            System.out.println("\n************************************************");
            System.out.println("PARABENS " + player.getUsername() + "!");
            System.out.println("Voce encontrou a sala do Tesouro e VENCEU o jogo!");
            System.out.println("************************************************");
            gameRunning = false;
        }
    }
}