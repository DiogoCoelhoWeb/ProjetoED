package files;

import events.BuffDebuffEvent;
import events.ChoiceEvent;
import events.ChoiceEventManager;
import lists.ArrayUnorderedList;
import map.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import rooms.MapLocations;
import rooms.Room;
import rooms.Treasure;

import java.io.FileReader;
import java.util.Iterator;

public class MapLoader {




    /**
     * Loads a map from a JSON file.
     *
     * @param filePath     The path to the JSON map file.
     * @param eventManager The manager to provide events for the rooms.
     * @return A constructed Map object, or null if loading failed.
     */
    public Map loadMap(String filePath, ChoiceEventManager eventManager) {
        JSONParser parser = new JSONParser();
        Map map = null;

        try (FileReader reader = new FileReader(filePath)) {
            Object obj = parser.parse(reader);
            JSONObject rootObject = (JSONObject) obj;

            // 1. Create Map Object
            String mapName = (String) rootObject.get("map_name");
            if (mapName == null) mapName = "Untitled Map";
            map = new Map(mapName);

            // Temporary storage to link rooms by their JSON ID (e.g., "room_1")
            // Replaced HashMap with parallel ArrayUnorderedLists to comply with project restrictions
            ArrayUnorderedList<String> roomIds = new ArrayUnorderedList<>();
            ArrayUnorderedList<MapLocations> roomLocations = new ArrayUnorderedList<>();

            // 2. Process Rooms
            JSONArray roomsArray = (JSONArray) rootObject.get("rooms");
            if (roomsArray != null) {
                for (Object item : roomsArray) {
                    JSONObject roomJson = (JSONObject) item;
                    String id = (String) roomJson.get("id");
                    String name = (String) roomJson.get("name");
                    String type = (String) roomJson.get("type");
                    Object eventObj = roomJson.get("event");
                    
                    // Determine Event (only embedded JSON objects are supported now)
                    ChoiceEvent event = null;
                    if (eventObj instanceof JSONObject) {
                        event = parseEmbeddedEvent((JSONObject) eventObj);
                    } else if (eventObj != null) {
                        System.err.println("Warning: Event for room " + name + " is not an embedded JSON object. Skipping event.");
                    }

                    // Create Room Instance
                    MapLocations location;
                    if ("treasure".equalsIgnoreCase(type) || "Treasure Room".equalsIgnoreCase(type)) {
                        location = new Treasure(name, event);
                    } else {
                        boolean isStart = "entrance".equalsIgnoreCase(type);
                        location = new Room(name, event, isStart);
                    }

                    map.addLocation(location);
                    roomIds.addToRear(id);
                    roomLocations.addToRear(location);
                }
            }

            // 3. Process Corridors
            JSONArray corridorsArray = (JSONArray) rootObject.get("corridors");
            if (corridorsArray != null) {
                for (Object item : corridorsArray) {
                    JSONObject corridorJson = (JSONObject) item;
                    String originId = (String) corridorJson.get("origin"); // "origin" in example, check alternative "from"
                    String destId = (String) corridorJson.get("destination"); // "destination" in example, check alternative "to"

                    if (originId == null) originId = (String) corridorJson.get("from");
                    if (destId == null) destId = (String) corridorJson.get("to");

                    MapLocations origin = findLocationById(roomIds, roomLocations, originId);
                    MapLocations destination = findLocationById(roomIds, roomLocations, destId);

                    if (origin != null && destination != null) {
                        BuffDebuffEvent corridorEvent = null;
                        Object corridorEventObj = corridorJson.get("event");

                        if (corridorEventObj instanceof JSONObject) {
                            corridorEvent = parseEmbeddedBuffDebuffEvent((JSONObject) corridorEventObj);
                        } else if (corridorEventObj != null) {
                            System.err.println("Warning: Event for corridor " + originId + " -> " + destId + " is not an embedded JSON object. Skipping event.");
                        }

                        map.addConnection(origin, destination, corridorEvent);

                    } else {
                        System.err.println("Warning: Skipping corridor between undefined rooms: " + originId + " -> " + destId);
                    }
                }
            }

            System.out.println("--- Map Loaded Successfully: " + map.getName() + " ---");

        } catch (Exception e) {
            System.err.println("FATAL ERROR loading map: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return map;
    }

    /**
     * Helper method to find a location by its JSON ID using parallel ArrayUnorderedLists.
     */
    private MapLocations findLocationById(ArrayUnorderedList<String> ids, ArrayUnorderedList<MapLocations> locations, String id) {
        if (id == null) return null;
        Iterator<String> idIterator = ids.iterator();
        Iterator<MapLocations> locationIterator = locations.iterator();
        while (idIterator.hasNext() && locationIterator.hasNext()) {
            String currentId = idIterator.next();
            MapLocations currentLocation = locationIterator.next();
            if (currentId.equals(id)) {
                return currentLocation;
            }
        }
        return null;
    }

    /**
     * Parses an embedded event object from the JSON map file.
     * Supports "enigma", "impossible", "lever", and "swap" types.
     *
     * @param eventJson The JSON object defining the event.
     * @return The constructed ChoiceEvent, or null if parsing fails.
     */
    private ChoiceEvent parseEmbeddedEvent(JSONObject eventJson) {
        String type = (String) eventJson.get("type");
        String description = (String) eventJson.get("question");
        if (description == null) description = (String) eventJson.get("description");

        ChoiceEvent event = null;

        if ("enigma".equalsIgnoreCase(type)) {
            Long correctLong = (Long) eventJson.get("correct");
            int correct = (correctLong != null) ? correctLong.intValue() : 0;
            event = new events.EnigmaEvent(description, correct);
        } else if ("impossible".equalsIgnoreCase(type)) {
            String response = (String) eventJson.get("response_quote");
            event = new events.ImpossibleEnigma(description, response);
        } else if ("lever".equalsIgnoreCase(type)) {
            Long correctLong = (Long) eventJson.get("correct");
            int correct = (correctLong != null) ? correctLong.intValue() : 0;
            event = new events.LeverEvent(description, correct);
        } else if ("swap".equalsIgnoreCase(type)) { // Added SwapEvent parsing
            event = new events.SwapEvent();
            if (description == null) description = "You have stepped into a dimensional rift!";
            event.setDescription(description);
        }

        if (event != null) {
            JSONArray choices = (JSONArray) eventJson.get("choices");
            if (choices != null) {
                for (Object c : choices) {
                    event.addChoice((String) c);
                }
            }
        }
        return event;
    }

    /**
     * Parses an embedded JSON object to create a BuffDebuffEvent for a corridor.
     * Supports "LoseTurnEvent" and "GoBackEvent" types.
     *
     * @param eventJson The JSON object defining the BuffDebuffEvent.
     * @return The constructed BuffDebuffEvent, or null if parsing fails.
     */
    private BuffDebuffEvent parseEmbeddedBuffDebuffEvent(JSONObject eventJson) {
        String type = (String) eventJson.get("type");
        String description = (String) eventJson.get("description");
        if (description == null) description = "Corridor Event"; // Default description

        BuffDebuffEvent event = null;

        if ("LoseTurnEvent".equalsIgnoreCase(type)) {
            Long value = (Long) eventJson.get("value");
            if (value != null) {
                event = new events.LoseTurnEvent(value.intValue());
            } else {
                event = new events.LoseTurnEvent();
            }
        } else if ("GoBackEvent".equalsIgnoreCase(type)) {
            event = new events.GoBackEvent();
        }
        
        if (event != null) {
            event.setDescription(description);
        }
        return event;
    }
}

