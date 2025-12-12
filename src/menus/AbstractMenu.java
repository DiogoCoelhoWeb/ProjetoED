package menus;

import map.Map;
import map.MapManager;

import java.io.BufferedReader;

public abstract class AbstractMenu {

    private MapManager mapManager;

    public AbstractMenu() {
        this.mapManager = new MapManager();
    }

    protected abstract void displayMenu();
    public abstract void runMenu();
    protected abstract void executeOption(int option);

    protected String readInput(String prompt) {
        System.out.print(prompt);
        try {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
            return reader.readLine();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading input");
        }
    }

    protected void printSeparator() {
        System.out.println("--------------------------------------------------");
    }
}
