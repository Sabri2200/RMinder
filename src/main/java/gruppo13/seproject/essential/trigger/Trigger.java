package gruppo13.seproject.essential.trigger;

public interface Trigger {
    public boolean verify();
    public TriggerType getType();
    public String toString();
}
