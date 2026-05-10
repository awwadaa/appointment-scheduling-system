package com.appointment.observer;

/**
 * Represents an exception that occurs when an email cannot be sent.
 */
public class EmailSendingException extends RuntimeException {

    public EmailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
