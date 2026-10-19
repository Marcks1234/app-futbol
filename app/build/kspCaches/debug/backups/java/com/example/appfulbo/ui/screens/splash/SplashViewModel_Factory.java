package com.example.appfulbo.ui.screens.splash;

import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository;
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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider;

  private SplashViewModel_Factory(
      Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider) {
    this.repositoryStoreAuthProvider = repositoryStoreAuthProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(repositoryStoreAuthProvider.get());
  }

  public static SplashViewModel_Factory create(
      Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider) {
    return new SplashViewModel_Factory(repositoryStoreAuthProvider);
  }

  public static SplashViewModel newInstance(FireBaseStoreAuthRepository repositoryStoreAuth) {
    return new SplashViewModel(repositoryStoreAuth);
  }
}
