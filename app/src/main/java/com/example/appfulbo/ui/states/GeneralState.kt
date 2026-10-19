package com.example.appfulbo.ui.states

sealed class GeneralState<out T> {
    data class Success<out T>(val item: T): GeneralState<T>()
    data class Error(val error: String) : GeneralState<Nothing>()
    object Loading : GeneralState<Nothing>()
}