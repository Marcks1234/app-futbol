package com.example.appfulbo.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appfulbo.navigation.routes.Routes
import com.example.appfulbo.navigation.routes.Routes.AdminHome
import com.example.appfulbo.navigation.routes.Routes.Login
import com.example.appfulbo.navigation.routes.Routes.Recover
import com.example.appfulbo.navigation.routes.Routes.Register
import com.example.appfulbo.navigation.routes.Routes.Splash
import com.example.appfulbo.navigation.routes.Routes.UserHome
import com.example.appfulbo.ui.screens.admin.AdminView
import com.example.appfulbo.ui.screens.login.LoginView
import com.example.appfulbo.ui.screens.recover.RecoverView
import com.example.appfulbo.ui.screens.register.RegisterView
import com.example.appfulbo.ui.screens.splash.SplashScreenView
import com.example.appfulbo.ui.screens.user.UserView

// CONTROLADOR del navegador, lo usamos para poder hacer el cambio de pestañas basado en la
// configuración hecha en esta pantalla.
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Splash.route
    ) {
        composable(route = Splash.route) {
            SplashScreenView(
                toLogin = navigateToView(navController, Login, Splash, true),
                toUserHome = navigateToView(navController, UserHome, Splash, true),
                toAdminHome = navigateToView(navController, AdminHome, Splash, true)
            )
        }
        composable(route = Register.route) {
            RegisterView(
                toLogin = navigateToView(navController, Login, Register, true),
            )
        }
        composable(route = Login.route) {
            LoginView(
                toAdminHome = navigateToView(navController, AdminHome, Login, true),
                toUserHome = navigateToView(navController, UserHome, Login, true),
                toRegister = navigateToView(navController, Register, Login, false),
                toPassword = navigateToView(navController, Recover, Login, false)
            )
        }
        composable(route = UserHome.route) {
            UserView(navController)
        }
        composable(route = AdminHome.route) {
            AdminView(navController)
        }
        composable(route = Recover.route) {
            RecoverView(navController)
        }
    }
}

fun navigateToView(
    navController: NavHostController,
    target: Routes,
    until: Routes,
    delete: Boolean = true
): () -> Unit {
    return {
        Log.i("Navigation para pagina", "Navigating from ${until.route} to ${target.route}")
        navController.navigate(
            target.route,
            {
                launchSingleTop = true
                popUpTo(until.route) { inclusive = delete }
            })
    }
}






