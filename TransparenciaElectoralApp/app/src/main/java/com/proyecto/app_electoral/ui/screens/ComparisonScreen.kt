package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.comparison.ComparisonHeader
import com.proyecto.app_electoral.ui.components.comparison.ComparisonTabs
import com.proyecto.app_electoral.ui.components.comparison.ExperienceContent
import com.proyecto.app_electoral.ui.components.comparison.ProposalContent // Importación necesaria para el siguiente paso
import com.proyecto.app_electoral.ui.navigation.Screen // Importación necesaria para la navegación
import com.proyecto.app_electoral.ui.viewmodel.ComparisonViewModel
import com.proyecto.app_electoral.ui.viewmodel.ComparisonViewModelFactory

// ------------------------------------------------------------------
// DATA/ENUMS (Necesarios para la UI)
// ------------------------------------------------------------------

enum class ComparisonTab {
    EXPERIENCE, PROPOSALS, POPULARITY
}

// -------------------------------------------------------------

@Composable
fun ComparisonScreen(
    navController: NavController,
    candidatoId1: Int?,
    candidatoId2: Int?,
    modifier: Modifier = Modifier // <<-- AÑADIDO: Recibir el modifier de AppNavigation
) {
    // Inyección del ViewModel usando la Factory
    val repository = Injector.provideCandidatoRepository()
    val viewModel: ComparisonViewModel = viewModel(
        factory = ComparisonViewModelFactory(repository)
    )

    // Observación del estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    var selectedTab by remember { mutableStateOf(ComparisonTab.EXPERIENCE) }

    // Función que se ejecuta cuando se hace clic en una tarjeta vacía
    val onSelectCandidate: (position: Int) -> Unit = { position ->
        // Obtenemos el ID del candidato opuesto (el que NO se está cambiando)
        val existingId = if (position == 1) candidatoId2 else candidatoId1

        // Navegamos a la pantalla de búsqueda (Screen.Search)
        // Usamos los IDs actuales y agregamos el parámetro 'position' (1 o 2)
        navController.navigate(
            // Asumo que tu ruta de búsqueda debe modificarse para aceptar argumentos
            // Por ahora, solo navegamos, pero el siguiente paso será corregir la ruta Search.
            Screen.Search.route
        )
        // NOTA: Para completar esta funcionalidad, necesitaremos modificar Screen.Search
        // para que acepte argumentos (ID1, ID2, POSITION) en un futuro paso.
    }


    // 2. Cargar datos al iniciar la pantalla
    LaunchedEffect(candidatoId1, candidatoId2) {
        viewModel.loadComparisonData(candidatoId1, candidatoId2)
    }

    Scaffold(
        // Usamos los candidatos del estado para el header
        topBar = {
            ComparisonHeader(
                navController = navController,
                candidato1 = uiState.candidate1,
                candidato2 = uiState.candidate2,
                onSelectCandidate = onSelectCandidate // <<-- CORRECCIÓN 1: Pasar el callback
            )
        }
    ) { paddingValues ->

        Column(
            modifier = modifier // <<-- Aplicar el modifier aquí
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {

            // 3. Manejo de estados (Carga / Error / Contenido)
            if (uiState.isLoading) {
                LoadingView()
            } else if (uiState.error != null) {
                ErrorView(uiState.error!!)
            } else {
                // Contenido principal de la comparación
                ComparisonTabs(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                when (selectedTab) {
                    ComparisonTab.EXPERIENCE -> ExperienceContent(
                        profile1 = uiState.candidate1Profile,
                        profile2 = uiState.candidate2Profile
                    )
                    // Integración de ProposalContent (asumiendo que ya fue importado)
                    ComparisonTab.PROPOSALS -> ProposalContent(
                        profile1 = uiState.candidate1Profile,
                        profile2 = uiState.candidate2Profile
                    )
                    ComparisonTab.POPULARITY -> PlaceholderContent("Popularidad")
                }
            }
        }
    }
}

// Componente Placeholder simple para las pestañas no implementadas
@Composable
fun PlaceholderContent(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Contenido de $title (PENDIENTE)",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Text("Cargando datos de comparación...")
        }
    }
}

@Composable
fun ErrorView(errorMessage: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Error: $errorMessage",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(32.dp)
        )
    }
}