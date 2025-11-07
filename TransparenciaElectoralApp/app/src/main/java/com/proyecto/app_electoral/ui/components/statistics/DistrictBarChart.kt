package com.proyecto.app_electoral.ui.components.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun DistrictBarChart(
    // [MODIFICADO] Recibe el mapa de distribución (Región -> Conteo)
    distribution: Map<String, Int>,
    maxHeightDp: Dp = 100.dp
) {
    // 1. Convertir el mapa a una lista de pares para iterar
    val dataList = distribution.toList().sortedByDescending { it.second }

    // 2. Encontrar el conteo máximo para normalización
    val maxCount = dataList.maxOfOrNull { it.second } ?: 1

    // Usamos el color de acento del tema (por ejemplo, secondary)
    val accentColor = MaterialTheme.colorScheme.secondary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(maxHeightDp + 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        if (dataList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay datos de distribución por región.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            // Iterar sobre los datos reales
            dataList.forEach { (regionName, count) ->
                // 3. Calcular altura de la barra
                val heightFactor = count.toFloat() / maxCount.toFloat()
                val barHeight = maxHeightDp * heightFactor

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f)
                ) {
                    // Texto del conteo
                    Text(
                        text = count.toString(),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    // Barra
                    Box(
                        modifier = Modifier
                            .width(28.dp)
                            .height(barHeight)
                            // [MODIFICADO] Usamos el color de acento del tema
                            .background(accentColor)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Nombre de la región (truncado o abreviado)
                    Text(
                        text = regionName.take(3).uppercase(Locale.ROOT), // Muestra las primeras 3 letras
                        fontSize = 10.sp,
                        maxLines = 1,
                        modifier = Modifier.widthIn(max = 40.dp) // Limitar ancho de texto
                    )
                }
            }
        }
    }
}