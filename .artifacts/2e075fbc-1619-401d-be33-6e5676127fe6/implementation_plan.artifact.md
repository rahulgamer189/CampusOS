# Implementation Plan - CampusOS (FacultyHub) Comprehensive Overhaul

This plan details the steps to transition CampusOS from a hardcoded prototype to a full-featured, secure, and customizable academic management app.

## User Review Required

> [!IMPORTANT]
> **Firebase Configuration**: I will add the Firebase plugins and dependencies, but you will need to ensure a valid `google-services.json` is placed in the `app/` directory for the app to run.
> **Role Management**: Since I am removing the role-switcher chip, roles will now be determined by the authenticated user's data in Firestore.

## Proposed Changes

### 1. Build & Dependencies
- **[MODIFY] [root build.gradle.kts](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/build.gradle.kts)**: Add Google Services classpath.
- **[MODIFY] [app build.gradle.kts](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/app/build.gradle.kts)**:
    - Add Firebase (Auth, Firestore, Storage) dependencies.
    - Add DataStore (Preferences) dependency.
    - Add Navigation Compose dependency.

---

### 2. Data Models & Core Logic
- **[MODIFY] [Models.kt](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/app/src/main/java/com/example/campusos/Models.kt)**:
    - Expand `User` model with phone, address, etc.
    - Add `TimetableEvent` and `ChatMessage` models.
    - Add `AppPreferences` model for theme/language.
- **[MODIFY] [DataLayer.kt](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/app/src/main/java/com/example/campusos/DataLayer.kt)**:
    - Update `CampusOSRepository` to integrate Firebase Auth and Firestore.
    - Implement `UserPreferencesRepository` using DataStore.
    - Add Room entities and DAOs for Timetable and Chat (for offline support).

---

### 3. Authentication & Security
- **[NEW] AuthScreen.kt**: Login and Registration UI.
- **[MODIFY] [firestore.rules](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/firestore.rules)**: Restrict access to `personalDocuments` to owner only. Faculty/Admin will have read access to student metadata but not private files.

---

### 4. Theming & Appearance
- **[MODIFY] [Theme.kt](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/app/src/main/java/com/example/campusos/Theme.kt)**:
    - Implement the specified RED, BLUE, and PURPLE color palettes.
    - Create a dynamic theme system that responds to DataStore preferences (color, dark mode, text size).

---

### 5. UI & Navigation Overhaul
- **[MODIFY] [MainActivity.kt](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/app/src/main/java/com/example/campusos/MainActivity.kt)**:
    - Implement `ModalNavigationDrawer` for the Hamburger menu.
    - Update `NavigationBar` to reflect new tabs: Home, Academic Hub, Timetable, Chat.
    - Replace the manual `tab` state with `NavHost` for robust navigation.
    - Implement new screens:
        - **Timetable**: Weekly/Monthly schedule.
        - **Chat**: Basic messaging interface.
        - **Settings**: Language selector and Appearance controls.
    - Update **Profile** to allow editing of user details.

---

### 6. Localization
- **[NEW] strings.xml (hi, ta, bn)**: Add string resources for Hindi, Tamil, and Bengali.
- **[MODIFY] strings.xml (en)**: Extract all hardcoded strings from UI code into resources.

---

### 7. Documentation
- **[MODIFY] [README.md](file:///C:/Users/HP/Desktop/Lernathon%205.0%20Project/EDU%20Talent%20Pro/MK-2/README.md)**: Document Auth flow, Privacy model, and Theme system.

## Verification Plan

### Automated Tests
- Run `gradle assembleDebug` to ensure all new dependencies and code compile.
- (Optional) Implement unit tests for `UserPreferencesRepository` if time permits.

### Manual Verification
- **Privacy Check**: Log in as a Faculty user and verify that private documents of students are not visible.
- **Theme Check**: Change color palette and toggle dark mode in Settings and verify it applies instantly app-wide.
- **Localization Check**: Change language to Hindi and verify UI text updates.
- **Auth Flow**: Perform a full Register -> Sign Out -> Login cycle.
