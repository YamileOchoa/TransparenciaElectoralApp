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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
// Importa el objeto Screen si ya lo tienes definido para las rutas
// import com.proyecto.app_electoral.ui.navigation.Screen

/**
 * Clase de datos para cada ítem de acceso rápido.
 */
data class QuickAccessItem(
    val title: String,
    val icon: ImageVector,
    val route: String // Ruta de navegación, usa un placeholder por ahora
)

// Definición de los 4 accesos rápidos
private val accessItems = listOf(
    // 1. Buscar Candidato
    QuickAccessItem("Buscar Candidato", Icons.Default.Search, "search_route"),
    // 2. Estadísticas
    QuickAccessItem("Estadísticas", Icons.Default.Assessment, "stats_route"),
    // 3. Comparar
    QuickAccessItem("Comparar", Icons.Default.CompareArrows, "compare_route"),
    // 4. Voto Informado
    QuickAccessItem("Voto Informado", Icons.Default.Description, "vote_info_route")
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
            horizontalArrangement = Arrangement.SpaceBetween // Distribuye el espacio entre ellas
        ) {
            QuickAccessItemCard(item = accessItems[0], navController, Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp)) // Espacio entre tarjetas
            QuickAccessItemCard(item = accessItems[1], navController, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre filas

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
            // Implementa navegación (reemplazar la lambda con la lógica de navegación real)
            .clickable { navController.navigate(item.route) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                tint = Color(0xFF542F96), // Color morado principal
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}