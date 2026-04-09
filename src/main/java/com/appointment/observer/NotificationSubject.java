package com.appointment.observer;

import java.util.ArrayList;
import java.util.List;

import com.appointment.domain.entities.User;

/**
 * Subject class that manages observers and sends notifications.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class NotificationSubject {

    private List<Observer> observers;

    /**
     * Constructs a NotificationSubject object.
     */
    public NotificationSubject() {
        this.observers = new ArrayList<>();
    }

    /**
     * Adds an observer.
     * 
     * @param observer the observer to add
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer.
     * 
     * @param observer the observer to remove
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers.
     * 
     * @param user the user receiving the notification
     * @param message the notification message
     */
    public void notifyObservers(User user, String message) {
        for (Observer observer : observers) {
            observer.update(user, message);
        }
    }
}