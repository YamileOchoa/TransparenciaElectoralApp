package com.proyecto.app_electoral.ui.components.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme // Importación CRÍTICA
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.proyecto.app_electoral.ui.navigation.Screen

/**
 * Clase de datos para cada ítem de acceso rápido.
 */
data class QuickAccessItem(
    val title: String,
    val icon: ImageVector,
    val route: String // Ruta de navegación, usa un placeholder por ahora
)

// Definición de los 4 accesos rápidos (Sin cambios, es data)
private val accessItems = listOf(
    QuickAccessItem("Buscar Candidato", Icons.Default.Search, Screen.Search.route),
    QuickAccessItem("Estadísticas", Icons.Default.Assessment, Screen.Stats.route),
    QuickAccessItem("Comparar", Icons.Default.CompareArrows, Screen.Compare.route),
    QuickAccessItem("Voto Informado", Icons.Default.Description, Screen.Search.route)
)

/**
 * Cuadrícula 2x2 de tarjetas de acceso rápido.
 */
@Composable
fun QuickAccessGrid(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Fila 1 (Ítem 1 y 2)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickAccessItemCard(item = accessItems[0], navController, Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            QuickAccessItemCard(item = accessItems[1], navController, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fila 2 (Ítem 3 y 4)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickAccessItemCard(item = accessItems[2], navController, Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            QuickAccessItemCard(item = accessItems[3], navController, Modifier.weight(1f))
        }
    }
}

@Composable
fun QuickAccessItemCard(item: QuickAccessItem, navController: NavController, modifier: Modifier) {
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable {
                navController.navigate(item.route) {
                    launchSingleTop = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    restoreState = true
                }
            },
        shape = RoundedCornerShape(12.dp),
        // [MODIFICADO] Usamos el color de Superficie del tema (Color.White en modo claro)
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                // [MODIFICADO] Usamos el color Primario del tema (el morado principal)
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                // [MODIFICADO] Usamos la tipografía del tema (labelLarge es ideal para botones/etiquetas)
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                // El color del texto será automáticamente MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
