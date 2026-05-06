package com.appointment.observer;

import com.appointment.domain.entities.User;

/**
 * Observer implementation for email notifications.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class EmailNotificationObserver implements Observer {

    private final EmailSender emailSender;

    /**
     * Constructs an email notification observer.
     *
     * @param emailSender email sender implementation
     */
    public EmailNotificationObserver(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * Sends an email notification to the user.
     *
     * @param user the user who will receive the email
     * @param message the notification message
     */
    @Override
    public void update(User user, String message) {
        emailSender.send(user.getEmail(), "Appointment Reminder", message);
    }
}