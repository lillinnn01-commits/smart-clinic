package com.smartclinic.service;

import com.smartclinic.model.Appointment;
import com.smartclinic.model.Doctor;
import com.smartclinic.repository.AppointmentRepository;
import com.smartclinic.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DoctorService {
    private final DoctorRepository doctors;
    private final AppointmentRepository appointments;
    private final TokenService tokenService;

    public DoctorService(DoctorRepository doctors, AppointmentRepository appointments, TokenService tokenService) {
        this.doctors = doctors;
        this.appointments = appointments;
        this.tokenService = tokenService;
    }

    public List<Doctor> available(String specialty) {
        return specialty == null || specialty.isBlank()
                ? doctors.findAll()
                : doctors.findBySpecialtyIgnoreCase(specialty);
    }

    public List<LocalTime> getAvailableTimes(Long doctorId, LocalDate date) {
        Doctor doctor = doctors.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        // Сначала получаем занятые часы за выбранный день.
        List<LocalTime> bookedTimes = appointments
                .findByDoctorIdAndAppointmentTimeBetween(doctorId, date.atStartOfDay(), date.plusDays(1).atStartOfDay())
                .stream()
                .map(Appointment::getAppointmentTime)
                .map(dateTime -> dateTime.toLocalTime().withSecond(0).withNano(0))
                .toList();
        // Копируем расписание врача и вычитаем уже занятые слоты.
        List<LocalTime> result = new ArrayList<>(doctor.getAvailableTimes());
        result.removeAll(bookedTimes);
        return result;
    }

    public Map<String, Object> login(String email, String password) {
        // Для учебного проекта пароль хранится как строка. В production здесь нужен BCrypt.
        Doctor doctor = doctors.findByEmail(email)
                .filter(candidate -> candidate.getPassword().equals(password))
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor credentials"));
        return Map.of(
                "authenticated", true,
                "doctorId", doctor.getId(),
                "name", doctor.getName(),
                "role", "DOCTOR",
                "token", tokenService.generateToken(doctor.getEmail(), "doctor")
        );
    }
}
