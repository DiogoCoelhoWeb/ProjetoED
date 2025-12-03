package menus;

import map.Map;
import map.MapManager;

public abstract class AbstractMenu {

    private MapManager mapManager;

    public AbstractMenu(String mapName) {
        this.mapManager = new MapManager();
    }

    protected abstract void displayMenu();
    public abstract void runMenu();
    protected abstract void executeOption(int option);
}
