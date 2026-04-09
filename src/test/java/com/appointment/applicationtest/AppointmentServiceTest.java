package com.appointment.applicationtest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointment.application.AppointmentService;
import com.appointment.application.ScheduleService;
import com.appointment.domain.entities.Appointment;
import com.appointment.domain.entities.Schedule;
import com.appointment.domain.entities.User;
import com.appointment.domain.enums.AppointmentStatus;
import com.appointment.domain.enums.AppointmentType;
import com.appointment.domain.valueobjects.TimeSlot;
import com.appointment.observer.NotificationSubject;
import com.appointment.persistence.AppointmentRepository;
import com.appointment.persistence.ScheduleRepository;
import com.appointment.strategy.AppointmentTypeRule;
import com.appointment.strategy.CapacityRule;
import com.appointment.strategy.DurationRule;

public class AppointmentServiceTest {

    private AppointmentService appointmentService;
    private AppointmentRepository appointmentRepository;
    private ScheduleRepository scheduleRepository;
    private ScheduleService scheduleService;
    private Appointment appointment;

    @BeforeEach
    public void setUp() {
        appointmentRepository = new AppointmentRepository();
        scheduleRepository = new ScheduleRepository();
        scheduleService = new ScheduleService(scheduleRepository);

        NotificationSubject notificationSubject = new NotificationSubject();

        appointmentService = new AppointmentService(
                appointmentRepository,
                scheduleService,
                notificationSubject
        );

        appointmentService.addBookingRule(new DurationRule());
        appointmentService.addBookingRule(new CapacityRule());
        appointmentService.addBookingRule(new AppointmentTypeRule());

        List<TimeSlot> slots = new ArrayList<>();
        TimeSlot slot = new TimeSlot("S1", LocalTime.of(9, 0), LocalTime.of(10, 0), true);
        slots.add(slot);

        Schedule schedule = new Schedule("SCH1", LocalDate.now().plusDays(1), slots);
        scheduleRepository.saveSchedule(schedule);

        User user = new User("U1", "awwad", "awwad@test.com", "0599999999");

        appointment = new Appointment(
                "AP1",
                user,
                LocalDate.now().plusDays(1),
                slot,
                60,
                1,
                AppointmentType.INDIVIDUAL,
                AppointmentStatus.CONFIRMED
        );
    }

    @Test
    public void testBookAppointmentSuccess() {
        boolean result = appointmentService.bookAppointment(appointment);

        assertTrue(result);
        assertNotNull(appointmentRepository.findById("AP1"));
    }

    @Test
    public void testBookAppointmentFailBecauseSlotUnavailable() {
        appointmentService.bookAppointment(appointment);

        User secondUser = new User("U2", "Ali", "ali@test.com", "0598888888");

        Appointment secondAppointment = new Appointment(
                "AP2",
                secondUser,
                LocalDate.now().plusDays(1),
                appointment.getTimeSlot(),
                60,
                1,
                AppointmentType.INDIVIDUAL,
                AppointmentStatus.CONFIRMED
        );

        boolean result = appointmentService.bookAppointment(secondAppointment);

        assertFalse(result);
    }

    @Test
    public void testCancelFutureAppointmentSuccess() {
        appointmentService.bookAppointment(appointment);

        boolean result = appointmentService.cancelAppointment("AP1");

        assertTrue(result);
        assertEquals(AppointmentStatus.CANCELLED,
                appointmentRepository.findById("AP1").getStatus());
    }
    @Test
    public void testBookAppointmentFailBecauseInvalidDuration() {
        appointment.setDurationMinutes(10);
        boolean result = appointmentService.bookAppointment(appointment);
        assertFalse(result);
    }

    @Test
    public void testBookAppointmentFailBecauseInvalidCapacity() {
        appointment.setParticipantCount(0);
        boolean result = appointmentService.bookAppointment(appointment);
        assertFalse(result);
    }

    @Test
    public void testBookAppointmentFailBecauseInvalidAppointmentTypeRule() {
        appointment.setAppointmentType(AppointmentType.GROUP);
        appointment.setParticipantCount(1);
        boolean result = appointmentService.bookAppointment(appointment);
        assertFalse(result);
    }

    @Test
    public void testCancelAppointmentFailWhenNotFound() {
        boolean result = appointmentService.cancelAppointment("UNKNOWN");
        assertFalse(result);
    }

    @Test
    public void testCancelAppointmentFailWhenAppointmentIsNotFuture() {
        User user = new User("U2", "Ali", "ali@test.com", "0598888888");
        TimeSlot slot = new TimeSlot("S2", LocalTime.of(10, 0), LocalTime.of(11, 0), true);

        Appointment oldAppointment = new Appointment(
                "OLD1",
                user,
                LocalDate.now(),
                slot,
                60,
                1,
                AppointmentType.INDIVIDUAL,
                AppointmentStatus.CONFIRMED
        );

        appointmentRepository.save(oldAppointment);

        boolean result = appointmentService.cancelAppointment("OLD1");
        assertFalse(result);
    }

    @Test
    public void testModifyAppointmentFailWhenNotFound() {
        boolean result = appointmentService.modifyAppointment("UNKNOWN", appointment);
        assertFalse(result);
    }

    @Test
    public void testModifyAppointmentFailWhenAppointmentIsNotFuture() {
        User user = new User("U3", "mohammed", "mohammed@test.com", "0597777777");
        TimeSlot slot = new TimeSlot("S3", LocalTime.of(11, 0), LocalTime.of(12, 0), true);

        Appointment oldAppointment = new Appointment(
                "OLD2",
                user,
                LocalDate.now(),
                slot,
                60,
                1,
                AppointmentType.INDIVIDUAL,
                AppointmentStatus.CONFIRMED
        );

        appointmentRepository.save(oldAppointment);

        boolean result = appointmentService.modifyAppointment("OLD2", appointment);
        assertFalse(result);
    }

    @Test
    public void testModifyAppointmentFailBecauseNewSlotUnavailable() {
        appointmentService.bookAppointment(appointment);

        TimeSlot anotherSlot = new TimeSlot("S2", LocalTime.of(10, 0), LocalTime.of(11, 0), false);

        User secondUser = new User("U4", "Ahmad", "ahmad@test.com", "0596666666");
        Appointment updatedAppointment = new Appointment(
                "AP1",
                secondUser,
                LocalDate.now().plusDays(1),
                anotherSlot,
                60,
                1,
                AppointmentType.INDIVIDUAL,
                AppointmentStatus.CONFIRMED
        );

        boolean result = appointmentService.modifyAppointment("AP1", updatedAppointment);

        assertFalse(result);
    }
}