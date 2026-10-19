package com.example.appfulbo.ui.screens.user.reserveSoccer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository
import com.example.appfulbo.data.repository.fireBase.store.FireBaseStoreRepository
import com.example.appfulbo.model.DaysOfWeek
import com.example.appfulbo.model.Place
import com.example.appfulbo.model.Reserve
import com.example.appfulbo.model.SoccerField
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ReserveSoccerViewModel @Inject constructor(
    private val repositoryStore: FireBaseStoreRepository,
    private val repositoryAuth: FireBaseAuthRepository
) : ViewModel() {

    private var reservaListener: ListenerRegistration? = null

    var UserId: String = repositoryAuth.currentUid() ?: ""

    var soccerField = MutableStateFlow<SoccerField?>(null)
        private set

    var place = MutableStateFlow<Place?>(null)
        private set

    var listHours = MutableStateFlow<List<String>>(emptyList())
        private set

    var listReserve = MutableStateFlow<List<Reserve>>(emptyList())
        private set

    fun getPlace(placeId: String, soccerFieldId: String) {
        viewModelScope.launch {
            place.value = repositoryStore.getPlaceWithId(placeId)
            soccerField.value = repositoryStore.getSoccerFieldWithId(placeId, soccerFieldId)
            place.value?.let {
                listHours.value = DaysOfWeek().getMatrizHours(place.value)
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
}
