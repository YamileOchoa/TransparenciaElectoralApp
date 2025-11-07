package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proyecto.app_electoral.ui.components.home.AppHeader
// Importamos los componentes de la subcarpeta 'home'
import com.proyecto.app_electoral.ui.components.home.CandidateListItem
import com.proyecto.app_electoral.ui.components.home.FeaturedCandidatesSection
import com.proyecto.app_electoral.ui.components.home.QuickAccessGrid
import com.proyecto.app_electoral.ui.components.home.WelcomeCard
import com.proyecto.app_electoral.ui.viewmodel.HomeViewModel

/**
 * 🗳️ Pantalla principal (Home) del diseño, que orquesta todos los componentes
 * y maneja el estado de los datos del CandidatoRepository.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    // El ViewModel se inyecta automáticamente usando la función compose 'viewModel()'
    viewModel: HomeViewModel = viewModel()
) {
    // Recolecta el estado del flujo de datos (HomeUiState)
    val uiState by viewModel.uiState.collectAsState()

    // --- Lógica de Filtrado y Ordenamiento (Basada en los datos del estado) ---

    // Asunción: Candidatos Destacados son aquellos con más de 500 visitas.
    val featuredCandidates = uiState.candidates.filter { it.visitas > 500 }

    // Los 3 Más Vistos: Ordenados por visitas descendente.
    val mostViewedCandidates = uiState.candidates.sortedByDescending { it.visitas }.take(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            // Fondo de toda la pantalla: #F5F5F5
            .background(Color(0xFFF5F5F5))
    ) {

        // --- 1. Navbar con Degradado ---
        AppHeader()

        // --- 2. Manejo del Estado (Carga / Error / Contenido) ---
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF542F96))
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "¡Error de Conexión!\n${uiState.error}",
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        } else {
            // --- Contenido Principal con Scroll ---
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                // --- A. Tarjeta de Bienvenida ---
                item {
                    WelcomeCard()
                }

                // --- B. Carrusel de Candidatos Destacados ---
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    if (featuredCandidates.isNotEmpty()) {
                        FeaturedCandidatesSection(candidates = featuredCandidates)
                    } else {
                        Text(
                            text = "No hay candidatos destacados disponibles.",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // --- C. Cuadrícula de Accesos Rápidos (2x2) ---
                item {
                    QuickAccessGrid(navController = navController)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // --- D. Los 3 Más Vistos (Título y Lista) ---
                if (mostViewedCandidates.isNotEmpty()) {
                    item {
                        Text(
                            text = "Los 3 Más Vistos",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                        )
                    }

                    items(mostViewedCandidates) { candidate ->
                        CandidateListItem(
                            candidate = candidate,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                // Espacio final
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}