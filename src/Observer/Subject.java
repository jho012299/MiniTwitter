package Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    } // not needed

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
