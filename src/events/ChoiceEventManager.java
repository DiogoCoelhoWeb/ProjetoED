package events;

import lists.ArrayUnorderedList;

public class ChoiceEventManager {
    private ArrayUnorderedList<ChoiceEvent> events;

    public ChoiceEventManager() {
        this.events = new ArrayUnorderedList<>();
    }
    public void addEvent(ChoiceEvent event) {
        this.events.addToRear(event);
    }
    public ArrayUnorderedList<ChoiceEvent> getEvents() {
        return this.events;
    }

    public void removeEvent(ChoiceEvent event) {
        this.events.remove(event);
    }

    public ChoiceEvent getRandomChoiceEvent() {
        int randomIndex=0;
        do {
            randomIndex = (int) (Math.random() * this.events.size());
        } while (this.events.get(randomIndex) instanceof ImpossibleEnigma);

        return this.events.get(randomIndex);
    }

    public ImpossibleEnigma getRandomImpossibleEnigma() {
        ArrayUnorderedList<ImpossibleEnigma> impossibleEnigmas = getImpossibleEnigmas();
        return impossibleEnigmas.get((int) (Math.random() * impossibleEnigmas.size()));
    }

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
