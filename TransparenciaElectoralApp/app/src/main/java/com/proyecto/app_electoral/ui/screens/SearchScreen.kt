package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.CircleShape
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.ui.components.search.SearchHeader
import com.proyecto.app_electoral.ui.theme.*
import com.proyecto.app_electoral.ui.viewmodel.HomeViewModel
import com.proyecto.app_electoral.ui.viewmodel.HomeViewModelFactory
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.home.ImageShapeType
import com.proyecto.app_electoral.ui.theme.BackgroundCustom
import com.proyecto.app_electoral.ui.theme.PrimaryCustom
import com.proyecto.app_electoral.ui.theme.DarkCustom
import com.proyecto.app_electoral.ui.theme.SurfaceLight
import com.proyecto.app_electoral.ui.components.home.ImageLoader
import com.proyecto.app_electoral.ui.navigation.Screen

/**
 * 🔍 Pantalla de Búsqueda de Candidatos.
 */
@Composable
fun SearchScreen(
    navController: NavController,
    // ⚙️ CORRECCIÓN 1: Agregar los parámetros de navegación
    searchMode: String,
    searchPosition: Int,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(Injector.provideCandidatoRepository()))
) {
    // 1. Estados de la UI
    var selectedFilter by remember { mutableStateOf("Nombre") }
    var searchQuery by remember { mutableStateOf("") }

    // 2. Datos y Estado de Carga
    val uiState by viewModel.uiState.collectAsState()

    // Lista filtrada
    val filteredCandidates = remember(uiState.candidates, searchQuery, selectedFilter) {
        viewModel.filterCandidates(searchQuery)
            .let { list ->
                when (selectedFilter) {
                    "Nombre" -> list.sortedBy { it.nombre }
                    "Partido" -> list.sortedBy { it.partido }
                    "Región" -> list.sortedBy { it.region }
                    else -> list
                }
            }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundCustom)
    ) {

        // ... (Cabecera, Caja de Búsqueda y Filtros de Búsqueda sin cambios) ...
        SearchHeader()
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = {
                Text(
                    text = "Buscar por nombre, partido o región...",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Color.Gray
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceLight,
                unfocusedContainerColor = SurfaceLight,
                cursorColor = PrimaryCustom,
                focusedBorderColor = PrimaryCustom,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Nombre", "Partido", "Región").forEach { filter ->
                val isSelected = selectedFilter == filter
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { selectedFilter = filter },
                    contentAlignment = Alignment.Center
                ) {
                    val backgroundModifier = if (isSelected) {
                        Modifier.background(
                            brush = Brush.horizontalGradient(listOf(PrimaryCustom, DarkCustom)),
                            shape = RoundedCornerShape(20.dp)
                        )
                    } else {
                        Modifier.background(
                            color = SurfaceLight,
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .then(backgroundModifier)
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) SurfaceLight else MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 13.sp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        // --- 4. Manejo del Estado (Carga / Error / Resultados Reales) ---

        when {
            uiState.isLoading && searchQuery.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryCustom)
                }
            }
            uiState.error != null -> {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            filteredCandidates.isEmpty() -> {
                Text(
                    text = if (searchQuery.isBlank()) "Cargando candidatos..." else "No se encontraron resultados para \"$searchQuery\".",
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            else -> {
                // Mostrar resultados reales
                Text(
                    text = "Resultados (${filteredCandidates.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredCandidates) { candidato ->
                        CandidateResultItem(
                            candidato = candidato,
                            // ⚙️ CORRECCIÓN 2: Implementación de la lógica de navegación condicional
                            onCandidateClick = {
                                if (searchMode == "COMPARE") {
                                    // Guardamos el ID del candidato y la posición a llenar
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("CANDIDATE_ID_RESULT", candidato.id)

                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("CANDIDATE_POSITION_RESULT", searchPosition)

                                    // Volvemos a la pantalla anterior (ComparisonScreen)
                                    navController.popBackStack()

                                } else {
                                    // Modo Normal: Navegar al perfil
                                    val route = Screen.Profile.createRoute(candidato.id)
                                    navController.navigate(route)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

// ... (El componente CandidateResultItem sigue igual) ...
// (Omisión de CandidateResultItem por brevedad)
@Composable
fun CandidateResultItem(candidato: Candidato, onCandidateClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceLight)
            .clickable(onClick = onCandidateClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageLoader(
            imageUrl = candidato.foto_url,
            contentDescription = "Foto de ${candidato.nombre}",
            width = 40.dp,
            height = 40.dp,
            shapeType = ImageShapeType.CIRCLE
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = candidato.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = candidato.partido,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Ver detalle",
            tint = Color.Gray
        )
    }
}