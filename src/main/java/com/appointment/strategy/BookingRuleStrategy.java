package com.appointment.strategy;

import com.appointment.domain.entities.Appointment;

/**
 * Strategy interface for validating appointment booking rules.
 * 
 * @author awwadaa
 * @version 1.0
 */
public interface BookingRuleStrategy {

    /**
     * Validates the appointment based on a specific rule.
     * 
     * @param appointment the appointment to validate
     * @return true if valid, false otherwise
     */
    boolean validate(Appointment appointment);
}