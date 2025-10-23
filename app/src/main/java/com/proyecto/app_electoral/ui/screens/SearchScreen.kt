package com.proyecto.app_electoral.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.search.CandidateListSection
import com.proyecto.app_electoral.ui.components.search.FilterChipsSection
import com.proyecto.app_electoral.ui.components.search.HeaderSection
import com.proyecto.app_electoral.ui.components.search.MostSearchedSection
import com.proyecto.app_electoral.ui.components.search.SearchBarSection
import com.proyecto.app_electoral.ui.components.search.StatsPanel
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModel

@Composable
fun SearchScreen(navController: NavHostController, onCandidateClick: (Int) -> Unit) {
    val TAG = "SearchScreen"
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "busqueda"

    val viewModel: CandidatosViewModel = viewModel(
        factory = Injector.provideViewModelFactory(context = LocalContext.current)
    )

    val totalCandidatos by viewModel.totalCandidatos.collectAsState()
    val totalPropuestas by viewModel.totalPropuestas.collectAsState()
    val filteredCandidatos by viewModel.filteredCandidatos.collectAsState(initial = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsState()
    val favoritos by viewModel.favoritos.collectAsState()

    Log.d(TAG, "Candidatos en la UI: ${filteredCandidatos.size}")

    Scaffold(
        containerColor = Color(0xFFFAFAFA),
        bottomBar = { BottomNavigationBar(navController, currentRoute) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item { HeaderSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                StatsPanel(
                    totalCandidatos = totalCandidatos,
                    totalPropuestas = totalPropuestas
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                SearchBarSection(
                    query = searchQuery,
                    onQueryChange = viewModel::onSearchQueryChange
                )
            }
            item { FilterChipsSection(viewModel = viewModel) }
            item { MostSearchedSection(viewModel = viewModel) }
            item {
                CandidateListSection(
                    candidatos = filteredCandidatos,
                    onCandidateClick = onCandidateClick,
                    favoritos = favoritos,
                    onToggleFavorito = viewModel::toggleFavorito
                )
            }
        }
    }
}

