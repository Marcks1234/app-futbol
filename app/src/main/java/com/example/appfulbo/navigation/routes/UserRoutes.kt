package com.example.appfulbo.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class UserRoutes(
    val route: String,
    val title: String = "",
    val icon: ImageVector = Icons.Default.Place
) {
    object Places : UserRoutes("places", "Locales", Icons.Default.Place)
    object Profile : UserRoutes("perfile", "Usuario", Icons.Rounded.Person)
    object Reserve : UserRoutes("reserve", "Detalles de reservas", Icons.Default.Done)
    object ReserveSoccer: UserRoutes("reserveField/{id}/{placeId}"){
        fun argument(id: String,placeId: String): String = "reserveField/$id/$placeId"
    }
    object SoccerFields : UserRoutes("soccerFields/{id}"){
    fun argument(id: String): String = "soccerFields/$id"
    }
}