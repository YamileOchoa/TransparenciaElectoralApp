package com.proyecto.app_electoral.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.proyecto.app_electoral.ui.screens.CompareScreen
import com.proyecto.app_electoral.ui.screens.DetailScreen
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
                onCandidateClick = { candidateId ->
                    navController.navigate("detalle/$candidateId")
                }
            )
        }

        composable(
            route = "detalle/{candidateId}",
            arguments = listOf(navArgument("candidateId") { type = NavType.IntType })
        ) { backStackEntry ->
            val candidateId = backStackEntry.arguments?.getInt("candidateId") ?: 0
            DetailScreen(
                navController = navController,
                candidateId = candidateId
            )
        }
        
        composable("comparar") { CompareScreen(navController) }
    }
}
