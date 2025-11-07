package com.proyecto.app_electoral.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// --- Dark color scheme ---
private val DarkColorScheme = darkColorScheme(
    primary = PurplePrimary,
    secondary = PurpleSecondary,
    tertiary = PurpleSecondary, // puedes cambiar a otro color si quieres
    background = Background,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onBackground = TextGray,
    onSurface = TextGray
)

// --- Light color scheme ---
private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    secondary = PurpleSecondary,
    tertiary = PurpleSecondary, // puedes cambiar a otro color si quieres
    background = Background,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onBackground = TextGray,
    onSurface = TextGray
)

@Composable
fun AppelectoralTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
