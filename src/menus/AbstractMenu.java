package menus;


import java.io.BufferedReader;

public abstract class AbstractMenu {

    /**
     * Displays the menu options specific to the subclass implementing this method.
     * This method is intended to define how the menu is presented to the user
     * and should be overridden by subclasses to provide custom menu options.
     */
    protected abstract void displayMenu();
    /**
     * Executes the menu functionality, providing an interactive experience for the user.
     * This method should handle the complete lifecycle of the menu, including displaying
     * the menu options, handling user inputs, validating selections, and potentially
     * invoking appropriate actions or transitions based on the selected option.
     *
     */
    public abstract void runMenu();
    /**
     * Executes the specific action corresponding to the provided menu option.
     * This method is intended to be implemented by subclasses to define the behavior
     * for each menu option.
     *
     * @param option the menu option selected by the user
     */
    protected abstract void executeOption(int option);

    /**
     * Reads a line of input from the user after displaying a specified prompt message.
     *
     * @param prompt The message displayed to the user before reading their input.
     * @return The input provided by the user as a string.
     * @throws IllegalArgumentException if an error occurs while reading input.
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
     * Reads an integer input from the user after displaying a given prompt.
     * If the input is invalid or an error occurs during reading, an {@link IllegalArgumentException} is thrown.
     *
     * @param prompt The message displayed to the user prompting them to enter an integer.
     * @return The integer value entered by the user.
     * @throws IllegalArgumentException if the input is invalid or an error occurs during the reading process.
     */
    protected int readIntengerInput(String prompt) {
        System.out.print(prompt);
        try {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
            return Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading input");
        }
    }

    /**
     * Prints a visual separator line to the console.
     */
    protected void printSeparator() {
        System.out.println("--------------------------------------------------");
    }
}
