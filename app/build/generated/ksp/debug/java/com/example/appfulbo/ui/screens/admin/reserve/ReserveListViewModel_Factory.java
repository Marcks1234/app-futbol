package com.example.appfulbo.ui.screens.admin.reserve;

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
public final class ReserveListViewModel_Factory implements Factory<ReserveListViewModel> {
  private final Provider<FireBaseStoreRepository> repositoryFireStoreProvider;

  private final Provider<FireBaseAuthRepository> repositoryFireAuthProvider;

  private ReserveListViewModel_Factory(
      Provider<FireBaseStoreRepository> repositoryFireStoreProvider,
      Provider<FireBaseAuthRepository> repositoryFireAuthProvider) {
    this.repositoryFireStoreProvider = repositoryFireStoreProvider;
    this.repositoryFireAuthProvider = repositoryFireAuthProvider;
  }

  @Override
  public ReserveListViewModel get() {
    return newInstance(repositoryFireStoreProvider.get(), repositoryFireAuthProvider.get());
  }

  public static ReserveListViewModel_Factory create(
      Provider<FireBaseStoreRepository> repositoryFireStoreProvider,
      Provider<FireBaseAuthRepository> repositoryFireAuthProvider) {
    return new ReserveListViewModel_Factory(repositoryFireStoreProvider, repositoryFireAuthProvider);
  }

  public static ReserveListViewModel newInstance(FireBaseStoreRepository repositoryFireStore,
      FireBaseAuthRepository repositoryFireAuth) {
    return new ReserveListViewModel(repositoryFireStore, repositoryFireAuth);
  }
}
