package com.appointment.domain.valueobjects;

import java.time.LocalTime;

/**
 * Represents a time slot for appointment scheduling.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class TimeSlot {

    private String slotId;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean available;

    /**
     * Constructs a TimeSlot object with all fields.
     * 
     * @param slotId the unique slot identifier
     * @param startTime the start time of the slot
     * @param endTime the end time of the slot
     * @param available indicates whether the slot is available
     */
    public TimeSlot(String slotId, LocalTime startTime, LocalTime endTime, boolean available) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }

    /**
     * Returns the slot id.
     * 
     * @return the slot id
     */
    public String getSlotId() {
        return slotId;
    }

    /**
     * Sets the slot id.
     * 
     * @param slotId the slot id to set
     */
    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    /**
     * Returns the start time.
     * 
     * @return the start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     * 
     * @param startTime the start time to set
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time.
     * 
     * @return the end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     * 
     * @param endTime the end time to set
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns whether the slot is available.
     * 
     * @return true if available, false otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the slot availability.
     * 
     * @param available the availability status to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Returns a string representation of the time slot.
     * 
     * @return a string containing slot details
     */
    @Override
    public String toString() {
        return "TimeSlot{" +
                "slotId='" + slotId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", available=" + available +
                '}';
    }
}