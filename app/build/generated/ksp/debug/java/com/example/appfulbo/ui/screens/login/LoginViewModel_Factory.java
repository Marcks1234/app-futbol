package com.example.appfulbo.ui.screens.login;

import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository;
import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository;
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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<FireBaseAuthRepository> authProvider;

  private final Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider;

  private LoginViewModel_Factory(Provider<FireBaseAuthRepository> authProvider,
      Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider) {
    this.authProvider = authProvider;
    this.repositoryStoreAuthProvider = repositoryStoreAuthProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(authProvider.get(), repositoryStoreAuthProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<FireBaseAuthRepository> authProvider,
      Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider) {
    return new LoginViewModel_Factory(authProvider, repositoryStoreAuthProvider);
  }

  public static LoginViewModel newInstance(FireBaseAuthRepository auth,
      FireBaseStoreAuthRepository repositoryStoreAuth) {
    return new LoginViewModel(auth, repositoryStoreAuth);
  }
}
