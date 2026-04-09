package com.appointment.applicationtest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.application.AdminService;
import com.appointment.application.AppointmentService;
import com.appointment.application.AuthService;
import com.appointment.domain.entities.Appointment;

public class AdminServiceTest {

    private AppointmentService appointmentService;
    private AuthService authService;
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        appointmentService = mock(AppointmentService.class);
        authService = mock(AuthService.class);
        adminService = new AdminService(appointmentService, authService);
    }

    @Test
    public void testCancelReservationAsAdminWhenLoggedIn() {
        when(authService.isAdminLoggedIn()).thenReturn(true);
        when(appointmentService.cancelAppointment("AP1")).thenReturn(true);

        boolean result = adminService.cancelReservationAsAdmin("AP1");

        assertTrue(result);
    }

    @Test
    public void testCancelReservationAsAdminWhenNotLoggedIn() {
        when(authService.isAdminLoggedIn()).thenReturn(false);

        boolean result = adminService.cancelReservationAsAdmin("AP1");

        assertFalse(result);
        verify(appointmentService, never()).cancelAppointment(anyString());
    }

    @Test
    public void testModifyReservationAsAdminWhenLoggedIn() {
        Appointment updatedAppointment = mock(Appointment.class);

        when(authService.isAdminLoggedIn()).thenReturn(true);
        when(appointmentService.modifyAppointment("AP1", updatedAppointment)).thenReturn(true);

        boolean result = adminService.modifyReservationAsAdmin("AP1", updatedAppointment);

        assertTrue(result);
    }

    @Test
    public void testModifyReservationAsAdminWhenNotLoggedIn() {
        Appointment updatedAppointment = mock(Appointment.class);

        when(authService.isAdminLoggedIn()).thenReturn(false);

        boolean result = adminService.modifyReservationAsAdmin("AP1", updatedAppointment);

        assertFalse(result);
        verify(appointmentService, never()).modifyAppointment(anyString(), any());
    }
}