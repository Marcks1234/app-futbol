package com.example.appfulbo.ui.screens.admin.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository
import com.example.appfulbo.model.Place
import com.example.appfulbo.ui.states.GeneralState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repositoryStoreAuth: FireBaseStoreAuthRepository
) : ViewModel() {

    var place = MutableStateFlow<Place>(Place())
        private set

    private val _saveState = MutableStateFlow<GeneralState<Unit>?>(null)
    val saveState: StateFlow<GeneralState<Unit>?> = _saveState.asStateFlow()

    fun getPlace() {
        viewModelScope.launch {
            place.value = repositoryStoreAuth.getPlace()
        }
    }

    fun savePlace(updatedPlace: Place) {
        viewModelScope.launch {
            _saveState.value = GeneralState.Loading
            val result = repositoryStoreAuth.savePlace(updatedPlace)
            if (result is GeneralState.Success) {
                place.value = updatedPlace
            }
            _saveState.value = result
        }
    }

    fun resetSaveState() {
        _saveState.value = null
    }
}
