package com.example.appfulbo.ui.screens.user.places;

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
public final class PlacesViewModel_Factory implements Factory<PlacesViewModel> {
  private final Provider<FireBaseStoreRepository> repositoryStoreProvider;

  private PlacesViewModel_Factory(Provider<FireBaseStoreRepository> repositoryStoreProvider) {
    this.repositoryStoreProvider = repositoryStoreProvider;
  }

  @Override
  public PlacesViewModel get() {
    return newInstance(repositoryStoreProvider.get());
  }

  public static PlacesViewModel_Factory create(
      Provider<FireBaseStoreRepository> repositoryStoreProvider) {
    return new PlacesViewModel_Factory(repositoryStoreProvider);
  }

  public static PlacesViewModel newInstance(FireBaseStoreRepository repositoryStore) {
    return new PlacesViewModel(repositoryStore);
  }
}
