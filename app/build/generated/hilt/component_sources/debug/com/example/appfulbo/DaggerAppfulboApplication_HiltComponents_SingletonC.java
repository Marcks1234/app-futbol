package com.example.appfulbo;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository;
import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository;
import com.example.appfulbo.data.repository.fireBase.storage.FireBaseStorageRepository;
import com.example.appfulbo.data.repository.fireBase.store.FireBaseStoreRepository;
import com.example.appfulbo.di.FirebaseModule_ProvideFirebaseAuthFactory;
import com.example.appfulbo.di.FirebaseModule_ProvideFirebaseFirestoreFactory;
import com.example.appfulbo.di.FirebaseModule_ProvideFirebaseStorageFactory;
import com.example.appfulbo.ui.screens.admin.createReserve.CreateReserveViewModel;
import com.example.appfulbo.ui.screens.admin.createReserve.CreateReserveViewModel_HiltModules;
import com.example.appfulbo.ui.screens.admin.createReserve.CreateReserveViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.admin.createReserve.CreateReserveViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.admin.place.PlaceViewModel;
import com.example.appfulbo.ui.screens.admin.place.PlaceViewModel_HiltModules;
import com.example.appfulbo.ui.screens.admin.place.PlaceViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.admin.place.PlaceViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.admin.profile.ProfileViewModel;
import com.example.appfulbo.ui.screens.admin.profile.ProfileViewModel_HiltModules;
import com.example.appfulbo.ui.screens.admin.profile.ProfileViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.admin.profile.ProfileViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.admin.reserve.ReserveListViewModel;
import com.example.appfulbo.ui.screens.admin.reserve.ReserveListViewModel_HiltModules;
import com.example.appfulbo.ui.screens.admin.reserve.ReserveListViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.admin.reserve.ReserveListViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.admin.soccerField.SoccerFieldViewModel;
import com.example.appfulbo.ui.screens.admin.soccerField.SoccerFieldViewModel_HiltModules;
import com.example.appfulbo.ui.screens.admin.soccerField.SoccerFieldViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.admin.soccerField.SoccerFieldViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.login.LoginViewModel;
import com.example.appfulbo.ui.screens.login.LoginViewModel_HiltModules;
import com.example.appfulbo.ui.screens.login.LoginViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.login.LoginViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.recover.RecoverViewModel;
import com.example.appfulbo.ui.screens.recover.RecoverViewModel_HiltModules;
import com.example.appfulbo.ui.screens.recover.RecoverViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.recover.RecoverViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.register.RegisterViewModel;
import com.example.appfulbo.ui.screens.register.RegisterViewModel_HiltModules;
import com.example.appfulbo.ui.screens.register.RegisterViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.register.RegisterViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.splash.SplashViewModel;
import com.example.appfulbo.ui.screens.splash.SplashViewModel_HiltModules;
import com.example.appfulbo.ui.screens.splash.SplashViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.splash.SplashViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.user.places.PlacesViewModel;
import com.example.appfulbo.ui.screens.user.places.PlacesViewModel_HiltModules;
import com.example.appfulbo.ui.screens.user.places.PlacesViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.user.places.PlacesViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.user.places.soccerFields.SoccerFieldsViewModel;
import com.example.appfulbo.ui.screens.user.places.soccerFields.SoccerFieldsViewModel_HiltModules;
import com.example.appfulbo.ui.screens.user.places.soccerFields.SoccerFieldsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.user.places.soccerFields.SoccerFieldsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.example.appfulbo.ui.screens.user.reserveSoccer.ReserveSoccerViewModel;
import com.example.appfulbo.ui.screens.user.reserveSoccer.ReserveSoccerViewModel_HiltModules;
import com.example.appfulbo.ui.screens.user.reserveSoccer.ReserveSoccerViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.example.appfulbo.ui.screens.user.reserveSoccer.ReserveSoccerViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerAppfulboApplication_HiltComponents_SingletonC {
  private DaggerAppfulboApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static AppfulboApplication_HiltComponents.SingletonC create() {
    return new Builder().build();
  }

  public static final class Builder {
    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public AppfulboApplication_HiltComponents.SingletonC build() {
      return new SingletonCImpl();
    }
  }

  private static final class ActivityRetainedCBuilder implements AppfulboApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public AppfulboApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements AppfulboApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public AppfulboApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements AppfulboApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public AppfulboApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements AppfulboApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public AppfulboApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements AppfulboApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public AppfulboApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements AppfulboApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public AppfulboApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements AppfulboApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public AppfulboApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends AppfulboApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends AppfulboApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    FragmentCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends AppfulboApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends AppfulboApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    ActivityCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    ImmutableMap keySetMapOfClassOfObjectAndBooleanBuilder() {
      ImmutableMap.Builder mapBuilder = ImmutableMap.<String, Boolean>builderWithExpectedSize(12);
      mapBuilder.put(CreateReserveViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, CreateReserveViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(LoginViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, LoginViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(PlaceViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, PlaceViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(PlacesViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, PlacesViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(ProfileViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ProfileViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(RecoverViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, RecoverViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(RegisterViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, RegisterViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(ReserveListViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ReserveListViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(ReserveSoccerViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ReserveSoccerViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(SoccerFieldViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SoccerFieldViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(SoccerFieldsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SoccerFieldsViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(SplashViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SplashViewModel_HiltModules.KeyModule.provide());
      return mapBuilder.build();
    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(keySetMapOfClassOfObjectAndBooleanBuilder());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends AppfulboApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    Provider<CreateReserveViewModel> createReserveViewModelProvider;

    Provider<LoginViewModel> loginViewModelProvider;

    Provider<PlaceViewModel> placeViewModelProvider;

    Provider<PlacesViewModel> placesViewModelProvider;

    Provider<ProfileViewModel> profileViewModelProvider;

    Provider<RecoverViewModel> recoverViewModelProvider;

    Provider<RegisterViewModel> registerViewModelProvider;

    Provider<ReserveListViewModel> reserveListViewModelProvider;

    Provider<ReserveSoccerViewModel> reserveSoccerViewModelProvider;

    Provider<SoccerFieldViewModel> soccerFieldViewModelProvider;

    Provider<SoccerFieldsViewModel> soccerFieldsViewModelProvider;

    Provider<SplashViewModel> splashViewModelProvider;

    ViewModelCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        SavedStateHandle savedStateHandleParam, ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    ImmutableMap hiltViewModelMapMapOfClassOfObjectAndProviderOfViewModelBuilder() {
      ImmutableMap.Builder mapBuilder = ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(12);
      mapBuilder.put(CreateReserveViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (createReserveViewModelProvider)));
      mapBuilder.put(LoginViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (loginViewModelProvider)));
      mapBuilder.put(PlaceViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (placeViewModelProvider)));
      mapBuilder.put(PlacesViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (placesViewModelProvider)));
      mapBuilder.put(ProfileViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (profileViewModelProvider)));
      mapBuilder.put(RecoverViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (recoverViewModelProvider)));
      mapBuilder.put(RegisterViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (registerViewModelProvider)));
      mapBuilder.put(ReserveListViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (reserveListViewModelProvider)));
      mapBuilder.put(ReserveSoccerViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (reserveSoccerViewModelProvider)));
      mapBuilder.put(SoccerFieldViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (soccerFieldViewModelProvider)));
      mapBuilder.put(SoccerFieldsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (soccerFieldsViewModelProvider)));
      mapBuilder.put(SplashViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (splashViewModelProvider)));
      return mapBuilder.build();
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.createReserveViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.loginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.placeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.placesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.recoverViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.registerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.reserveListViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.reserveSoccerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.soccerFieldViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.soccerFieldsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(hiltViewModelMapMapOfClassOfObjectAndProviderOfViewModelBuilder());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // com.example.appfulbo.ui.screens.admin.createReserve.CreateReserveViewModel
          return (T) new CreateReserveViewModel(singletonCImpl.fireBaseStoreRepositoryProvider.get(), singletonCImpl.fireBaseStoreAuthRepositoryProvider.get(), singletonCImpl.fireBaseAuthRepositoryProvider.get());

          case 1: // com.example.appfulbo.ui.screens.login.LoginViewModel
          return (T) new LoginViewModel(singletonCImpl.fireBaseAuthRepositoryProvider.get(), singletonCImpl.fireBaseStoreAuthRepositoryProvider.get());

          case 2: // com.example.appfulbo.ui.screens.admin.place.PlaceViewModel
          return (T) new PlaceViewModel(singletonCImpl.fireBaseStoreAuthRepositoryProvider.get());

          case 3: // com.example.appfulbo.ui.screens.user.places.PlacesViewModel
          return (T) new PlacesViewModel(singletonCImpl.fireBaseStoreRepositoryProvider.get());

          case 4: // com.example.appfulbo.ui.screens.admin.profile.ProfileViewModel
          return (T) new ProfileViewModel(singletonCImpl.fireBaseStoreAuthRepositoryProvider.get(), singletonCImpl.fireBaseAuthRepositoryProvider.get(), singletonCImpl.fireBaseStorageRepositoryProvider.get());

          case 5: // com.example.appfulbo.ui.screens.recover.RecoverViewModel
          return (T) new RecoverViewModel(singletonCImpl.fireBaseStoreAuthRepositoryProvider.get());

          case 6: // com.example.appfulbo.ui.screens.register.RegisterViewModel
          return (T) new RegisterViewModel(singletonCImpl.fireBaseStoreAuthRepositoryProvider.get());

          case 7: // com.example.appfulbo.ui.screens.admin.reserve.ReserveListViewModel
          return (T) new ReserveListViewModel(singletonCImpl.fireBaseStoreRepositoryProvider.get(), singletonCImpl.fireBaseAuthRepositoryProvider.get());

          case 8: // com.example.appfulbo.ui.screens.user.reserveSoccer.ReserveSoccerViewModel
          return (T) new ReserveSoccerViewModel(singletonCImpl.fireBaseStoreRepositoryProvider.get(), singletonCImpl.fireBaseAuthRepositoryProvider.get());

          case 9: // com.example.appfulbo.ui.screens.admin.soccerField.SoccerFieldViewModel
          return (T) new SoccerFieldViewModel(singletonCImpl.fireBaseStoreAuthRepositoryProvider.get());

          case 10: // com.example.appfulbo.ui.screens.user.places.soccerFields.SoccerFieldsViewModel
          return (T) new SoccerFieldsViewModel(singletonCImpl.fireBaseStoreRepositoryProvider.get());

          case 11: // com.example.appfulbo.ui.screens.splash.SplashViewModel
          return (T) new SplashViewModel(singletonCImpl.fireBaseStoreAuthRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends AppfulboApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends AppfulboApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends AppfulboApplication_HiltComponents.SingletonC {
    private final SingletonCImpl singletonCImpl = this;

    Provider<FirebaseFirestore> provideFirebaseFirestoreProvider;

    Provider<FireBaseStoreRepository> fireBaseStoreRepositoryProvider;

    Provider<FirebaseAuth> provideFirebaseAuthProvider;

    Provider<FireBaseAuthRepository> fireBaseAuthRepositoryProvider;

    Provider<FireBaseStoreAuthRepository> fireBaseStoreAuthRepositoryProvider;

    Provider<FirebaseStorage> provideFirebaseStorageProvider;

    Provider<FireBaseStorageRepository> fireBaseStorageRepositoryProvider;

    SingletonCImpl() {

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideFirebaseFirestoreProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseFirestore>(singletonCImpl, 1));
      this.fireBaseStoreRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<FireBaseStoreRepository>(singletonCImpl, 0));
      this.provideFirebaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuth>(singletonCImpl, 4));
      this.fireBaseAuthRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<FireBaseAuthRepository>(singletonCImpl, 3));
      this.fireBaseStoreAuthRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<FireBaseStoreAuthRepository>(singletonCImpl, 2));
      this.provideFirebaseStorageProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseStorage>(singletonCImpl, 6));
      this.fireBaseStorageRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<FireBaseStorageRepository>(singletonCImpl, 5));
    }

    @Override
    public void injectAppfulboApplication(AppfulboApplication appfulboApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // com.example.appfulbo.data.repository.fireBase.store.FireBaseStoreRepository
          return (T) new FireBaseStoreRepository(singletonCImpl.provideFirebaseFirestoreProvider.get());

          case 1: // com.google.firebase.firestore.FirebaseFirestore
          return (T) FirebaseModule_ProvideFirebaseFirestoreFactory.provideFirebaseFirestore();

          case 2: // com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository
          return (T) new FireBaseStoreAuthRepository(singletonCImpl.fireBaseAuthRepositoryProvider.get(), singletonCImpl.fireBaseStoreRepositoryProvider.get());

          case 3: // com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository
          return (T) new FireBaseAuthRepository(singletonCImpl.provideFirebaseAuthProvider.get());

          case 4: // com.google.firebase.auth.FirebaseAuth
          return (T) FirebaseModule_ProvideFirebaseAuthFactory.provideFirebaseAuth();

          case 5: // com.example.appfulbo.data.repository.fireBase.storage.FireBaseStorageRepository
          return (T) new FireBaseStorageRepository(singletonCImpl.provideFirebaseStorageProvider.get());

          case 6: // com.google.firebase.storage.FirebaseStorage
          return (T) FirebaseModule_ProvideFirebaseStorageFactory.provideFirebaseStorage();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
