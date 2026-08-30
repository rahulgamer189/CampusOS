package com.example.campusos;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class CampusOSRepository_Factory implements Factory<CampusOSRepository> {
  private final Provider<DocumentDao> documentDaoProvider;

  private final Provider<TimetableDao> timetableDaoProvider;

  private final Provider<ChatDao> chatDaoProvider;

  private final Provider<AppointmentDao> appointmentDaoProvider;

  private final Provider<CVApplicationDao> cvApplicationDaoProvider;

  private final Provider<AcademicItemDao> academicItemDaoProvider;

  private final Provider<TeacherShortageDao> teacherShortageDaoProvider;

  private final Provider<AchievementDao> achievementDaoProvider;

  private final Provider<FirebaseAuth> authProvider;

  private final Provider<FirebaseFirestore> firestoreProvider;

  public CampusOSRepository_Factory(Provider<DocumentDao> documentDaoProvider,
      Provider<TimetableDao> timetableDaoProvider, Provider<ChatDao> chatDaoProvider,
      Provider<AppointmentDao> appointmentDaoProvider,
      Provider<CVApplicationDao> cvApplicationDaoProvider,
      Provider<AcademicItemDao> academicItemDaoProvider,
      Provider<TeacherShortageDao> teacherShortageDaoProvider,
      Provider<AchievementDao> achievementDaoProvider, Provider<FirebaseAuth> authProvider,
      Provider<FirebaseFirestore> firestoreProvider) {
    this.documentDaoProvider = documentDaoProvider;
    this.timetableDaoProvider = timetableDaoProvider;
    this.chatDaoProvider = chatDaoProvider;
    this.appointmentDaoProvider = appointmentDaoProvider;
    this.cvApplicationDaoProvider = cvApplicationDaoProvider;
    this.academicItemDaoProvider = academicItemDaoProvider;
    this.teacherShortageDaoProvider = teacherShortageDaoProvider;
    this.achievementDaoProvider = achievementDaoProvider;
    this.authProvider = authProvider;
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public CampusOSRepository get() {
    return newInstance(documentDaoProvider.get(), timetableDaoProvider.get(), chatDaoProvider.get(), appointmentDaoProvider.get(), cvApplicationDaoProvider.get(), academicItemDaoProvider.get(), teacherShortageDaoProvider.get(), achievementDaoProvider.get(), authProvider.get(), firestoreProvider.get());
  }

  public static CampusOSRepository_Factory create(Provider<DocumentDao> documentDaoProvider,
      Provider<TimetableDao> timetableDaoProvider, Provider<ChatDao> chatDaoProvider,
      Provider<AppointmentDao> appointmentDaoProvider,
      Provider<CVApplicationDao> cvApplicationDaoProvider,
      Provider<AcademicItemDao> academicItemDaoProvider,
      Provider<TeacherShortageDao> teacherShortageDaoProvider,
      Provider<AchievementDao> achievementDaoProvider, Provider<FirebaseAuth> authProvider,
      Provider<FirebaseFirestore> firestoreProvider) {
    return new CampusOSRepository_Factory(documentDaoProvider, timetableDaoProvider, chatDaoProvider, appointmentDaoProvider, cvApplicationDaoProvider, academicItemDaoProvider, teacherShortageDaoProvider, achievementDaoProvider, authProvider, firestoreProvider);
  }

  public static CampusOSRepository newInstance(DocumentDao documentDao, TimetableDao timetableDao,
      ChatDao chatDao, AppointmentDao appointmentDao, CVApplicationDao cvApplicationDao,
      AcademicItemDao academicItemDao, TeacherShortageDao teacherShortageDao,
      AchievementDao achievementDao, FirebaseAuth auth, FirebaseFirestore firestore) {
    return new CampusOSRepository(documentDao, timetableDao, chatDao, appointmentDao, cvApplicationDao, academicItemDao, teacherShortageDao, achievementDao, auth, firestore);
  }
}
