package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.app_electoral.ui.viewmodel.CandidatoUiState
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModel
import com.proyecto.app_electoral.ui.viewmodel.ViewModelFactory

// Usaremos la función create() de su ViewModelFactory
@Composable
private fun createCandidatosViewModelFactory(): ViewModelProvider.Factory {
    return remember { ViewModelFactory.create() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidatoTestScreen() {
    // 1. Obtener el ViewModel usando su Factory y el remember
    val factory = createCandidatosViewModelFactory()
    val viewModel: CandidatosViewModel = viewModel(factory = factory)

    // 2. Observar el estado de la UI
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Prueba de Candidatos", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when (val state = uiState) {
                is CandidatoUiState.Loading -> {
                    // Muestra el indicador de carga
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is CandidatoUiState.Error -> {
                    // Muestra el error
                    Text(
                        text = "Error al cargar candidatos:\n${state.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CandidatoUiState.Success -> {
                    // Muestra la lista de candidatos
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        item {
                            Text(
                                text = "✅ ${state.candidatos.size} Candidatos Cargados",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                        items(state.candidatos) { candidato ->
                            // Muestra solo el nombre y el partido de cada candidato
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = candidato.nombre,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "Partido: ${candidato.partido}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    // Muestra el ID para confirmar que se está deserializando
                                    Text(
                                        text = "ID: ${candidato.id}",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}