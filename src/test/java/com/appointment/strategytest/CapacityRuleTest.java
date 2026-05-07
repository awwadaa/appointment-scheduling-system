package com.appointment.strategytest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.Appointment;
import com.appointment.domain.entities.User;
import com.appointment.domain.enums.AppointmentStatus;
import com.appointment.domain.enums.AppointmentType;
import com.appointment.domain.valueobjects.TimeSlot;
import com.appointment.strategy.CapacityRule;

class CapacityRuleTest {

    private CapacityRule capacityRule;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        capacityRule = new CapacityRule();

        User user = new User("U1", "Awwad", "awwad@test.com", "0599999999");
        TimeSlot slot = new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true);

        appointment = new Appointment(
                "AP1",
                user,
                LocalDate.now().plusDays(1),
                slot,
                60,
                1,
                AppointmentType.INDIVIDUAL,
                AppointmentStatus.CONFIRMED
        );
    }

    @Test
    void testValidCapacity() {
        appointment.setParticipantCount(3);

        assertTrue(capacityRule.validate(appointment));
    }

    @Test
    void testInvalidZeroCapacity() {
        appointment.setParticipantCount(0);

        assertFalse(capacityRule.validate(appointment));
    }

    @Test
    void testInvalidExceedingCapacity() {
        appointment.setParticipantCount(11);

        assertFalse(capacityRule.validate(appointment));
    }
}
