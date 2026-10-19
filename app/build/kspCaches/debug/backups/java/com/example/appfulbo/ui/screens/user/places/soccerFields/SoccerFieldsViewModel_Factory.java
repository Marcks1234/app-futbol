package com.example.appfulbo.ui.screens.user.places.soccerFields;

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
public final class SoccerFieldsViewModel_Factory implements Factory<SoccerFieldsViewModel> {
  private final Provider<FireBaseStoreRepository> storeProvider;

  private SoccerFieldsViewModel_Factory(Provider<FireBaseStoreRepository> storeProvider) {
    this.storeProvider = storeProvider;
  }

  @Override
  public SoccerFieldsViewModel get() {
    return newInstance(storeProvider.get());
  }

  public static SoccerFieldsViewModel_Factory create(
      Provider<FireBaseStoreRepository> storeProvider) {
    return new SoccerFieldsViewModel_Factory(storeProvider);
  }

  public static SoccerFieldsViewModel newInstance(FireBaseStoreRepository store) {
    return new SoccerFieldsViewModel(store);
  }
}
