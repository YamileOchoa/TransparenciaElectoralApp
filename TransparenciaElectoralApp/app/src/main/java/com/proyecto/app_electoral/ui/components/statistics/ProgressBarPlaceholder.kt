package com.proyecto.app_electoral.ui.components.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ProgressBarPlaceholder(
    // [MODIFICADO] Recibe una lista de pares (Nombre del Candidato, Progreso 0.0-1.0)
    mostViewed: List<Pair<String, Float>>
) {
    // Usamos el color primario y el color de fondo del tema
    val primaryColor = MaterialTheme.colorScheme.primary
    val trackColor = MaterialTheme.colorScheme.surfaceVariant // Color para el fondo de la barra

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (mostViewed.isEmpty()) {
            Text("No hay datos de candidatos más buscados.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        } else {
            // Iterar sobre los datos reales
            mostViewed.forEach { (name, progress) ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = name,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface // Color de texto primario
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            // Fondo de la barra (Track)
                            .background(trackColor, shape = RoundedCornerShape(8.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                // [CONECTADO] Usamos el progreso real
                                .fillMaxWidth(progress)
                                // [MODIFICADO] Usamos el color primario del tema
                                .background(primaryColor, shape = RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(8.dp)) // Recorte final para asegurar el borde
                        )
                    }
                }
            }
        }
    }
}