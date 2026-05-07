package com.appointment.applicationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.application.ScheduleService;
import com.appointment.domain.entities.Schedule;
import com.appointment.domain.valueobjects.TimeSlot;
import com.appointment.persistence.ScheduleRepository;

class ScheduleServiceTest {

    private ScheduleService scheduleService;
    private ScheduleRepository scheduleRepository;
    private LocalDate date;
    private TimeSlot slot1;
    private TimeSlot slot2;

    @BeforeEach
    void setUp() {
        scheduleRepository = new ScheduleRepository();
        scheduleService = new ScheduleService(scheduleRepository);

        date = LocalDate.now().plusDays(1);
        slot1 = new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true);
        slot2 = new TimeSlot("S2", LocalTime.of(10, 0), LocalTime.of(11, 0), false);

        List<TimeSlot> slots = new ArrayList<>();
        slots.add(slot1);
        slots.add(slot2);

        scheduleRepository.saveSchedule(new Schedule("SCH1", date, slots));
    }

    @Test
    void testGetAvailableSlots() {
        List<TimeSlot> result = scheduleService.getAvailableSlots(date);

        assertEquals(1, result.size());
        assertEquals("S1", result.get(0).getSlotId());
    }

    @Test
    void testSlotAvailable() {
        assertTrue(scheduleService.isSlotAvailable(date, slot1));
        assertFalse(scheduleService.isSlotAvailable(date, slot2));
    }

    @Test
    void testReserveSlot() {
        scheduleService.reserveSlot(date, slot1);

        assertFalse(slot1.isAvailable());
    }

    @Test
    void testReleaseSlot() {
        scheduleService.releaseSlot(date, slot2);

        assertTrue(slot2.isAvailable());
    }

    @Test
    void testGetAvailableSlotsWhenScheduleNotFound() {
        List<TimeSlot> result = scheduleService.getAvailableSlots(LocalDate.now().plusDays(5));

        assertTrue(result.isEmpty());
    }
}
