package com.appointment.observer;

import com.appointment.domain.entities.User;

/**
 * Observer implementation for SMS notifications.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class SMSNotificationObserver implements Observer {

    @Override
    public void update(User user, String message) {
        System.out.println("SMS sent to " + user.getPhone() + ": " + message);
    }
}