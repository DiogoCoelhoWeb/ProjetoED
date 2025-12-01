package demos;

import menus.MainMenu;

public class MenuDemo {
    public static void main(String[] args) {
        System.out.println("--- Menu Demo ---");
        MainMenu mainMenu = new MainMenu();
        mainMenu.run();
        System.out.println("--- Menu Demo Finished ---");
    }
}
