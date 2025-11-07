package com.proyecto.app_electoral.ui.screens

import com.proyecto.app_electoral.ui.theme.Background
import com.proyecto.app_electoral.ui.theme.TextGray
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.statistics.*

@Composable
fun StatisticsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
                .verticalScroll(rememberScrollState())
        ) {
            StatsHeader()

            Column(modifier = Modifier.padding(16.dp)) {
                Text("Updated: 24/10/2024", color = TextGray, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(16.dp))

                StatsCard(
                    title = "Candidatos sin denuncias",
                    subtitle = "Basado en 1500 perfiles"
                ) {
                    CircularPercentage(percentage = 0.82f)
                }

                Spacer(modifier = Modifier.height(16.dp))

                StatsCard(
                    title = "Partidos más activos",
                    subtitle = "Top 5 por # de candidatos"
                ) {
                    BarChartPlaceholder()
                }

                Spacer(modifier = Modifier.height(16.dp))

                StatsCard(
                    title = "Candidatos más buscados",
                    subtitle = "Vistas del perfil en la última semana"
                ) {
                    ProgressBarPlaceholder(
                        progresses = listOf(0.7f, 0.5f, 0.8f, 0.6f),
                        candidateNames = listOf("Candidato A", "Candidato B", "Candidato C", "Candidato D")
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                StatsCard(
                    title = "Distribución de Candidatos",
                    subtitle = "Total de candidatos: 1500"
                ) {
                    DistrictBarChart()
                }
            }
        }

        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BottomNavigationBar()
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 1160)
@Composable
fun StatisticsScreenPreview() {
    StatisticsScreen()
}
