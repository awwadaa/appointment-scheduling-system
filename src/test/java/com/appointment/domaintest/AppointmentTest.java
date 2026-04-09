package com.appointment.domaintest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.Appointment;
import com.appointment.domain.entities.User;
import com.appointment.domain.enums.AppointmentStatus;
import com.appointment.domain.enums.AppointmentType;
import com.appointment.domain.valueobjects.TimeSlot;

public class AppointmentTest {

    @Test
    public void testAppointmentConstructorGettersSettersAndToString() {
        User user = new User("U1", "Awwad", "awwad@test.com", "0599999999");
        TimeSlot slot = new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true);

        Appointment appointment = new Appointment(
                "AP1",
                user,
                LocalDate.now().plusDays(1),
                slot,
                60,
                1,
                AppointmentType.INDIVIDUAL,
                AppointmentStatus.CONFIRMED
        );

        assertEquals("AP1", appointment.getAppointmentId());
        assertEquals(user, appointment.getUser());
        assertEquals(slot, appointment.getTimeSlot());
        assertEquals(60, appointment.getDurationMinutes());
        assertEquals(1, appointment.getParticipantCount());
        assertEquals(AppointmentType.INDIVIDUAL, appointment.getAppointmentType());
        assertEquals(AppointmentStatus.CONFIRMED, appointment.getStatus());

        appointment.setDurationMinutes(90);
        appointment.setParticipantCount(2);
        appointment.setAppointmentType(AppointmentType.GROUP);
        appointment.setStatus(AppointmentStatus.CANCELLED);

        assertEquals(90, appointment.getDurationMinutes());
        assertEquals(2, appointment.getParticipantCount());
        assertEquals(AppointmentType.GROUP, appointment.getAppointmentType());
        assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus());

        assertNotNull(appointment.toString());
    }
}