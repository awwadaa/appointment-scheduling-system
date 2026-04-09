package com.appointment.persistencetest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.User;
import com.appointment.persistence.UserRepository;

public class UserRepositoryTest {

    private UserRepository repository;
    private User user;

    @BeforeEach
    public void setUp() {
        repository = new UserRepository();
        user = new User("U1", "Awwad", "awwad@test.com", "0599999999");
    }

    @Test
    public void testAddUserAndFindById() {
        repository.addUser(user);
        assertEquals(user, repository.findById("U1"));
    }

    @Test
    public void testFindAll() {
        repository.addUser(user);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void testFindByIdWhenNotFound() {
        assertNull(repository.findById("UNKNOWN"));
    }
}