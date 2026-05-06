package com.appointment.presentation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Starts the application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {

        AdminRepository adminRepository = new AdminRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        ScheduleRepository scheduleRepository = new ScheduleRepository();

        Administrator administrator = new Administrator("A1", "admin", "admin123", false);
        adminRepository.addAdmin(administrator);

        List<TimeSlot> slots = new ArrayList<>();
        slots.add(new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true));
        slots.add(new TimeSlot("S2", LocalTime.of(10, 0), LocalTime.of(11, 0), true));
        slots.add(new TimeSlot("S3", LocalTime.of(11, 0), LocalTime.of(12, 0), true));

        Schedule schedule = new Schedule("SCH1", LocalDate.now().plusDays(1), slots);
        scheduleRepository.saveSchedule(schedule);

        NotificationSubject notificationSubject = new NotificationSubject();

        EmailSender emailSender = new GmailEmailSender(
                "awwad3905@gmail.com",
                "yhgtwuzjcxxkvnqb"
        );

        notificationSubject.addObserver(new EmailNotificationObserver(emailSender));
        notificationSubject.addObserver(new SMSNotificationObserver());

        AuthService authService = new AuthService(adminRepository);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository);
        ReminderService reminderService = new ReminderService(notificationSubject);

        AppointmentService appointmentService =
                new AppointmentService(appointmentRepository, scheduleService, notificationSubject);

        appointmentService.addBookingRule(new DurationRule());
        appointmentService.addBookingRule(new CapacityRule());
        appointmentService.addBookingRule(new AppointmentTypeRule());

        AdminService adminService = new AdminService(appointmentService, authService);

        MenuDisplay menuDisplay = new MenuDisplay();
        InputHandler inputHandler = new InputHandler();

        MenuController menuController = new MenuController(
                menuDisplay,
                inputHandler,
                authService,
                scheduleService,
                appointmentService,
                reminderService,
                adminService
        );

        menuController.start();
    }
}