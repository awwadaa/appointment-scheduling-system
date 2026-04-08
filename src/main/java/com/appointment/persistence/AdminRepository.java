package com.appointment.persistence;

import java.util.ArrayList;
import java.util.List;

import com.appointment.domain.entities.Administrator;

/**
 * Repository for managing administrators in memory.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class AdminRepository {

    private List<Administrator> administrators;

    /**
     * Constructs an AdminRepository object.
     */
    public AdminRepository() {
        this.administrators = new ArrayList<>();
    }

    /**
     * Adds an administrator to the repository.
     * 
     * @param administrator the administrator to add
     */
    public void addAdmin(Administrator administrator) {
        administrators.add(administrator);
    }

    /**
     * Finds an administrator by username.
     * 
     * @param username the username to search for
     * @return the matching administrator, or null if not found
     */
    public Administrator findByUsername(String username) {
        for (Administrator administrator : administrators) {
            if (administrator.getUsername().equals(username)) {
                return administrator;
            }
        }
        return null;
    }

    /**
     * Returns all administrators.
     * 
     * @return the list of administrators
     */
    public List<Administrator> findAll() {
        return administrators;
    }
}