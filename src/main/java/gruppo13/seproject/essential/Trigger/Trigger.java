package gruppo13.seproject.essential.Trigger;

public abstract class Trigger {
    TriggerType type;
    public Trigger(TriggerType type) {
        this.type = type;
    }

    public abstract Boolean verify();

    public abstract String toString();

    public TriggerType getType() {
        return type;
    }

}
