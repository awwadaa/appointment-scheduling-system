package com.appointment.persistence;

import java.util.ArrayList;
import java.util.List;

import com.appointment.domain.entities.Appointment;

/**
 * Repository for managing appointments in memory.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class AppointmentRepository {

    private List<Appointment> appointments;

    /**
     * Constructs an AppointmentRepository object.
     */
    public AppointmentRepository() {
        this.appointments = new ArrayList<>();
    }

    /**
     * Saves an appointment to the repository.
     * 
     * @param appointment the appointment to save
     */
    public void save(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Finds an appointment by id.
     * 
     * @param appointmentId the appointment id to search for
     * @return the matching appointment, or null if not found
     */
    public Appointment findById(String appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Returns all appointments.
     * 
     * @return the list of appointments
     */
    public List<Appointment> findAll() {
        return appointments;
    }

    /**
     * Deletes an appointment by id.
     * 
     * @param appointmentId the appointment id to delete
     */
    public void delete(String appointmentId) {
        Appointment appointment = findById(appointmentId);
        if (appointment != null) {
            appointments.remove(appointment);
        }
    }

    /**
     * Updates an existing appointment.
     * 
     * @param updatedAppointment the updated appointment data
     */
    public void update(Appointment updatedAppointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentId().equals(updatedAppointment.getAppointmentId())) {
                appointments.set(i, updatedAppointment);
                return;
            }
        }
    }
}