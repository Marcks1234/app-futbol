package com.example.appfulbo.ui.screens.user.reserveSoccer;

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
public final class ReserveSoccerViewModel_Factory implements Factory<ReserveSoccerViewModel> {
  private final Provider<FireBaseStoreRepository> repositoryStoreProvider;

  private final Provider<FireBaseAuthRepository> repositoryAuthProvider;

  private ReserveSoccerViewModel_Factory(Provider<FireBaseStoreRepository> repositoryStoreProvider,
      Provider<FireBaseAuthRepository> repositoryAuthProvider) {
    this.repositoryStoreProvider = repositoryStoreProvider;
    this.repositoryAuthProvider = repositoryAuthProvider;
  }

  @Override
  public ReserveSoccerViewModel get() {
    return newInstance(repositoryStoreProvider.get(), repositoryAuthProvider.get());
  }

  public static ReserveSoccerViewModel_Factory create(
      Provider<FireBaseStoreRepository> repositoryStoreProvider,
      Provider<FireBaseAuthRepository> repositoryAuthProvider) {
    return new ReserveSoccerViewModel_Factory(repositoryStoreProvider, repositoryAuthProvider);
  }

  public static ReserveSoccerViewModel newInstance(FireBaseStoreRepository repositoryStore,
      FireBaseAuthRepository repositoryAuth) {
    return new ReserveSoccerViewModel(repositoryStore, repositoryAuth);
  }
}
