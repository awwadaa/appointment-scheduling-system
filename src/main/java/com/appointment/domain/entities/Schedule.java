package com.appointment.domain.entities;

import java.time.LocalDate;
import java.util.List;

import com.appointment.domain.valueobjects.TimeSlot;

/**
 * Represents a daily schedule containing available time slots.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class Schedule {

    private String scheduleId;
    private LocalDate date;
    private List<TimeSlot> availableSlots;

    /**
     * Constructs a Schedule object with all fields.
     * 
     * @param scheduleId the unique schedule id
     * @param date the schedule date
     * @param availableSlots the available time slots for the date
     */
    public Schedule(String scheduleId, LocalDate date, List<TimeSlot> availableSlots) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.availableSlots = availableSlots;
    }

    /**
     * Returns the schedule id.
     * 
     * @return the schedule id
     */
    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * Sets the schedule id.
     * 
     * @param scheduleId the schedule id to set
     */
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * Returns the schedule date.
     * 
     * @return the schedule date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the schedule date.
     * 
     * @param date the schedule date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the available time slots.
     * 
     * @return the list of available time slots
     */
    public List<TimeSlot> getAvailableSlots() {
        return availableSlots;
    }

    /**
     * Sets the available time slots.
     * 
     * @param availableSlots the list of slots to set
     */
    public void setAvailableSlots(List<TimeSlot> availableSlots) {
        this.availableSlots = availableSlots;
    }

    /**
     * Returns a string representation of the schedule.
     * 
     * @return a string containing schedule details
     */
    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", date=" + date +
                ", availableSlots=" + availableSlots +
                '}';
    }
}