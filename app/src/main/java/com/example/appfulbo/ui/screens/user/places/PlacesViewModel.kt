package com.example.appfulbo.ui.screens.user.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.store.FireBaseStoreRepository
import com.example.appfulbo.model.Place
import com.example.appfulbo.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val repositoryStore: FireBaseStoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Place>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Place>>> = _uiState.asStateFlow()

    fun getDataPlaces() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = repositoryStore.getPlaces()
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error al cargar los lugares")
            }
        }
    }
}
