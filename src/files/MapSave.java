package files;

import events.EnigmaEvent;
import events.Event;
import graph.NetworkGraph;
import lists.ArrayUnorderedList;
import map.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rooms.MapLocations;
import rooms.RoomType;

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

    private JSONObject serializeMap(Map map) {
        JSONObject mapJson = new JSONObject();
        mapJson.put("name", map.getName());
        mapJson.put("locations", serializeLocations(map));
        mapJson.put("corridors", serializeCorridors(map));
        return mapJson;
    }

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

    private JSONArray serializeLocations(Map map) {
        JSONArray locationsJson = new JSONArray();
        ArrayUnorderedList<MapLocations> locations = getLocationsList(map);

        for (MapLocations location : locations) {
            locationsJson.add(serializeLocation(location));
        }

        return locationsJson;
    }

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

    private JSONObject serializeLocationEvent(MapLocations location) {
        JSONObject eventJson = new JSONObject();
        eventJson.put("description", location.getEvent().getDescription());
        eventJson.put("correctChoice", ((EnigmaEvent) location.getEvent()).getCorrectChoice());
        eventJson.put("choices", serializeChoices(location.getEvent().getChoices()));
        return eventJson;
    }

    private JSONArray serializeChoices(ArrayUnorderedList<String> choices) {
        JSONArray choicesJson = new JSONArray();
        for (String choice : choices) {
            choicesJson.add(choice);
        }
        return choicesJson;
    }

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
                    corridorJson.put("event", event.getDescription());
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
}
