package com.proyecto.app_electoral.ui.components.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.ui.components.home.ImageLoader // Importamos la utilidad ImageLoader

/**
 * Tarjeta horizontal para mostrar un candidato (usada en Los 3 Más Vistos).
 * Ocupa casi todo el ancho del móvil.
 */
@Composable
fun CandidateListItem(candidate: Candidato, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { /* Acción de navegación a detalle del candidato */ },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp) // Sombra suave
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto (usamos la utilidad)
            ImageLoader(
                imageUrl = candidate.foto_url,
                contentDescription = "Foto de ${candidate.nombre}",
                width = 60.dp,
                height = 60.dp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                // Nombre en negrita
                Text(
                    text = candidate.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Partido Político como gris suave
                Text(
                    text = candidate.partido,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}