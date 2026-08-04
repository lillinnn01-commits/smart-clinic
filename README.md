# Smart Clinic — готовый учебный проект

## Самый простой запуск

1. Установите и запустите Docker Desktop.
2. Откройте PowerShell в папке проекта.
3. Выполните `docker compose up --build`.
4. Подождите сообщение `Started SmartClinicApplication`, затем откройте http://localhost:8080.

Остановка: `Ctrl+C`, затем `docker compose down`. Для полного удаления тестовой БД: `docker compose down -v`.

## Тестовые учётные записи

| Роль | Email | Пароль |
|---|---|---|
| Администратор | admin@clinic.local | admin123 |
| Врач | doctor@clinic.local | doctor123 |
| Пациент | patient@clinic.local | patient123 |

## Что отправлять в 26 ответах

1. Общедоступная GitHub-ссылка на `user-stories.md`.
2. Ссылка на `schema-design.md`.
3. Ссылка на `src/main/java/com/smartclinic/model/Doctor.java`.
4. Ссылка на `src/main/java/com/smartclinic/model/Appointment.java`.
5. Ссылка на `src/main/java/com/smartclinic/web/ApiController.java` (метод `doctorAppointments`).
6. Ссылка на `src/main/java/com/smartclinic/service/AppointmentService.java`.
7. Ссылка на `ApiController.java` (метод `prescription`).
8. Ссылка на `src/main/java/com/smartclinic/repository/PatientRepository.java`.
9. Ссылка на `src/main/java/com/smartclinic/service/TokenService.java`.
10. Ссылка на `src/main/java/com/smartclinic/service/DoctorService.java`.
11. Ссылка на `Dockerfile`.
12. Ссылка на `.github/workflows/build.yml`.
13. Скриншот страницы «Вход» с выбранной ролью «Администратор».
14. Скриншот страницы «Вход» с ролью «Врач».
15. Скриншот страницы «Вход» с ролью «Пациент».
16. Войдите администратором, заполните форму нового врача и сделайте скриншот результата.
17. Войдите пациентом, введите `Анна` в поиск, нажмите «Найти» и сделайте скриншот.
18. Сначала создайте запись пациентом, затем войдите врачом, выберите дату записи и сделайте скриншот.
19–23. Выполните SQL-команды ниже и приложите скриншот вывода.
24–26. Выполните curl-команды ниже и приложите скриншот вывода.

## SQL для пунктов 19–23

В другом PowerShell при запущенном проекте:

```powershell
docker compose exec mysql mysql -uclinic -pclinic123 smart_clinic
```

Затем по одной команде:

```sql
SHOW TABLES;
SELECT * FROM patients LIMIT 5;
CALL GetDailyAppointmentReportByDoctor(1, '2026-08-10');
CALL GetDoctorWithMostPatientsByMonth(2026, 8);
CALL GetDoctorWithMostPatientsByYear(2026);
```

Чтобы процедуры вернули строки, сначала создайте одну или несколько записей на указанные даты через портал пациента.

## curl для пунктов 24–26 (PowerShell)

Сначала получите токен пациента:

```powershell
$body = @{role='patient';email='patient@clinic.local';password='patient123'} | ConvertTo-Json
$token = (Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/login -ContentType application/json -Body $body).token
```

Пункт 24 — все врачи:

```powershell
curl.exe http://localhost:8080/api/doctors
```

Пункт 25 — записи пациента с его учётными данными:

```powershell
curl.exe -H "Authorization: Bearer $token" http://localhost:8080/api/patients/1/appointments
```

Пункт 26 — врачи нужной специальности и их свободное время:

```powershell
curl.exe "http://localhost:8080/api/doctors?specialty=Терапевт"
```

## Публикация на GitHub

Создайте пустой публичный репозиторий на github.com, а в этой папке выполните (подставьте свой адрес):

```powershell
git init
git add .
git commit -m "Smart Clinic project"
git branch -M main
git remote add origin https://github.com/ВАШ_ЛОГИН/smart-clinic.git
git push -u origin main
```

Для ответов 1–12 откройте нужный файл на GitHub и скопируйте адрес из браузера.
