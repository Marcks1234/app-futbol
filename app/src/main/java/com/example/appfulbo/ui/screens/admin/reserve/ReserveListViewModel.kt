package com.example.appfulbo.ui.screens.admin.reserve

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository
import com.example.appfulbo.data.repository.fireBase.store.FireBaseStoreRepository
import com.example.appfulbo.model.ReservationDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ReserveListViewModel @Inject constructor(
    private val repositoryFireStore: FireBaseStoreRepository,
    private val repositoryFireAuth: FireBaseAuthRepository
) : ViewModel() {
    var detailReserves = MutableStateFlow<List<ReservationDetails>>(emptyList())
        private set

    fun getReserveDetail() {
        viewModelScope.launch {
            val userId = repositoryFireAuth.currentUid()
            userId?.let {
                repositoryFireStore.getReservesWithUserId(userId).collect { reserves ->
                    detailReserves.value = reserves
                }
            }
        }
    }

    fun filterReserves(query: String = "", dateReserve: String = ""): List<ReservationDetails> {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return detailReserves.value
            .filter {
                val name = it.soccerField.name.lowercase()
                val date = it.reserva.date
                val queryEqual = query.lowercase()
                val equalName = name.contains(queryEqual)
                val equialDate = if (dateReserve.isNotEmpty()) date.contains(dateReserve) else true
                equalName && equialDate
            }
            .sortedByDescending { sdf.parse(it.reserva.date) }
    }
}
