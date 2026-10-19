package com.example.appfulbo.ui.screens.admin.soccerField;

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
public final class SoccerFieldViewModel_Factory implements Factory<SoccerFieldViewModel> {
  private final Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider;

  private SoccerFieldViewModel_Factory(
      Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider) {
    this.repositoryStoreAuthProvider = repositoryStoreAuthProvider;
  }

  @Override
  public SoccerFieldViewModel get() {
    return newInstance(repositoryStoreAuthProvider.get());
  }

  public static SoccerFieldViewModel_Factory create(
      Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider) {
    return new SoccerFieldViewModel_Factory(repositoryStoreAuthProvider);
  }

  public static SoccerFieldViewModel newInstance(FireBaseStoreAuthRepository repositoryStoreAuth) {
    return new SoccerFieldViewModel(repositoryStoreAuth);
  }
}
