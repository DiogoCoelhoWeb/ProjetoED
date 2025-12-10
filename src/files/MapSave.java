package files;

import events.Event;
import graph.NetworkGraph;
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
        JSONObject mapJson = new JSONObject();

        // Map Metadata
        // Since Map doesn't have an ID, we generate one or use the name.
        // The PDF example uses "maps_1", we'll use "map_" + name hash or just name.
        mapJson.put("id", "map_" + map.getName().replaceAll("\\s+", "_").toLowerCase());
        mapJson.put("map_name", map.getName());

        // Rooms
        JSONArray roomsJson = new JSONArray();
        NetworkGraph<MapLocations> graph = map.getGraph();
        Object[] vertices = graph.getVertices();
        int numVertices = graph.getNumVertices();

        for (int i = 0; i < numVertices; i++) {
            MapLocations loc = (MapLocations) vertices[i];
            if (loc == null) continue;

            JSONObject roomJson = new JSONObject();
            String roomId = "room_" + loc.getId();
            roomJson.put("id", roomId);
            roomJson.put("name", loc.getName());
            
            // MapLocations doesn't have description, so we use a placeholder or event description
            if (loc.getEvent() != null) {
                roomJson.put("description", loc.getEvent().getDescription());
                roomJson.put("event", "event_room_" + loc.getId()); // or loc.getEvent().getId()
            } else {
                roomJson.put("description", "No description available");
                roomJson.put("event", null);
            }
            
            if (loc.isStart()) {
                roomJson.put("type", "entrance");
            } else if (loc.getType().equalsIgnoreCase("Treasure Room")) {
                 roomJson.put("type", "treasure");
            } else {
                 roomJson.put("type", "room");
            }

            roomsJson.add(roomJson);
        }
        mapJson.put("rooms", roomsJson);

        // Corridors
        JSONArray corridorsJson = new JSONArray();
        // To avoid duplicates in undirected graph, we track processed edges
        // But since we can't easily map objects to visited status without modifying them,
        // we can iterate and only add if id1 < id2.
        
        for (int i = 0; i < numVertices; i++) {
            MapLocations u = (MapLocations) vertices[i];
            if (u == null) continue;

            for (int j = i + 1; j < numVertices; j++) {
                MapLocations v = (MapLocations) vertices[j];
                if (v == null) continue;

                if (graph.veifyToVertex(u, v)) {
                    JSONObject corridorJson = new JSONObject();
                    corridorJson.put("origin", "room_" + u.getId());
                    corridorJson.put("destination", "room_" + v.getId());

                    Event event = graph.getEdgeWeight(u, v);
                    if (event != null) {
                        corridorJson.put("event", "event_corridor_" + event.getId());
                    } else {
                         corridorJson.put("event", null);
                    }

                    corridorsJson.add(corridorJson);
                }
            }
        }
        mapJson.put("corridors", corridorsJson);

        // Write to file
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(mapJson.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
