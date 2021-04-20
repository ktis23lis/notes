package com.example.notes;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer){
        observers.add(observer);
    }

    public void unsubscribe(Observer observer){
        observers.remove(observer);
    }

    public void notifySingle(NameNotes nameNotes){
        for (Observer observer: observers){
            observer.updateCardData(nameNotes);
            unsubscribe(observer);
        }
    }
}
