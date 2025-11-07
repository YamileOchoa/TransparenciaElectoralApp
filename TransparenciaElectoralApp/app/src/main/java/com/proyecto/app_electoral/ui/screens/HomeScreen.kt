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
import androidx.compose.material3.MaterialTheme // Importación CRÍTICA
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proyecto.app_electoral.ui.components.home.AppHeader
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
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // --- Lógica de Filtrado y Ordenamiento ---
    val featuredCandidates = uiState.candidates.filter { it.visitas > 500 }
    val mostViewedCandidates = uiState.candidates.sortedByDescending { it.visitas }.take(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            // [MODIFICADO] Usamos el color de fondo definido en Theme.kt (#F5F5F5 en modo claro)
            .background(MaterialTheme.colorScheme.background)
    ) {

        // --- 1. Navbar con Degradado ---
        AppHeader()

        // --- 2. Manejo del Estado (Carga / Error / Contenido) ---
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // [MODIFICADO] Usamos el color Primario del tema para el indicador de carga
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
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
                    // [MODIFICADO] Usamos el color de Error del tema
                    color = MaterialTheme.colorScheme.error,
                    // [MODIFICADO] Usamos la tipografía del tema
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        } else {
            // --- Contenido Principal con Scroll ---
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                // --- A. Tarjeta de Bienvenida ---
                item { WelcomeCard() }

                // --- B. Carrusel de Candidatos Destacados ---
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    if (featuredCandidates.isNotEmpty()) {
                        FeaturedCandidatesSection(candidates = featuredCandidates)
                    } else {
                        Text(
                            text = "No hay candidatos destacados disponibles.",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            // [MODIFICADO] Usamos el color para texto secundario
                            color = MaterialTheme.colorScheme.onBackground
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
                            // [MODIFICADO] Usamos la tipografía del tema para el título de sección
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground,
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