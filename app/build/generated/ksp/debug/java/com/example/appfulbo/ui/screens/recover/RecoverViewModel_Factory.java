package com.example.appfulbo.ui.screens.recover;

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
public final class RecoverViewModel_Factory implements Factory<RecoverViewModel> {
  private final Provider<FireBaseStoreAuthRepository> repositoryAuthStoreProvider;

  private RecoverViewModel_Factory(
      Provider<FireBaseStoreAuthRepository> repositoryAuthStoreProvider) {
    this.repositoryAuthStoreProvider = repositoryAuthStoreProvider;
  }

  @Override
  public RecoverViewModel get() {
    return newInstance(repositoryAuthStoreProvider.get());
  }

  public static RecoverViewModel_Factory create(
      Provider<FireBaseStoreAuthRepository> repositoryAuthStoreProvider) {
    return new RecoverViewModel_Factory(repositoryAuthStoreProvider);
  }

  public static RecoverViewModel newInstance(FireBaseStoreAuthRepository repositoryAuthStore) {
    return new RecoverViewModel(repositoryAuthStore);
  }
}
