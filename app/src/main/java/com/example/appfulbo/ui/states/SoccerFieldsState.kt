package com.example.appfulbo.ui.states

import com.example.appfulbo.model.SoccerField

sealed class StateSoccerField() {
    object Empty : StateSoccerField()
    object Loading : StateSoccerField()
    data class Success(val soccerFields: List<SoccerField> = emptyList()) : StateSoccerField()
    data class Error(val message: String) : StateSoccerField()
}
