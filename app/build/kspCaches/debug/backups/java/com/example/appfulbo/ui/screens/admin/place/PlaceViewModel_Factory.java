package com.example.appfulbo.ui.screens.admin.place;

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
public final class PlaceViewModel_Factory implements Factory<PlaceViewModel> {
  private final Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider;

  private PlaceViewModel_Factory(
      Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider) {
    this.repositoryStoreAuthProvider = repositoryStoreAuthProvider;
  }

  @Override
  public PlaceViewModel get() {
    return newInstance(repositoryStoreAuthProvider.get());
  }

  public static PlaceViewModel_Factory create(
      Provider<FireBaseStoreAuthRepository> repositoryStoreAuthProvider) {
    return new PlaceViewModel_Factory(repositoryStoreAuthProvider);
  }

  public static PlaceViewModel newInstance(FireBaseStoreAuthRepository repositoryStoreAuth) {
    return new PlaceViewModel(repositoryStoreAuth);
  }
}
