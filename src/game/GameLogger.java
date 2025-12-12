package game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser; // NEW
import org.json.simple.parser.ParseException; // NEW
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import lists.ArrayUnorderedList;
import player.Player;
import player.PlayerManager;
import stack.LinkedStack;
import rooms.MapLocations;
import events.Event; // NEW

public class GameLogger {
    private ArrayUnorderedList<String> logs;

    public GameLogger() {
        this.logs = new ArrayUnorderedList<>();
    }

    /**
     * Logs a message with a timestamp. The message is added to the log storage.
     *
     * @param message The message to be logged.
     */
    public void log(String message) {
        logs.addToRear(message); 
    }

    /**
     * Logs a structured event with player context and outcome.
     * @param player The player involved in the event.
     * @param event The event object.
     * @param outcomeMessage A message describing the outcome or effect of the event.
     */
    public void logEvent(Player player, Event event, String outcomeMessage) {
        JSONObject eventLog = new JSONObject();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        
        eventLog.put("timestamp", timestamp);
        eventLog.put("type", "EVENT");
        eventLog.put("player_username", player.getUsername());
        eventLog.put("event_type", event.getClass().getSimpleName());
        eventLog.put("event_description", event.getDescription());
        eventLog.put("outcome", outcomeMessage);
        
        logs.addToRear(eventLog.toJSONString());
    }

    /**
     * Saves a game report containing the game log and player path histories to a JSON file.
     * The report includes structured event logs and each player's path history during the game.
     *
     * @param playerManager the PlayerManager instance that provides access to the list of players
     *                      and their respective path histories
     */
    public void saveReport(PlayerManager playerManager) {
        JSONObject report = new JSONObject();
        

        JSONArray logArray = new JSONArray();
        Iterator<String> logIt = logs.iterator();
        JSONParser parser = new JSONParser(); // New parser instance
        while(logIt.hasNext()){
            String logEntry = logIt.next();

            try {
                JSONObject parsedLog = (JSONObject) parser.parse(logEntry);
                logArray.add(parsedLog);
            } catch (ParseException | ClassCastException e) {
                JSONObject simpleLog = new JSONObject();
                simpleLog.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                simpleLog.put("type", "MESSAGE");
                simpleLog.put("message", logEntry);
                logArray.add(simpleLog);
            }
        }
        report.put("game_log", logArray);

        JSONArray playersArray = new JSONArray();
        Iterator<Player> playerIt = playerManager.getPlayers().iterator();
        while(playerIt.hasNext()){
            Player player = playerIt.next();
            JSONObject playerJson = new JSONObject();
            playerJson.put("username", player.getUsername());
            
            JSONArray pathArray = new JSONArray();
            LinkedStack<MapLocations> path = player.getPathHistory();
            
            LinkedStack<MapLocations> tempStack = new LinkedStack<>();
            while (!path.isEmpty()) {
                tempStack.push(path.pop());
            }

            while (!tempStack.isEmpty()) {
                MapLocations loc = tempStack.pop();
                pathArray.add(loc.getName());
                path.push(loc);
            }
            playerJson.put("path_history", pathArray);
            playersArray.add(playerJson);
        }
        report.put("players", playersArray);

        report.put("timestamp", LocalDateTime.now().toString());

        String filename = "data/Reports/game_report_" + System.currentTimeMillis() + ".json";
        try (FileWriter file = new FileWriter(filename)) {
            file.write(report.toJSONString());
            System.out.println("Game report saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving game report: " + e.getMessage());
        }
    }
}
