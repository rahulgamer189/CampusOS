package com.example.campusos;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideAppointmentDaoFactory implements Factory<AppointmentDao> {
  private final Provider<CampusOSDatabase> dbProvider;

  public DatabaseModule_ProvideAppointmentDaoFactory(Provider<CampusOSDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AppointmentDao get() {
    return provideAppointmentDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAppointmentDaoFactory create(
      Provider<CampusOSDatabase> dbProvider) {
    return new DatabaseModule_ProvideAppointmentDaoFactory(dbProvider);
  }

  public static AppointmentDao provideAppointmentDao(CampusOSDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAppointmentDao(db));
  }
}
