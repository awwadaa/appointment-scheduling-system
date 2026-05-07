package com.appointment.presentation;

import java.util.logging.Logger;

/**
 * Handles displaying menus to the user.
 *
 * @author awwadaa
 * @version 1.0
 */
public class MenuDisplay {

    private static final Logger LOGGER = Logger.getLogger(MenuDisplay.class.getName());

    private static final String MAIN_MENU = String.join(
            System.lineSeparator(),
            "",
            "===== Appointment Scheduling System =====",
            "1. Administrator Login",
            "2. View Available Slots",
            "3. Book Appointment",
            "4. Cancel Appointment",
            "5. Modify Appointment",
            "6. Send Reminder",
            "7. Administrator Logout",
            "0. Exit",
            "Choose an option:"
    );

    /**
     * Displays the main menu.
     */
    public void displayMainMenu() {
        LOGGER.info(MAIN_MENU);
    }
}
