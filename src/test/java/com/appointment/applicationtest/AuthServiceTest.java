package com.appointment.applicationtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.application.AuthService;
import com.appointment.domain.entities.Administrator;
import com.appointment.persistence.AdminRepository;

public class AuthServiceTest {

    private AdminRepository adminRepository;
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        adminRepository = new AdminRepository();
        adminRepository.addAdmin(new Administrator("A1", "admin", "admin123", false));
        authService = new AuthService(adminRepository);
    }

    @Test
    public void testLoginSuccess() {
        boolean result = authService.login("admin", "admin123");
        assertTrue(result);
        assertTrue(authService.isAdminLoggedIn());
    }

    @Test
    public void testLoginFailure() {
        boolean result = authService.login("admin", "wrongpass");
        assertFalse(result);
        assertFalse(authService.isAdminLoggedIn());
    }

    @Test
    public void testLogout() {
        authService.login("admin", "admin123");
        authService.logout();
        assertFalse(authService.isAdminLoggedIn());
    }
}