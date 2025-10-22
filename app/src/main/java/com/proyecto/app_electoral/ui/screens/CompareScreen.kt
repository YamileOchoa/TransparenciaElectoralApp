package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.compare.CandidateSelectionSection
import com.proyecto.app_electoral.ui.components.compare.ComparisonSection
import com.proyecto.app_electoral.ui.components.compare.DenunciasSection
import com.proyecto.app_electoral.ui.components.search.HeaderSection
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModel

@Composable
fun CompareScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "comparar"

    val viewModel: CandidatosViewModel = viewModel(
        factory = Injector.provideViewModelFactory(context = LocalContext.current)
    )
    val candidatos by viewModel.filteredCandidatos.collectAsState(initial = emptyList())

    val candidato1 = candidatos.getOrNull(0)
    val candidato2 = candidatos.getOrNull(1)

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, currentRoute) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .background(Color(0xFFF5F6FA)),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item { HeaderSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                CandidateSelectionSection(
                    candidato1 = candidato1,
                    candidato2 = candidato2
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                ComparisonSection(
                    title = "📋 Propuestas",
                    candidate1Items = candidato1?.propuestas?.map { it.titulo } ?: emptyList(),
                    candidate1Button = "Ver todas (${candidato1?.propuestas?.size ?: 0})",
                    candidate2Items = candidato2?.propuestas?.map { it.titulo } ?: emptyList(),
                    candidate2Button = "Ver todas (${candidato2?.propuestas?.size ?: 0})",
                    hasCandidate2 = candidato2 != null
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                ComparisonSection(
                    title = "🏗️ Historial de Cargos",
                    candidate1Items = candidato1?.historial?.map { it.cargo } ?: emptyList(),
                    candidate1Button = "Ver todos (${candidato1?.historial?.size ?: 0})",
                    candidate2Items = candidato2?.historial?.map { it.cargo } ?: emptyList(),
                    candidate2Button = "Ver todos (${candidato2?.historial?.size ?: 0})",
                    hasCandidate2 = candidato2 != null
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                DenunciasSection(
                    denuncias1 = candidato1?.denuncias ?: emptyList(),
                    denuncias2 = candidato2?.denuncias ?: emptyList()
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Vista de Comparación")
@Composable
fun PreviewCompareScreen() {
    val navController = rememberNavController()
    CompareScreen(navController = navController)
}
