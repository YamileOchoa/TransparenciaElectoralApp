package com.proyecto.app_electoral

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.proyecto.app_electoral.data.repository.JsonRepository
import com.proyecto.app_electoral.ui.theme.AppelectoralTheme
import com.proyecto.app_electoral.ui.screens.CandidatoTestScreen

// En MainActivity.kt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppelectoralTheme {
                // LLAMAR AQUÍ:
                CandidatoTestScreen()
            }
        }
    }
}
