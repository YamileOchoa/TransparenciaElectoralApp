package com.proyecto.app_electoral.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.proyecto.app_electoral.ui.screens.CompareScreen
import com.proyecto.app_electoral.ui.screens.DetailScreen
import com.proyecto.app_electoral.ui.screens.FavoritosScreen
import com.proyecto.app_electoral.ui.screens.HomeScreen
import com.proyecto.app_electoral.ui.screens.SearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") { HomeScreen(navController) }

        composable("busqueda") {
            SearchScreen(
                navController = navController,
                onCandidateClick = { candidatoId ->
                    navController.navigate("detalle/$candidatoId")
                }
            )
        }

        composable("favoritos") {
            FavoritosScreen(
                navController = navController,
                onCandidateClick = { candidatoId ->
                    navController.navigate("detalle/$candidatoId")
                }
            )
        }

        composable(
            route = "detalle/{candidatoId}",
            arguments = listOf(navArgument("candidatoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val candidatoId = backStackEntry.arguments?.getInt("candidatoId") ?: 0
            DetailScreen(
                navController = navController,
                candidatoId = candidatoId
            )
        }
        
        composable("comparar") { CompareScreen(navController) }
    }
}
