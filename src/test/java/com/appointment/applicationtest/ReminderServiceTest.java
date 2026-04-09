package com.appointment.applicationtest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.application.ReminderService;
import com.appointment.domain.entities.Appointment;
import com.appointment.domain.entities.User;
import com.appointment.domain.enums.AppointmentStatus;
import com.appointment.domain.enums.AppointmentType;
import com.appointment.domain.valueobjects.NotificationMessage;
import com.appointment.domain.valueobjects.TimeSlot;
import com.appointment.observer.NotificationSubject;

public class ReminderServiceTest {

    private ReminderService reminderService;
    private NotificationSubject notificationSubject;
    private Appointment appointment;

    @BeforeEach
    public void setUp() {
        notificationSubject = mock(NotificationSubject.class);
        reminderService = new ReminderService(notificationSubject);

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
    public void testCreateReminder() {
        NotificationMessage message = reminderService.createReminder(appointment);

        assertNotNull(message);
        assertEquals("REM-AP1", message.getNotificationId());
        assertTrue(message.getMessage().contains("Reminder"));
    }

    @Test
    public void testSendReminder() {
        reminderService.sendReminder(appointment);

        verify(notificationSubject, times(1))
                .notifyObservers(eq(appointment.getUser()), contains("Reminder"));
    }
}