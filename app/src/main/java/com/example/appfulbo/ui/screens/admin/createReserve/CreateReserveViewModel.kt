package com.example.appfulbo.ui.screens.admin.createReserve

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository
import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository
import com.example.appfulbo.data.repository.fireBase.store.FireBaseStoreRepository
import com.example.appfulbo.model.DaysOfWeek
import com.example.appfulbo.model.Place
import com.example.appfulbo.model.Reserve
import com.example.appfulbo.model.SoccerField
import com.example.appfulbo.model.User
import com.example.appfulbo.ui.states.GeneralState
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CreateReserveViewModel @Inject constructor(
    private val repositoryStore: FireBaseStoreRepository,
    private val repositoryAuthStore: FireBaseStoreAuthRepository,
    private val repositoryAuth: FireBaseAuthRepository
) : ViewModel() {
    var place = MutableStateFlow<Place?>(null)
        private set

    private var reservaListener: ListenerRegistration? = null
    private var allSoccerFields = listOf<SoccerField>()
    private var allUsers = listOf<User>()

    var userResults = MutableStateFlow<List<User>>(emptyList())
        private set
    var soccerFieldsResult = MutableStateFlow<List<SoccerField>>(emptyList())
        private set
    var listHours = MutableStateFlow<List<String>>(emptyList())
        private set
    var listReserve = MutableStateFlow<List<Reserve>>(emptyList())
        private set
    var reserveSuccess = MutableStateFlow(false)
        private set
    var errorMessage = MutableStateFlow<String?>(null)
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadAllUsersOnce() {
        viewModelScope.launch {
            try {
                place.value = repositoryAuthStore.getPlace()
                allSoccerFields = repositoryAuthStore.getAllSoccerField()
                allUsers = repositoryStore.getUsers()
                userResults.value = allUsers
                soccerFieldsResult.value = allSoccerFields
                place?.let {
                    listHours.value = DaysOfWeek().getMatrizHours(place.value)
                }
            } catch (e: Exception) {
                allUsers = emptyList()
                userResults.value = emptyList()
                allSoccerFields = emptyList()
                soccerFieldsResult.value = emptyList()
            }
        }
    }

    fun addReserve(reserve: Reserve) {
        viewModelScope.launch {
            val result = repositoryStore.addReserve(reserve)
            when (result) {
                is GeneralState.Success -> reserveSuccess.value = true
                is GeneralState.Error -> errorMessage.value = result.error
                else -> {}
            }
        }
    }

    fun getListReserve(soccerFieldId: String, date: String) {
        reservaListener?.remove()
        reservaListener =
            repositoryStore.getReserveWithSoccerIdAndDate(soccerFieldId, date) { reservas ->
                listReserve.value = reservas
            }
    }

    override fun onCleared() {
        super.onCleared()
        reservaListener?.remove()
    }

    fun filterUsers(query: String) {
        userResults.value = if (query.isBlank()) {
            allUsers
        } else {
            allUsers.filter {
                it.userName.contains(query, ignoreCase = true)
            }
        }
    }

    fun filterSoccerFields(query: String) {
        soccerFieldsResult.value = if (query.isBlank()) {
            allSoccerFields
        } else {
            allSoccerFields.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }

    fun getCurrentId(): String {
        return repositoryAuth.currentUid() ?: ""
    }
}
