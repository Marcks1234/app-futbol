package com.example.appfulbo

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appfulbo.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            handleDeepLink(intent?.data)
            navController = rememberNavController()
            AppNavigation(navController)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent.data)
    }

    private fun handleDeepLink(data: Uri?) {
        data?.let {
            if (it.scheme == "fulboapp" && it.host == "payment") {
                val status = it.getQueryParameter("status")
                when (status) {
                    "success" -> {
                        Toast.makeText(this, "¡Pago exitoso!", Toast.LENGTH_SHORT).show()
                    }
                    "failure" -> Toast.makeText(this, "Pago fallido", Toast.LENGTH_SHORT).show()
                    "pending" -> Toast.makeText(this, "Pago pendiente", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

