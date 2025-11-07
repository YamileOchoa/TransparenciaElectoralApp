package com.proyecto.app_electoral.ui.components.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressBarPlaceholder(
    progresses: List<Float> = listOf(0.7f, 0.5f, 0.8f, 0.6f),
    candidateNames: List<String> = listOf("Candidato A", "Candidato B", "Candidato C", "Candidato D")
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (i in progresses.indices) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = candidateNames.getOrNull(i) ?: "Candidato ${i + 1}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(Color.LightGray, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progresses.getOrNull(i) ?: 0f)
                            .background(Color(0xFF542F96), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    }
}
