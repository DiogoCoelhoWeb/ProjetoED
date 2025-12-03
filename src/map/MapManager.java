package map;

import lists.ArrayUnorderedList;

public class MapManager {
    private ArrayUnorderedList<Map> maps;

    public MapManager() {
        this.maps = new ArrayUnorderedList<>();
    }
    public void addMap(Map map) {
        this.maps.addToRear(map);
    }
    public ArrayUnorderedList<Map> getMaps() {
        return this.maps;
    }
    public Map getMap(String name) {
        for (Map map : this.maps) {
            if (map.getName().equals(name)) return map;
        }
        return null;
    }
    public void removeMap(String name) {
        this.maps.remove(getMap(name));
    }

}
