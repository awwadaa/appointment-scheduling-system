package com.appointment.domain.entities;

/**
 * Represents an administrator who manages the appointment system.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class Administrator {

    private String adminId;
    private String username;
    private String password;
    private boolean loggedIn;

    /**
     * Constructs an Administrator object with all fields.
     * 
     * @param adminId the unique administrator id
     * @param username the administrator username
     * @param password the administrator password
     * @param loggedIn the login status
     */
    public Administrator(String adminId, String username, String password, boolean loggedIn) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.loggedIn = loggedIn;
    }

    /**
     * Returns the administrator id.
     * 
     * @return the administrator id
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * Sets the administrator id.
     * 
     * @param adminId the administrator id to set
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    /**
     * Returns the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns whether the administrator is logged in.
     * 
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Sets the login status.
     * 
     * @param loggedIn the login status to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Returns a string representation of the administrator.
     * 
     * @return a string containing administrator details
     */
    @Override
    public String toString() {
        return "Administrator{" +
                "adminId='" + adminId + '\'' +
                ", username='" + username + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }
}