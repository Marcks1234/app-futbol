package com.example.appfulbo.ui.screens.recover

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appfulbo.ui.components.TextFieldEdit
import com.example.appfulbo.ui.components.TypeText
import com.example.appfulbo.ui.states.UserState
import com.example.appfulbo.ui.theme.PrimaryColor
import com.example.appfulbo.ui.theme.SecondaryColor
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appfulbo.ui.theme.SecondaryColorDisable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverView(navController: NavController, viewModel: RecoverViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val userState by viewModel.userState.collectAsState()
    var email by remember { mutableStateOf("") }
    var enable by remember { mutableStateOf(true) }

    LaunchedEffect(userState) {
        when (userState) {
            is UserState.Loading -> enable = false
            is UserState.Error -> {
                enable = true
                Toast.makeText(context, (userState as UserState.Error).massage, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            is UserState.Success -> {
                enable = true
                Toast.makeText(context, "Correo enviado a $email", Toast.LENGTH_SHORT).show()
                viewModel.resetState()
                navController.popBackStack()
            }
            else -> enable = true
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xff67CB6D)),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Recuperar Contraseña",
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Correo Electrónico"
            )

            TextFieldEdit(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                typeText = TypeText.EMAIL,
                placeholder = "Ingresar correo electrónico"
            )

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = enable,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = SecondaryColor,
                    disabledContainerColor = SecondaryColorDisable,
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                ),
                border = BorderStroke(0.dp, Color.Transparent),
                onClick = {
                    if (email.isBlank()) {
                        Toast.makeText(context, "Ingrese su correo", Toast.LENGTH_SHORT).show()
                        return@OutlinedButton
                    }
                    viewModel.sendEmail(email)
                }
            ) {
                Icon(Icons.Default.Email, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(10.dp))
                Text("Enviar Correo", color = Color.White)
            }

            Spacer(modifier = Modifier.weight(1f))

            if (userState == UserState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    color = PrimaryColor
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
