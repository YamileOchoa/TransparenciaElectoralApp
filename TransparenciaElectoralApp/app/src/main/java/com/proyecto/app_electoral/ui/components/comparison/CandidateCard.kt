package com.proyecto.app_electoral.ui.components.comparison

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.ui.components.home.ImageLoader
import com.proyecto.app_electoral.ui.components.home.ImageShapeType

/**
 * Tarjeta individual del candidato o placeholder para seleccionar.
 *
 * @param onCardClick Acción a ejecutar cuando se hace clic en la tarjeta.
 */
@Composable
fun CandidateCard(
    candidato: Candidato?,
    isSelected: Boolean,
    onCardClick: () -> Unit, // <<-- NUEVO: Acción de clic
    modifier: Modifier
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val cardColor = if (candidato != null) Color(0xFF6A1B9A) else Color(0xFF6A1B9A).copy(alpha = 0.5f)

    Box(
        modifier = modifier
            .height(130.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(cardColor)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onCardClick), // <<-- Aplicamos la acción de clic
        contentAlignment = Alignment.Center
    ) {
        if (candidato != null) {
            // Contenido del candidato (se mantiene igual)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ImageLoader(
                    imageUrl = candidato.foto_url,
                    contentDescription = candidato.nombre,
                    width = 60.dp,
                    height = 60.dp,
                    shapeType = ImageShapeType.CIRCLE
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = candidato.nombre,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = candidato.partido,
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        } else {
            // Contenido del Placeholder (se mantiene igual)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Seleccionar candidato",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Seleccionar candidato",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}