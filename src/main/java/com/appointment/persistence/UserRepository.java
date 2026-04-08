package com.appointment.persistence;

import java.util.ArrayList;
import java.util.List;

import com.appointment.domain.entities.User;

/**
 * Repository for managing users in memory.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class UserRepository {

    private List<User> users;

    /**
     * Constructs a UserRepository object.
     */
    public UserRepository() {
        this.users = new ArrayList<>();
    }

    /**
     * Adds a user to the repository.
     * 
     * @param user the user to add
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Finds a user by id.
     * 
     * @param userId the user id to search for
     * @return the matching user, or null if not found
     */
    public User findById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns all users.
     * 
     * @return the list of users
     */
    public List<User> findAll() {
        return users;
    }
}