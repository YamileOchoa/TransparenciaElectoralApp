package com.proyecto.app_electoral.ui.components.search


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CandidateProgressBar(
    name: String,
    progress: Float,
    progressColor: Color = Color(0xFF542F96),
    backgroundColor: Color = Color.LightGray,
    maxWidthDp: Dp = 170.dp
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = name, fontSize = 14.sp, color = Color.Black)
        Box(
            modifier = Modifier
                .width(maxWidthDp)
                .height(6.dp)
                .background(backgroundColor, shape = RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(progressColor, shape = RoundedCornerShape(4.dp))
            )
        }
    }
}