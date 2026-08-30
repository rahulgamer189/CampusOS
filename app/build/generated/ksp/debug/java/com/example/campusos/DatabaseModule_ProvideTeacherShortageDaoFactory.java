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
public final class DatabaseModule_ProvideTeacherShortageDaoFactory implements Factory<TeacherShortageDao> {
  private final Provider<CampusOSDatabase> dbProvider;

  public DatabaseModule_ProvideTeacherShortageDaoFactory(Provider<CampusOSDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TeacherShortageDao get() {
    return provideTeacherShortageDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTeacherShortageDaoFactory create(
      Provider<CampusOSDatabase> dbProvider) {
    return new DatabaseModule_ProvideTeacherShortageDaoFactory(dbProvider);
  }

  public static TeacherShortageDao provideTeacherShortageDao(CampusOSDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTeacherShortageDao(db));
  }
}
