package com.appointment.strategy;

import com.appointment.domain.entities.Appointment;

/**
 * Validates the duration of the appointment.
 */
public class DurationRule implements BookingRuleStrategy {

    private static final int MIN_DURATION = 15;
    private static final int MAX_DURATION = 180;

    @Override
    public boolean validate(Appointment appointment) {
        int duration = appointment.getDurationMinutes();
        return duration >= MIN_DURATION && duration <= MAX_DURATION;
    }
}