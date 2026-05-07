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
    private static final String DATE_PROMPT = "Enter appointment date (yyyy-mm-dd):";
    private static final String NEW_DATE_PROMPT = "Enter new appointment date (yyyy-mm-dd):";
    private static final String DURATION_PROMPT = "Enter duration in minutes:";
    private static final String NEW_DURATION_PROMPT = "Enter new duration in minutes:";
    private static final String PARTICIPANT_COUNT_PROMPT = "Enter participant count:";
    private static final String NEW_PARTICIPANT_COUNT_PROMPT = "Enter new participant count:";
    private static final String TYPE_PROMPT = "Enter appointment type exactly as shown:";
    private static final String NEW_TYPE_PROMPT = "Enter new appointment type exactly as shown:";

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
            handleMainMenuChoice(choice);
        } while (choice != 0);

        inputHandler.close();
    }

    private void handleMainMenuChoice(int choice) {
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
    }

    /**
     * Handles administrator login.
     */
    private void handleAdminLogin() {
        displayMessage("Enter username:");
        String username = inputHandler.readString();

        displayMessage("Enter password:");
        String password = inputHandler.readString();

        displayOperationResult(
                authService.login(username, password),
                "Administrator logged in successfully.",
                "Invalid username or password."
        );
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
        LocalDate date = readDate(DATE_PROMPT);
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

        AppointmentInputData inputData = readAppointmentInputData(
                DATE_PROMPT,
                "No available slots found for this date.",
                DURATION_PROMPT,
                PARTICIPANT_COUNT_PROMPT,
                TYPE_PROMPT
        );

        if (inputData == null) {
            return;
        }

        Appointment appointment = createAppointment(
                appointmentId,
                user,
                inputData,
                AppointmentStatus.CONFIRMED
        );

        displayOperationResult(
                appointmentService.bookAppointment(appointment),
                "Appointment booked successfully.",
                "Appointment booking failed."
        );
    }

    /**
     * Handles cancelling an appointment.
     */
    private void handleCancelAppointment() {
        displayMessage("Enter appointment id to cancel:");
        String appointmentId = inputHandler.readString();

        boolean cancelled = cancelAppointment(appointmentId);

        displayOperationResult(
                cancelled,
                "Appointment cancelled successfully.",
                "Cancellation failed."
        );
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

        AppointmentInputData inputData = readAppointmentInputData(
                NEW_DATE_PROMPT,
                "No available slots for the selected date.",
                NEW_DURATION_PROMPT,
                NEW_PARTICIPANT_COUNT_PROMPT,
                NEW_TYPE_PROMPT
        );

        if (inputData == null) {
            return;
        }

        Appointment updatedAppointment = createAppointment(
                appointmentId,
                existingAppointment.getUser(),
                inputData,
                existingAppointment.getStatus()
        );

        boolean modified = modifyAppointment(appointmentId, updatedAppointment);

        displayOperationResult(
                modified,
                "Appointment modified successfully.",
                "Modification failed."
        );
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

    private AppointmentInputData readAppointmentInputData(String datePrompt,
                                                          String emptySlotsMessage,
                                                          String durationPrompt,
                                                          String participantCountPrompt,
                                                          String typePrompt) {
        LocalDate appointmentDate = readDate(datePrompt);
        List<TimeSlot> availableSlots = scheduleService.getAvailableSlots(appointmentDate);

        if (availableSlots.isEmpty()) {
            displayMessage(emptySlotsMessage);
            return null;
        }

        displayAvailableSlots(availableSlots);

        TimeSlot selectedSlot = readSelectedSlot(availableSlots);

        if (selectedSlot == null) {
            displayMessage(INVALID_SLOT_ID);
            return null;
        }

        int duration = readInt(durationPrompt);
        int participantCount = readInt(participantCountPrompt);
        AppointmentType appointmentType = readAppointmentType(typePrompt);

        if (appointmentType == null) {
            displayMessage(INVALID_APPOINTMENT_TYPE);
            return null;
        }

        return new AppointmentInputData(
                appointmentDate,
                selectedSlot,
                duration,
                participantCount,
                appointmentType
        );
    }

    private LocalDate readDate(String promptMessage) {
        displayMessage(promptMessage);
        return inputHandler.readDate();
    }

    private int readInt(String promptMessage) {
        displayMessage(promptMessage);
        return inputHandler.readInt();
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

        return findAppointmentType(appointmentTypeInput);
    }

    private static AppointmentType findAppointmentType(String appointmentTypeInput) {
        for (AppointmentType type : AppointmentType.values()) {
            if (type.name().equalsIgnoreCase(appointmentTypeInput)) {
                return type;
            }
        }

        return null;
    }

    private Appointment createAppointment(String appointmentId,
                                          User user,
                                          AppointmentInputData inputData,
                                          AppointmentStatus status) {
        return new Appointment(
                appointmentId,
                user,
                inputData.appointmentDate,
                inputData.selectedSlot,
                inputData.duration,
                inputData.participantCount,
                inputData.appointmentType,
                status
        );
    }

    private boolean cancelAppointment(String appointmentId) {
        if (authService.isAdminLoggedIn()) {
            return adminService.cancelReservationAsAdmin(appointmentId);
        }

        return appointmentService.cancelAppointment(appointmentId);
    }

    private boolean modifyAppointment(String appointmentId, Appointment updatedAppointment) {
        if (authService.isAdminLoggedIn()) {
            return adminService.modifyReservationAsAdmin(appointmentId, updatedAppointment);
        }

        return appointmentService.modifyAppointment(appointmentId, updatedAppointment);
    }

    private static void displayOperationResult(boolean successful,
                                               String successMessage,
                                               String failureMessage) {
        if (successful) {
            displayMessage(successMessage);
        } else {
            displayMessage(failureMessage);
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

    private static final class AppointmentInputData {

        private final LocalDate appointmentDate;
        private final TimeSlot selectedSlot;
        private final int duration;
        private final int participantCount;
        private final AppointmentType appointmentType;

        private AppointmentInputData(LocalDate appointmentDate,
                                     TimeSlot selectedSlot,
                                     int duration,
                                     int participantCount,
                                     AppointmentType appointmentType) {
            this.appointmentDate = appointmentDate;
            this.selectedSlot = selectedSlot;
            this.duration = duration;
            this.participantCount = participantCount;
            this.appointmentType = appointmentType;
        }
    }
}
