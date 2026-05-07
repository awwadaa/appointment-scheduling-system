package com.appointment.persistencetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.User;
import com.appointment.persistence.UserRepository;

class UserRepositoryTest {

    private static final String USER_ID = "U1";
    private static final String USER_NAME = "Awwad";
    private static final String USER_EMAIL = "awwad@test.com";
    private static final String USER_PHONE = "0599999999";
    private static final String UNKNOWN_USER_ID = "UNKNOWN";

    private UserRepository repository;
    private User user;

    @BeforeEach
    void setUp() {
        repository = new UserRepository();
        user = new User(USER_ID, USER_NAME, USER_EMAIL, USER_PHONE);
    }

    @Test
    void testAddUserAndFindById() {
        repository.addUser(user);

        assertEquals(user, repository.findById(USER_ID));
    }

    @Test
    void testFindAll() {
        repository.addUser(user);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testFindByIdWhenNotFound() {
        assertNull(repository.findById(UNKNOWN_USER_ID));
    }
}
