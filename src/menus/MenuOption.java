package menus;

public class MenuOption {
    private int id;
    private String description;

    public MenuOption(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
