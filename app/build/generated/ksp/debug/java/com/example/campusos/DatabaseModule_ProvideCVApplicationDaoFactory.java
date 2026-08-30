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
public final class DatabaseModule_ProvideCVApplicationDaoFactory implements Factory<CVApplicationDao> {
  private final Provider<CampusOSDatabase> dbProvider;

  public DatabaseModule_ProvideCVApplicationDaoFactory(Provider<CampusOSDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CVApplicationDao get() {
    return provideCVApplicationDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCVApplicationDaoFactory create(
      Provider<CampusOSDatabase> dbProvider) {
    return new DatabaseModule_ProvideCVApplicationDaoFactory(dbProvider);
  }

  public static CVApplicationDao provideCVApplicationDao(CampusOSDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCVApplicationDao(db));
  }
}
