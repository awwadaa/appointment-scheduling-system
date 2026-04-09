package com.appointment.observer;

import com.appointment.domain.entities.User;

/**
 * Observer implementation for email notifications.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class EmailNotificationObserver implements Observer {

    @Override
    public void update(User user, String message) {
        System.out.println("Email sent to " + user.getEmail() + ": " + message);
    }
}