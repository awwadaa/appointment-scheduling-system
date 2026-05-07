package com.appointment.persistencetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.Administrator;
import com.appointment.persistence.AdminRepository;

class AdminRepositoryTest {

    private static final String ADMIN_ID = "A1";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String UNKNOWN_USERNAME = "unknown";

    private AdminRepository repository;
    private Administrator admin;

    @BeforeEach
    void setUp() {
        repository = new AdminRepository();
        admin = new Administrator(ADMIN_ID, ADMIN_USERNAME, ADMIN_PASSWORD, false);
    }

    @Test
    void testAddAdminAndFindByUsername() {
        repository.addAdmin(admin);

        assertEquals(admin, repository.findByUsername(ADMIN_USERNAME));
    }

    @Test
    void testFindAll() {
        repository.addAdmin(admin);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testFindByUsernameWhenNotFound() {
        assertNull(repository.findByUsername(UNKNOWN_USERNAME));
    }
}
