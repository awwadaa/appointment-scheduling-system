package com.appointment.domaintest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.appointment.domain.entities.Administrator;

public class AdministratorTest {

    @Test
    public void testAdministratorConstructorGettersSettersAndToString() {
        Administrator admin = new Administrator("A1", "admin", "admin123", false);

        assertEquals("A1", admin.getAdminId());
        assertEquals("admin", admin.getUsername());
        assertEquals("admin123", admin.getPassword());
        assertFalse(admin.isLoggedIn());

        admin.setAdminId("A2");
        admin.setUsername("superadmin");
        admin.setPassword("pass");
        admin.setLoggedIn(true);

        assertEquals("A2", admin.getAdminId());
        assertEquals("superadmin", admin.getUsername());
        assertEquals("pass", admin.getPassword());
        assertTrue(admin.isLoggedIn());

        assertNotNull(admin.toString());
    }
}