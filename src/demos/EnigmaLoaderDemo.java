package demos;

import events.ChoiceEvent;
import events.ChoiceEventManager;
import files.EnigmaLoader;

public class EnigmaLoaderDemo {

    public static void main(String[] args) {
        ChoiceEventManager eventManager = EnigmaLoader.loadEnigmas();

        if (eventManager != null) {
            System.out.println("Total events loaded: " + eventManager.getEvents().size());
            System.out.println("\n--- First 3 Events ---");
            for (int i = 0; i < Math.min(3, eventManager.getEvents().size()); i++) {
                ChoiceEvent event = eventManager.getEvents().get(i);
                System.out.println(event.getId() +"  - " + event.getDescription());
                System.out.println("Choices: \n" + "\t"+event.getChoices());
            }
        } else {
            System.out.println(" Failed to load enigmas.");
        }
        System.out.println("\n=== End Enigma Loader Demo ===");
    }
}
