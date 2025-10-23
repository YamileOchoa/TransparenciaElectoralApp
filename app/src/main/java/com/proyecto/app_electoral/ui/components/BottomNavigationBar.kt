package com.proyecto.app_electoral.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

data class BottomNavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val route: String)

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    val items = listOf(
        BottomNavItem("Inicio", Icons.Default.Home, "inicio"),
        BottomNavItem("Búsqueda", Icons.Default.Search, "busqueda"),
        BottomNavItem("Favoritos", Icons.Default.Star, "favoritos"),
        BottomNavItem("Comparar", Icons.Default.CompareArrows, "comparar")
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF1a237e)
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF1a237e),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color(0xFF1a237e),
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFFE8EAF6)
                )
            )
        }
    }
}
