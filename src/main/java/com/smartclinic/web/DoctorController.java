package com.smartclinic.web;

import com.smartclinic.service.DoctorService;
import com.smartclinic.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final TokenService tokenService;

    public DoctorController(DoctorService doctorService, TokenService tokenService) {
        this.doctorService = doctorService;
        this.tokenService = tokenService;
    }

    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<Map<String, Object>> getAvailability(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long doctorId,
            @RequestParam LocalDate date) {
        try {
            // Доступность врача относится к персональным данным, поэтому сначала проверяем JWT.
            tokenService.validate(authorization);
            java.util.List<LocalTime> availableTimes = doctorService.getAvailableTimes(doctorId, date);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "doctorId", doctorId,
                    "date", date,
                    "availableTimes", availableTimes
            ));
        } catch (Exception exception) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", exception.getMessage()
            ));
        }
    }
}
