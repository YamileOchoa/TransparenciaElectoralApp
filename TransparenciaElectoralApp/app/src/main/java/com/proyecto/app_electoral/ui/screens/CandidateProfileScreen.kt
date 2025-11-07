package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.profile.CandidateProfileContent
import com.proyecto.app_electoral.ui.components.profile.CandidateProfileHeader
import com.proyecto.app_electoral.ui.viewmodel.CandidateProfileUiState
import com.proyecto.app_electoral.ui.viewmodel.CandidateProfileViewModel
import com.proyecto.app_electoral.ui.viewmodel.CandidateProfileViewModelFactory

@Composable
fun CandidateProfileScreen(
    candidatoId: Int, // ⬅️ 1. Recibe el ID de la navegación
    onBackClick: () -> Unit = {}
) {
    // ⬅️ 2. OBTENER EL VIEWMODEL USANDO LA FACTORY
    // Simula la inyección de dependencias para el ID y el Repositorio
    val repository: CandidatoRepository = Injector.provideCandidatoRepository()
    val viewModel: CandidateProfileViewModel = viewModel(
        key = candidatoId.toString(), // Clave única para que se cree un ViewModel por cada candidato
        factory = CandidateProfileViewModelFactory(candidatoId, repository)
    )

    // ⬅️ 3. OBSERVAR EL ESTADO DEL VIEWMODEL
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // ⬅️ 4. MANEJO DE ESTADOS (Loading, Success, Error)

        when (uiState) {
            is CandidateProfileUiState.Loading -> {
                // Muestra un indicador de carga en el centro
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is CandidateProfileUiState.Error -> {
                // Muestra un mensaje de error
                val message = (uiState as CandidateProfileUiState.Error).message
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Error al cargar el perfil.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = message, modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
            is CandidateProfileUiState.Success -> {
                val data = (uiState as CandidateProfileUiState.Success).data

                // Si la carga fue exitosa, muestra el Header y el Contenido
                CandidateProfileHeader(
                    candidatoName = data.candidato.nombre, // Usamos el nombre real
                    onBackClick = onBackClick
                )

                // Pasa los datos consolidados al componente de contenido
                CandidateProfileContent(data = data)
            }
        }
    }
}

// Bloque de Preview eliminado/comentado para evitar el error de sintaxis y problemas de Mock.