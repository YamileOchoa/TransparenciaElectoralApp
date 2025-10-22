package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.proyecto.app_electoral.data.model.*
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.profile.InfoCard
import com.proyecto.app_electoral.ui.components.profile.ProfileCard
import com.proyecto.app_electoral.ui.components.profile.ProfileHeader
import com.proyecto.app_electoral.ui.components.profile.SocialMediaCard
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModel

@Composable
fun DetailScreen(
    navController: NavHostController,
    candidateId: Int
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "detalle"

    val viewModel: CandidatosViewModel = viewModel(
        factory = Injector.provideViewModelFactory(context = LocalContext.current)
    )

    LaunchedEffect(candidateId) {
        viewModel.getCandidatoById(candidateId)
    }

    val candidato by viewModel.selectedCandidato.collectAsState()

    Scaffold(
        topBar = { ProfileHeader(title = "Perfil de Candidato", onBack = { navController.popBackStack() }) },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = currentRoute)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF7FFF7)),
            contentAlignment = Alignment.Center
        ) {
            candidato?.let {
                DetailContent(it)
            } ?: CircularProgressIndicator()
        }
    }
}

@Composable
fun DetailContent(candidato: Candidato) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp)
    ) {
        item { ProfileCard(candidato) }

        item {
            InfoCard("Biografía", candidato.biografia)
        }

        if (candidato.propuestas.isNotEmpty()) {
            item {
                InfoCard(
                    "Propuestas",
                    candidato.propuestas.joinToString("\n\n") {
                        "• ${it.titulo}\nCategoría: ${it.categoria}\nPrioridad: ${it.prioridad}\n${it.descripcion}"
                    }
                )
            }
        }

        if (candidato.historial.isNotEmpty()) {
            item {
                InfoCard(
                    "Historial Político",
                    candidato.historial.joinToString("\n\n") {
                        "• ${it.cargo} - ${it.institucion}\n(${it.fecha_inicio} - ${it.fecha_fin})\n${it.descripcion}"
                    }
                )
            }
        }

        if (candidato.denuncias.isNotEmpty()) {
            item {
                InfoCard(
                    "Denuncias",
                    candidato.denuncias.joinToString("\n\n") {
                        "• ${it.titulo} (${it.estado})\nDelito: ${it.delito}\nFecha: ${it.fecha_denuncia}\n${it.descripcion}\nFuente: ${it.fuente_url}\nExpediente: ${it.expediente}"
                    }
                )
            }
        }

        item {
            InfoCard(
                "Datos Personales",
                """
                DNI: ${candidato.dni}
                Nacimiento: ${candidato.nacimiento}
                Región: ${candidato.region}
                Experiencia: ${candidato.experiencia} años
                Estado: ${candidato.estado}
                """.trimIndent()
            )
        }

        item { SocialMediaCard() }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    val mockCandidato = Candidato(
        id = 1,
        nombre = "Ricardo Gómez",
        partido = "Transparencia Electoral Perú",
        cargo = "Candidato a Alcalde",
        region = "Lima",
        foto_url = "", // puede estar vacío
        dni = 12345678L,
        nacimiento = "1980-05-12",
        biografia = "Ricardo Gómez es un político con amplia experiencia en gestión pública y desarrollo local.",
        experiencia = 10,
        estado = "Activo",
        historial = listOf(
            HistorialCargo(
                cargo = "Regidor Municipal",
                institucion = "Municipalidad de Lima",
                fecha_inicio = "2018",
                fecha_fin = "2022",
                descripcion = "Participó en proyectos de mejora urbana y transporte sostenible."
            )
        ),
        propuestas = listOf(
            Propuesta(
                titulo = "Educación de calidad",
                descripcion = "Implementar programas de capacitación docente y acceso a tecnología.",
                categoria = "Educación",
                prioridad = "Alta"
            )
        ),
        denuncias = listOf(
            Denuncia(
                titulo = "Uso indebido de fondos",
                descripcion = "Investigación archivada por falta de pruebas.",
                expediente = "EXP-2023-0456",
                delito = "Corrupción administrativa",
                fecha_denuncia = "2023-08-15",
                estado = "Archivado",
                fuente_url = "https://transparencia.pe/expedientes/EXP-2023-0456"
            )
        )
    )

    // For preview purposes, we wrap DetailContent directly
    Scaffold(
        topBar = { ProfileHeader(title = "Perfil de Candidato", onBack = { }) },
        bottomBar = {
            BottomNavigationBar(navController = rememberNavController(), currentRoute = "detalle")
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF7FFF7)),
            contentAlignment = Alignment.Center
        ) {
            DetailContent(candidato = mockCandidato)
        }
    }
}
