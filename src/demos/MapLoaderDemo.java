package demos;

import events.ChoiceEventManager;
import files.EnigmaLoader;
import files.MapLoader;
import map.Map;
import rooms.MapLocations;

import java.util.Iterator;

public class MapLoaderDemo {

    public static void main(String[] args) {

        MapLoader map = new MapLoader();
        Map map1 = map.loadMap("SaveDemoMap");

        Iterator<MapLocations> itr = map1.getStartLocation().iterator();
        while(itr.hasNext()){
            MapLocations loc = itr.next();
            System.out.println(loc.getName());
        }
    }
}
