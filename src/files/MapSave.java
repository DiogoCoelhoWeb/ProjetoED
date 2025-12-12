package files;

import events.*;
import graph.NetworkGraph;
import lists.ArrayUnorderedList;
import map.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rooms.MapLocations;
import java.io.FileWriter;
import java.io.IOException;

public class MapSave {

    private static final String MAP_PATH = "data/maps/";


    /**
     * Saves the given Map object to a JSON file at the specified file path.
     *
     * @param map      the Map object to save
     */
    public void saveMap(Map map) {
        String filePath = MAP_PATH + map.getName() + ".json";
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(filePath);
            JSONObject mapJson = serializeMap(map);
            fileWriter.write(mapJson.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            System.err.println("Error saving map to file: " + e.getMessage());
        }
    }

    /**
     * Serializes a {@link Map} object into a {@link JSONObject} representation.
     * This method creates a JSON object containing the map's name, locations, and corridors.
     * It utilizes other helper methods to serialize locations and corridors.
     *
     * @param map the {@link Map} object to be serialized
     * @return a {@link JSONObject} representing the serialized map data, including its name, locations, and corridors
     */
    private JSONObject serializeMap(Map map) {
        JSONObject mapJson = new JSONObject();
        mapJson.put("name", map.getName());
        mapJson.put("locations", serializeLocations(map));
        mapJson.put("corridors", serializeCorridors(map));
        return mapJson;
    }

    /**
     * Retrieves a list of all map locations from the specified map.
     * This method collects all vertices of the type {@code MapLocations} from the graph
     * associated with the given map and returns them in an {@code ArrayUnorderedList}.
     *
     * @param map the {@code Map} object containing the graph from which map locations are fetched
     * @return an {@code ArrayUnorderedList} of {@code MapLocations} containing all map locations in the graph
     */
    private ArrayUnorderedList<MapLocations> getLocationsList(Map map) {
        ArrayUnorderedList<MapLocations> locations = new ArrayUnorderedList<>();
        Object[] vertices = map.getGraph().getVertices();

        for (Object vertex : vertices) {
            if (vertex instanceof MapLocations) {
                locations.addToRear((MapLocations) vertex);
            }
        }

        return locations;
    }

    /**
     * Serializes the locations within a given map into a JSONArray representation.
     * This method retrieves all locations of the map, converts each location to
     * its JSON representation using the `serializeLocation` method, and adds them
     * to a JSON array.
     *
     * @param map the Map object containing the locations to be serialized
     * @return a JSONArray containing the serialized representations of the map's locations
     */
    private JSONArray serializeLocations(Map map) {
        JSONArray locationsJson = new JSONArray();
        ArrayUnorderedList<MapLocations> locations = getLocationsList(map);

        for (MapLocations location : locations) {
            locationsJson.add(serializeLocation(location));
        }

        return locationsJson;
    }

    /**
     * Serializes the specified {@link MapLocations} object into a {@link JSONObject}.
     * This method converts the map location's properties, such as its name, type,
     * and start status, into a JSON representation. If the map location has an associated
     * event, it is serialized as well using the {@code serializeLocationEvent} method.
     *
     * @param location the {@link MapLocations} object to be serialized
     * @return a {@link JSONObject} representing the serialized map location
     */
    private JSONObject serializeLocation(MapLocations location) {
        JSONObject locationJson = new JSONObject();
        locationJson.put("name", location.getName());
        locationJson.put("type", location.getType().ordinal());
        locationJson.put("isStart", location.isStart());

        if (location.getEvent() == null) {
            locationJson.put("event", null);
        } else {
            locationJson.put("event", serializeLocationEvent(location));
        }

        return locationJson;
    }

    /**
     * Serializes the location event data into a JSON object.
     * This method converts the event associated with the given MapLocations object
     * into a JSON representation, including its description, correct choice, and list of choices.
     *
     * @param location the MapLocations object containing the event to be serialized
     * @return a JSONObject representing the serialized event data, including description,
     *         correct choice, and choices
     */
    private JSONObject serializeLocationEvent(MapLocations location) {
        JSONObject eventJson = new JSONObject();
        eventJson.put("description", location.getEvent().getDescription());
        eventJson.put("correctChoice", ((EnigmaEvent) location.getEvent()).getCorrectChoice());
        eventJson.put("choices", serializeChoices(location.getEvent().getChoices()));
        return eventJson;
    }

    /**
     * Serializes a list of choices into a JSON array.
     *
     * @param choices the unordered list of choices to be serialized
     * @return a JSON array representing the serialized choices
     */
    private JSONArray serializeChoices(ArrayUnorderedList<String> choices) {
        JSONArray choicesJson = new JSONArray();
        for (String choice : choices) {
            choicesJson.add(choice);
        }
        return choicesJson;
    }

    /**
     * Serializes the corridors (connections between map locations) in the specified map
     * into a JSONArray representation. Each corridor is represented as a JSON object
     * containing the indices of the origin and destination locations, and any associated event.
     *
     * @param map the Map object containing the corridors to be serialized
     * @return a JSONArray representing the serialized corridors of the map, including the
     *         origin, destination, and associated event information
     */
    private JSONArray serializeCorridors(Map map) {
        JSONArray corridorsJson = new JSONArray();
        NetworkGraph<MapLocations> graph = map.getGraph();
        ArrayUnorderedList<MapLocations> locations = getLocationsList(map);

        for (MapLocations location: locations) {
            ArrayUnorderedList<MapLocations> neighbors = graph.getNeighbors(location);
            MapLocations from = location;

            for (MapLocations neighborList : neighbors) {
                JSONObject corridorJson = new JSONObject();
                corridorJson.put("origin", locations.indexOf(from));
                corridorJson.put("destination", locations.indexOf(neighborList));

                Event event = graph.getEdgeWeight(from, neighborList);

                if (event != null) {
                    JSONObject eventJson = serializeCorridorEvent((BuffDebuffEvent) event);
                    corridorJson.put("event", eventJson);
                } else {
                    corridorJson.put("event", null);
                }

                if(!corridorsJson.contains(corridorJson) && locations.indexOf(from) < locations.indexOf(neighborList)) {
                    corridorsJson.add(corridorJson);
                }
            }
        }

        return corridorsJson;
    }

    /**
     * Serializes a given BuffDebuffEvent instance into a JSONObject representation.
     * This method converts event-specific details, such as the event type, number of turns (if applicable),
     * and description, into a JSON format that can be used for further processing or storage.
     *
     * @param event the BuffDebuffEvent instance to be serialized; can be of type LoseTurnEvent or GoBackEvent
     * @return a JSONObject representing the serialized details of the event, including its type, description,
     *         and additional event-specific properties
     */
    private JSONObject serializeCorridorEvent(BuffDebuffEvent event) {
        JSONObject eventJson = new JSONObject();
        if (event instanceof LoseTurnEvent){
            LoseTurnEvent loseTurnEvent = (LoseTurnEvent) event;
            eventJson.put("type", BuffDebuffTypes.LOSE_TURN.toString().replace("_",""));
            eventJson.put("turns", loseTurnEvent.getNumTurns());
        }
        else if (event instanceof GoBackEvent){
            eventJson.put("type", BuffDebuffTypes.GO_BACK.toString().replace("_",""));
        }
        eventJson.put("description", event.getDescription());

        return eventJson;
    }
}
