package com.proyecto.app_electoral.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape // Importación clave para la forma circular
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.Shape // Importación para manejar la forma

/**
 * 🎨 Define la forma de recorte de la imagen.
 */
enum class ImageShapeType {
    ROUNDED_RECTANGLE,
    CIRCLE
}

/**
 * Componente reutilizable para cargar imágenes usando Coil.
 * Maneja URL nulas o errores con un placeholder y acepta una forma de recorte.
 */
@Composable
fun ImageLoader(
    imageUrl: String?,
    contentDescription: String,
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier,
    // [NUEVO PARÁMETRO] Permite especificar la forma, por defecto es Esquinas Redondeadas
    shapeType: ImageShapeType = ImageShapeType.ROUNDED_RECTANGLE
) {
    val finalUrl = imageUrl ?: "https://placehold.co/1x1.png"

    // 1. Determinar la forma de recorte
    val clipShape: Shape = when (shapeType) {
        ImageShapeType.CIRCLE -> CircleShape
        ImageShapeType.ROUNDED_RECTANGLE -> RoundedCornerShape(8.dp)
    }

    // 2. Definir estilos de placeholder
    val placeholderBackgroundColor = MaterialTheme.colorScheme.surfaceVariant

    // 3. Renderizar AsyncImage
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(finalUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier
            .size(width, height)
            .clip(clipShape), // APLICAR LA FORMA DINÁMICA
        contentScale = ContentScale.Crop,
        error = ColorPainter(placeholderBackgroundColor),
        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceContainerHighest)
    )
}