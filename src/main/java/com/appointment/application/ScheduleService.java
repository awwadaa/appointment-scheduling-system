package com.appointment.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.appointment.domain.entities.Schedule;
import com.appointment.domain.valueobjects.TimeSlot;
import com.appointment.persistence.ScheduleRepository;

/**
 * Service responsible for managing schedules and available slots.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    /**
     * Constructs a ScheduleService object.
     * 
     * @param scheduleRepository the schedule repository
     */
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * Returns available time slots for a specific date.
     * 
     * @param date the required date
     * @return list of available time slots
     */
    public List<TimeSlot> getAvailableSlots(LocalDate date) {
        Schedule schedule = scheduleRepository.findByDate(date);
        List<TimeSlot> availableSlots = new ArrayList<>();

        if (schedule != null) {
            for (TimeSlot slot : schedule.getAvailableSlots()) {
                if (slot.isAvailable()) {
                    availableSlots.add(slot);
                }
            }
        }

        return availableSlots;
    }

    /**
     * Checks whether a given slot is available on a specific date.
     * 
     * @param date the appointment date
     * @param timeSlot the time slot to check
     * @return true if the slot is available, false otherwise
     */
    public boolean isSlotAvailable(LocalDate date, TimeSlot timeSlot) {
        Schedule schedule = scheduleRepository.findByDate(date);

        if (schedule != null) {
            for (TimeSlot slot : schedule.getAvailableSlots()) {
                if (slot.getSlotId().equals(timeSlot.getSlotId()) && slot.isAvailable()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Marks a slot as unavailable after booking.
     * 
     * @param date the appointment date
     * @param timeSlot the slot to reserve
     */
    public void reserveSlot(LocalDate date, TimeSlot timeSlot) {
        Schedule schedule = scheduleRepository.findByDate(date);

        if (schedule != null) {
            for (TimeSlot slot : schedule.getAvailableSlots()) {
                if (slot.getSlotId().equals(timeSlot.getSlotId())) {
                    slot.setAvailable(false);
                    return;
                }
            }
        }
    }

    /**
     * Releases a slot and makes it available again.
     * 
     * @param date the appointment date
     * @param timeSlot the slot to release
     */
    public void releaseSlot(LocalDate date, TimeSlot timeSlot) {
        Schedule schedule = scheduleRepository.findByDate(date);

        if (schedule != null) {
            for (TimeSlot slot : schedule.getAvailableSlots()) {
                if (slot.getSlotId().equals(timeSlot.getSlotId())) {
                    slot.setAvailable(true);
                    return;
                }
            }
        }
    }
}