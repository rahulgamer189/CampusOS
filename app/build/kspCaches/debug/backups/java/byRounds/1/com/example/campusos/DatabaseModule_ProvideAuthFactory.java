package com.example.campusos;

import com.google.firebase.auth.FirebaseAuth;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class DatabaseModule_ProvideAuthFactory implements Factory<FirebaseAuth> {
  @Override
  public FirebaseAuth get() {
    return provideAuth();
  }

  public static DatabaseModule_ProvideAuthFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseAuth provideAuth() {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAuth());
  }

  private static final class InstanceHolder {
    private static final DatabaseModule_ProvideAuthFactory INSTANCE = new DatabaseModule_ProvideAuthFactory();
  }
}
