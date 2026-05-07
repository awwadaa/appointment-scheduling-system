package com.appointment.presentation;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import com.appointment.application.AdminService;
import com.appointment.application.AppointmentService;
import com.appointment.application.AuthService;
import com.appointment.application.ReminderService;
import com.appointment.application.ScheduleService;
import com.appointment.domain.entities.Appointment;
import com.appointment.domain.entities.User;
import com.appointment.domain.enums.AppointmentStatus;
import com.appointment.domain.enums.AppointmentType;
import com.appointment.domain.valueobjects.TimeSlot;

/**
 * Controls menu actions and connects the presentation layer with the services.
 *
 * @author awwadaa
 * @version 1.0
 */
public class MenuController {

    private static final Logger LOGGER = Logger.getLogger(MenuController.class.getName());

    private static final String APPOINTMENT_NOT_FOUND = "Appointment not found.";
    private static final String INVALID_SLOT_ID = "Invalid slot id.";
    private static final String INVALID_APPOINTMENT_TYPE = "Invalid appointment type.";
    private static final String APPOINTMENT_TYPES_TITLE = "Appointment Types:";

    private final MenuDisplay menuDisplay;
    private final InputHandler inputHandler;
    private final AuthService authService;
    private final ScheduleService scheduleService;
    private final AppointmentService appointmentService;
    private final ReminderService reminderService;
    private final AdminService adminService;

    /**
     * Constructs a MenuController object.
     *
     * @param menuDisplay the menu display
     * @param inputHandler the input handler
     * @param authService the authentication service
     * @param scheduleService the schedule service
     * @param appointmentService the appointment service
     * @param reminderService the reminder service
     * @param adminService the admin service
     */
    public MenuController(MenuDisplay menuDisplay,
                          InputHandler inputHandler,
                          AuthService authService,
                          ScheduleService scheduleService,
                          AppointmentService appointmentService,
                          ReminderService reminderService,
                          AdminService adminService) {
        this.menuDisplay = menuDisplay;
        this.inputHandler = inputHandler;
        this.authService = authService;
        this.scheduleService = scheduleService;
        this.appointmentService = appointmentService;
        this.reminderService = reminderService;
        this.adminService = adminService;
    }

    /**
     * Starts the menu loop.
     */
    public void start() {
        int choice;

        do {
            menuDisplay.displayMainMenu();
            choice = inputHandler.readInt();

            switch (choice) {
                case 1:
                    handleAdminLogin();
                    break;
                case 2:
                    handleViewAvailableSlots();
                    break;
                case 3:
                    handleBookAppointment();
                    break;
                case 4:
                    handleCancelAppointment();
                    break;
                case 5:
                    handleModifyAppointment();
                    break;
                case 6:
                    handleSendReminder();
                    break;
                case 7:
                    handleAdminLogout();
                    break;
                case 0:
                    displayMessage("Exiting system...");
                    break;
                default:
                    displayMessage("Invalid option.");
            }
        } while (choice != 0);

        inputHandler.close();
    }

    /**
     * Handles administrator login.
     */
    private void handleAdminLogin() {
        displayMessage("Enter username:");
        String username = inputHandler.readString();

        displayMessage("Enter password:");
        String password = inputHandler.readString();

        if (authService.login(username, password)) {
            displayMessage("Administrator logged in successfully.");
        } else {
            displayMessage("Invalid username or password.");
        }
    }

    /**
     * Handles administrator logout.
     */
    private void handleAdminLogout() {
        authService.logout();
        displayMessage("Administrator logged out.");
    }

    /**
     * Handles viewing available slots for a given date.
     */
    private void handleViewAvailableSlots() {
        displayMessage("Enter appointment date (yyyy-mm-dd):");
        LocalDate date = inputHandler.readDate();

        List<TimeSlot> availableSlots = scheduleService.getAvailableSlots(date);

        if (availableSlots.isEmpty()) {
            displayMessage("No available slots found.");
            return;
        }

        displayAvailableSlots(availableSlots);
    }

    /**
     * Handles booking an appointment.
     */
    private void handleBookAppointment() {
        displayMessage("Enter appointment id:");
        String appointmentId = inputHandler.readString();

        User user = readUserDetails();

        displayMessage("Enter appointment date (yyyy-mm-dd):");
        LocalDate appointmentDate = inputHandler.readDate();

        List<TimeSlot> availableSlots = scheduleService.getAvailableSlots(appointmentDate);

        if (availableSlots.isEmpty()) {
            displayMessage("No available slots found for this date.");
            return;
        }

        displayAvailableSlots(availableSlots);

        TimeSlot selectedSlot = readSelectedSlot(availableSlots);

        if (selectedSlot == null) {
            displayMessage(INVALID_SLOT_ID);
            return;
        }

        displayMessage("Enter duration in minutes:");
        int duration = inputHandler.readInt();

        displayMessage("Enter participant count:");
        int participantCount = inputHandler.readInt();

        AppointmentType appointmentType = readAppointmentType("Enter appointment type exactly as shown:");

        if (appointmentType == null) {
            displayMessage(INVALID_APPOINTMENT_TYPE);
            return;
        }

        Appointment appointment = new Appointment(
                appointmentId,
                user,
                appointmentDate,
                selectedSlot,
                duration,
                participantCount,
                appointmentType,
                AppointmentStatus.CONFIRMED
        );

        if (appointmentService.bookAppointment(appointment)) {
            displayMessage("Appointment booked successfully.");
        } else {
            displayMessage("Appointment booking failed.");
        }
    }

