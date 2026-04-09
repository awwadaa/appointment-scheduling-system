package com.appointment.presentation;

import java.time.LocalDate;
import java.util.List;

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

    private MenuDisplay menuDisplay;
    private InputHandler inputHandler;
    private AuthService authService;
    private ScheduleService scheduleService;
    private AppointmentService appointmentService;
    private ReminderService reminderService;
    private AdminService adminService;

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
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 0);

        inputHandler.close();
    }

    /**
     * Handles administrator login.
     */
    private void handleAdminLogin() {
        System.out.print("Enter username: ");
        String username = inputHandler.readString();

        System.out.print("Enter password: ");
        String password = inputHandler.readString();

        if (authService.login(username, password)) {
            System.out.println("Administrator logged in successfully.");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    /**
     * Handles administrator logout.
     */
    private void handleAdminLogout() {
        authService.logout();
        System.out.println("Administrator logged out.");
    }

    /**
     * Handles viewing available slots for a given date.
     */
    private void handleViewAvailableSlots() {
        System.out.print("Enter appointment date (yyyy-mm-dd): ");
        LocalDate date = inputHandler.readDate();

        List<TimeSlot> availableSlots = scheduleService.getAvailableSlots(date);

        if (availableSlots.isEmpty()) {
            System.out.println("No available slots found.");
            return;
        }

        System.out.println("Available slots:");
        for (TimeSlot slot : availableSlots) {
            System.out.println(slot);
        }
    }

    /**
     * Handles booking an appointment.
     */
    private void handleBookAppointment() {
        System.out.print("Enter appointment id: ");
        String appointmentId = inputHandler.readString();

        System.out.print("Enter user id: ");
        String userId = inputHandler.readString();

        System.out.print("Enter user name: ");
        String userName = inputHandler.readString();

        System.out.print("Enter user email: ");
        String userEmail = inputHandler.readString();

        System.out.print("Enter user phone: ");
        String userPhone = inputHandler.readString();

        User user = new User(userId, userName, userEmail, userPhone);

        System.out.print("Enter appointment date (yyyy-mm-dd): ");
        LocalDate appointmentDate = inputHandler.readDate();

        System.out.println("Available slots for this date:");
        List<TimeSlot> availableSlots = scheduleService.getAvailableSlots(appointmentDate);

        if (availableSlots.isEmpty()) {
            System.out.println("No available slots found for this date.");
            return;
        }

        for (TimeSlot slot : availableSlots) {
            System.out.println(slot);
        }

        System.out.print("Enter slot id: ");
        String slotId = inputHandler.readString();

        TimeSlot selectedSlot = null;
        for (TimeSlot slot : availableSlots) {
            if (slot.getSlotId().equals(slotId)) {
                selectedSlot = slot;
                break;
            }
        }

        if (selectedSlot == null) {
            System.out.println("Invalid slot id.");
            return;
        }

        System.out.print("Enter duration in minutes: ");
        int duration = inputHandler.readInt();

        System.out.print("Enter participant count: ");
        int participantCount = inputHandler.readInt();

        System.out.println("Appointment Types:");
        for (AppointmentType type : AppointmentType.values()) {
            System.out.println("- " + type);
        }

        System.out.print("Enter appointment type exactly as shown: ");
        String appointmentTypeInput = inputHandler.readString();

        AppointmentType appointmentType;
        try {
            appointmentType = AppointmentType.valueOf(appointmentTypeInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid appointment type.");
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
            System.out.println("Appointment booked successfully.");
        } else {
            System.out.println("Appointment booking failed.");
        }
    }

    /**
     * Handles cancelling an appointment.
     */
    private void handleCancelAppointment() {
        System.out.print("Enter appointment id to cancel: ");
        String appointmentId = inputHandler.readString();

        boolean cancelled;

        if (authService.isAdminLoggedIn()) {
            cancelled = adminService.cancelReservationAsAdmin(appointmentId);
        } else {
            cancelled = appointmentService.cancelAppointment(appointmentId);
        }

        if (cancelled) {
            System.out.println("Appointment cancelled successfully.");
        } else {
            System.out.println("Cancellation failed.");
        }
    }

    /**
     * Handles modifying an appointment.
     */
    private void handleModifyAppointment() {
        System.out.print("Enter appointment id to modify: ");
        String appointmentId = inputHandler.readString();

        Appointment existingAppointment = appointmentService.findAppointmentById(appointmentId);

        if (existingAppointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        System.out.print("Enter new appointment date (yyyy-mm-dd): ");
        LocalDate newDate = inputHandler.readDate();

        List<TimeSlot> availableSlots = scheduleService.getAvailableSlots(newDate);

        if (availableSlots.isEmpty()) {
            System.out.println("No available slots for the selected date.");
            return;
        }

        System.out.println("Available slots:");
        for (TimeSlot slot : availableSlots) {
            System.out.println(slot);
        }

        System.out.print("Enter new slot id: ");
        String slotId = inputHandler.readString();

        TimeSlot selectedSlot = null;
        for (TimeSlot slot : availableSlots) {
            if (slot.getSlotId().equals(slotId)) {
                selectedSlot = slot;
                break;
            }
        }

        if (selectedSlot == null) {
            System.out.println("Invalid slot id.");
            return;
        }

        System.out.print("Enter new duration in minutes: ");
        int duration = inputHandler.readInt();

        System.out.print("Enter new participant count: ");
        int participantCount = inputHandler.readInt();

        System.out.println("Appointment Types:");
        for (AppointmentType type : AppointmentType.values()) {
            System.out.println("- " + type);
        }

        System.out.print("Enter new appointment type exactly as shown: ");
        String appointmentTypeInput = inputHandler.readString();

        AppointmentType appointmentType;
        try {
            appointmentType = AppointmentType.valueOf(appointmentTypeInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid appointment type.");
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
            System.out.println("Appointment modified successfully.");
        } else {
            System.out.println("Modification failed.");
        }
    }

    /**
     * Handles sending a reminder for an appointment.
     */
    private void handleSendReminder() {
        System.out.print("Enter appointment id: ");
        String appointmentId = inputHandler.readString();

        Appointment appointment = appointmentService.findAppointmentById(appointmentId);

        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        reminderService.sendReminder(appointment);
        System.out.println("Reminder sent successfully.");
    }
}