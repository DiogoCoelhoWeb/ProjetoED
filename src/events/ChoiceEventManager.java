package events;

import lists.ArrayUnorderedList;

public class ChoiceEventManager {
    private ArrayUnorderedList<ChoiceEvent> events;

    /**
     * Constructs an instance of {@code ChoiceEventManager}.
     *
     * Initializes the internal list of {@code ChoiceEvent} objects using
     * an {@code ArrayUnorderedList}, enabling the management of events
     * that present choices to players.
     */
    public ChoiceEventManager() {
        this.events = new ArrayUnorderedList<>();
    }

    /**
     * Adds a given {@code ChoiceEvent} to the end of the event list.
     *
     * @param event the {@code ChoiceEvent} to be added to the list
     */
    public void addEvent(ChoiceEvent event) {
        this.events.addToRear(event);
    }
    /**
     * Retrieves the list of all {@code ChoiceEvent} objects currently managed.
     *
     * @return an ArrayUnorderedList containing all the {@code ChoiceEvent} instances
     *         managed by this class
     */
    public ArrayUnorderedList<ChoiceEvent> getEvents() {
        return this.events;
    }

    /**
     * Retrieves a random {@code ChoiceEvent} from the list of events, ensuring
     * that the selected event is not an instance of {@code ImpossibleEnigma}.
     *
     * The method avoids selecting events that are represented by the
     * {@code ImpossibleEnigma} class to provide valid and resolvable gameplay choices.
     *
     * @return a randomly selected {@code ChoiceEvent} from the list of events,
     *         excluding instances of {@code ImpossibleEnigma}
     */
    public ChoiceEvent getRandomChoiceEvent() {
        int randomIndex=0;
        do {
            randomIndex = (int) (Math.random() * this.events.size()-1);
        } while ((this.events.get(randomIndex) instanceof ImpossibleEnigma));

        return this.events.get(randomIndex);
    }

    /**
     * Retrieves a random {@code ImpossibleEnigma} from the list of events.
     * An {@code ImpossibleEnigma} is a specific type of {@code ChoiceEvent}
     * that presents an unsolvable scenario to the player.
     *
     * @return a randomly selected {@code ImpossibleEnigma} from the list of events
     */
    public ImpossibleEnigma getRandomImpossibleEnigma() {
        ArrayUnorderedList<ImpossibleEnigma> impossibleEnigmas = getImpossibleEnigmas();
        return impossibleEnigmas.get((int) (Math.random() * impossibleEnigmas.size()));
    }

    /**
     * Retrieves a list of all events that are instances of ImpossibleEnigma
     * within the current set of events.
     *
     * @return an ArrayUnorderedList containing all ImpossibleEnigma instances
     *         from the events list
     */
    private ArrayUnorderedList<ImpossibleEnigma> getImpossibleEnigmas() {
        ArrayUnorderedList<ImpossibleEnigma> impossibleEnigmas = new ArrayUnorderedList<>();

        for (ChoiceEvent event : this.events) {
            if (event instanceof ImpossibleEnigma){
                impossibleEnigmas.addToRear((ImpossibleEnigma) event);
            }
        }
        return impossibleEnigmas;
    }

}
