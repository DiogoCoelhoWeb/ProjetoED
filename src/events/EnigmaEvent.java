package events;

public class EnigmaEvent extends ChoiceEvent{

    private int correctChoice;

    public EnigmaEvent(String description, int correctChoice) {
        super(description);
        this.correctChoice = correctChoice;
    }
}
