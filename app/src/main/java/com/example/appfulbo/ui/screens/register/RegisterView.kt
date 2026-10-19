package com.example.appfulbo.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.appfulbo.model.User
import com.example.appfulbo.ui.components.TextFieldEdit
import com.example.appfulbo.ui.components.TypeText
import com.example.appfulbo.ui.states.UserState
import com.example.appfulbo.ui.theme.PrimaryColor
import com.example.appfulbo.ui.theme.SecondaryColor

@Composable
fun RegisterView(
    toLogin: () -> Unit,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val userState by registerViewModel.userState.collectAsState()
    val userName by registerViewModel.userName.collectAsState()
    val firstNames by registerViewModel.firstNames.collectAsState()
    val lastNames by registerViewModel.lastNames.collectAsState()
    val email by registerViewModel.email.collectAsState()
    val phoneNumber by registerViewModel.phoneNumber.collectAsState()
    val password by registerViewModel.password.collectAsState()
    val rPassword by registerViewModel.rPassword.collectAsState()
    val errores by registerViewModel.errores.collectAsState()

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isRPasswordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(userState) {
        when (userState) {
            UserState.Success -> {
                Toast.makeText(context, "¡Cuenta creada exitosamente!", Toast.LENGTH_LONG).show()
                toLogin()
            }
            is UserState.Error -> {
                val msg = (userState as UserState.Error).massage
                Toast.makeText(context, "Error: $msg", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = toLogin) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Crear Nueva Cuenta",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        AsyncImage(
            model = "https://cdn-icons-png.freepik.com/256/10574/10574160.png",
            contentDescription = "Logo futbol",
            modifier = Modifier
                .height(80.dp)
                .padding(bottom = 12.dp),
            contentScale = ContentScale.Fit
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    "Información Personal",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )

                Text("Nombre de Usuario", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.DarkGray)
                TextFieldEdit(
                    modifier = Modifier.fillMaxWidth(),
                    value = userName,
                    placeholder = "ej: marcoperez",
                    onValueChange = registerViewModel::changeUsername,
                    supportingText = { errores["userName"]?.let { Text(it, color = Color.Red, fontSize = 12.sp) } }
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Nombre(s)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.DarkGray)
                        TextFieldEdit(
                            modifier = Modifier.fillMaxWidth(),
                            value = firstNames,
                            placeholder = "Nombres",
                            onValueChange = registerViewModel::changeName,
                            supportingText = { errores["firstNames"]?.let { Text(it, color = Color.Red, fontSize = 12.sp) } }
                        )
                    }
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Apellido(s)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.DarkGray)
                        TextFieldEdit(
                            modifier = Modifier.fillMaxWidth(),
                            value = lastNames,
                            placeholder = "Apellidos",
                            onValueChange = registerViewModel::changeLastName,
                            supportingText = { errores["lastNames"]?.let { Text(it, color = Color.Red, fontSize = 12.sp) } }
                        )
                    }
                }

                Text("Teléfono", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.DarkGray)
                TextFieldEdit(
                    modifier = Modifier.fillMaxWidth(),
                    value = phoneNumber,
                    placeholder = "987654321",
                    typeText = TypeText.NUMBER,
                    onValueChange = registerViewModel::changePhoneNumber,
                    supportingText = { errores["phoneNumber"]?.let { Text(it, color = Color.Red, fontSize = 12.sp) } }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color.LightGray.copy(alpha = 0.5f))

                Text(
                    "Credenciales de Acceso",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )

                Text("Correo Electrónico", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.DarkGray)
                TextFieldEdit(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    placeholder = "correo@ejemplo.com",
                    onValueChange = registerViewModel::changeEmail,
                    supportingText = { errores["email"]?.let { Text(it, color = Color.Red, fontSize = 12.sp) } }
                )

                Text("Contraseña", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.DarkGray)
                TextFieldEdit(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    placeholder = "••••••••",
                    typeText = if (isPasswordVisible) TypeText.TEXT else TypeText.PASSWORD,
                    onValueChange = registerViewModel::changePassword,
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = SecondaryColor
                            )
                        }
                    },
                    supportingText = { errores["password"]?.let { Text(it, color = Color.Red, fontSize = 12.sp) } }
                )

                Text("Confirmar Contraseña", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.DarkGray)
                TextFieldEdit(
                    modifier = Modifier.fillMaxWidth(),
                    value = rPassword,
                    placeholder = "••••••••",
                    typeText = if (isRPasswordVisible) TypeText.TEXT else TypeText.PASSWORD,
                    onValueChange = registerViewModel::changeRPassword,
                    trailingIcon = {
                        IconButton(onClick = { isRPasswordVisible = !isRPasswordVisible }) {
                            Icon(
                                imageVector = if (isRPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = SecondaryColor
                            )
                        }
                    },
                    supportingText = { errores["rPassword"]?.let { Text(it, color = Color.Red, fontSize = 12.sp) } }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
                    onClick = {
                        if (userState == UserState.Loading) return@Button
                        if (registerViewModel.validateAllFields()) {
                            val newUser = User(
                                userName = userName,
                                firstNames = firstNames,
                                lastNames = lastNames,
                                admin = false,
                                email = email,
                                phoneNumber = phoneNumber,
                                profileImageURL = ""
                            )
                            registerViewModel.registerUser(email, password, newUser)
                        }
                    },
                    enabled = userState != UserState.Loading
                ) {
                    if (userState == UserState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        Text(
                            "Crear Cuenta",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("¿Ya tienes una cuenta?", fontSize = 14.sp, color = Color.Gray)
                    TextButton(onClick = toLogin) {
                        Text("Inicia sesión", color = SecondaryColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
