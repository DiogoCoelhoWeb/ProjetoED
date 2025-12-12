package menus;

import map.MapManager;

import java.io.BufferedReader;

public abstract class AbstractMenu {

    private MapManager mapManager;

    /**
     * Constructs a new AbstractMenu instance.
     * Initializes the menu by creating a new instance of the MapManager, which is used to manage
     * and interact with a collection of maps throughout the lifecycle of the menu.
     */
    public AbstractMenu() {
        this.mapManager = new MapManager();
    }

    /**
     * Displays the menu options to the user. This method is abstract and must be implemented
     * by subclasses to provide specific menu options relevant to the context of the menu.
     *
     * Each subclass implementation should print the available options to the console,
     * providing the user with actionable choices for interaction.
     */
    protected abstract void displayMenu();

    /**
     * Executes the menu's primary functionality, allowing the user to interact with the menu.
     * The specific details of the menu behavior, such as displaying options, handling input,
     * and executing selections, are defined by the implementing class.
     *
     */
    public abstract void runMenu();

    /**
     * Executes the desired action based on the provided option. The behavior of this
     * method should be implemented by subclasses, as the logic for handling options
     * may vary depending on the specific menu.
     *
     * @param option the selected option to execute. It is expected to correspond to a valid
     *               option presented in the menu.
     */
    protected abstract void executeOption(int option);

    /**
     * Reads a line of input from the user after displaying the specified prompt message.
     *
     * @param prompt the message to display to the user before reading input
     * @return the input entered by the user as a String
     * @throws IllegalArgumentException if an error occurs while reading input
     */
    protected String readInput(String prompt) {
        System.out.print(prompt);
        try {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
            return reader.readLine();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading input");
        }
    }

    /**
     * Prints a visual separator line to the console.
     * This method is intended to enhance the readability and organization of console-based outputs
     * by providing a clear visual divider. It is commonly used to separate sections
     * of menu outputs or other displayed information.
     */
    protected void printSeparator() {
        System.out.println("--------------------------------------------------");
    }
}
