package com.appointment.application;

import com.appointment.domain.entities.Appointment;

/**
 * Service responsible for administrator actions on appointments.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class AdminService {

    private AppointmentService appointmentService;
    private AuthService authService;

    /**
     * Constructs an AdminService object.
     * 
     * @param appointmentService the appointment service
     * @param authService the authentication service
     */
    public AdminService(AppointmentService appointmentService, AuthService authService) {
        this.appointmentService = appointmentService;
        this.authService = authService;
    }

    /**
     * Cancels a reservation as an administrator.
     * 
     * @param appointmentId the appointment id
     * @return true if cancellation succeeds, false otherwise
     */
    public boolean cancelReservationAsAdmin(String appointmentId) {
        if (!authService.isAdminLoggedIn()) {
            return false;
        }

        return appointmentService.cancelAppointment(appointmentId);
    }

    /**
     * Modifies a reservation as an administrator.
     * 
     * @param appointmentId the appointment id
     * @param updatedAppointment the updated appointment
     * @return true if modification succeeds, false otherwise
     */
    public boolean modifyReservationAsAdmin(String appointmentId, Appointment updatedAppointment) {
        if (!authService.isAdminLoggedIn()) {
            return false;
        }

        return appointmentService.modifyAppointment(appointmentId, updatedAppointment);
    }
}