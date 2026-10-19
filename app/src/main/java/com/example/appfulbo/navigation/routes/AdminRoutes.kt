package com.example.appfulbo.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AdminRoutes(
    val route: String,
    val title: String = "",
    val icon: ImageVector = Icons.Rounded.Done
) {
    object SoccerField : AdminRoutes("soccerField", "Mis Canchas", Icons.Rounded.Home)
    object Reserve : AdminRoutes("reserve", "Mis Reservas", Icons.Rounded.Done)
    object Place : AdminRoutes("place", "Mi Local", Icons.Rounded.Place)
    object Profile : AdminRoutes("profile", "Mi Perfil", Icons.Rounded.Person)
    object CreateSoccerField : AdminRoutes("createSoccerField")
    object CreateReserve: AdminRoutes("createReserve", "Reservación")
}