package com.example.appfulbo.ui.screens.admin.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository
import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository
import com.example.appfulbo.data.repository.fireBase.storage.FireBaseStorageRepository
import com.example.appfulbo.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repositoryAuthStore: FireBaseStoreAuthRepository,
    private val auth: FireBaseAuthRepository,
    private val storageRepository: FireBaseStorageRepository
) : ViewModel() {

    var user = MutableStateFlow(User())
        private set

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            user.value = repositoryAuthStore.getUser()
        }
    }

    fun closeSesion() {
        auth.logout()
    }

    fun updateProfileImage(uri: Uri) {
        viewModelScope.launch {
            try {
                val url = storageRepository.uploadProfileImage(uri, user.value.id)
                repositoryAuthStore.updateUserProfileImage(url)
                getUser()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
