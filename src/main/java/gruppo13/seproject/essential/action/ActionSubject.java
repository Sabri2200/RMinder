package gruppo13.seproject.essential.action;


public interface ActionSubject {
    void registerObserver(ActionObserver o);
    void removeObserver(ActionObserver o);
    void notifyObservers(Action a);
}
