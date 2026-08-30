# Implementation Plan - Fix Chat, Appointment Sync, and Restructure UI

This plan addresses the chat crash, real-time sync issues for appointments across devices, and the UI requirement to move Chat inside the Appointments tab.

## User Review Required

> [!IMPORTANT]
> The "Class Teacher" for students and the "Student" for faculty/admin in the chat will be mocked using the existing mock IDs (`admin_uid`, `student_uid`) as the current system doesn't have a formal class assignment structure.

## Proposed Changes

### Data & Sync Layer

#### [MODIFY] [Models.kt](file:///C:/Users/HP/Desktop/Lernathon 5.0 Project/EDU Talent Pro/MK-2/app/src/main/java/com/example/campusos/Models.kt)
- Add default values to `ChatMessage`, `SubmissionEntity`, `PersonalDocumentEntity`, `CachedAcademicItemEntity`, and `TimetableEvent` to provide no-argument constructors for Firestore deserialization. This is the primary suspect for the chat crash.

#### [MODIFY] [DataLayer.kt](file:///C:/Users/HP/Desktop/Lernathon 5.0 Project/EDU Talent Pro/MK-2/app/src/main/java/com/example/campusos/DataLayer.kt)
- Fix Appointment Sync: Add multiple Firestore listeners to cover both `studentId` and `facultyId` fields so appointments show up for both parties in real-time.
- Fix Chat Sync: Add listeners for both `senderId` and `receiverId` to ensure the user sees incoming messages as well as sent ones.
- Improve `sendMessage` robustness.
- Replace `GlobalScope` with a dedicated `CoroutineScope` managed within the repository (though `init` in a Singleton is usually tied to the app lifecycle).

### UI & Navigation

#### [MODIFY] [MainActivity.kt](file:///C:/Users/HP/Desktop/Lernathon 5.0 Project/EDU Talent Pro/MK-2/app/src/main/java/com/example/campusos/MainActivity.kt)
- Remove the "Chat" option from the navigation drawer and the bottom navigation bar.
- Update `MainNavigation` to handle the removal of the `chat` route.

#### [MODIFY] [AppScreens.kt](file:///C:/Users/HP/Desktop/Lernathon 5.0 Project/EDU Talent Pro/MK-2/app/src/main/java/com/example/campusos/AppScreens.kt)
- Redesign `AppointmentScreen` to include a two-button selection (segmented control or TabRow) at the top: **Chat** and **Appointments**.
- Integrate the `ChatScreen` logic directly into `AppointmentScreen` when the "Chat" tab is selected.
- Update `ChatScreen` to be more robust and fit into the new layout.

## Verification Plan

### Automated Tests
- N/A (Manual verification on device/emulator is preferred for real-time sync and UI flow).

### Manual Verification
1. **Chat Crash Fix**: Open the app, navigate to Appointments -> Chat, send a message. Verify the app does not crash and the message is saved locally and in Firestore.
2. **Appointment Sync**: Log in on two different devices (or one real user, one mock). Book an appointment on one and verify it appears instantly on the other.
3. **UI Restructure**: Verify "Chat" is gone from the main navigation. Verify the Appointments tab has the new two-button interface.
