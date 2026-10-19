package com.example.appfulbo.ui.screens.register;

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
public final class RegisterViewModel_Factory implements Factory<RegisterViewModel> {
  private final Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider;

  private RegisterViewModel_Factory(
      Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider) {
    this.repositoryStoreAuthProvider = repositoryStoreAuthProvider;
  }

  @Override
  public RegisterViewModel get() {
    return newInstance(repositoryStoreAuthProvider.get());
  }

  public static RegisterViewModel_Factory create(
      Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider) {
    return new RegisterViewModel_Factory(repositoryStoreAuthProvider);
  }

  public static RegisterViewModel newInstance(FireBaseStoreAuthRepository repositoryStoreAuth) {
    return new RegisterViewModel(repositoryStoreAuth);
  }
}
