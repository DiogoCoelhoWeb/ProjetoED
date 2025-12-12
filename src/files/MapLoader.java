package files;

import events.*;
import lists.ArrayUnorderedList;
import map.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import rooms.MapLocations;
import rooms.Room;
import rooms.Treasure;

import java.io.FileReader;



public class MapLoader {


    private static String mapPath = "data/maps/";


    /**
     * Loads a map from a JSON file, constructs the map object, and populates it
     * with rooms and connections based on the data in the file.
     *
     * @param mapName the name of the map file to be loaded, without the file extension
     * @return a Map object populated with locations and their connections
     * @throws RuntimeException if there is an error while reading or parsing the file
     */
    public Map loadMap(String mapName) {

        JSONParser parser = new JSONParser();
        Map map = new Map(mapName);
        ArrayUnorderedList<MapLocations> createdLocations = new ArrayUnorderedList<>();

        try (FileReader reader = new FileReader(mapPath + mapName + ".json")){

            Object object = parser.parse(reader);
            JSONObject rootObject = (JSONObject) object;

            loadRooms(rootObject, createdLocations,map);
            loadConnections(rootObject, map, createdLocations);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * Loads the rooms from the given JSON object and populates the map with
     * corresponding locations. The method processes the "locations" array in
     * the JSON, creates MapLocations objects based on their type, and adds
     * them to the provided map and a list of created locations.
     *
     * @param rootObject the JSON object containing the map's data, including the "locations" array
     * @param createdLocations an unordered list to store the created MapLocations objects
     * @param map the map to which the loaded locations will be added
     */
    private void loadRooms(JSONObject rootObject,  ArrayUnorderedList<MapLocations> createdLocations, Map map) {
        JSONArray locationsArray = (JSONArray) rootObject.get("locations");
        if (locationsArray != null) {
            for (Object locObj : locationsArray) {
                JSONObject locJson = (JSONObject) locObj;
                String name = (String) locJson.get("name");
                boolean isStart = (boolean) locJson.get("isStart");
                int type = ((Number) locJson.get("type")).intValue();

                // Load Event
                JSONObject eventJson = (JSONObject) locJson.get("event");
                ChoiceEvent event = null;
                if (eventJson != null) {
                    String description = (String) eventJson.get("description");
                    int correctChoice = 0;
                    if (eventJson.containsKey("correctChoice")) {
                        correctChoice = ((Number) eventJson.get("correctChoice")).intValue();
                    }
                    event = new EnigmaEvent(description, correctChoice);

                    JSONArray choicesArray = (JSONArray) eventJson.get("choices");
                    if (choicesArray != null) {
                        for (Object choiceObj : choicesArray) {
                            event.addChoice((String) choiceObj);
                        }
                    }
                }

                MapLocations location;
                switch (type) {
                    case 2: // Treasure Room
                        location = new Treasure(name, event);
                        break;
                    case 0: // Entrance Hall
                    case 1: // Room
                    default:
                        location = new Room(name, event, isStart);
                        break;
                }

                map.addLocation(location);
                createdLocations.addToRear(location);
            }
        }
    }

    /**
     * Populates the map's connections based on the data provided in the JSON object.
     * The method processes the "corridors" array from the JSON file, creating connections
     * between locations in the map, with optional events associated with those connections.
     *
     * @param rootObject the JSON object containing the map's data, specifically the "corridors" array
     * @param map the map to which the connections will be added
     * @param createdLocations an unordered list of pre-created MapLocations objects used to establish connections
     */
    private void loadConnections(JSONObject rootObject, Map map, ArrayUnorderedList<MapLocations> createdLocations) {
        JSONArray corridorsArray = (JSONArray) rootObject.get("corridors");
        if (corridorsArray != null) {
            for (Object corrObj : corridorsArray) {
                JSONObject corrJson = (JSONObject) corrObj;
                int originIdx = ((Number) corrJson.get("origin")).intValue();
                int destIdx = ((Number) corrJson.get("destination")).intValue();

                if (originIdx >= 0 && originIdx < createdLocations.size() &&
                        destIdx >= 0 && destIdx < createdLocations.size()) {

                    MapLocations origin = createdLocations.get(originIdx);
                    MapLocations dest = createdLocations.get(destIdx);

                    JSONObject eventJson = (JSONObject) corrJson.get("event");
                    BuffDebuffEvent event = null;

                    if (eventJson != null) {
                        String description = (String) eventJson.get("description");
                        String rawType = (String) eventJson.get("type");

                        // Normalização
                        String type = rawType != null ? rawType.toUpperCase().replace("_", "") : "";

                        if (type.equals("LOSETURN")) {
                            int numTurns = eventJson.containsKey("turns") ? ((Number) eventJson.get("turns")).intValue() : 1;
                            event = new LoseTurnEvent(numTurns, description);
                        } else if (type.equals("GOBACK")) {
                            event = new GoBackEvent(description);
                        }
                    }

                    if (event != null) {
                        map.addConnection(origin, dest, event);
                    } else {
                        map.addConnection(origin, dest);
                    }
                }
            }
        }
    }


}

