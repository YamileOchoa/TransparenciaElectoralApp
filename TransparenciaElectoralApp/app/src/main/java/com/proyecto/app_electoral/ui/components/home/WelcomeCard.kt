package com.proyecto.app_electoral.ui.components.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme // Importación clave
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Tarjeta de bienvenida expandida que contiene el mensaje introductorio.
 */
@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        shape = RoundedCornerShape(12.dp),
        // [MODIFICADO] Usamos el color de Superficie del tema (adapta a blanco/oscuro)
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Bienvenido a CandidatoInfo",
                // [MODIFICADO] Usamos la tipografía del tema
                style = MaterialTheme.typography.titleLarge,
                // [MODIFICADO] Usamos el color de "encima de la superficie"
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Explora, compara y toma una decisión informada.",
                fontSize = 14.sp,
                // [MODIFICADO] Usamos una variante para texto secundario
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}