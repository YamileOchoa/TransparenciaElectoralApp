package com.proyecto.app_electoral.ui.components.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.app_electoral.ui.viewmodel.BusyParty // Importar el modelo de datos

@Composable
fun BarChartPlaceholder(
    // [MODIFICADO] Recibe la lista real de partidos ocupados
    parties: List<BusyParty>,
    maxHeightDp: Dp = 90.dp
) {
    // 1. Encontrar el conteo máximo para normalización
    val maxCount = parties.maxOfOrNull { it.candidateCount } ?: 1

    // Usamos el color primario del tema para las barras
    val primaryColor = MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(maxHeightDp + 24.dp), // Altura total para barras y etiquetas
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        if (parties.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay datos de partidos activos.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            parties.forEach { party ->
                // 2. Calcular altura de la barra basada en el máximo
                val heightFactor = party.candidateCount.toFloat() / maxCount.toFloat()
                val barHeight = maxHeightDp * heightFactor

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f)
                ) {
                    // Texto del conteo sobre la barra (opcional, pero útil)
                    Text(
                        text = party.candidateCount.toString(),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    // Barra
                    Box(
                        modifier = Modifier
                            .width(35.dp)
                            .height(barHeight)
                            // [MODIFICADO] Usamos el color primario del tema
                            .background(primaryColor)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Nombre del partido (puede ser truncado)
                    Text(
                        text = party.partyName,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier.widthIn(max = 50.dp) // Limitar ancho de texto
                    )
                }
            }
        }
    }
}