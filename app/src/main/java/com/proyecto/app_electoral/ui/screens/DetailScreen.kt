package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.profile.InfoCard
import com.proyecto.app_electoral.ui.components.profile.ProfileCard
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, candidatoId: Int) {
    val viewModel: CandidatosViewModel = viewModel(
        factory = Injector.provideViewModelFactory(context = LocalContext.current)
    )

    LaunchedEffect(candidatoId) {
        viewModel.getCandidatoById(candidatoId)
    }

    val candidato by viewModel.selectedCandidato.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del Candidato") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1a237e), titleContentColor = Color.White, navigationIconContentColor = Color.White)
            )
        }
    ) { padding ->
        candidato?.let { cand ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF5F6FA)),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { ProfileCard(candidato = cand) }

                item { 
                    InfoCard(title = "Biografía", content = cand.biografia)
                }

                item { 
                     InfoCard(
                        title = "Propuestas (${cand.propuestas?.size ?: 0})", 
                        content = cand.propuestas?.joinToString(separator = "\n") { "• ${it.titulo}" } ?: "Sin propuestas registradas."
                    )
                }

                item { 
                    InfoCard(
                        title = "Historial de Cargos (${cand.historial?.size ?: 0})", 
                        content = cand.historial?.joinToString(separator = "\n") { "• ${it.cargo} (${it.institucion})" } ?: "Sin historial registrado."
                    )
                }

                item { 
                    InfoCard(
                        title = "Denuncias (${cand.denuncias?.size ?: 0})", 
                        content = cand.denuncias?.joinToString(separator = "\n") { "• ${it.titulo} (${it.delito})" } ?: "Sin denuncias registradas."
                    )
                }
                
                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }
}
