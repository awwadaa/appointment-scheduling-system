package com.appointment.persistencetest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.Schedule;
import com.appointment.domain.valueobjects.TimeSlot;
import com.appointment.persistence.ScheduleRepository;

public class ScheduleRepositoryTest {

    private ScheduleRepository repository;
    private Schedule schedule;
    private LocalDate date;

    @BeforeEach
    public void setUp() {
        repository = new ScheduleRepository();
        date = LocalDate.now().plusDays(1);

        List<TimeSlot> slots = new ArrayList<>();
        slots.add(new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true));

        schedule = new Schedule("SCH1", date, slots);
    }

    @Test
    public void testSaveAndFindByDate() {
        repository.saveSchedule(schedule);
        assertEquals(schedule, repository.findByDate(date));
    }

    @Test
    public void testFindAll() {
        repository.saveSchedule(schedule);
        assertEquals(1, repository.findAll().size());
    }
}