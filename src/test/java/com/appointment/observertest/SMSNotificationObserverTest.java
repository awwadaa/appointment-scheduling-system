package com.appointment.observertest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.User;
import com.appointment.observer.SMSNotificationObserver;

public class SMSNotificationObserverTest {

    @Test
    public void testUpdate() {
        User user = new User("U1", "Awwad", "awwad@test.com", "0599999999");
        SMSNotificationObserver observer = new SMSNotificationObserver();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        observer.update(user, "Test sms");

        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.contains("SMS sent to 0599999999"));
        assertTrue(output.contains("Test sms"));
    }
}