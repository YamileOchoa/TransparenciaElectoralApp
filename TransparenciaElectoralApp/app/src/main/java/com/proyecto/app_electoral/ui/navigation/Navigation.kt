package com.proyecto.app_electoral.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.proyecto.app_electoral.ui.screens.HomeScreen
import com.proyecto.app_electoral.ui.screens.SplashScreen

/**
 * Defines all navigation routes (screens) in the application.
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    // Add other screens here (e.g., Detail, Search, Profile)
    // object Detail : Screen("detail_screen/{candidatoId}") {
    //     fun createRoute(candidatoId: Int) = "detail_screen/$candidatoId"
    // }
}

/**
 * Main navigation host for the application.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // --- 1. Splash Screen ---
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        // --- 2. Home Screen (Placeholder) ---
        composable(Screen.Home.route) {
            // Placeholder for the main screen content
            HomeScreen(navController = navController)
        }

        // Add more composables here for detail screens, etc.
    }
}