package events;

import lists.ArrayUnorderedList;

public class ChoiceEventManager {
    private ArrayUnorderedList<ChoiceEvent> events;

    /**
     * Constructs a new instance of ChoiceEventManager.
     *
     * Initializes an empty list to store events. This manager is designed
     * to handle and manage a variety of ChoiceEvent objects, such as adding,
     * removing, and retrieving events from the list.
     */
    public ChoiceEventManager() {
        this.events = new ArrayUnorderedList<>();
    }
    /**
     * Adds a {@link ChoiceEvent} to the list of events managed by this {@code ChoiceEventManager}.
     *
     * @param event the {@link ChoiceEvent} to be added to the list of events. Must not be null.
     * @throws NullPointerException if the provided {@code event} is null.
     */
    public void addEvent(ChoiceEvent event) {
        this.events.addToRear(event);
    }
    /**
     * Retrieves the list of all ChoiceEvents managed by the ChoiceEventManager.
     *
     * @return an ArrayUnorderedList of ChoiceEvent objects representing the currently managed events
     */
    public ArrayUnorderedList<ChoiceEvent> getEvents() {
        return this.events;
    }

    /**
     * Removes a specified ChoiceEvent from the event list.
     *
     * @param event the ChoiceEvent to be removed from the list
     */
    public void removeEvent(ChoiceEvent event) {
        this.events.remove(event);
    }

    /**
     * Retrieves a random ChoiceEvent from the list of available events, ensuring
     * that the selected event is not an instance of ImpossibleEnigma.
     *
     * @return a random ChoiceEvent that is not an ImpossibleEnigma
     */
    public ChoiceEvent getRandomChoiceEvent() {
        int randomIndex=0;
        do {
            randomIndex = (int) (Math.random() * this.events.size());
        } while (this.events.get(randomIndex) instanceof ImpossibleEnigma);

        return this.events.get(randomIndex);
    }

    /**
     * Retrieves a random ImpossibleEnigma from the list of events. This method collects
     * all ImpossibleEnigma instances currently present in the events list, and then selects
     * one randomly.
     *
     * @return a randomly selected ImpossibleEnigma from the available events
     */
    public ImpossibleEnigma getRandomImpossibleEnigma() {
        ArrayUnorderedList<ImpossibleEnigma> impossibleEnigmas = getImpossibleEnigmas();
        return impossibleEnigmas.get((int) (Math.random() * impossibleEnigmas.size()));
    }

    /**
     * Retrieves a list of all events that are instances of ImpossibleEnigma
     * from the current collection of events.
     *
     * @return an ArrayUnorderedList containing all ImpossibleEnigma objects
     *         present in the events collection
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
