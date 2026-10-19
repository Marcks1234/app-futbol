package com.example.appfulbo.ui.screens.admin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appfulbo.data.repository.fireBase.TypeUser
import com.example.appfulbo.navigation.AdminNavigation
import com.example.appfulbo.navigation.routes.AdminRoutes
import com.example.appfulbo.navigation.routes.AdminRoutes.CreateReserve
import com.example.appfulbo.navigation.routes.AdminRoutes.CreateSoccerField
import com.example.appfulbo.navigation.routes.AdminRoutes.Place
import com.example.appfulbo.navigation.routes.AdminRoutes.Profile
import com.example.appfulbo.navigation.routes.AdminRoutes.Reserve
import com.example.appfulbo.navigation.routes.AdminRoutes.SoccerField
import com.example.appfulbo.ui.components.ListNavigationRouterModalDrawer
import com.example.appfulbo.ui.screens.admin.soccerField.SoccerFieldViewModel
import com.example.appfulbo.ui.theme.PrimaryColor
import com.example.appfulbo.ui.theme.SecondaryColor
import kotlinx.coroutines.launch

val adminViews: List<AdminRoutes> = listOf(
    SoccerField,
    Place,
    Reserve,
    Profile,
    CreateReserve
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminView(rootNavController: NavHostController) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: SoccerField.route
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel: SoccerFieldViewModel = hiltViewModel()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ListNavigationRouterModalDrawer(
                currentRoute = currentRoute,
                typeRoutes = TypeUser.ADMIN,
                onNavigate = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onLogout = {
                    rootNavController.popBackStack()
                    rootNavController.navigate("login")
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                val title = adminViews.find { it.route == currentRoute }?.title ?: "Panel Admin"
                TopAppBar(
                    title = {
                        Text(
                            title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor)
                )
            },
            floatingActionButton = {
                when (currentRoute) {
                    SoccerField.route -> {
                        FloatingActionButton(
                            onClick = { navController.navigate(CreateSoccerField.route) },
                            shape = CircleShape,
                            containerColor = SecondaryColor,
                            contentColor = Color.White
                        ) {
                            Icon(Icons.Rounded.Add, contentDescription = "Agregar Cancha")
                        }
                    }

                    Reserve.route -> {
                        FloatingActionButton(
                            onClick = { navController.navigate(CreateReserve.route) },
                            shape = CircleShape,
                            containerColor = SecondaryColor,
                            contentColor = Color.White
                        ) {
                            Icon(Icons.Rounded.Add, contentDescription = "Crear Reserva")
                        }
                    }
                }
            },
            bottomBar = {
                NavigationBar(containerColor = PrimaryColor) {
                    adminViews
                        .filter { it != CreateReserve }
                        .forEach { view ->
                            val isSelected = currentRoute == view.route
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = {
                                    if (currentRoute != view.route) {
                                        navController.navigate(view.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                },
                                icon = { Icon(view.icon, contentDescription = view.title) },
                                label = { Text(view.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = PrimaryColor,
                                    selectedTextColor = Color.White,
                                    indicatorColor = Color.White,
                                    unselectedIconColor = Color.White.copy(alpha = 0.7f),
                                    unselectedTextColor = Color.White.copy(alpha = 0.7f)
                                )
                            )
                        }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AdminNavigation(
                    navController = navController,
                    rootNavController = rootNavController,
                    sharedViewModel = viewModel
                )
            }
        }
    }
}
