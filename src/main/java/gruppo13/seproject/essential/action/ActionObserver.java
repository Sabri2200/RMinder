package gruppo13.seproject.essential.action;

public interface ActionObserver {
    public void execute(Action a);
    public void notifyError(Action a, Exception e);
}
