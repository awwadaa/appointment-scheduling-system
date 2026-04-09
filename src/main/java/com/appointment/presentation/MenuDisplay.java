package com.appointment.presentation;

/**
 * Handles displaying menus to the user.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class MenuDisplay {

    /**
     * Displays the main menu.
     */
    public void displayMainMenu() {
        System.out.println("\n===== Appointment Scheduling System =====");
        System.out.println("1. Administrator Login");
        System.out.println("2. View Available Slots");
        System.out.println("3. Book Appointment");
        System.out.println("4. Cancel Appointment");
        System.out.println("5. Modify Appointment");
        System.out.println("6. Send Reminder");
        System.out.println("7. Administrator Logout");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }
}