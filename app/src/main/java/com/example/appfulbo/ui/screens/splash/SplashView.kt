package com.example.appfulbo.ui.screens.splash

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.appfulbo.data.repository.fireBase.TypeUser
import com.example.appfulbo.ui.theme.PrimaryColor

@Composable
fun SplashScreenView(
    viewModel: SplashViewModel = hiltViewModel(),
    toLogin: () -> Unit,
    toUserHome: () -> Unit,
    toAdminHome: () -> Unit
) {
    val result by viewModel.result.collectAsState()

    LaunchedEffect(result) {
        result?.let { userType ->
            Log.i("resultado tipo usuario", "el resultado es: ${userType.name}")
            when (userType) {
                TypeUser.NEW_USER -> toLogin()
                TypeUser.USER -> toUserHome()
                TypeUser.ADMIN -> toAdminHome()
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "https://cdn-icons-png.freepik.com/256/10574/10574160.png",
                contentDescription = "Logo futbol",
                modifier = Modifier
                    .height(120.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "FÚTBOL APP", fontSize = 30.sp, fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Cargando la pasión del fútbol…", fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CircularProgressIndicator(
                color = Color.White, strokeWidth = 5.dp
            )
        }
    }
}
