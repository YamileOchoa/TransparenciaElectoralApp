package com.proyecto.app_electoral.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// NOTA: Para usar una fuente personalizada como Inter,
// debe añadir los archivos .ttf a la carpeta 'res/font'
// y definir el 'FontFamily'.

// Usaremos la fuente por defecto de Compose (Roboto/System) para evitar dependencias
// de archivos .ttf si no están incluidos en el proyecto.

// Definición de la Tipografía de Material Design 3
val AppTypography = Typography(

    // Títulos grandes y llamativos (Ej. Título principal de la app)
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),

    // Títulos de secciones principales (Ej. "Candidatos Destacados")
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),

    // Texto de Cards y botones principales (Ej. "Bienvenido a CandidatoInfo")
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Títulos pequeños y texto importante (Ej. Nombres de Candidatos)
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),

    // Texto del cuerpo principal (Ej. Descripciones)
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // Etiquetas de botones y texto pequeño (Ej. Partido Político)
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

    // Otras propiedades de Typography (displaySmall, labelLarge, etc.) se omiten por brevedad
)