package com.appointment.domain.valueobjects;

import java.time.LocalDate;

/**
 * Represents a notification message sent to users.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class NotificationMessage {

    private String notificationId;
    private String message;
    private LocalDate sendDate;

    /**
     * Constructs a NotificationMessage object with all fields.
     * 
     * @param notificationId the unique notification id
     * @param message the notification message content
     * @param sendDate the date the message is sent
     */
    public NotificationMessage(String notificationId, String message, LocalDate sendDate) {
        this.notificationId = notificationId;
        this.message = message;
        this.sendDate = sendDate;
    }

    /**
     * Returns the notification id.
     * 
     * @return the notification id
     */
    public String getNotificationId() {
        return notificationId;
    }

    /**
     * Sets the notification id.
     * 
     * @param notificationId the notification id to set
     */
    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Returns the notification message.
     * 
     * @return the message content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the notification message.
     * 
     * @param message the message content to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the send date.
     * 
     * @return the send date
     */
    public LocalDate getSendDate() {
        return sendDate;
    }

    /**
     * Sets the send date.
     * 
     * @param sendDate the send date to set
     */
    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * Returns a string representation of the notification message.
     * 
     * @return a string containing notification details
     */
    @Override
    public String toString() {
        return "NotificationMessage{" +
                "notificationId='" + notificationId + '\'' +
                ", message='" + message + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}