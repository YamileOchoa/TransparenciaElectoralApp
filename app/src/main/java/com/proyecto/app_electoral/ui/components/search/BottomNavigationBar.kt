package com.proyecto.app_electoral.ui.components.search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "inicio",
            onClick = { navController.navigate("inicio") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            alwaysShowLabel = true
        )

        NavigationBarItem(
            selected = currentRoute == "comparar",
            onClick = { navController.navigate("comparar") },
            icon = { Icon(Icons.Filled.Compare, contentDescription = "Comparar") },
            label = { Text("Comparar") },
            alwaysShowLabel = true
        )

        NavigationBarItem(
            selected = currentRoute == "favoritos",
            onClick = { navController.navigate("favoritos") },
            icon = { Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            alwaysShowLabel = true
        )
    }
}