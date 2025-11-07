package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.viewmodel.CandidatoUiState
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModel
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModelFactory

/**
 * Muestra la lista de candidatos para la pestaña "Candidatos" y maneja la navegación al perfil.
 *
 * @param onCandidatoClick Función lambda que se ejecuta al hacer clic, debe recibir el candidatoId.
 */
@Composable
fun CandidatosScreen(
    onCandidatoClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // 1. Inyección y Observación del ViewModel
    val repository: CandidatoRepository = Injector.provideCandidatoRepository()
    val viewModel: CandidatosViewModel = viewModel(
        factory = CandidatosViewModelFactory(repository)
    )
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is CandidatoUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is CandidatoUiState.Error -> {
                Text(
                    text = "Error al cargar candidatos: ${(uiState as CandidatoUiState.Error).message}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            is CandidatoUiState.Success -> {
                val candidatos = (uiState as CandidatoUiState.Success).candidatos
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(candidatos) { candidato ->
                        CandidatoListItem(
                            candidato = candidato,
                            onClick = { onCandidatoClick(candidato.id) } // ⬅️ Acción de Clic
                        )
                    }
                }
            }
        }
    }
}

/**
 * Item clickeable para cada candidato en la lista.
 */
@Composable
fun CandidatoListItem(
    candidato: Candidato,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick), // ⬅️ Hace el item clickeable
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Aquí iría la foto del candidato (simplificado por ahora)
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .aspectRatio(1f)
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = candidato.partido.take(2),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = candidato.nombre,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = candidato.partido,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}