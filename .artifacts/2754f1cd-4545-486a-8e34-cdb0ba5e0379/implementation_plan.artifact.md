# Implementation Plan - FacultyHub Upgrade (University Talent Gap Solution)

Upgrade the CampusOS app to a comprehensive Faculty Lifecycle Management system, including automated recruitment, appointment booking, and a professional UI.

## User Review Required

> [!IMPORTANT]
> **Google Sign-In**: I have previously added Google Sign-In placeholder. You must ensure the Web Client ID in `AuthScreen.kt` is correct for it to function in production.
> **CV Parsing**: The "Automated CV Reader" will use a keyword-based text extraction logic as a baseline. For production, this can be swapped with a dedicated ML-based PDF parsing service.

## Proposed Changes

### 1. Data Models & Layer
Add new entities for Recruitment and Appointments.

#### [MODIFY] [Models.kt](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/app/src/main/java/com/example/campusos/Models.kt)
- Add `CVApplication` and `Appointment` data classes.
- Update `User` model to include fields for faculty matching (e.g., skills, department).

#### [MODIFY] [DataLayer.kt](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/app/src/main/java/com/example/campusos/DataLayer.kt)
- Add DAOs for Appointments and CV Applications.
- Update `CampusOSRepository` to handle:
    - CV Upload and Mock Parsing (Keyword/Regex).
    - Appointment availability check (Timetable cross-reference).
    - Admin approval workflow.

---

### 2. UI & Navigation
Implement the professional Navigation Drawer and updated Bottom Navigation.

#### [MODIFY] [MainActivity.kt](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/app/src/main/java/com/example/campusos/MainActivity.kt)
- Replace `ModalNavigationDrawer` content with full sections: Profile, Settings, Language, Appearance, Data & Storage, About Us.
- Update `NavigationBar` to: Home | Academic Hub | Timetable | Appointments.
- Integrate `DataStore` preferences app-wide (Theme, Font Size, Language).

---

### 3. Feature: Recruitment Pipeline
#### [NEW] `RecruitmentScreen.kt`
- **Candidate View**: Upload PDF (Mock), View Parsing results, Track Status.
- **Admin View**: List all candidates, see scores, Shortlist, Schedule Interview.

---

### 4. Feature: Appointment Booking
#### [NEW] `AppointmentScreen.kt`
- **Student View**: Pick Faculty, select from free slots (based on Timetable), Add note.
- **Admin/Faculty View**: Verify availability, Approve/Reject, Mark as Completed.

---

### 5. Security & Persistence
#### [MODIFY] [firestore.rules](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/firestore.rules)
- Add rules for `cv_applications` (Candidates see own, Admin sees all).
- Add rules for `appointments` (Participants see own, Admin sees all).

## Verification Plan

### Automated Tests
- `gradlew test`: Verify repository logic for slot availability.
- `gradlew assembleDebug`: Ensure project builds with new dependencies.

### Manual Verification
- Deploy to device/emulator.
- Test "Continue as Guest" or Google Sign-In.
- Navigate via Drawer to "Appearance" and change colors/fonts.
- Upload a dummy PDF to see mock CV parsing.
- Book an appointment and approve as Admin.
