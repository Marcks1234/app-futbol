package com.example.appfulbo.ui.screens.user.places.soccerFields

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.store.FireBaseStoreRepository
import com.example.appfulbo.model.SoccerField
import com.example.appfulbo.ui.states.StateSoccerField
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SoccerFieldsViewModel @Inject constructor(
    val store: FireBaseStoreRepository
) : ViewModel() {

    var soccerFields = MutableStateFlow<List<SoccerField>>(emptyList())
        private set
    var stateSoccerField = MutableStateFlow<StateSoccerField>(StateSoccerField.Empty)
        private set

    fun getSoccerFields(idPalce: String) {
        viewModelScope.launch {
            store.getSoccerFields(idPalce).collect { result ->
                stateSoccerField.value = result
                if (result is StateSoccerField.Success) {
                    soccerFields.value = result.soccerFields
                }
            }
        }
    }
}
