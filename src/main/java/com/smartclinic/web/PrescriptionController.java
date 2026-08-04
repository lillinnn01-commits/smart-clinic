package com.smartclinic.web;

import com.smartclinic.model.Prescription;
import com.smartclinic.repository.DoctorRepository;
import com.smartclinic.repository.PatientRepository;
import com.smartclinic.repository.PrescriptionRepository;
import com.smartclinic.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/prescription")
public class PrescriptionController {
    private final PrescriptionRepository prescriptions;
    private final DoctorRepository doctors;
    private final PatientRepository patients;
    private final TokenService tokenService;

    public PrescriptionController(PrescriptionRepository prescriptions, DoctorRepository doctors,
                                  PatientRepository patients, TokenService tokenService) {
        this.prescriptions = prescriptions;
        this.doctors = doctors;
        this.patients = patients;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader("Authorization") String authorization,
                                    @RequestBody Map<String, String> body) {
        try {
            // Рецепт может создать только авторизованный пользователь портала врача.
            tokenService.validate(authorization);
            if (body.get("medication") == null || body.get("medication").isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Medication is required"));
            }
            Prescription prescription = new Prescription();
            prescription.setDoctor(doctors.findById(Long.valueOf(body.get("doctorId"))).orElseThrow());
            prescription.setPatient(patients.findById(Long.valueOf(body.get("patientId"))).orElseThrow());
            prescription.setMedication(body.get("medication"));
            Prescription saved = prescriptions.save(prescription);
            return ResponseEntity.ok(Map.of("success", true, "message", "Prescription saved", "prescription", saved));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", exception.getMessage()));
        }
    }
}
