package com.appointment.applicationtest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.application.AuthService;
import com.appointment.domain.entities.Administrator;
import com.appointment.persistence.AdminRepository;

class AuthServiceTest {

    private static final String ADMIN_ID = "A1";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String WRONG_PASSWORD = "wrongpass";

    private AdminRepository adminRepository;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        adminRepository = new AdminRepository();
        adminRepository.addAdmin(new Administrator(ADMIN_ID, ADMIN_USERNAME, ADMIN_PASSWORD, false));
        authService = new AuthService(adminRepository);
    }

    @Test
    void testLoginSuccess() {
        boolean result = authService.login(ADMIN_USERNAME, ADMIN_PASSWORD);

        assertTrue(result);
        assertTrue(authService.isAdminLoggedIn());
    }

    @Test
    void testLoginFailure() {
        boolean result = authService.login(ADMIN_USERNAME, WRONG_PASSWORD);

        assertFalse(result);
        assertFalse(authService.isAdminLoggedIn());
    }

    @Test
    void testLogout() {
        authService.login(ADMIN_USERNAME, ADMIN_PASSWORD);

        authService.logout();

        assertFalse(authService.isAdminLoggedIn());
    }
}
