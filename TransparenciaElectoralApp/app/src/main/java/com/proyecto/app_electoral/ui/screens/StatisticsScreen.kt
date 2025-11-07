package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // NUEVA Importación
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.proyecto.app_electoral.di.Injector // NUEVA Importación
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.statistics.*
import com.proyecto.app_electoral.ui.theme.AppElectoralTheme
import com.proyecto.app_electoral.ui.theme.BackgroundCustom
import com.proyecto.app_electoral.ui.viewmodel.StatsViewModel // NUEVA Importación
import com.proyecto.app_electoral.ui.viewmodel.StatsViewModelFactory // NUEVA Importación

/**
 * 📊 Pantalla principal de estadísticas.
 */
@Composable
fun StatisticsScreen(
    navController: NavController,
    // [MODIFICADO] Inyectamos el StatsViewModel y su Factory
    viewModel: StatsViewModel = viewModel(factory = StatsViewModelFactory(Injector.provideStatsRepository()))
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCustom)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                // Padding inferior para dejar espacio al BottomNavigationBar
                .padding(bottom = 56.dp)
                .verticalScroll(rememberScrollState())
        ) {
            StatsHeader()

            if (uiState.isLoading) {
                // Muestra el indicador de carga si los datos se están obteniendo
                Box(modifier = Modifier.fillMaxSize().padding(top = 64.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (uiState.error != null) {
                // Muestra error si falla la API
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Error al cargar: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                // --- Contenido Real CONECTADO AL VIEWMODEL ---
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        // [CONECTADO] Usa la hora de actualización real
                        "Updated: ${uiState.lastUpdated}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 1. Circular Percentage (Candidatos sin denuncias)
                    StatsCard(
                        title = "Candidatos sin denuncias",
                        // [CONECTADO] Total de candidatos real
                        subtitle = "Basado en ${uiState.denunciaStats.totalCandidates} perfiles"
                    ) {
                        // [CONECTADO] Porcentaje real de candidatos sin denuncias
                        CircularPercentage(percentage = uiState.denunciaStats.percentageClean)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 2. Bar Chart (Partidos más activos)
                    StatsCard(
                        title = "Partidos más activos",
                        subtitle = "Top ${uiState.busyParties.size} por # de candidatos"
                    ) {
                        // [CONECTADO] Pasa la lista real de partidos (BusyParty)
                        BarChartPlaceholder(parties = uiState.busyParties)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 3. Progress Bars (Candidatos más buscados)
                    StatsCard(
                        title = "Candidatos más buscados",
                        subtitle = "Vistas del perfil en la última semana"
                    ) {
                        // [CONECTADO] Pasa la lista real de candidatos más vistos (Pair<String, Float>)
                        ProgressBarPlaceholder(
                            mostViewed = uiState.mostViewed
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 4. Distribution Chart (Distribución de Candidatos)
                    StatsCard(
                        title = "Distribución de Candidatos",
                        subtitle = "Total de candidatos: ${uiState.denunciaStats.totalCandidates}"
                    ) {
                        // [CONECTADO] Pasa el mapa de distribución real
                        DistrictBarChart(distribution = uiState.distribution)
                    }
                }
            }
        }

        // Posiciona la barra de navegación en la parte inferior
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BottomNavigationBar(navController = navController)
        }
    }
}

/**
 * Función de Preview para visualizar el composable sin ejecutar la app completa.
 */
@Preview(showBackground = true, widthDp = 360, heightDp = 1160)
@Composable
fun StatisticsScreenPreview() {
    // Crea un NavController simulado para satisfacer el requisito de la función StatisticsScreen
    val navController = rememberNavController()
    AppElectoralTheme {
        // En Preview, llamamos a la pantalla sin el ViewModel para evitar llamadas a la API
        // El Preview mostrará la pantalla en estado inicial (cargando o datos por defecto si los proveemos).
        StatisticsScreen(navController = navController)
    }
}