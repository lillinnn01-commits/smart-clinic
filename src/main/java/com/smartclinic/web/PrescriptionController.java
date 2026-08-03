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
            tokenService.validate(authorization);
            Prescription prescription = new Prescription();
            prescription.setDoctor(doctors.findById(Long.valueOf(body.get("doctorId"))).orElseThrow());
            prescription.setPatient(patients.findById(Long.valueOf(body.get("patientId"))).orElseThrow());
            prescription.setMedication(body.get("medication"));
            return ResponseEntity.ok(prescriptions.save(prescription));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
        }
    }
}
