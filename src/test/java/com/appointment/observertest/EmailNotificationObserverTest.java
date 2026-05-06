package com.appointment.observertest;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.appointment.domain.entities.User;
import com.appointment.observer.EmailNotificationObserver;
import com.appointment.observer.EmailSender;

/**
 * Test class for EmailNotificationObserver.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class EmailNotificationObserverTest {

    /**
     * Tests that update() delegates email sending to EmailSender.
     */
    @Test
    public void testUpdate() {
        User user = new User("U1", "Awwad", "awwad@test.com", "0599999999");

        EmailSender emailSender = Mockito.mock(EmailSender.class);
        EmailNotificationObserver observer = new EmailNotificationObserver(emailSender);

        observer.update(user, "Test email");

        verify(emailSender).send("awwad@test.com", "Appointment Reminder", "Test email");
    }
}