package com.appointment.domain.entities;

import java.time.LocalDate;

import com.appointment.domain.enums.AppointmentStatus;
import com.appointment.domain.enums.AppointmentType;
import com.appointment.domain.valueobjects.TimeSlot;

/**
 * Represents an appointment booked by a user.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class Appointment {

    private String appointmentId;
    private User user;
    private LocalDate appointmentDate;
    private TimeSlot timeSlot;
    private int durationMinutes;
    private int participantCount;
    private AppointmentType appointmentType;
    private AppointmentStatus status;

    /**
     * Constructs an Appointment object with all fields.
     * 
     * @param appointmentId the unique appointment id
     * @param user the user who booked the appointment
     * @param appointmentDate the date of the appointment
     * @param timeSlot the selected time slot
     * @param durationMinutes the appointment duration in minutes
     * @param participantCount the number of participants
     * @param appointmentType the type of the appointment
     * @param status the current appointment status
     */
    public Appointment(String appointmentId, User user, LocalDate appointmentDate, TimeSlot timeSlot,
            int durationMinutes, int participantCount, AppointmentType appointmentType, AppointmentStatus status) {
        this.appointmentId = appointmentId;
        this.user = user;
        this.appointmentDate = appointmentDate;
        this.timeSlot = timeSlot;
        this.durationMinutes = durationMinutes;
        this.participantCount = participantCount;
        this.appointmentType = appointmentType;
        this.status = status;
    }

    /**
     * Returns the appointment id.
     * 
     * @return the appointment id
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment id.
     * 
     * @param appointmentId the appointment id to set
     */
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Returns the user who booked the appointment.
     * 
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who booked the appointment.
     * 
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the appointment date.
     * 
     * @return the appointment date
     */
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the appointment date.
     * 
     * @param appointmentDate the appointment date to set
     */
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Returns the time slot.
     * 
     * @return the time slot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Sets the time slot.
     * 
     * @param timeSlot the time slot to set
     */
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Returns the appointment duration in minutes.
     * 
     * @return the duration in minutes
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Sets the appointment duration in minutes.
     * 
     * @param durationMinutes the duration to set
     */
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * Returns the number of participants.
     * 
     * @return the participant count
     */
    public int getParticipantCount() {
        return participantCount;
    }

    /**
     * Sets the number of participants.
     * 
     * @param participantCount the participant count to set
     */
    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    /**
     * Returns the appointment type.
     * 
     * @return the appointment type
     */
    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    /**
     * Sets the appointment type.
     * 
     * @param appointmentType the appointment type to set
     */
    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * Returns the appointment status.
     * 
     * @return the appointment status
     */
    public AppointmentStatus getStatus() {
        return status;
    }

    /**
     * Sets the appointment status.
     * 
     * @param status the appointment status to set
     */
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the appointment.
     * 
     * @return a string containing appointment details
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", user=" + user +
                ", appointmentDate=" + appointmentDate +
                ", timeSlot=" + timeSlot +
                ", durationMinutes=" + durationMinutes +
                ", participantCount=" + participantCount +
                ", appointmentType=" + appointmentType +
                ", status=" + status +
                '}';
    }
}