    /**
     * Handles cancelling an appointment.
     */
    private void handleCancelAppointment() {
        displayMessage("Enter appointment id to cancel:");
        String appointmentId = inputHandler.readString();

        boolean cancelled;

        if (authService.isAdminLoggedIn()) {
            cancelled = adminService.cancelReservationAsAdmin(appointmentId);
        } else {
            cancelled = appointmentService.cancelAppointment(appointmentId);
        }

        if (cancelled) {
            displayMessage("Appointment cancelled successfully.");
        } else {
            displayMessage("Cancellation failed.");
        }
    }

    /**
     * Handles modifying an appointment.
     */
    private void handleModifyAppointment() {
        displayMessage("Enter appointment id to modify:");
        String appointmentId = inputHandler.readString();

        Appointment existingAppointment = appointmentService.findAppointmentById(appointmentId);

        if (existingAppointment == null) {
            displayMessage(APPOINTMENT_NOT_FOUND);
            return;
        }

        displayMessage("Enter new appointment date (yyyy-mm-dd):");
        LocalDate newDate = inputHandler.readDate();

        List<TimeSlot> availableSlots = scheduleService.getAvailableSlots(newDate);

        if (availableSlots.isEmpty()) {
            displayMessage("No available slots for the selected date.");
            return;
        }

        displayAvailableSlots(availableSlots);

        TimeSlot selectedSlot = readSelectedSlot(availableSlots);

        if (selectedSlot == null) {
            displayMessage(INVALID_SLOT_ID);
            return;
        }

        displayMessage("Enter new duration in minutes:");
        int duration = inputHandler.readInt();

        displayMessage("Enter new participant count:");
        int participantCount = inputHandler.readInt();

        AppointmentType appointmentType = readAppointmentType("Enter new appointment type exactly as shown:");

        if (appointmentType == null) {
            displayMessage(INVALID_APPOINTMENT_TYPE);
            return;
        }

        Appointment updatedAppointment = new Appointment(
                appointmentId,
                existingAppointment.getUser(),
                newDate,
                selectedSlot,
                duration,
                participantCount,
                appointmentType,
                existingAppointment.getStatus()
        );

        boolean modified;

        if (authService.isAdminLoggedIn()) {
            modified = adminService.modifyReservationAsAdmin(appointmentId, updatedAppointment);
        } else {
            modified = appointmentService.modifyAppointment(appointmentId, updatedAppointment);
        }

        if (modified) {
            displayMessage("Appointment modified successfully.");
        } else {
            displayMessage("Modification failed.");
        }
    }

    /**
     * Handles sending a reminder for an appointment.
     */
    private void handleSendReminder() {
        displayMessage("Enter appointment id:");
        String appointmentId = inputHandler.readString();

        Appointment appointment = appointmentService.findAppointmentById(appointmentId);

        if (appointment == null) {
            displayMessage(APPOINTMENT_NOT_FOUND);
            return;
        }

        reminderService.sendReminder(appointment);
        displayMessage("Reminder sent successfully.");
    }

    private User readUserDetails() {
        displayMessage("Enter user id:");
        String userId = inputHandler.readString();

        displayMessage("Enter user name:");
        String userName = inputHandler.readString();

        displayMessage("Enter user email:");
        String userEmail = inputHandler.readString();

        displayMessage("Enter user phone:");
        String userPhone = inputHandler.readString();

        return new User(userId, userName, userEmail, userPhone);
    }

    private TimeSlot readSelectedSlot(List<TimeSlot> availableSlots) {
        displayMessage("Enter slot id:");
        String slotId = inputHandler.readString();

        for (TimeSlot slot : availableSlots) {
            if (slot.getSlotId().equals(slotId)) {
                return slot;
            }
        }

        return null;
    }

    private AppointmentType readAppointmentType(String promptMessage) {
        displayAppointmentTypes();
        displayMessage(promptMessage);

        String appointmentTypeInput = inputHandler.readString();

        try {
            return AppointmentType.valueOf(appointmentTypeInput.toUpperCase());
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    private static void displayAppointmentTypes() {
        displayMessage(APPOINTMENT_TYPES_TITLE);

        for (AppointmentType type : AppointmentType.values()) {
            displayMessage("- " + type);
        }
    }

    private static void displayAvailableSlots(List<TimeSlot> availableSlots) {
        displayMessage("Available slots:");

        for (TimeSlot slot : availableSlots) {
            displayMessage(slot.toString());
        }
    }

    private static void displayMessage(String message) {
        LOGGER.info(message);
    }
}
