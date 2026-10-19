package com.example.appfulbo.ui.screens.admin.profile;

import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository;
import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository;
import com.example.appfulbo.data.repository.fireBase.storage.FireBaseStorageRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<FireBaseStoreAuthRepository> repositoryAuthStoreProvider;

  private final Provider<FireBaseAuthRepository> authProvider;

  private final Provider<FireBaseStorageRepository> storageRepositoryProvider;

  private ProfileViewModel_Factory(
      Provider<FireBaseStoreAuthRepository> repositoryAuthStoreProvider,
      Provider<FireBaseAuthRepository> authProvider,
      Provider<FireBaseStorageRepository> storageRepositoryProvider) {
    this.repositoryAuthStoreProvider = repositoryAuthStoreProvider;
    this.authProvider = authProvider;
    this.storageRepositoryProvider = storageRepositoryProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(repositoryAuthStoreProvider.get(), authProvider.get(), storageRepositoryProvider.get());
  }

  public static ProfileViewModel_Factory create(
      Provider<FireBaseStoreAuthRepository> repositoryAuthStoreProvider,
      Provider<FireBaseAuthRepository> authProvider,
      Provider<FireBaseStorageRepository> storageRepositoryProvider) {
    return new ProfileViewModel_Factory(repositoryAuthStoreProvider, authProvider, storageRepositoryProvider);
  }

  public static ProfileViewModel newInstance(FireBaseStoreAuthRepository repositoryAuthStore,
      FireBaseAuthRepository auth, FireBaseStorageRepository storageRepository) {
    return new ProfileViewModel(repositoryAuthStore, auth, storageRepository);
  }
}
