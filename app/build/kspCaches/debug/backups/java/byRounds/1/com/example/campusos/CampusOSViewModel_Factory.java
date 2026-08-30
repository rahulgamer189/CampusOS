package com.example.campusos;

import com.google.firebase.auth.FirebaseAuth;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class CampusOSViewModel_Factory implements Factory<CampusOSViewModel> {
  private final Provider<CampusOSRepository> repositoryProvider;

  private final Provider<UserPreferencesRepository> prefRepositoryProvider;

  private final Provider<FirebaseAuth> authProvider;

  public CampusOSViewModel_Factory(Provider<CampusOSRepository> repositoryProvider,
      Provider<UserPreferencesRepository> prefRepositoryProvider,
      Provider<FirebaseAuth> authProvider) {
    this.repositoryProvider = repositoryProvider;
    this.prefRepositoryProvider = prefRepositoryProvider;
    this.authProvider = authProvider;
  }

  @Override
  public CampusOSViewModel get() {
    return newInstance(repositoryProvider.get(), prefRepositoryProvider.get(), authProvider.get());
  }

  public static CampusOSViewModel_Factory create(Provider<CampusOSRepository> repositoryProvider,
      Provider<UserPreferencesRepository> prefRepositoryProvider,
      Provider<FirebaseAuth> authProvider) {
    return new CampusOSViewModel_Factory(repositoryProvider, prefRepositoryProvider, authProvider);
  }

  public static CampusOSViewModel newInstance(CampusOSRepository repository,
      UserPreferencesRepository prefRepository, FirebaseAuth auth) {
    return new CampusOSViewModel(repository, prefRepository, auth);
  }
}
