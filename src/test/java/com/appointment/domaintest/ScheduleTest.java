package com.appointment.domaintest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.Schedule;
import com.appointment.domain.valueobjects.TimeSlot;

public class ScheduleTest {

    @Test
    public void testScheduleConstructorGettersSettersAndToString() {
        List<TimeSlot> slots = new ArrayList<>();
        slots.add(new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true));

        Schedule schedule = new Schedule("SCH1", LocalDate.now().plusDays(1), slots);

        assertEquals("SCH1", schedule.getScheduleId());
        assertEquals(1, schedule.getAvailableSlots().size());

        schedule.setScheduleId("SCH2");
        schedule.setDate(LocalDate.now().plusDays(2));
        schedule.setAvailableSlots(new ArrayList<>());

        assertEquals("SCH2", schedule.getScheduleId());
        assertEquals(0, schedule.getAvailableSlots().size());

        assertNotNull(schedule.toString());
    }
}