package com.appointment.persistencetest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.Administrator;
import com.appointment.persistence.AdminRepository;

public class AdminRepositoryTest {

    private AdminRepository repository;
    private Administrator admin;

    @BeforeEach
    public void setUp() {
        repository = new AdminRepository();
        admin = new Administrator("A1", "admin", "admin123", false);
    }

    @Test
    public void testAddAdminAndFindByUsername() {
        repository.addAdmin(admin);
        assertEquals(admin, repository.findByUsername("admin"));
    }

    @Test
    public void testFindAll() {
        repository.addAdmin(admin);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void testFindByUsernameWhenNotFound() {
        assertNull(repository.findByUsername("unknown"));
    }
}