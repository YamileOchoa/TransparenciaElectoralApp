package com.proyecto.app_electoral.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.proyecto.app_electoral.ui.screens.HomeScreen
import com.proyecto.app_electoral.ui.screens.SearchScreen
import com.proyecto.app_electoral.ui.screens.DetailScreen
import com.proyecto.app_electoral.ui.screens.CompareScreen

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") { HomeScreen(navController) }
        composable("busqueda") {
            SearchScreen(navController, onCandidateClick = {})
        }
        // composable("detalle") { DetailScreen(navController) }
        composable("comparar") { CompareScreen(navController) }
    }
}
