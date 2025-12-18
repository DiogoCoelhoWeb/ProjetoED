/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 */

import files.MapLoader;
import map.Map;
import rooms.MapLocations;

public class DefesaED {
    public static void main(String[] args){
        MapLoader loader = new MapLoader();

        Map map = loader.loadMap("SaveDemoMap");

        for (MapLocations location: map.getNoEnigmaLocations()){
            System.out.println(location);
        }
    }
}
