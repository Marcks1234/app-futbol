package com.example.appfulbo.ui.screens.admin.soccerField

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository
import com.example.appfulbo.model.FieldSize
import com.example.appfulbo.model.GoalSize
import com.example.appfulbo.model.SoccerField
import com.example.appfulbo.ui.states.StateSoccerField
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SoccerFieldViewModel @Inject constructor(
    private val repositoryStoreAuth: FireBaseStoreAuthRepository
) : ViewModel() {

    var name = MutableStateFlow<String>("")
        private set
    var price = MutableStateFlow<String>("")
        private set
    var maxPlayers = MutableStateFlow<String>("")
        private set
    var type = MutableStateFlow<String>("")
        private set
    var sizeLength = MutableStateFlow<String>("")
        private set
    var sizeWidth = MutableStateFlow<String>("")
        private set
    var goalSizeHeight = MutableStateFlow<String>("")
        private set
    var goalSizeWidth = MutableStateFlow<String>("")
        private set

    var selectedSoccerField = MutableStateFlow<SoccerField?>(null)
        private set

    fun selectSoccerField(soccerField: SoccerField) {
        selectedSoccerField.value = soccerField
    }

    var errors = MutableStateFlow<Map<String, String?>>(
        mapOf(
            "name" to null,
            "price" to null,
            "maxPlayers" to null,
            "type" to null,
            "sizeLength" to null,
            "sizeWidth" to null,
            "goalSizeHeight" to null,
            "goalSizeWidth" to null
        )
    )
        private set

    var stateCreateSoccerField = MutableStateFlow<StateSoccerField>(StateSoccerField.Empty)
        private set
    var soccerFields = MutableStateFlow<List<SoccerField>>(emptyList())
        private set
    var stateSoccerField = MutableStateFlow<StateSoccerField>(StateSoccerField.Empty)
        private set

    init {
        getSoccerFields()
    }

    fun changeName(newName: String) {
        name.value = newName
    }

    fun changePrice(newPrice: String) {
        price.value = newPrice
    }

    fun changeMaxPlayers(newMaxPlayers: String) {
        maxPlayers.value = newMaxPlayers
    }

    fun changeType(newType: String) {
        type.value = newType
    }

    fun changeSizeLength(newSizeLength: String) {
        sizeLength.value = newSizeLength
    }

    fun changeSizeWidth(newSizeWidth: String) {
        sizeWidth.value = newSizeWidth
    }

    fun changeGoalSizeWidth(newGoalSizeWidth: String) {
        goalSizeWidth.value = newGoalSizeWidth
    }

    fun changeGoalSizeHeight(newGoalSizeHeight: String) {
        goalSizeHeight.value = newGoalSizeHeight
    }

    fun getFieldSize(): FieldSize {
        return FieldSize(sizeLength.value.toDoubleOrNull() ?: 0.0, sizeWidth.value.toDoubleOrNull() ?: 0.0)
    }

    fun getGoalSize(): GoalSize {
        return GoalSize(goalSizeHeight.value.toDoubleOrNull() ?: 0.0, goalSizeWidth.value.toDoubleOrNull() ?: 0.0)
    }

    fun createSoccerField(newSoccerField: SoccerField) {
        viewModelScope.launch {
            stateCreateSoccerField.value = StateSoccerField.Loading
            stateCreateSoccerField.value = repositoryStoreAuth.registerSoccerField(newSoccerField)
        }
    }

    fun getSoccerFields() {
        viewModelScope.launch {
            repositoryStoreAuth.getRealTimeSoccerFields().collect { result ->
                stateSoccerField.value = result
                if (result is StateSoccerField.Success) {
                    soccerFields.value = result.soccerFields
                }
            }
        }
    }

    fun createSoccerFieldIfValid() {
        val currentErrors = mutableMapOf<String, String?>()

        if (name.value.isBlank()) currentErrors["name"] = "Campo obligatorio"
        if (price.value.isBlank()) currentErrors["price"] = "Campo obligatorio"
        else if (price.value.toDoubleOrNull() == null) currentErrors["price"] = "Debe ser un número"

        if (maxPlayers.value.isBlank()) currentErrors["maxPlayers"] = "Campo obligatorio"
        else if (maxPlayers.value.toIntOrNull() == null) currentErrors["maxPlayers"] = "Debe ser un número entero"

        if (sizeLength.value.isNotEmpty() && sizeLength.value.toDoubleOrNull() == null) currentErrors["sizeLength"] = "Debe ser un número"
        if (sizeWidth.value.isNotEmpty() && sizeWidth.value.toDoubleOrNull() == null) currentErrors["sizeWidth"] = "Debe ser un número"
        if (goalSizeHeight.value.isNotEmpty() && goalSizeHeight.value.toDoubleOrNull() == null) currentErrors["goalSizeHeight"] = "Debe ser un número"
        if (goalSizeWidth.value.isNotEmpty() && goalSizeWidth.value.toDoubleOrNull() == null) currentErrors["goalSizeWidth"] = "Debe ser un número"

        val existingName = soccerFields.value.any {
            it.name.trim().equals(name.value.trim(), ignoreCase = true)
        }
        if (existingName) {
            currentErrors["name"] = "Ya existe una cancha con ese nombre"
        }

        errors.value = currentErrors

        if (currentErrors.isEmpty()) {
            val newSoccerField = SoccerField(
                name = name.value,
                priceHour = price.value.toDouble(),
                maxPlayers = maxPlayers.value.toInt(),
                size = getFieldSize(),
                goalSize = getGoalSize(),
                type = type.value
            )
            createSoccerField(newSoccerField)
        }
    }

    fun updateSoccerField(updatedField: SoccerField) {
        viewModelScope.launch {
            repositoryStoreAuth.updateSoccerField(updatedField)
        }
    }

    fun resetForm() {
        name.value = ""
        price.value = ""
        maxPlayers.value = ""
        type.value = ""
        sizeLength.value = ""
        sizeWidth.value = ""
        goalSizeWidth.value = ""
        goalSizeHeight.value = ""
        stateCreateSoccerField.value = StateSoccerField.Empty
    }
}
