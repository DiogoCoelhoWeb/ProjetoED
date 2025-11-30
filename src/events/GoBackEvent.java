package events;

public class GoBackEvent extends BuffDebuffEvent {

    public GoBackEvent() {
        this("Behold the Sphere! It laughs at thy retreat. Unworthy is this path.");
    }

    public GoBackEvent(String description) {
        super(description);
    }
}
