package menus;

public class MapEditorMenu extends AbstractMenu {



    public MapEditorMenu(String title) {
        super("Create Map Menu");
        setUpOptions();
    }
    private void setUpOptions() {
       this.addOption(1, "Add Room");
       this.addOption(2, "Delete Room");
       this.addOption(3, "Exit");
    }

    @Override
    protected void executeOption(int option) {
    }

    @Override
    protected int getExitOptionId() {
        return 0;
    }
}
