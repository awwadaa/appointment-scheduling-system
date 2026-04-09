package com.appointment.persistencetest;

import static org.junit.jupiter.api.Assertions.*;

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

public class AppointmentRepositoryTest {

    private AppointmentRepository repository;
    private Appointment appointment;

    @BeforeEach
    public void setUp() {
        repository = new AppointmentRepository();

        User user = new User("U1", "Awwad", "awwad@test.com", "0599999999");
        TimeSlot slot = new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true);

        appointment = new Appointment(
                "AP1", user, LocalDate.now().plusDays(1), slot,
                60, 1, AppointmentType.INDIVIDUAL, AppointmentStatus.CONFIRMED
        );
    }

    @Test
    public void testSaveAndFindById() {
        repository.save(appointment);
        assertEquals(appointment, repository.findById("AP1"));
    }

    @Test
    public void testFindAll() {
        repository.save(appointment);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void testDelete() {
        repository.save(appointment);
        repository.delete("AP1");
        assertNull(repository.findById("AP1"));
    }

    @Test
    public void testUpdate() {
        repository.save(appointment);
        appointment.setParticipantCount(3);
        repository.update(appointment);

        assertEquals(3, repository.findById("AP1").getParticipantCount());
    }
}