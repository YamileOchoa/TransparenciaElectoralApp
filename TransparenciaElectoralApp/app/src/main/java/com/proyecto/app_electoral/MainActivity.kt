package com.proyecto.app_electoral

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.proyecto.app_electoral.ui.navigation.AppNavigation // Importamos el NavHost principal
import com.proyecto.app_electoral.ui.theme.AppElectoralTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Asegúrese de usar el nombre correcto de su tema (usé 'TransparenciaElectoralAppTheme' del ejemplo anterior)
            AppElectoralTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 🎉 ¡Iniciamos la navegación aquí!
                    // La AppNavigation contiene el NavHost que inicia en SplashScreen.
                    AppNavigation()
                }
            }
        }
    }
}


