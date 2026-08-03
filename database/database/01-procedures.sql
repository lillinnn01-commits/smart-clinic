USE smart_clinic;
DROP PROCEDURE IF EXISTS GetDailyAppointmentReportByDoctor;
DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByMonth;
DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByYear;
DELIMITER //
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN p_doctor_id BIGINT, IN p_date DATE)
BEGIN SELECT a.id,d.name AS doctor,p.name AS patient,a.appointment_time,a.status FROM appointments a JOIN doctors d ON d.id=a.doctor_id JOIN patients p ON p.id=a.patient_id WHERE a.doctor_id=p_doctor_id AND DATE(a.appointment_time)=p_date ORDER BY a.appointment_time; END//
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN p_year INT, IN p_month INT)
BEGIN SELECT d.id,d.name,COUNT(DISTINCT a.patient_id) AS patient_count FROM doctors d JOIN appointments a ON a.doctor_id=d.id WHERE YEAR(a.appointment_time)=p_year AND MONTH(a.appointment_time)=p_month GROUP BY d.id,d.name ORDER BY patient_count DESC LIMIT 1; END//
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN p_year INT)
BEGIN SELECT d.id,d.name,COUNT(DISTINCT a.patient_id) AS patient_count FROM doctors d JOIN appointments a ON a.doctor_id=d.id WHERE YEAR(a.appointment_time)=p_year GROUP BY d.id,d.name ORDER BY patient_count DESC LIMIT 1; END//
DELIMITER ;
