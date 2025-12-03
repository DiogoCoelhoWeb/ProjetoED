package map;

import lists.ArrayUnorderedList;

public class MapManager {
    private ArrayUnorderedList<Map> maps;

    /**
     * Constructs a new instance of MapManager.
     * Initializes the MapManager with an empty list to manage multiple maps.
     * Each map can be added to this collection for further operations, such as retrieval or removal.
     */
    public MapManager() {
        this.maps = new ArrayUnorderedList<>();
    }
    /**
     * Adds a map to the collection of managed maps.
     *
     * @param map the map to be added to the collection
     */
    public void addMap(Map map) {
        this.maps.addToRear(map);
    }
    /**
     * Retrieves the list of maps managed by the MapManager.
     *
     * @return an ArrayUnorderedList containing all the maps managed by this instance
     */
    public ArrayUnorderedList<Map> getMaps() {
        return this.maps;
    }
    /**
     * Retrieves a map from the collection based on its name.
     * If no map with the specified name exists in the collection, this method returns {@code null}.
     *
     * @param name the name of the map to retrieve
     * @return the map with the specified name, or {@code null} if no matching map is found
     */
    public Map getMap(String name) {
        for (Map map : this.maps) {
            if (map.getName().equals(name)) return map;
        }
        return null;
    }
    /**
     * Removes the map with the specified name from the list of maps.
     * If the map does not exist, no action is taken.
     *
     * @param name the name of the map to be removed
     */
    public void removeMap(String name) {
        this.maps.remove(getMap(name));
    }

}
