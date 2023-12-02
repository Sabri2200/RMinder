package coscarelli.seproject.essential.action;

import coscarelli.seproject.essential.action.ActionObserver;

public interface ActionSubject {
    void registerObserver(ActionObserver o);
    void removeObserver(ActionObserver o);
    void notifyObservers(Action a);
}
