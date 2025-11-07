package com.proyecto.app_electoral.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue // Importación necesaria
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.proyecto.app_electoral.ui.navigation.Screen // Importar las rutas

data class ItemNavInferior(val ruta: String, val etiqueta: String, val icono: androidx.compose.ui.graphics.vector.ImageVector)

// Lista de items CON sus rutas
private val bottomNavItems = listOf(
    ItemNavInferior(Screen.Home.route, "Inicio", Icons.Default.Home),
    ItemNavInferior(Screen.Candidates.route, "Candidatos", Icons.Default.Person),
    ItemNavInferior(Screen.Compare.route, "Comparar", Icons.Default.SwapHoriz),
    ItemNavInferior(Screen.Stats.route, "Estadísticas", Icons.Default.BarChart)
)


@Composable
fun BottomNavigationBar(navController: NavController) { // Aceptar NavController
    // Obtener la ruta actual para saber qué pestaña está seleccionada
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                // Usamos el color de borde del tema
                .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        )

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface, // Usar surface del tema
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            bottomNavItems.forEach { item ->
                val isSelected = currentRoute == item.ruta

                NavigationBarItem(
                    icon = { Icon(item.icono, contentDescription = item.etiqueta) },
                    label = { Text(item.etiqueta) },
                    selected = isSelected,
                    onClick = {
                        if (currentRoute != item.ruta) {
                            navController.navigate(item.ruta) {
                                // Asegura que solo haya una instancia de la pantalla en la pila
                                launchSingleTop = true
                                // Evita acumular historial volviendo a la pantalla principal
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                // Mantiene el estado del LazyColumn al cambiar de pestaña
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        // Usar colores del tema M3
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                )
            }
        }
    }
}