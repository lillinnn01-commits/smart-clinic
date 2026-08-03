package com.smartclinic.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="prescriptions") public class Prescription {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(optional=false) private Doctor doctor; @ManyToOne(optional=false) private Patient patient; @Column(nullable=false,length=2000) private String medication; private LocalDate issuedAt=LocalDate.now();
 public Long getId(){return id;} public Doctor getDoctor(){return doctor;} public void setDoctor(Doctor v){doctor=v;} public Patient getPatient(){return patient;} public void setPatient(Patient v){patient=v;} public String getMedication(){return medication;} public void setMedication(String v){medication=v;} public LocalDate getIssuedAt(){return issuedAt;}
}
