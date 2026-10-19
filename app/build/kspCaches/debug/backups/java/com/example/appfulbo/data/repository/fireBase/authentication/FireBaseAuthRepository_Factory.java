package com.example.appfulbo.data.repository.fireBase.authentication;

import com.google.firebase.auth.FirebaseAuth;
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
public final class FireBaseAuthRepository_Factory implements Factory<FireBaseAuthRepository> {
  private final Provider<FirebaseAuth> authProvider;

  private FireBaseAuthRepository_Factory(Provider<FirebaseAuth> authProvider) {
    this.authProvider = authProvider;
  }

  @Override
  public FireBaseAuthRepository get() {
    return newInstance(authProvider.get());
  }

  public static FireBaseAuthRepository_Factory create(Provider<FirebaseAuth> authProvider) {
    return new FireBaseAuthRepository_Factory(authProvider);
  }

  public static FireBaseAuthRepository newInstance(FirebaseAuth auth) {
    return new FireBaseAuthRepository(auth);
  }
}
