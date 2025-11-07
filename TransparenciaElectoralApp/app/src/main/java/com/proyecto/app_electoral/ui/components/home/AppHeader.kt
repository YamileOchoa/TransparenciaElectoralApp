package com.proyecto.app_electoral.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppHeader() {
    val startColor = Color(0xFF2E155C)
    val endColor   = Color(0xFF542F96)

    // Caja exterior pinta el degradado y llega hasta arriba (detrás de status bar)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.horizontalGradient(listOf(startColor, endColor)))
    ) {
        // Caja interior aplica statusBarsPadding y la altura de la AppBar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()   // mueve el contenido debajo de la barra de estado
                .height(56.dp)         // altura estándar de app bar
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Candidato Info",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
