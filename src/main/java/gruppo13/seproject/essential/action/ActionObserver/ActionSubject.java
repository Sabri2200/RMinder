package gruppo13.seproject.essential.action.ActionObserver;


import gruppo13.seproject.essential.action.Action;

public interface ActionSubject {
    void registerObserver(ActionObserver o);
    void removeObserver(ActionObserver o);
    void notifyObservers(Action a);
}
