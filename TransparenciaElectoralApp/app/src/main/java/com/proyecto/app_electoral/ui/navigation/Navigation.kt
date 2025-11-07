package com.proyecto.app_electoral.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.navigation.navArgument
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.screens.CandidateProfileScreen
import com.proyecto.app_electoral.ui.screens.HomeScreen
import com.proyecto.app_electoral.ui.screens.SplashScreen
import com.proyecto.app_electoral.ui.screens.SearchScreen
import com.proyecto.app_electoral.ui.screens.StatisticsScreen
import com.proyecto.app_electoral.ui.screens.ComparisonScreen // <<-- Importación necesaria

/**
 * Define todas las rutas (pantallas) de navegación y sus metadatos (título/BottomBar).
 */
sealed class Screen(val route: String, val title: String? = null) {
    // Pantallas sin BottomBar (o que manejan su propia navegación de vuelta)
    object Splash : Screen("splash_screen")
    object Search : Screen("search_screen?mode={mode}&position={position}") {
        /**
         * Crea la ruta de navegación para SearchScreen.
         * Por defecto, mode="NORMAL" y position=0 (comportamiento de búsqueda estándar).
         */
        fun createRoute(mode: String = "NORMAL", position: Int = 0) =
            "search_screen?mode=$mode&position=$position"
    }
    // RUTA CON ARGUMENTO: Define la ruta base y el placeholder del argumento
    object Profile : Screen("candidate_profile_screen/{candidatoId}") {
        // Función helper para construir la ruta final con un ID
        fun createRoute(candidatoId: Int) = "candidate_profile_screen/$candidatoId"
    }

    // Pantallas con BottomBar
    object Home : Screen("home_screen", "Inicio")
    object Candidates : Screen("candidates_screen", "Candidatos")

    // CORRECCIÓN CLAVE: RUTA DE COMPARACIÓN CON ARGUMENTOS OPCIONALES
    object Compare : Screen("compare_screen?candidatoId1={candidatoId1}&candidatoId2={candidatoId2}", "Comparar") {
        fun createRoute(candidatoId1: Int? = null, candidatoId2: Int? = null): String {
            // Se usa "null" como string para la navegación cuando el ID es nulo
            val id1 = candidatoId1?.toString() ?: "null"
            val id2 = candidatoId2?.toString() ?: "null"
            return "compare_screen?candidatoId1=$id1&candidatoId2=$id2"
        }
    }
    // FIN CORRECCIÓN CLAVE

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

        // --- 2. PANTALLA DE BÚSQUEDA (Ruta directa de acceso rápido)
        // --- 2. PANTALLA DE BÚSQUEDA (Ruta directa de acceso rápido)
        // MODIFICACIÓN CLAVE: Definimos y leemos los argumentos de modo y posición.
        composable(
            route = Screen.Search.route, // Ahora usa la ruta con ?mode={mode}&position={position}
            arguments = listOf(
                navArgument("mode") { // Argumento para el modo (NORMAL o COMPARE)
                    type = NavType.StringType
                    defaultValue = "NORMAL"
                },
                navArgument("position") { // Argumento para la posición (1 o 2 en modo COMPARE)
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            // Leemos los argumentos
            val mode = backStackEntry.arguments?.getString("mode") ?: "NORMAL"
            val position = backStackEntry.arguments?.getInt("position") ?: 0

            // NOTA: Para simplificar, si la navegación de búsqueda no viene del BottomBar,
            // podemos omitir el AppScreenWrapper para que la pantalla ocupe todo el espacio.
            // Aquí se mantiene la lógica original que usa AppScreenWrapper para aplicar padding.

            AppScreenWrapper(navController = navController) { paddingValues ->
                SearchScreen(
                    navController = navController,
                    // PASAMOS LOS NUEVOS ARGUMENTOS A LA COMPOSABLE DE BÚSQUEDA
                    searchMode = mode,
                    searchPosition = position,
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

        // Pestaña: CANDIDATOS (Redirigida a la misma SearchScreen)
        composable(Screen.Candidates.route) {
            AppScreenWrapper(navController = navController) {
                navController.navigate(Screen.Search.route) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                    launchSingleTop = true
                }
                Box(Modifier.fillMaxSize()) {}
            }
        }


        // Pestaña: COMPARAR (IMPLEMENTACIÓN DE COMPARISONSCREEN)
        composable(
            route = Screen.Compare.route,
            arguments = listOf(
                navArgument("candidatoId1") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = "null" // Usamos "null" para representar la ausencia
                },
                navArgument("candidatoId2") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = "null"
                }
            )
        ) { backStackEntry ->
            // Convertimos la cadena "null" de vuelta a null (Int?)
            val id1 = backStackEntry.arguments?.getString("candidatoId1").takeIf { it != "null" }?.toIntOrNull()
            val id2 = backStackEntry.arguments?.getString("candidatoId2").takeIf { it != "null" }?.toIntOrNull()

            AppScreenWrapper(navController = navController) { paddingValues ->
                ComparisonScreen(
                    navController = navController,
                    candidatoId1 = id1,
                    candidatoId2 = id2,
                    modifier = Modifier.padding(paddingValues) // Aplicamos el padding
                )
            }
        }

        // Pestaña: ESTADÍSTICAS
        composable(Screen.Stats.route) {
            AppScreenWrapper(navController = navController) { paddingValues ->
                StatisticsScreen(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

/**
 * Componente temporal para las pantallas que aún no tienen contenido.
 * Se deja privado para ser usado solo si es necesario, pero ya no se usa para COMPARAR.
 */
@Composable
private fun PlaceholderScreen(title: String, paddingValues: PaddingValues) {
    Box(modifier = Modifier.padding(paddingValues).fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Contenido de $title (PENDIENTE)")
    }
}