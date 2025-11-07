package com.proyecto.app_electoral.ui.components.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme // Importación clave
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.ui.components.home.ImageLoader

/**
 * Sección que muestra el título "Candidatos Destacados" y un carrusel horizontal.
 */
@Composable
fun FeaturedCandidatesSection(candidates: List<Candidato>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Candidatos Destacados",
            // [MODIFICADO] Usamos headlineMedium o titleLarge para títulos de sección
            style = MaterialTheme.typography.headlineMedium,
            // Aseguramos el color de contraste
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Espacio entre tarjetas
        ) {
            items(candidates) { candidate ->
                CandidateCarouselItem(candidate = candidate)
            }
        }
    }
}

/**
 * Diseño individual de la tarjeta para el carrusel (168dp ancho, 190dp alto).
 */
@Composable
fun CandidateCarouselItem(candidate: Candidato) {
    Column(
        modifier = Modifier
            .width(168.dp)
            .clickable { /* Acción de navegación a detalle del candidato */ }
            .padding(bottom = 8.dp)
    ) {
        // Foto de 168dp x 190dp
        ImageLoader(
            imageUrl = candidate.foto_url,
            contentDescription = "Foto de ${candidate.nombre}",
            width = 168.dp,
            height = 190.dp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = candidate.nombre,
            // [MODIFICADO] Usamos titleMedium para el nombre
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis // Para manejar nombres largos
        )
        Text(
            text = candidate.partido, // Campo partido
            // [MODIFICADO] Usamos labelSmall o bodySmall para información secundaria
            style = MaterialTheme.typography.bodySmall,
            // Usamos onSurfaceVariant para texto secundario grisáceo
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}