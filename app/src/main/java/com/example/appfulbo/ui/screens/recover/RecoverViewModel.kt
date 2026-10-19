package com.example.appfulbo.ui.screens.recover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository
import com.example.appfulbo.ui.states.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RecoverViewModel @Inject constructor(
    private val repositoryAuthStore: FireBaseStoreAuthRepository
) : ViewModel() {

    var userState = MutableStateFlow<UserState>(UserState.Empty)
        private set

    fun sendEmail(email: String) {
        viewModelScope.launch {
            userState.value = UserState.Loading
            userState.value = repositoryAuthStore.sendEmail(email)
        }
    }

    fun resetState() {
        userState.value = UserState.Empty
    }
}
