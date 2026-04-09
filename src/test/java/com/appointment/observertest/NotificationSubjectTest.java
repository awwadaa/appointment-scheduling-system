package com.appointment.observertest;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.User;
import com.appointment.observer.NotificationSubject;
import com.appointment.observer.Observer;

public class NotificationSubjectTest {

    private NotificationSubject subject;
    private Observer observer1;
    private Observer observer2;
    private User user;

    @BeforeEach
    public void setUp() {
        subject = new NotificationSubject();
        observer1 = mock(Observer.class);
        observer2 = mock(Observer.class);
        user = new User("U1", "Awwad", "awwad@test.com", "0599999999");
    }

    @Test
    public void testNotifyObservers() {
        subject.addObserver(observer1);
        subject.addObserver(observer2);

        subject.notifyObservers(user, "Test message");

        verify(observer1, times(1)).update(user, "Test message");
        verify(observer2, times(1)).update(user, "Test message");
    }

    @Test
    public void testRemoveObserver() {
        subject.addObserver(observer1);
        subject.addObserver(observer2);
        subject.removeObserver(observer1);

        subject.notifyObservers(user, "Test message");

        verify(observer1, never()).update(any(), anyString());
        verify(observer2, times(1)).update(user, "Test message");
    }
}