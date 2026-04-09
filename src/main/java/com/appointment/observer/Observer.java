package com.appointment.observer;

import com.appointment.domain.entities.User;

/**
 * Observer interface for sending notifications to users.
 * 
 * @author awwadaa
 * @version 1.0
 */
public interface Observer {

    /**
     * Sends a notification message to a user.
     * 
     * @param user the user who will receive the notification
     * @param message the notification message
     */
    void update(User user, String message);
}