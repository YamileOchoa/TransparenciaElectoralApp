package com.proyecto.app_electoral.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Navbar superior con degradado (#2E155C a #542F96) y título centrado "Candidato Info".
 */
@Composable
fun AppHeader() {
    // Colores del degradado
    val startColor = Color(0xFF2E155C) // El color oscuro
    val endColor = Color(0xFF542F96)   // El color claro

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Altura estándar para una AppBar
            // Aplicación del degradado horizontal
            .background(Brush.horizontalGradient(listOf(startColor, endColor)))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center // Centra el contenido (el texto)
    ) {
        Text(
            text = "Candidato Info",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}