package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.proyecto.app_electoral.data.model.Candidato
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.compare.CandidateSelectionSection
import com.proyecto.app_electoral.ui.components.compare.ComparisonSection
import com.proyecto.app_electoral.ui.components.compare.DenunciasSection
import com.proyecto.app_electoral.ui.components.search.HeaderSection
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModel

@Composable
fun CompareScreen(navController: NavHostController) {
    var candidato1Selected by remember { mutableStateOf<Candidato?>(null) }
    var candidato2Selected by remember { mutableStateOf<Candidato?>(null) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "comparar"

    val viewModel: CandidatosViewModel = viewModel(
        factory = Injector.provideViewModelFactory(context = LocalContext.current)
    )

    val candidatos by viewModel.filteredCandidatos.collectAsState(initial = emptyList())

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
                    candidatos = candidatos,
                    candidato1 = candidato1Selected,
                    candidato2 = candidato2Selected,
                    onSelectCandidato1 = { candidato1Selected = it },
                    onSelectCandidato2 = { candidato2Selected = it }
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                ComparisonSection(
                    title = "📋 Propuestas",
                    candidate1Items = candidato1Selected?.propuestas?.map { it.titulo } ?: emptyList(),
                    candidate1Button = "Ver todas (${candidato1Selected?.propuestas?.size ?: 0})",
                    candidate2Items = candidato2Selected?.propuestas?.map { it.titulo } ?: emptyList(),
                    candidate2Button = "Ver todas (${candidato2Selected?.propuestas?.size ?: 0})",
                    hasCandidate2 = candidato2Selected != null
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                ComparisonSection(
                    title = "🏗️ Historial de Cargos",
                    candidate1Items = candidato1Selected?.historial?.map { it.cargo } ?: emptyList(),
                    candidate1Button = "Ver todos (${candidato1Selected?.historial?.size ?: 0})",
                    candidate2Items = candidato2Selected?.historial?.map { it.cargo } ?: emptyList(),
                    candidate2Button = "Ver todos (${candidato2Selected?.historial?.size ?: 0})",
                    hasCandidate2 = candidato2Selected != null
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                DenunciasSection(
                    denuncias1 = candidato1Selected?.denuncias ?: emptyList(),
                    denuncias2 = candidato2Selected?.denuncias ?: emptyList()
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
