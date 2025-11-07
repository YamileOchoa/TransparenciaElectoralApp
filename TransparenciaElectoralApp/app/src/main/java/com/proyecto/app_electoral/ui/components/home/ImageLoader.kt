package com.proyecto.app_electoral.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.compose.rememberAsyncImagePainter // Necesario si usa versiones antiguas de Coil
import androidx.compose.runtime.remember // Importar remember
import androidx.compose.ui.graphics.painter.ColorPainter


/**
 * Componente reutilizable para cargar imágenes usando Coil.
 * Maneja URL nulas o errores con un placeholder.
 */
@Composable
fun ImageLoader(
    imageUrl: String?, // Puede ser nulo
    contentDescription: String,
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    // Si la URL es nula, usamos un placeholder genérico
    val finalUrl = imageUrl ?: "https://placehold.co/1x1.png" // URL de placeholder mínima

    // Definimos el Composable que actuará como 'error' y 'placeholder'
    val errorPlaceholder: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .size(width, height)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFCCCCCC)), // Fondo gris
            contentAlignment = Alignment.Center
        ) {
            Text("N/A", color = Color.Black)
        }
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(finalUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier
            .size(width, height)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
        error = ColorPainter(Color(0xFFCCCCCC)), // simple fondo gris como error
        placeholder = ColorPainter(Color.LightGray) // color de carga
    )
}