package com.example.appfulbo.data.repository.fireBase.storage;

import com.google.firebase.storage.FirebaseStorage;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class FireBaseStorageRepository_Factory implements Factory<FireBaseStorageRepository> {
  private final Provider<FirebaseStorage> storageProvider;

  private FireBaseStorageRepository_Factory(Provider<FirebaseStorage> storageProvider) {
    this.storageProvider = storageProvider;
  }

  @Override
  public FireBaseStorageRepository get() {
    return newInstance(storageProvider.get());
  }

  public static FireBaseStorageRepository_Factory create(
      Provider<FirebaseStorage> storageProvider) {
    return new FireBaseStorageRepository_Factory(storageProvider);
  }

  public static FireBaseStorageRepository newInstance(FirebaseStorage storage) {
    return new FireBaseStorageRepository(storage);
  }
}
