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
import com.appointment.strategy.DurationRule;

class DurationRuleTest {

    private DurationRule durationRule;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        durationRule = new DurationRule();

        User user = new User("U1", "Awwad", "Awwad@test.com", "0599999999");
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
    void testValidDuration() {
        appointment.setDurationMinutes(60);

        assertTrue(durationRule.validate(appointment));
    }

    @Test
    void testInvalidTooShortDuration() {
        appointment.setDurationMinutes(10);

        assertFalse(durationRule.validate(appointment));
    }

    @Test
    void testInvalidTooLongDuration() {
        appointment.setDurationMinutes(200);

        assertFalse(durationRule.validate(appointment));
    }
}
