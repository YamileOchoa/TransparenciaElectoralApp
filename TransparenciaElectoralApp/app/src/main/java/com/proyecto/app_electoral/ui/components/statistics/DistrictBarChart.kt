package com.proyecto.app_electoral.ui.components.statistics


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DistrictBarChart(
    districts: List<String> = listOf("Lima", "Arequipa", "La Libertad", "Piura"),
    maxHeightDp: Dp = 100.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(maxHeightDp + 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        val barCount = districts.size
        repeat(barCount) { index ->
            val heightFactor = 1f - index * (0.7f / (barCount - 1))
            val barHeight = maxHeightDp * heightFactor

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .width(28.dp)
                        .height(barHeight)
                        .background(Color(0xFF542F96))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = districts[index], fontSize = 12.sp)
            }
        }
    }
}
