package com.appointment.strategy;

import com.appointment.domain.entities.Appointment;
import com.appointment.domain.enums.AppointmentType;

/**
 * Validates rules based on appointment type.
 */
public class AppointmentTypeRule implements BookingRuleStrategy {

    @Override
    public boolean validate(Appointment appointment) {

        AppointmentType type = appointment.getAppointmentType();

        // Example rule:
        if (type == AppointmentType.GROUP) {
            return appointment.getParticipantCount() >= 2;
        }

        if (type == AppointmentType.INDIVIDUAL) {
            return appointment.getParticipantCount() == 1;
        }

        return true;
    }
}