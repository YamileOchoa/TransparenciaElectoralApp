package com.proyecto.app_electoral

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.proyecto.app_electoral.ui.navigation.AppNavigation
import com.proyecto.app_electoral.ui.theme.AppElectoralTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Permitir dibujar edge-to-edge (detrás de status/navigation bars)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // Opcional: hacer status bar transparente (si quieres ver el degradado debajo)
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        super.onCreate(savedInstanceState)
        setContent {
            AppElectoralTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
