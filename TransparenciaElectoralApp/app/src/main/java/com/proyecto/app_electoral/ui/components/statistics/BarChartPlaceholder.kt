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
fun BarChartPlaceholder(
    barCount: Int = 5,
    maxHeightDp: Dp = 90.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(maxHeightDp + 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
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
                        .width(35.dp)
                        .height(barHeight)
                        .background(Color(0xFF542F96))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "P${index + 1}", fontSize = 12.sp)
            }
        }
    }
}
