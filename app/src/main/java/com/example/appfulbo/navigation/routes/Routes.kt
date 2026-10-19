package com.example.appfulbo.navigation.routes

sealed class Routes(val route: String) {
    object Splash: Routes("splash")
    object Login : Routes("login")
    object Register : Routes("register")
    object AdminHome : Routes("soccerField")
    object UserHome: Routes("UserHome")
    object Recover: Routes("password")

}