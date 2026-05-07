package com.appointment.observertest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.User;
import com.appointment.observer.SMSNotificationObserver;

class SMSNotificationObserverTest {

    private static final String USER_ID = "U1";
    private static final String USER_NAME = "Awwad";
    private static final String USER_EMAIL = "awwad@test.com";
    private static final String USER_PHONE = "0599999999";
    private static final String TEST_SMS = "Test sms";

    @Test
    void testUpdate() {
        User user = new User(USER_ID, USER_NAME, USER_EMAIL, USER_PHONE);
        SMSNotificationObserver observer = new SMSNotificationObserver();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));

            observer.update(user, TEST_SMS);
        } finally {
            System.setOut(originalOut);
        }

        String output = outputStream.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("SMS sent to " + USER_PHONE));
        assertTrue(output.contains(TEST_SMS));
    }
}
