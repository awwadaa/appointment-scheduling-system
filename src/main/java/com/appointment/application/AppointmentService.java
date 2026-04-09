package com.appointment.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.appointment.domain.entities.Appointment;
import com.appointment.domain.enums.AppointmentStatus;
import com.appointment.observer.NotificationSubject;
import com.appointment.persistence.AppointmentRepository;
import com.appointment.strategy.BookingRuleStrategy;

/**
 * Service responsible for booking, modifying, and cancelling appointments.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private ScheduleService scheduleService;
    private NotificationSubject notificationSubject;
    private List<BookingRuleStrategy> bookingRules;

    /**
     * Constructs an AppointmentService object.
     * 
     * @param appointmentRepository the appointment repository
     * @param scheduleService the schedule service
     * @param notificationSubject the notification subject
     */
    public AppointmentService(AppointmentRepository appointmentRepository,
                              ScheduleService scheduleService,
                              NotificationSubject notificationSubject) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleService = scheduleService;
        this.notificationSubject = notificationSubject;
        this.bookingRules = new ArrayList<>();
    }

    /**
     * Adds a booking validation rule.
     * 
     * @param rule the rule to add
     */
    public void addBookingRule(BookingRuleStrategy rule) {
        bookingRules.add(rule);
    }

    /**
     * Books an appointment if all rules pass and the slot is available.
     * 
     * @param appointment the appointment to book
     * @return true if booking succeeds, false otherwise
     */
    public boolean bookAppointment(Appointment appointment) {
        for (BookingRuleStrategy rule : bookingRules) {
            if (!rule.validate(appointment)) {
                return false;
            }
        }

        if (!scheduleService.isSlotAvailable(appointment.getAppointmentDate(), appointment.getTimeSlot())) {
            return false;
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
        scheduleService.reserveSlot(appointment.getAppointmentDate(), appointment.getTimeSlot());

        notificationSubject.notifyObservers(
                appointment.getUser(),
                "Your appointment has been booked successfully."
        );

        return true;
    }

    /**
     * Cancels a future appointment.
     * 
     * @param appointmentId the appointment id
     * @return true if cancellation succeeds, false otherwise
     */
    public boolean cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId);

        if (appointment == null) {
            return false;
        }

        if (!appointment.getAppointmentDate().isAfter(LocalDate.now())) {
            return false;
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.update(appointment);
        scheduleService.releaseSlot(appointment.getAppointmentDate(), appointment.getTimeSlot());

        notificationSubject.notifyObservers(
                appointment.getUser(),
                "Your appointment has been cancelled."
        );

        return true;
    }

    /**
     * Modifies a future appointment.
     * 
     * @param appointmentId the appointment id to modify
     * @param updatedAppointment the updated appointment data
     * @return true if modification succeeds, false otherwise
     */
    public boolean modifyAppointment(String appointmentId, Appointment updatedAppointment) {
        Appointment existingAppointment = appointmentRepository.findById(appointmentId);

        if (existingAppointment == null) {
            return false;
        }

        if (!existingAppointment.getAppointmentDate().isAfter(LocalDate.now())) {
            return false;
        }

        for (BookingRuleStrategy rule : bookingRules) {
            if (!rule.validate(updatedAppointment)) {
                return false;
            }
        }

        scheduleService.releaseSlot(existingAppointment.getAppointmentDate(), existingAppointment.getTimeSlot());

        if (!scheduleService.isSlotAvailable(updatedAppointment.getAppointmentDate(), updatedAppointment.getTimeSlot())) {
            scheduleService.reserveSlot(existingAppointment.getAppointmentDate(), existingAppointment.getTimeSlot());
            return false;
        }

        updatedAppointment.setStatus(existingAppointment.getStatus());
        appointmentRepository.update(updatedAppointment);
        scheduleService.reserveSlot(updatedAppointment.getAppointmentDate(), updatedAppointment.getTimeSlot());

        notificationSubject.notifyObservers(
                updatedAppointment.getUser(),
                "Your appointment has been updated successfully."
        );

        return true;
    }

    /**
     * Finds an appointment by id.
     * 
     * @param appointmentId the appointment id
     * @return the matching appointment, or null if not found
     */
    public Appointment findAppointmentById(String appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    /**
     * Returns all appointments for a specific user.
     * 
     * @param userId the user id
     * @return list of appointments for the user
     */
    public List<Appointment> getAppointmentsByUser(String userId) {
        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointmentRepository.findAll()) {
            if (appointment.getUser().getUserId().equals(userId)) {
                result.add(appointment);
            }
        }

        return result;
    }

    /**
     * Returns all appointments.
     * 
     * @return list of all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
