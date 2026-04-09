package com.appointment.strategy;

import com.appointment.domain.entities.Appointment;

/**
 * Validates the number of participants.
 */
public class CapacityRule implements BookingRuleStrategy {

    private static final int MAX_CAPACITY = 10;

    @Override
    public boolean validate(Appointment appointment) {
        return appointment.getParticipantCount() > 0 &&
               appointment.getParticipantCount() <= MAX_CAPACITY;
    }
}