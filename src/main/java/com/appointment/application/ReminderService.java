package com.appointment.application;

import java.time.LocalDate;

import com.appointment.domain.entities.Appointment;
import com.appointment.domain.valueobjects.NotificationMessage;
import com.appointment.observer.NotificationSubject;

/**
 * Service responsible for creating and sending appointment reminders.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class ReminderService {

    private NotificationSubject notificationSubject;

    /**
     * Constructs a ReminderService object.
     * 
     * @param notificationSubject the notification subject
     */
    public ReminderService(NotificationSubject notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    /**
     * Creates a reminder message for an appointment.
     * 
     * @param appointment the appointment
     * @return the created notification message
     */
    public NotificationMessage createReminder(Appointment appointment) {
        String messageText = "Reminder: You have an appointment on "
                + appointment.getAppointmentDate()
                + " at "
                + appointment.getTimeSlot().getStartTime() + ".";

        return new NotificationMessage(
                "REM-" + appointment.getAppointmentId(),
                messageText,
                LocalDate.now()
        );
    }

    /**
     * Sends a reminder notification to the user.
     * 
     * @param appointment the appointment
     */
    public void sendReminder(Appointment appointment) {
        NotificationMessage reminder = createReminder(appointment);

        notificationSubject.notifyObservers(
                appointment.getUser(),
                reminder.getMessage()
        );
    }
}