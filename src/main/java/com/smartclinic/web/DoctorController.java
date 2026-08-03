package com.smartclinic.web;

import com.smartclinic.model.Appointment;
import com.smartclinic.service.AppointmentService;
import com.smartclinic.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    private final AppointmentService appointmentService;
    private final TokenService tokenService;

    public DoctorController(AppointmentService appointmentService, TokenService tokenService) {
        this.appointmentService = appointmentService;
        this.tokenService = tokenService;
    }

    @GetMapping("/{doctorId}/appointments")
    public ResponseEntity<List<Appointment>> getAppointments(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long doctorId,
            @RequestParam LocalDate date) {
        tokenService.validate(authorization);
        return ResponseEntity.ok(appointmentService.forDoctorOn(doctorId, date));
    }
}
