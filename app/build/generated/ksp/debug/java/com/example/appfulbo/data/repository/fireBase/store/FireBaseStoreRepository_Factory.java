package com.example.appfulbo.data.repository.fireBase.store;

import com.google.firebase.firestore.FirebaseFirestore;
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
public final class FireBaseStoreRepository_Factory implements Factory<FireBaseStoreRepository> {
  private final Provider<FirebaseFirestore> dbProvider;

  private FireBaseStoreRepository_Factory(Provider<FirebaseFirestore> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public FireBaseStoreRepository get() {
    return newInstance(dbProvider.get());
  }

  public static FireBaseStoreRepository_Factory create(Provider<FirebaseFirestore> dbProvider) {
    return new FireBaseStoreRepository_Factory(dbProvider);
  }

  public static FireBaseStoreRepository newInstance(FirebaseFirestore db) {
    return new FireBaseStoreRepository(db);
  }
}
