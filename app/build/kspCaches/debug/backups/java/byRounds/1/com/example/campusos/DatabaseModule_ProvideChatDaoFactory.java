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
public final class DatabaseModule_ProvideChatDaoFactory implements Factory<ChatDao> {
  private final Provider<CampusOSDatabase> dbProvider;

  public DatabaseModule_ProvideChatDaoFactory(Provider<CampusOSDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ChatDao get() {
    return provideChatDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideChatDaoFactory create(Provider<CampusOSDatabase> dbProvider) {
    return new DatabaseModule_ProvideChatDaoFactory(dbProvider);
  }

  public static ChatDao provideChatDao(CampusOSDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideChatDao(db));
  }
}
