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
public final class DatabaseModule_ProvideTimetableDaoFactory implements Factory<TimetableDao> {
  private final Provider<CampusOSDatabase> dbProvider;

  public DatabaseModule_ProvideTimetableDaoFactory(Provider<CampusOSDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TimetableDao get() {
    return provideTimetableDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTimetableDaoFactory create(
      Provider<CampusOSDatabase> dbProvider) {
    return new DatabaseModule_ProvideTimetableDaoFactory(dbProvider);
  }

  public static TimetableDao provideTimetableDao(CampusOSDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTimetableDao(db));
  }
}
