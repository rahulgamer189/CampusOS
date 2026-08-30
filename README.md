# FacultyHub

FacultyHub is a native Android application written entirely in Kotlin with Jetpack Compose. It provides a student-first academic hub and a role-aware shell for student, faculty, and administrator workflows.

## Current implementation

| Area | Included behavior |
|---|---|
| Student dashboard | Attendance and CGPA metrics, academic hub entry point, recent notifications |
| Academic Hub | Search, content filters, homework, assignments, projects, activities, notes, and tests |
| Submissions | Text submission flow with local Room persistence and submitted state |
| Personal documents | Student-scoped local document list, add/delete controls, privacy messaging |
| Calendar | Upcoming academic items in a calendar-oriented list |
| Roles | Student, Faculty, and Admin role preview switcher for UI validation |
| Theme | Material 3 light/dark mode with FacultyHub brand colors |
| Offline data | Room database for submissions and personal documents; seeded academic content for immediate preview |
| Backend readiness | Firebase Auth, Firestore, Storage, and Cloud Messaging dependencies are configured |

## Architecture

The project uses a single-activity Compose UI with MVVM state management. `FacultyHubRepository` is the domain boundary, `FacultyHubViewModel` exposes lifecycle-aware state, and Room entities/DAOs provide offline persistence. The repository is intentionally replaceable: Firebase Auth/Firestore/Storage implementations can be introduced behind the same repository interface without changing the Compose screens.

The application package is `com.example.facultyhub`. The source tree is organized as follows:

```text
app/src/main/java/com/example/facultyhub/
├── MainActivity.kt       # Compose shell, screens, view model, navigation tabs
├── Models.kt             # Domain models and Room entities
├── DataLayer.kt          # Room database, DAOs, DI module, seeded repository
└── ui/theme/Theme.kt     # Material 3 light/dark brand theme
```

## Run in Android Studio

Open `/home/ubuntu/facultyhub` in the latest stable Android Studio. Allow Gradle synchronization, install an Android 7.0/API 24 or newer emulator, and run the `app` configuration. The project uses compile SDK 25, targets SDK 25, and supports Android 7.0/API 24 and newer through `minSdk = 24`. It uses Java/Kotlin toolchain 21 for the build environment. A generated Gradle wrapper is included.

To connect Firebase, create a Firebase Android application with package name `com.example.facultyhub`, download `google-services.json` into `app/`, and apply the Google Services plugin in `app/build.gradle.kts`. Then configure Firebase Authentication providers, Firestore, Storage, and Cloud Messaging. The current local repository allows the UI to run before a backend project is connected.

## Security model

Personal documents must be enforced by Firebase Storage and Firestore rules, not only by UI visibility. The intended identity claim is `request.auth.uid == studentId`; faculty and administrators must not receive document read access.

## Localization

The project reserves Android resource directories for English, Hindi, Tamil, and Bengali. For production completion, move all visible Compose copy into `stringResource` calls and add translated values to `values-hi`, `values-ta`, and `values-bn`. Persist the selected locale using DataStore and apply it through `AppCompatDelegate.setApplicationLocales` or a Compose locale provider. The current code is structured so this extraction can be completed without changing the domain layer.

## Production expansion points

The next backend iteration should add Firebase Auth registration and role claims, Firestore synchronization for enrolled subjects and academic content, Cloud Storage upload/download with signed access, FCM deadline/result notifications, Cloud Functions for monthly feedback and optimized timetables, QR attendance scanning, and admin audit-log screens. These are intentionally kept behind the repository boundary so the current UI remains testable offline.
