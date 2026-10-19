package com.example.appfulbo.ui.screens.admin.createReserve;

import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository;
import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository;
import com.example.appfulbo.data.repository.fireBase.store.FireBaseStoreRepository;
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
public final class CreateReserveViewModel_Factory implements Factory<CreateReserveViewModel> {
  private final Provider<FireBaseStoreRepository> repositoryStoreProvider;

  private final Provider<FireBaseStoreAuthRepository> repositoryAuthStoreProvider;

  private final Provider<FireBaseAuthRepository> repositoryAuthProvider;

  private CreateReserveViewModel_Factory(Provider<FireBaseStoreRepository> repositoryStoreProvider,
      Provider<FireBaseStoreAuthRepository> repositoryAuthStoreProvider,
      Provider<FireBaseAuthRepository> repositoryAuthProvider) {
    this.repositoryStoreProvider = repositoryStoreProvider;
    this.repositoryAuthStoreProvider = repositoryAuthStoreProvider;
    this.repositoryAuthProvider = repositoryAuthProvider;
  }

  @Override
  public CreateReserveViewModel get() {
    return newInstance(repositoryStoreProvider.get(), repositoryAuthStoreProvider.get(), repositoryAuthProvider.get());
  }

  public static CreateReserveViewModel_Factory create(
      Provider<FireBaseStoreRepository> repositoryStoreProvider,
      Provider<FireBaseStoreAuthRepository> repositoryAuthStoreProvider,
      Provider<FireBaseAuthRepository> repositoryAuthProvider) {
    return new CreateReserveViewModel_Factory(repositoryStoreProvider, repositoryAuthStoreProvider, repositoryAuthProvider);
  }

  public static CreateReserveViewModel newInstance(FireBaseStoreRepository repositoryStore,
      FireBaseStoreAuthRepository repositoryAuthStore, FireBaseAuthRepository repositoryAuth) {
    return new CreateReserveViewModel(repositoryStore, repositoryAuthStore, repositoryAuth);
  }
}
