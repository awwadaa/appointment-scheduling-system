package com.appointment.domain.entities;

/**
 * Represents a user who books appointments in the system.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class User {

    private String userId;
    private String name;
    private String email;
    private String phone;

    /**
     * Constructs a User object with all fields.
     * 
     * @param userId the unique user id
     * @param name the user's name
     * @param email the user's email
     * @param phone the user's phone number
     */
    public User(String userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Returns the user id.
     * 
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     * 
     * @param userId the user id to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the user's name.
     * 
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user's email.
     * 
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's phone number.
     * 
     * @return the user's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the user's phone number.
     * 
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns a string representation of the user.
     * 
     * @return a string containing user details
     */
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}