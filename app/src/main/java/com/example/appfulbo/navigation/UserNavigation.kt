package com.example.appfulbo.navigation

import com.example.appfulbo.ui.screens.user.reserve.ReserveListScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.appfulbo.navigation.routes.Routes.Login
import com.example.appfulbo.navigation.routes.Routes.UserHome
import com.example.appfulbo.navigation.routes.UserRoutes.Places
import com.example.appfulbo.navigation.routes.UserRoutes.Profile
import com.example.appfulbo.navigation.routes.UserRoutes.Reserve
import com.example.appfulbo.navigation.routes.UserRoutes.ReserveSoccer
import com.example.appfulbo.navigation.routes.UserRoutes.SoccerFields
import com.example.appfulbo.ui.screens.admin.profile.ProfileAdminView
import com.example.appfulbo.ui.screens.user.places.PlacesView
import com.example.appfulbo.ui.screens.user.places.soccerFields.SoccerFieldsView
import com.example.appfulbo.ui.screens.user.reserveSoccer.ReserveSoccerScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserNavigation(navController: NavHostController, rootNavController: NavController) {

    NavHost(
        navController = navController,
        startDestination = Places.route
    ) {
        composable(route = Places.route) {
            PlacesView(toSoccerFields = { idPlace ->
                navController.navigate(SoccerFields.argument(idPlace))
            })
        }

        composable(
            route = SoccerFields.route,
            arguments = listOf(
                navArgument("id") { NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""

            SoccerFieldsView(
                idSoccer = id,
                toReserve = { idSoccerField, idPlaceSoccerField ->
                    navController.navigate(ReserveSoccer.argument(idSoccerField, idPlaceSoccerField))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = ReserveSoccer.route,
            arguments = listOf(
                navArgument("id") { NavType.StringType },
                navArgument("placeId") { NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val placeId = backStackEntry.arguments?.getString("placeId") ?: ""
            ReserveSoccerScreen(
                soccerFieldId = id,
                placeId = placeId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = Reserve.route) {
            ReserveListScreen()
        }

        composable(route = Profile.route) {
            ProfileAdminView(
                toLogin = {
                    rootNavController.navigate(route = Login.route) {
                        popUpTo(route = UserHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
