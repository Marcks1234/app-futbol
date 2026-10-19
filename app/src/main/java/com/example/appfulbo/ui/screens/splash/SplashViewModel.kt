package com.example.appfulbo.ui.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository
import com.example.appfulbo.data.repository.fireBase.TypeUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repositoryStoreAuth: FireBaseStoreAuthRepository
) : ViewModel() {

    var result = MutableStateFlow<TypeUser?>(null)
        private set

    init {
        resultView()
    }

    fun resultView() {
        viewModelScope.launch {
            val userType = repositoryStoreAuth.getUserType()
            Log.i("SplashViewModel", "Tipo de usuario obtenido: $userType")
            result.value = userType
        }
    }
}
