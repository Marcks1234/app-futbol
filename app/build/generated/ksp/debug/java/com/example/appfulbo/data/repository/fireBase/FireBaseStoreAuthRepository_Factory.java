package com.example.appfulbo.data.repository.fireBase;

import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository;
import com.example.appfulbo.data.repository.fireBase.store.FireBaseStoreRepository;
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
public final class FireBaseStoreAuthRepository_Factory implements Factory<FireBaseStoreAuthRepository> {
  private final Provider<FireBaseAuthRepository> authProvider;

  private final Provider<FireBaseStoreRepository> dbProvider;

  private FireBaseStoreAuthRepository_Factory(Provider<FireBaseAuthRepository> authProvider,
      Provider<FireBaseStoreRepository> dbProvider) {
    this.authProvider = authProvider;
    this.dbProvider = dbProvider;
  }

  @Override
  public FireBaseStoreAuthRepository get() {
    return newInstance(authProvider.get(), dbProvider.get());
  }

  public static FireBaseStoreAuthRepository_Factory create(
      Provider<FireBaseAuthRepository> authProvider, Provider<FireBaseStoreRepository> dbProvider) {
    return new FireBaseStoreAuthRepository_Factory(authProvider, dbProvider);
  }

  public static FireBaseStoreAuthRepository newInstance(FireBaseAuthRepository auth,
      FireBaseStoreRepository db) {
    return new FireBaseStoreAuthRepository(auth, db);
  }
}
