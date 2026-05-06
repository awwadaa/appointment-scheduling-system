package com.appointment.observertest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.User;
import com.appointment.observer.NotificationSubject;
import com.appointment.observer.Observer;

public class NotificationSubjectRemoveObserverTest {

    @Test
    public void testRemoveObserver() {
        NotificationSubject subject = new NotificationSubject();

        Observer observer = mock(Observer.class);
        subject.addObserver(observer);
        subject.removeObserver(observer);

        User user = new User("U1", "Awwad", "awwad@test.com", "0599999999");
        subject.notifyObservers(user, "Reminder message");

        verifyNoInteractions(observer);
    }
}