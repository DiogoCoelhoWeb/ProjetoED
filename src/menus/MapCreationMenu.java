package menus;

public class MapCreationMenu extends AbstractMenu{


    public MapCreationMenu(String mapName) {
        super(mapName);
    }

    protected void displayMenu() {
        System.out.println("Map Creation Menu");
        System.out.println("1. Create Room");
        System.out.println("2. Link Rooms");
        System.out.println("3. Visualize Map");
        System.out.println("4. Exit");
    }

    public void runMenu() {
        displayMenu();
        System.out.println("Please select an option: ");

    }


    protected void executeOption(int option) {
        do {
            switch (option) {
                case 1:
                    System.out.println("Creating room...");
                    break;
                case 2:
                    System.out.println("Linking rooms...");
                    break;
                case 3:
                    System.out.println("Visualizing map...");

                    break;
            }
        } while (option < 1 || option > 3);
    }

    private void createRoom() {

    }
}
