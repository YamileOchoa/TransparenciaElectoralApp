package com.proyecto.app_electoral.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.navArgument
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.screens.CandidateProfileScreen
import com.proyecto.app_electoral.ui.screens.CandidatosScreen
import com.proyecto.app_electoral.ui.screens.CompareScreen
import com.proyecto.app_electoral.ui.screens.HomeScreen
import com.proyecto.app_electoral.ui.screens.SplashScreen
import com.proyecto.app_electoral.ui.screens.SearchScreen
import com.proyecto.app_electoral.ui.screens.StatisticsScreen

/**
 * Define todas las rutas (pantallas) de navegación y sus metadatos (título/BottomBar).
 */
sealed class Screen(val route: String, val title: String? = null) {
    // Pantallas sin BottomBar (o que manejan su propia navegación de vuelta)
    object Splash : Screen("splash_screen")
    object Search : Screen("search_screen")

    // RUTA CON ARGUMENTO: Define la ruta base y el placeholder del argumento
    object Profile : Screen("candidate_profile_screen/{candidatoId}") {
        // Función helper para construir la ruta final con un ID
        fun createRoute(candidatoId: Int) = "candidate_profile_screen/$candidatoId"
    }

    // Pantallas con BottomBar
    object Home : Screen("home_screen", "Inicio")
    object Candidates : Screen("candidates_screen", "Candidatos")
    object Compare : Screen("compare_screen", "Comparar")
    object Stats : Screen("stats_screen", "Estadísticas")
}

/**
 * Wrapper que aplica el Scaffold y el BottomBar a las pantallas principales.
 */
@Composable
fun AppScreenWrapper(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        content(paddingValues)
    }
}

/**
 * El NavHost principal que orquesta todas las pantallas de la aplicación.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // --- 1. PANTALLA DE CARGA (Sin Bottom Bar) ---
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        // --- 2. PANTALLA DE BÚSQUEDA (Ruta directa de acceso rápido) ⬅️ CORRECCIÓN: Ahora usa el Wrapper
        // Usado por el Quick Access Grid y también la pestaña Candidatos.
        composable(Screen.Search.route) {
            AppScreenWrapper(navController = navController) { paddingValues -> // ⬅️ AÑADIDO WRAPPER
                SearchScreen(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

        // --- 3. PANTALLA DE PERFIL (Sin Bottom Bar, con argumento) ---
        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument("candidatoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val candidatoId = backStackEntry.arguments?.getInt("candidatoId")
                ?: throw IllegalStateException("Candidato ID es obligatorio")

            CandidateProfileScreen(
                candidatoId = candidatoId,
                onBackClick = { navController.popBackStack() }
            )
        }

        // --- 4. PANTALLAS PRINCIPALES (Con Bottom Bar) ---

        // Pestaña: INICIO
        composable(Screen.Home.route) {
            AppScreenWrapper(navController = navController) { paddingValues ->
                HomeScreen(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

        // Pestaña: CANDIDATOS (Redirigida a la misma SearchScreen) ⬅️ CAMBIO IMPORTANTE
        // Ahora solo navegamos a la ruta Screen.Search.route
        composable(Screen.Candidates.route) {
            // Utilizamos el Wrapper para mantener la estructura del TabBar, pero el contenido es la redirección.
            AppScreenWrapper(navController = navController) {
                // Navegamos inmediatamente a la ruta principal de búsqueda
                navController.navigate(Screen.Search.route) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                    launchSingleTop = true
                }
                Box(Modifier.fillMaxSize()) {} // Placeholder mientras ocurre la navegación
            }
        }


        // Pestaña: COMPARAR
        composable(Screen.Compare.route) {
            AppScreenWrapper(navController = navController) { paddingValues ->
                CompareScreen(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

        // Pestaña: ESTADÍSTICAS
        composable(Screen.Stats.route) {
            AppScreenWrapper(navController = navController) {
                StatisticsScreen(navController = navController)
            }
        }
    }
}
