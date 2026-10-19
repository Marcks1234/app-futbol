package com.example.appfulbo.ui.screens.login

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
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.appfulbo.data.repository.fireBase.TypeUser
import com.example.appfulbo.ui.components.TextFieldEdit
import com.example.appfulbo.ui.components.TypeText
import com.example.appfulbo.ui.states.UserState
import com.example.appfulbo.ui.theme.PrimaryColor
import com.example.appfulbo.ui.theme.SecondaryColor

@Composable
fun LoginView(
    viewModel: LoginViewModel = hiltViewModel(),
    toAdminHome: () -> Unit,
    toUserHome: () -> Unit,
    toRegister: () -> Unit,
    toPassword: () -> Unit
) {
    val userState by viewModel.userState.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val errores by viewModel.errores.collectAsState()
    var isVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(userState) {
        if (userState is UserState.Error) {
            if (viewModel.verifiqueDataInput()) {
                Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = "https://cdn-icons-png.freepik.com/256/10574/10574160.png",
            contentDescription = "Logo futbol",
            modifier = Modifier
                .height(96.dp)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "FÚTBOL APP",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Reserva tu cancha favorita en segundos",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.85f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    "Iniciar Sesión",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                Text("Correo Electrónico", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.DarkGray)
                TextFieldEdit(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    placeholder = "ejemplo@correo.com",
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = PrimaryColor) },
                    onValueChange = viewModel::changeEmail,
                    supportingText = { errores["email"]?.let { Text(it, color = Color.Red, fontSize = 12.sp) } }
                )

                Text("Contraseña", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.DarkGray)
                TextFieldEdit(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    placeholder = "••••••••",
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = PrimaryColor) },
                    typeText = if (isVisible) TypeText.TEXT else TypeText.PASSWORD,
                    onValueChange = viewModel::changePassword,
                    trailingIcon = {
                        IconButton(onClick = { isVisible = !isVisible }) {
                            Icon(
                                imageVector = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = SecondaryColor
                            )
                        }
                    },
                    supportingText = { errores["password"]?.let { Text(it, color = Color.Red, fontSize = 12.sp) } }
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = toPassword) {
                        Text(
                            text = "¿Olvidaste tu contraseña?",
                            textAlign = TextAlign.End,
                            color = SecondaryColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
                    onClick = {
                        viewModel.loginUser(email, password) { isAdmin ->
                            when (isAdmin) {
                                TypeUser.NEW_USER -> {}
                                TypeUser.USER -> toUserHome()
                                TypeUser.ADMIN -> toAdminHome()
                            }
                        }
                    },
                    enabled = userState != UserState.Loading
                ) {
                    if (userState == UserState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp))
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Ingresar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.AutoMirrored.Filled.Login, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }
                }

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), modifier = Modifier.padding(top = 4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("¿Aún no tienes una cuenta?", fontSize = 14.sp, color = Color.Gray)
                    TextButton(onClick = toRegister) {
                        Text("Regístrate aquí", color = SecondaryColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
