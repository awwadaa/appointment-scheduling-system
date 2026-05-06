package com.appointment.observer;

/**
 * Email sender abstraction for sending emails.
 * 
 * @author awwadaa
 * @version 1.0
 */
public interface EmailSender {

    /**
     * Sends an email message.
     *
     * @param recipientEmail recipient email address
     * @param subject email subject
     * @param body email body
     */
    void send(String recipientEmail, String subject, String body);
}