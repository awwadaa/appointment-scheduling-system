package com.appointment.presentation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.appointment.application.AdminService;
import com.appointment.application.AppointmentService;
import com.appointment.application.AuthService;
import com.appointment.application.ReminderService;
import com.appointment.application.ScheduleService;
import com.appointment.domain.entities.Administrator;
import com.appointment.domain.entities.Schedule;
import com.appointment.domain.valueobjects.TimeSlot;
import com.appointment.observer.EmailNotificationObserver;
import com.appointment.observer.EmailSender;
import com.appointment.observer.GmailEmailSender;
import com.appointment.observer.NotificationSubject;
import com.appointment.observer.SMSNotificationObserver;
import com.appointment.persistence.AdminRepository;
import com.appointment.persistence.AppointmentRepository;
import com.appointment.persistence.ScheduleRepository;
import com.appointment.strategy.AppointmentTypeRule;
import com.appointment.strategy.CapacityRule;
import com.appointment.strategy.DurationRule;

/**
 * Main entry point for the Appointment Scheduling System.
 *
 * @author awwadaa
 * @version 1.0
 */
public class MainApp {

    private static final String ADMIN_ID = "A1";
    private static final String ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD_ENV = "DEFAULT_ADMIN_PASSWORD";
    private static final String APP_EMAIL_ENV = "APP_EMAIL";
    private static final String APP_PASSWORD_ENV = "APP_PASSWORD";
    private static final String SCHEDULE_ID = "SCH1";

    /**
     * Starts the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        configureCleanLogging();

        AdminRepository adminRepository = new AdminRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        ScheduleRepository scheduleRepository = new ScheduleRepository();

        addDefaultAdmin(adminRepository);
        addDefaultSchedule(scheduleRepository);

        NotificationSubject notificationSubject = createNotificationSubject();

        AuthService authService = new AuthService(adminRepository);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository);
        ReminderService reminderService = new ReminderService(notificationSubject);

        AppointmentService appointmentService =
                new AppointmentService(appointmentRepository, scheduleService, notificationSubject);

        addBookingRules(appointmentService);

        AdminService adminService = new AdminService(appointmentService, authService);

        MenuController menuController = new MenuController(
                new MenuDisplay(),
                new InputHandler(),
                authService,
                scheduleService,
                appointmentService,
                reminderService,
                adminService
        );

        menuController.start();
    }

    private static void configureCleanLogging() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n");

        Logger rootLogger = LogManager.getLogManager().getLogger("");

        for (Handler handler : rootLogger.getHandlers()) {
            handler.setFormatter(new SimpleFormatter());
        }
    }

    private static void addDefaultAdmin(AdminRepository adminRepository) {
        String adminPassword = getEnvironmentValue(DEFAULT_ADMIN_PASSWORD_ENV);
        Administrator administrator = new Administrator(ADMIN_ID, ADMIN_USERNAME, adminPassword, false);
        adminRepository.addAdmin(administrator);
    }

    private static void addDefaultSchedule(ScheduleRepository scheduleRepository) {
        Schedule schedule = new Schedule(
                SCHEDULE_ID,
                LocalDate.now().plusDays(1),
                createDefaultTimeSlots()
        );

        scheduleRepository.saveSchedule(schedule);
    }

    private static List<TimeSlot> createDefaultTimeSlots() {
        List<TimeSlot> slots = new ArrayList<>();
        slots.add(new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true));
        slots.add(new TimeSlot("S2", LocalTime.of(10, 0), LocalTime.of(11, 0), true));
        slots.add(new TimeSlot("S3", LocalTime.of(11, 0), LocalTime.of(12, 0), true));
        return slots;
    }

    private static NotificationSubject createNotificationSubject() {
        NotificationSubject notificationSubject = new NotificationSubject();

        addEmailObserverIfConfigured(notificationSubject);
        notificationSubject.addObserver(new SMSNotificationObserver());

        return notificationSubject;
    }

    private static void addEmailObserverIfConfigured(NotificationSubject notificationSubject) {
        String senderEmail = getEnvironmentValue(APP_EMAIL_ENV);
        String appPassword = getEnvironmentValue(APP_PASSWORD_ENV);

        if (!senderEmail.isEmpty() && !appPassword.isEmpty()) {
            EmailSender emailSender = new GmailEmailSender(senderEmail, appPassword);
            notificationSubject.addObserver(new EmailNotificationObserver(emailSender));
        }
    }

    private static void addBookingRules(AppointmentService appointmentService) {
        appointmentService.addBookingRule(new DurationRule());
        appointmentService.addBookingRule(new CapacityRule());
        appointmentService.addBookingRule(new AppointmentTypeRule());
    }

    private static String getEnvironmentValue(String variableName) {
        String value = System.getenv(variableName);

        if (value == null) {
            return "";
        }

        return value;
    }
}