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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.statistics.*
import com.proyecto.app_electoral.ui.theme.AppElectoralTheme
import com.proyecto.app_electoral.ui.theme.BackgroundCustom
import com.proyecto.app_electoral.ui.viewmodel.StatsViewModel
import com.proyecto.app_electoral.ui.viewmodel.StatsViewModelFactory

/**
 * 📊 Pantalla principal de estadísticas.
 */
@Composable
fun StatisticsScreen(
    navController: NavController,
    modifier: Modifier = Modifier, // <<-- CORRECCIÓN 1: AÑADIR MODIFIER
    // [MODIFICADO] Inyectamos el StatsViewModel y su Factory
    viewModel: StatsViewModel = viewModel(factory = StatsViewModelFactory(Injector.provideStatsRepository()))
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        // Aplicamos el modifier que viene del AppNavigation
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundCustom)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                // Se mantiene el padding inferior para manejar el scroll correctamente
                // ya que el paddingValues del Scaffold se aplica al padre en AppNavigation
                .padding(bottom = 0.dp)
                .verticalScroll(rememberScrollState())
        ) {
            StatsHeader()

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize().padding(top = 64.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (uiState.error != null) {
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
                        "Updated: ${uiState.lastUpdated}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    StatsCard(
                        title = "Candidatos sin denuncias",
                        subtitle = "Basado en ${uiState.denunciaStats.totalCandidates} perfiles"
                    ) {
                        CircularPercentage(percentage = uiState.denunciaStats.percentageClean)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    StatsCard(
                        title = "Partidos más activos",
                        subtitle = "Top ${uiState.busyParties.size} por # de candidatos"
                    ) {
                        BarChartPlaceholder(parties = uiState.busyParties)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    StatsCard(
                        title = "Candidatos más buscados",
                        subtitle = "Vistas del perfil en la última semana"
                    ) {
                        ProgressBarPlaceholder(
                            mostViewed = uiState.mostViewed
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    StatsCard(
                        title = "Distribución de Candidatos",
                        subtitle = "Total de candidatos: ${uiState.denunciaStats.totalCandidates}"
                    ) {
                        DistrictBarChart(distribution = uiState.distribution)
                    }
                }
            }
        }

        // <<-- CORRECCIÓN 2: ELIMINADO BottomNavigationBar REDUNDANTE >>
    }
}

/**
 * Función de Preview para visualizar el composable sin ejecutar la app completa.
 */
@Preview(showBackground = true, widthDp = 360, heightDp = 1160)
@Composable
fun StatisticsScreenPreview() {
    val navController = rememberNavController()
    AppElectoralTheme {
        StatisticsScreen(navController = navController)
    }
}