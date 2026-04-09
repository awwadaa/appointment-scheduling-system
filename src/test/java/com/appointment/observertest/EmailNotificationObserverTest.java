package com.appointment.observertest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.User;
import com.appointment.observer.EmailNotificationObserver;

public class EmailNotificationObserverTest {

    @Test
    public void testUpdate() {
        User user = new User("U1", "Awwad", "awwad@test.com", "0599999999");
        EmailNotificationObserver observer = new EmailNotificationObserver();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        observer.update(user, "Test email");

        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.contains("Email sent to awwad@test.com"));
        assertTrue(output.contains("Test email"));
    }
}