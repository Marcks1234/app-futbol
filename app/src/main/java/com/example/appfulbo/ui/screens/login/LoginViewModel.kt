package com.example.appfulbo.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository
import com.example.appfulbo.data.repository.fireBase.TypeUser
import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository
import com.example.appfulbo.ui.states.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FireBaseAuthRepository,
    private val repositoryStoreAuth: FireBaseStoreAuthRepository
) : ViewModel() {

    var userState = MutableStateFlow<UserState>(UserState.Empty)
        private set

    var errores = MutableStateFlow<Map<String, String?>>(mapOf("email" to null, "password" to null))
        private set
    var email = MutableStateFlow<String>("")
        private set
    var password = MutableStateFlow<String>("")
        private set

    fun changeEmail(newEmail: String) {
        email.value = newEmail
        val error = when {
            newEmail.isBlank() -> "El email no puede estar vacio"
            !newEmail.contains("@") -> "El email tiene que contener @"
            !newEmail.endsWith(".com") -> "El email tiene que terminar con .com"
            else -> null
        }
        errores.value = errores.value.toMutableMap().apply { this["email"] = error }
    }

    fun changePassword(newPassword: String) {
        password.value = newPassword
        val error = when {
            newPassword.isBlank() -> "La contraseña no puede estar vacia"
            newPassword.length < 6 -> "La contraseña tiene que ser minimo 6 caracteres"
            else -> null
        }
        errores.value = errores.value.toMutableMap().apply {
            this["password"] = error
        }
    }

    fun verifiqueDataInput(): Boolean {
        val errorEmail = errores.value["email"] == null
        val errorPassword = errores.value["password"] == null
        return errorEmail && errorPassword
    }

    fun loginUser(email: String, password: String, loginResult: (TypeUser) -> Unit) {
        viewModelScope.launch {
            userState.value = UserState.Loading
            val result = auth.loginUserAuth(email, password)
            userState.value = result
            if (result == UserState.Success) {
                val isAdmin = repositoryStoreAuth.getUserType()
                loginResult(isAdmin)
            }
        }
    }
}
