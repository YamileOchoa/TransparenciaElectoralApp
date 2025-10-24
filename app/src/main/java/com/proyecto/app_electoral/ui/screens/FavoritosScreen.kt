package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.search.CandidateListSection
import com.proyecto.app_electoral.ui.components.search.HeaderSection
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModel

@Composable
fun FavoritosScreen(navController: NavHostController, onCandidateClick: (Int) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "favoritos"

    val viewModel: CandidatosViewModel = viewModel(
        factory = Injector.provideViewModelFactory(context = LocalContext.current)
    )

    val favoritos by viewModel.favoritos.collectAsState()
    val candidatosFavoritos by viewModel.candidatosFavoritos.collectAsState(initial = emptyList())

    Scaffold(
        containerColor = Color(0xFFFAFAFA),
        bottomBar = { BottomNavigationBar(navController, currentRoute) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item { HeaderSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            if (candidatosFavoritos.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(bottom = 80.dp), // Compensar el espacio de la barra de navegación
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No se marcó a ningún candidato como favorito",
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                item {
                    CandidateListSection(
                        candidatos = candidatosFavoritos,
                        onCandidateClick = onCandidateClick,
                        favoritos = favoritos,
                        onToggleFavorito = viewModel::toggleFavorito
                    )
                }
            }
        }
    }
}
