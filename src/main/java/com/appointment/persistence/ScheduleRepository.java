package com.appointment.persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.appointment.domain.entities.Schedule;

/**
 * Repository for managing schedules in memory.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class ScheduleRepository {

    private List<Schedule> schedules;

    /**
     * Constructs a ScheduleRepository object.
     */
    public ScheduleRepository() {
        this.schedules = new ArrayList<>();
    }

    /**
     * Saves a schedule to the repository.
     * 
     * @param schedule the schedule to save
     */
    public void saveSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    /**
     * Finds a schedule by date.
     * 
     * @param date the date to search for
     * @return the matching schedule, or null if not found
     */
    public Schedule findByDate(LocalDate date) {
        for (Schedule schedule : schedules) {
            if (schedule.getDate().equals(date)) {
                return schedule;
            }
        }
        return null;
    }

    /**
     * Returns all schedules.
     * 
     * @return the list of schedules
     */
    public List<Schedule> findAll() {
        return schedules;
    }
}