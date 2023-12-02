package gruppo13.seproject.essential.rule.ListObserver;

public interface ListSubject {
    void registerObserver(ListObserver o);
    void removeObserver(ListObserver o);
    void notifyObservers();
}
