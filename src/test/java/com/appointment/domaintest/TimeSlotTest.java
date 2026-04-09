package com.appointment.domaintest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.appointment.domain.valueobjects.TimeSlot;

public class TimeSlotTest {

    @Test
    public void testTimeSlotConstructorGettersSettersAndToString() {
        TimeSlot slot = new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true);

        assertEquals("S1", slot.getSlotId());
        assertEquals(LocalTime.of(9, 0), slot.getStartTime());
        assertEquals(LocalTime.of(10, 0), slot.getEndTime());
        assertTrue(slot.isAvailable());

        slot.setSlotId("S2");
        slot.setStartTime(LocalTime.of(11, 0));
        slot.setEndTime(LocalTime.of(12, 0));
        slot.setAvailable(false);

        assertEquals("S2", slot.getSlotId());
        assertEquals(LocalTime.of(11, 0), slot.getStartTime());
        assertEquals(LocalTime.of(12, 0), slot.getEndTime());
        assertFalse(slot.isAvailable());

        assertNotNull(slot.toString());
    }
}