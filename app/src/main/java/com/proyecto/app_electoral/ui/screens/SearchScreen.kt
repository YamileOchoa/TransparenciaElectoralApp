package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SearchScreen(navController: NavController) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Pantalla de Búsqueda")

            Button(onClick = { navController.navigate("detalle") }) {
                Text("Ir a Detalle")
            }
            Button(onClick = { navController.navigate("comparar") }) {
                Text("Ir a Comparar")
            }
            Button(onClick = { navController.navigate("inicio") }) {
                Text("Volver a Inicio")
            }
        }
    }
}