# Проект базы данных Smart Clinic

## Таблицы

- `doctors`: `id BIGINT PK`, `name VARCHAR`, `specialty VARCHAR`, `email VARCHAR UNIQUE`, `password VARCHAR`.
- `patients`: `id BIGINT PK`, `name VARCHAR`, `email VARCHAR UNIQUE`, `phone VARCHAR`, `password VARCHAR`.
- `appointments`: `id BIGINT PK`, `doctor_id BIGINT FK -> doctors.id`, `patient_id BIGINT FK -> patients.id`, `appointment_time DATETIME`, `status VARCHAR`.
- `prescriptions`: `id BIGINT PK`, `doctor_id BIGINT FK -> doctors.id`, `patient_id BIGINT FK -> patients.id`, `medication VARCHAR(2000)`, `issued_at DATE`.
- `doctor_available_times`: `doctor_id BIGINT FK -> doctors.id`, `available_time VARCHAR`.

Один врач и один пациент могут иметь много записей. Каждый рецепт принадлежит одному врачу и одному пациенту.
