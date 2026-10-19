package com.example.appfulbo.ui.states

sealed class UserState {
    object Empty : UserState()
    object Loading : UserState()
    object Success : UserState()
    data class Error(var massage: String) : UserState()
}