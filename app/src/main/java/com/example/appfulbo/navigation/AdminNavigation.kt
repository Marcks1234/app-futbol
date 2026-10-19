package com.example.appfulbo.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appfulbo.navigation.routes.AdminRoutes.CreateReserve
import com.example.appfulbo.navigation.routes.AdminRoutes.CreateSoccerField
import com.example.appfulbo.navigation.routes.AdminRoutes.Place
import com.example.appfulbo.navigation.routes.AdminRoutes.Profile
import com.example.appfulbo.navigation.routes.AdminRoutes.Reserve
import com.example.appfulbo.navigation.routes.AdminRoutes.SoccerField
import com.example.appfulbo.navigation.routes.Routes.AdminHome
import com.example.appfulbo.navigation.routes.Routes.Login
import com.example.appfulbo.ui.screens.admin.createReserve.CreateReserveScreen
import com.example.appfulbo.ui.screens.admin.place.PlaceAdminView
import com.example.appfulbo.ui.screens.admin.profile.ProfileAdminView
import com.example.appfulbo.ui.screens.admin.reserve.ReserveListScreen
import com.example.appfulbo.ui.screens.admin.soccerField.CreateSoccerFieldAdminView
import com.example.appfulbo.ui.screens.admin.soccerField.SoccerFieldAdminView
import com.example.appfulbo.ui.screens.admin.soccerField.SoccerFieldViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminNavigation(
    navController: NavHostController,
    rootNavController: NavHostController,
    sharedViewModel: SoccerFieldViewModel
) {
    NavHost(navController = navController, startDestination = SoccerField.route) {

        composable(route = SoccerField.route) {
            SoccerFieldAdminView(sharedViewModel)
        }
        composable(route = CreateSoccerField.route) {
            CreateSoccerFieldAdminView(
                viewModel = sharedViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = CreateReserve.route) {
            CreateReserveScreen(onBack = { navController.popBackStack() })
        }
        composable(route = Reserve.route) { ReserveListScreen() }
        composable(route = Place.route) { PlaceAdminView() }
        composable(route = Profile.route) {
            ProfileAdminView(toLogin = {
                rootNavController.navigate(Login.route) {
                    popUpTo(AdminHome.route) {
                        inclusive = true
                    }
                }
            })
        }
    }
}
