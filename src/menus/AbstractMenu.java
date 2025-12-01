package menus;

import lists.ArrayUnorderedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public abstract class AbstractMenu {
    protected String title;
    protected ArrayUnorderedList<MenuOption> options; // Replaced List<MenuOption>
    protected BufferedReader reader; // Replaced Scanner

    public AbstractMenu(String title) { // Removed Scanner from constructor
        this.title = title;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.options = new ArrayUnorderedList<>(); // Use custom ArrayUnorderedList
    }

    protected void addOption(int id, String description) {
        options.addToRear(new MenuOption(id, description)); // Use addToRear
    }

    public void display() {
        System.out.println("\n--- " + title + " ---");
        Iterator<MenuOption> it = options.iterator();
        while(it.hasNext()){
            MenuOption option = it.next();
            System.out.println(option.getId() + ". " + option.getDescription());
        }
        System.out.print("Enter your choice: ");
    }

    public int getUserChoice() {
        String line;
        int choice = -1;
        try {
            line = reader.readLine();
            choice = Integer.parseInt(line);
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        return choice;
    }

    public void run() {
        int choice;
        do {
            display();
            choice = getUserChoice();
            if (choice != -1) { // Only execute if input was valid
                executeOption(choice);
            }
        } while (choice != getExitOptionId());
    }

    protected abstract void executeOption(int option);

    protected abstract int getExitOptionId(); // Subclasses define their exit option
}
