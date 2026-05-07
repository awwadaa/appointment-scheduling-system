package com.appointment.observertest;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.User;
import com.appointment.observer.NotificationSubject;
import com.appointment.observer.Observer;

class NotificationSubjectTest {

    private static final String USER_ID = "U1";
    private static final String USER_NAME = "Awwad";
    private static final String USER_EMAIL = "awwad@test.com";
    private static final String USER_PHONE = "0599999999";
    private static final String TEST_MESSAGE = "Test message";

    private NotificationSubject subject;
    private Observer observer1;
    private Observer observer2;
    private User user;

    @BeforeEach
    void setUp() {
        subject = new NotificationSubject();
        observer1 = mock(Observer.class);
        observer2 = mock(Observer.class);
        user = new User(USER_ID, USER_NAME, USER_EMAIL, USER_PHONE);
    }

    @Test
    void testNotifyObservers() {
        subject.addObserver(observer1);
        subject.addObserver(observer2);

        subject.notifyObservers(user, TEST_MESSAGE);

        verify(observer1, times(1)).update(user, TEST_MESSAGE);
        verify(observer2, times(1)).update(user, TEST_MESSAGE);
    }

    @Test
    void testRemoveObserver() {
        subject.addObserver(observer1);
        subject.addObserver(observer2);
        subject.removeObserver(observer1);

        subject.notifyObservers(user, TEST_MESSAGE);

        verify(observer1, never()).update(any(), anyString());
        verify(observer2, times(1)).update(user, TEST_MESSAGE);
    }
}
