package com.appointment.application;

import com.appointment.domain.entities.Administrator;
import com.appointment.persistence.AdminRepository;

/**
 * Service responsible for administrator authentication.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class AuthService {

    private AdminRepository adminRepository;
    private Administrator loggedInAdmin;

    /**
     * Constructs an AuthService object.
     * 
     * @param adminRepository the admin repository
     */
    public AuthService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Attempts to log in an administrator.
     * 
     * @param username the username
     * @param password the password
     * @return true if login succeeds, false otherwise
     */
    public boolean login(String username, String password) {
        Administrator administrator = adminRepository.findByUsername(username);

        if (administrator != null && administrator.getPassword().equals(password)) {
            administrator.setLoggedIn(true);
            loggedInAdmin = administrator;
            return true;
        }

        return false;
    }

    /**
     * Logs out the currently logged-in administrator.
     */
    public void logout() {
        if (loggedInAdmin != null) {
            loggedInAdmin.setLoggedIn(false);
            loggedInAdmin = null;
        }
    }

    /**
     * Checks whether an administrator is currently logged in.
     * 
     * @return true if an administrator is logged in, false otherwise
     */
    public boolean isAdminLoggedIn() {
        return loggedInAdmin != null && loggedInAdmin.isLoggedIn();
    }

    /**
     * Returns the currently logged-in administrator.
     * 
     * @return the logged-in administrator, or null if none
     */
    public Administrator getLoggedInAdmin() {
        return loggedInAdmin;
    }
}