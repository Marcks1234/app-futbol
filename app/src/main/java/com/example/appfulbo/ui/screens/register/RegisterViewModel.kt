package com.example.appfulbo.ui.screens.register

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfulbo.data.repository.fireBase.FireBaseStoreAuthRepository
import com.example.appfulbo.model.User
import com.example.appfulbo.ui.states.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repositoryStoreAuth: FireBaseStoreAuthRepository
) : ViewModel() {

    var userState = MutableStateFlow<UserState>(UserState.Empty)
        private set
    var userName = MutableStateFlow("")
        private set
    var firstNames = MutableStateFlow("")
        private set
    var lastNames = MutableStateFlow("")
        private set
    var email = MutableStateFlow("")
        private set
    var phoneNumber = MutableStateFlow("")
        private set
    var password = MutableStateFlow("")
        private set
    var rPassword = MutableStateFlow("")
        private set

    var errores = MutableStateFlow<Map<String, String?>>(
        mapOf(
            "userName" to null,
            "firstNames" to null,
            "lastNames" to null,
            "email" to null,
            "phoneNumber" to null,
            "password" to null,
            "rPassword" to null
        )
    )
        private set

    fun changeUsername(newValue: String) {
        userName.value = newValue
        validateField("userName", newValue)
    }

    fun changeName(newName: String) {
        firstNames.value = newName
        validateField("firstNames", newName)
    }

    fun changeLastName(newLastName: String) {
        lastNames.value = newLastName
        validateField("lastNames", newLastName)
    }

    fun changeEmail(newEmail: String) {
        email.value = newEmail
        validateField("email", newEmail)
    }

    fun changePhoneNumber(newPhoneNumber: String) {
        if (newPhoneNumber.length > 9) return
        phoneNumber.value = newPhoneNumber
        validateField("phoneNumber", newPhoneNumber)
    }

    fun changePassword(newPassword: String) {
        password.value = newPassword
        validateField("password", newPassword)
        if (rPassword.value.isNotEmpty()) {
            validateField("rPassword", rPassword.value)
        }
    }

    fun changeRPassword(newRPassword: String) {
        rPassword.value = newRPassword
        validateField("rPassword", newRPassword)
    }

    private fun validateField(field: String, value: String) {
        val error = when (field) {
            "userName" -> when {
                value.isBlank() -> "El nombre de usuario no puede estar vacío"
                value.length < 3 -> "Debe tener al menos 3 caracteres"
                else -> null
            }

            "firstNames" -> when {
                value.isBlank() -> "Los nombres no pueden estar vacíos"
                value.length < 2 -> "Debe tener al menos 2 caracteres"
                else -> null
            }

            "lastNames" -> when {
                value.isBlank() -> "Los apellidos no pueden estar vacíos"
                value.length < 2 -> "Debe tener al menos 2 caracteres"
                else -> null
            }

            "email" -> when {
                value.isBlank() -> "El correo no puede estar vacío"
                !value.contains("@") || !value.contains(".") -> "Ingresa un correo electrónico válido"
                else -> null
            }

            "phoneNumber" -> when {
                value.isBlank() -> "El teléfono no puede estar vacío"
                !value.isDigitsOnly() -> "Debe ser un número válido"
                value.length != 9 -> "El número debe tener 9 dígitos"
                else -> null
            }

            "password" -> when {
                value.isBlank() -> "La contraseña no puede estar vacía"
                value.length < 6 -> "Debe tener al menos 6 caracteres"
                else -> null
            }

            "rPassword" -> when {
                value.isBlank() -> "Confirma tu contraseña"
                value != password.value -> "Las contraseñas no coinciden"
                else -> null
            }

            else -> null
        }

        errores.value = errores.value.toMutableMap().apply {
            this[field] = error
        }
    }

    fun validateAllFields(): Boolean {
        validateField("userName", userName.value)
        validateField("firstNames", firstNames.value)
        validateField("lastNames", lastNames.value)
        validateField("email", email.value)
        validateField("phoneNumber", phoneNumber.value)
        validateField("password", password.value)
        validateField("rPassword", rPassword.value)

        return errores.value.values.all { it == null }
    }

    fun registerUser(emailStr: String, passwordStr: String, newUser: User) {
        viewModelScope.launch {
            userState.value = UserState.Loading
            val result = repositoryStoreAuth.registerUser(emailStr, passwordStr, newUser)

            if (result is UserState.Error && result.massage.contains("ya está en uso", ignoreCase = true)) {
                errores.value = errores.value.toMutableMap().apply {
                    put("userName", "Este nombre de usuario ya está en uso")
                }
            }
            userState.value = result
        }
    }
}
