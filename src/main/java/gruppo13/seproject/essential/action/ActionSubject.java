package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.action.ActionObserver;

public interface ActionSubject {
    void registerObserver(ActionObserver o);
    void removeObserver(ActionObserver o);
    void notifyObservers(Action a);
}
