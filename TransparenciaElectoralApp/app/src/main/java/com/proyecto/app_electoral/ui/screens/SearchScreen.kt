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
import androidx.lifecycle.viewmodel.compose.viewModel // Importación del ViewModel
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.ui.components.search.SearchHeader
import com.proyecto.app_electoral.ui.theme.*
import com.proyecto.app_electoral.ui.viewmodel.HomeViewModel // Importación del HomeViewModel
import com.proyecto.app_electoral.ui.viewmodel.HomeViewModelFactory // Importación del Factory
import com.proyecto.app_electoral.di.Injector // Importación del Injector
import com.proyecto.app_electoral.ui.components.home.ImageShapeType
// --- Importaciones de Colores ---
import com.proyecto.app_electoral.ui.theme.BackgroundCustom
import com.proyecto.app_electoral.ui.theme.PrimaryCustom
import com.proyecto.app_electoral.ui.theme.DarkCustom
import com.proyecto.app_electoral.ui.theme.SurfaceLight
import com.proyecto.app_electoral.ui.components.home.ImageLoader
// ⬅️ IMPORTACIÓN CLAVE: Necesaria para acceder a las rutas de navegación
import com.proyecto.app_electoral.ui.navigation.Screen

/**
 * 🔍 Pantalla de Búsqueda de Candidatos.
 * Muestra el campo de búsqueda, filtros y los resultados en tiempo real.
 */
@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    // [CRÍTICO] Inyectar HomeViewModel (usando el Factory si es necesario)
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(Injector.provideCandidatoRepository()))
) {
    // 1. Estados de la UI
    var selectedFilter by remember { mutableStateOf("Nombre") }
    var searchQuery by remember { mutableStateOf("") } // Estado del texto de búsqueda

    // 2. Datos y Estado de Carga
    val uiState by viewModel.uiState.collectAsState()

    // Lista filtrada basada en el texto de búsqueda y el filtro
    val filteredCandidates = remember(uiState.candidates, searchQuery, selectedFilter) {
        viewModel.filterCandidates(searchQuery) // Usa la función de filtrado del ViewModel
            .let { list ->
                // Aplica el filtro adicional por campo (si es necesario)
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

        // --- 1. Cabecera ---
        SearchHeader()
        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. Caja de Búsqueda (Ahora un OutlinedTextField funcional) ---
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
            // Estilos M3
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceLight,
                unfocusedContainerColor = SurfaceLight,
                cursorColor = PrimaryCustom,
                focusedBorderColor = PrimaryCustom,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 3. Filtros de Búsqueda ---
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
                            color = SurfaceLight, // Fondo visible para el no seleccionado
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
                // Mostrar indicador de carga solo si no se está buscando (carga inicial)
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
                // Mostrar mensaje si no hay resultados
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
                            // ⬅️ IMPLEMENTACIÓN DE LA NAVEGACIÓN
                            onCandidateClick = {
                                val route = Screen.Profile.createRoute(candidato.id)
                                navController.navigate(route)
                            }
                        )
                    }
                }
            }
        }
    }
}

// --- Componente de Ítem de Resultado Real (Reemplaza el Placeholder) ---

/**
 * Muestra la información básica de un candidato en la lista de resultados.
 */
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
        // ✅ CORRECTO: Usamos ImageLoader con la forma circular
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