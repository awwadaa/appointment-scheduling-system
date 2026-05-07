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
import com.appointment.strategy.AppointmentTypeRule;

class AppointmentTypeRuleTest {

    private AppointmentTypeRule appointmentTypeRule;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointmentTypeRule = new AppointmentTypeRule();

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
    void testIndividualAppointmentValid() {
        appointment.setAppointmentType(AppointmentType.INDIVIDUAL);
        appointment.setParticipantCount(1);

        assertTrue(appointmentTypeRule.validate(appointment));
    }

    @Test
    void testIndividualAppointmentInvalid() {
        appointment.setAppointmentType(AppointmentType.INDIVIDUAL);
        appointment.setParticipantCount(2);

        assertFalse(appointmentTypeRule.validate(appointment));
    }

    @Test
    void testGroupAppointmentValid() {
        appointment.setAppointmentType(AppointmentType.GROUP);
        appointment.setParticipantCount(3);

        assertTrue(appointmentTypeRule.validate(appointment));
    }

    @Test
    void testGroupAppointmentInvalid() {
        appointment.setAppointmentType(AppointmentType.GROUP);
        appointment.setParticipantCount(1);

        assertFalse(appointmentTypeRule.validate(appointment));
    }

    @Test
    void testOtherAppointmentTypesAreValid() {
        appointment.setAppointmentType(AppointmentType.URGENT);
        appointment.setParticipantCount(1);

        assertTrue(appointmentTypeRule.validate(appointment));
    }
}
