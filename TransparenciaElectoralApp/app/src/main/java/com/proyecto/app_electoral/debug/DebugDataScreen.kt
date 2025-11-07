package com.proyecto.app_electoral.debug

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.viewmodel.DebugViewModel
import com.proyecto.app_electoral.ui.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class) // Necesario para TopAppBar, Card y Scaffold
@Composable
fun DebugDataScreen() {
    // 1. Crear la ViewModelFactory usando remember.
    // Esto resuelve el error de composición y garantiza que la factory solo se cree una vez.
    val factory: ViewModelProvider.Factory = remember { ViewModelFactory.create() }

    // 2. Obtener el ViewModel usando la Factory dentro del contexto Composable.
    val viewModel: DebugViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Prueba de Endpoints de Django", fontWeight = FontWeight.Bold) },
                // Las advertencias de API experimental (como TopAppBarColors) son normales
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                actions = {
                    IconButton(onClick = viewModel::loadAllData, enabled = !uiState.isLoading) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Recargar Datos")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {

            when {
                uiState.isLoading -> {
                    // Indicador de carga
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                uiState.error != null -> {
                    // Mostrar mensaje de error
                    Card(
                        modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEEE))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("¡ERROR DE CONEXIÓN! 🔌", style = MaterialTheme.typography.titleLarge, color = Color.Red)
                            Spacer(Modifier.height(8.dp))
                            Text(uiState.error!!, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(4.dp))
                            Text("Asegúrese de que el servidor esté corriendo en 'http://127.0.0.1:8000' (accedido como 10.0.2.2 desde el emulador).",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
                else -> {
                    // Datos cargados exitosamente
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(top = 8.dp)
                    ) {
                        item {
                            Text("Resumen de Registros Obtenidos", style = MaterialTheme.typography.headlineSmall)
                            Spacer(Modifier.height(8.dp))
                        }

                        // Mostrar el estado de cada endpoint
                        EndpointStatusCard("Candidatos", uiState.candidatos.size, Color(0xFF4CAF50))
                        EndpointStatusCard("Historial Cargos", uiState.historialCargos.size, Color(0xFF2196F3))
                        EndpointStatusCard("Denuncias", uiState.denuncias.size, Color(0xFFFF5722))
                        EndpointStatusCard("Proyectos", uiState.proyectos.size, Color(0xFF9C27B0))
                        EndpointStatusCard("Propuestas", uiState.propuestas.size, Color(0xFFFFC107))

                        item {
                            Spacer(Modifier.height(16.dp))
                            Text("Detalle del primer registro (Candidato):", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            // Muestra el primer candidato para una verificación rápida de la estructura
                            if (uiState.candidatos.isNotEmpty()) {
                                Text(uiState.candidatos.first().toString(), style = MaterialTheme.typography.bodySmall)
                            } else {
                                Text("No hay datos de Candidatos para mostrar.", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EndpointStatusCard(title: String, count: Int, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$title:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = color.copy(alpha = 0.9f)
            )
            Text(
                text = "$count Registros",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
        }
    }
}