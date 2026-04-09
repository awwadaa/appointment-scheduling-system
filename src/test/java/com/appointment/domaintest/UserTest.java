package com.appointment.domaintest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.User;

public class UserTest {

    @Test
    public void testUserConstructorGettersSettersAndToString() {
        User user = new User("U1", "Awwad", "awwad@test.com", "0599999999");

        assertEquals("U1", user.getUserId());
        assertEquals("Awwad", user.getName());
        assertEquals("awwad@test.com", user.getEmail());
        assertEquals("0599999999", user.getPhone());

        user.setUserId("U2");
        user.setName("Ali");
        user.setEmail("ali@test.com");
        user.setPhone("0588888888");

        assertEquals("U2", user.getUserId());
        assertEquals("Ali", user.getName());
        assertEquals("ali@test.com", user.getEmail());
        assertEquals("0588888888", user.getPhone());

        assertNotNull(user.toString());
    }
}