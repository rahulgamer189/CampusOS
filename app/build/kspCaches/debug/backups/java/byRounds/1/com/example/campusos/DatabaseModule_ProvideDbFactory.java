package com.example.campusos;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideDbFactory implements Factory<CampusOSDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideDbFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public CampusOSDatabase get() {
    return provideDb(contextProvider.get());
  }

  public static DatabaseModule_ProvideDbFactory create(Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideDbFactory(contextProvider);
  }

  public static CampusOSDatabase provideDb(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDb(context));
  }
}
