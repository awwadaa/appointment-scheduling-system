package com.appointment.domaintest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.appointment.domain.valueobjects.NotificationMessage;

public class NotificationMessageTest {

    @Test
    public void testNotificationMessageConstructorGettersSettersAndToString() {
        NotificationMessage message = new NotificationMessage("N1", "hello", LocalDate.now());

        assertEquals("N1", message.getNotificationId());
        assertEquals("hello", message.getMessage());
        assertEquals(LocalDate.now(), message.getSendDate());

        message.setNotificationId("N2");
        message.setMessage("updated");
        message.setSendDate(LocalDate.now().plusDays(1));

        assertEquals("N2", message.getNotificationId());
        assertEquals("updated", message.getMessage());
        assertEquals(LocalDate.now().plusDays(1), message.getSendDate());

        assertNotNull(message.toString());
    }
}