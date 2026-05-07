package com.appointment.persistencetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.Appointment;
import com.appointment.domain.entities.User;
import com.appointment.domain.enums.AppointmentStatus;
import com.appointment.domain.enums.AppointmentType;
import com.appointment.domain.valueobjects.TimeSlot;
import com.appointment.persistence.AppointmentRepository;

class AppointmentRepositoryTest {

    private static final String APPOINTMENT_ID = "AP1";
    private static final String USER_ID = "U1";
    private static final String USER_NAME = "Awwad";
    private static final String USER_EMAIL = "awwad@test.com";
    private static final String USER_PHONE = "0599999999";

    private AppointmentRepository repository;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        repository = new AppointmentRepository();

        User user = new User(USER_ID, USER_NAME, USER_EMAIL, USER_PHONE);
        TimeSlot slot = new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true);

        appointment = new Appointment(
                APPOINTMENT_ID,
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
    void testSaveAndFindById() {
        repository.save(appointment);

        assertEquals(appointment, repository.findById(APPOINTMENT_ID));
    }

    @Test
    void testFindAll() {
        repository.save(appointment);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testDelete() {
        repository.save(appointment);

        repository.delete(APPOINTMENT_ID);

        assertNull(repository.findById(APPOINTMENT_ID));
    }

    @Test
    void testUpdate() {
        repository.save(appointment);

        appointment.setParticipantCount(3);
        repository.update(appointment);

        assertEquals(3, repository.findById(APPOINTMENT_ID).getParticipantCount());
    }
}
