package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Pantalla de Inicio")

            Button(onClick = { navController.navigate("busqueda") }) {
                Text("Ir a Búsqueda")
            }
        }
    }
}

