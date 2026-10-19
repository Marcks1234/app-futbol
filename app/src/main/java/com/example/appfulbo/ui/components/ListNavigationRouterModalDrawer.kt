package com.example.appfulbo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appfulbo.data.repository.fireBase.TypeUser
import com.example.appfulbo.navigation.routes.AdminRoutes.CreateReserve
import com.example.appfulbo.ui.screens.admin.adminViews
import com.example.appfulbo.ui.screens.user.userViews
import com.example.appfulbo.ui.theme.PrimaryColor

@Composable
fun ListNavigationRouterModalDrawer(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    typeRoutes: TypeUser = TypeUser.USER
) {
    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
        modifier = Modifier.width(285.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = PrimaryColor,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SportsSoccer,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "FULBO APP",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryColor
                        )
                        Surface(
                            color = PrimaryColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = if (typeRoutes == TypeUser.ADMIN) "Panel Admin" else "Panel Usuario",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryColor,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

            Spacer(modifier = Modifier.height(4.dp))

            if (typeRoutes == TypeUser.ADMIN) {
                adminViews.filter { it != CreateReserve }.forEach { view ->
                    val isSelected = currentRoute == view.route
                    NavigationDrawerItem(
                        label = { Text(view.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
                        selected = isSelected,
                        onClick = { onNavigate(view.route) },
                        icon = { Icon(view.icon, contentDescription = view.title) },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = PrimaryColor,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.DarkGray
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            if (typeRoutes == TypeUser.USER) {
                userViews.forEach { view ->
                    val isSelected = currentRoute == view.route
                    NavigationDrawerItem(
                        label = { Text(view.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
                        selected = isSelected,
                        onClick = { onNavigate(view.route) },
                        icon = { Icon(view.icon, contentDescription = view.title) },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = PrimaryColor,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.DarkGray
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

            NavigationDrawerItem(
                label = { Text("Cerrar Sesión", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold) },
                selected = false,
                onClick = onLogout,
                icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesión", tint = Color(0xFFD32F2F)) },
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}
