package com.appointment.applicationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

class ReminderServiceTest {

    private static final String APPOINTMENT_ID = "AP1";
    private static final String USER_ID = "U1";
    private static final String USER_NAME = "Awwad";
    private static final String USER_EMAIL = "awwad@test.com";
    private static final String USER_PHONE = "0599999999";
    private static final String SLOT_ID = "S1";
    private static final String EXPECTED_REMINDER_ID = "REM-AP1";
    private static final String REMINDER_TEXT = "Reminder";

    private ReminderService reminderService;
    private NotificationSubject notificationSubject;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        notificationSubject = mock(NotificationSubject.class);
        reminderService = new ReminderService(notificationSubject);

        User user = new User(USER_ID, USER_NAME, USER_EMAIL, USER_PHONE);
        TimeSlot slot = new TimeSlot(SLOT_ID, LocalTime.of(9, 0), LocalTime.of(10, 0), true);

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
    void testCreateReminder() {
        NotificationMessage message = reminderService.createReminder(appointment);

        assertNotNull(message);
        assertEquals(EXPECTED_REMINDER_ID, message.getNotificationId());
        assertTrue(message.getMessage().contains(REMINDER_TEXT));
    }

    @Test
    void testSendReminder() {
        reminderService.sendReminder(appointment);

        verify(notificationSubject, times(1))
                .notifyObservers(eq(appointment.getUser()), contains(REMINDER_TEXT));
    }
}
