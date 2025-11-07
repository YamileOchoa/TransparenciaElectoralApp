package com.proyecto.app_electoral.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.proyecto.app_electoral.data.network.model.Denuncia
import com.proyecto.app_electoral.data.network.model.HistorialCargo
import com.proyecto.app_electoral.data.network.model.Propuesta
import com.proyecto.app_electoral.data.network.model.Proyecto
import com.proyecto.app_electoral.data.repository.CandidateProfileData

@Composable
fun CandidateProfileContent(
    data: CandidateProfileData // ⬅️ Recibe los datos consolidados del ViewModel
) {
    // Definición de las pestañas
    val tabs = listOf("Denuncias", "Proyectos", "Propuestas", "Historial")
    var selectedTab by remember { mutableStateOf(0) }

    // Función para obtener la lista de datos según la pestaña seleccionada
    val currentListData: List<Any> = remember(selectedTab, data) {
        when (selectedTab) {
            0 -> data.denuncias
            1 -> data.proyectos
            2 -> data.propuestas
            3 -> data.historialCargos
            else -> emptyList()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Foto y datos
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageUrl = data.candidato.foto_url ?: "https://placehold.co/120x159"
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Foto de ${data.candidato.nombre}",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(data.candidato.nombre, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Text(data.candidato.partido, color = Color.Gray, fontSize = 13.sp)
            Text("${data.candidato.region} | ${data.candidato.profesion ?: "N/D"}", color = Color.Gray, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card Biografía
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Biografía", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = data.candidato.biografia,
                    fontSize = 12.sp,
                    color = Color(0xFF333333),
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = Color(0xFF542F96),
            indicator = {
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(it[selectedTab])
                        .height(2.dp),
                    color = Color(0xFF542F96)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        selectedTab = index
                    },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTab == index) Color.Black else Color.Gray,
                            fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Contenedor de la lista basado en la pestaña seleccionada
        CandidateReportList(listData = currentListData)
    }
}

/**
 * Muestra una lista genérica de Denuncias, Proyectos, Propuestas o Historial.
 * Usa LazyColumn para eficiencia.
 */
@Composable
fun CandidateReportList(listData: List<Any>) {
    if (listData.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text("No hay registros disponibles para esta sección.")
        }
        return
    }

    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(listData) { item ->
            when (item) {
                is Denuncia -> DenunciaCard(denuncia = item)
                is Proyecto -> ProyectoCard(proyecto = item)
                is Propuesta -> PropuestaCard(propuesta = item)
                is HistorialCargo -> HistorialCard(historial = item)
            }
        }
    }
}

@Composable
fun DenunciaCard(denuncia: Denuncia) {
    val statusColor = when (denuncia.estado.toLowerCase()) {
        "investigacion" -> Color(0xFFFFA726)
        "archivada" -> Color(0xFF66BB6A)
        else -> Color(0xFFE53935)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(denuncia.titulo, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text("Expediente: ${denuncia.expediente}", color = Color.Gray, fontSize = 11.sp)
                Text("Fecha: ${denuncia.fecha_denuncia}", color = Color.Gray, fontSize = 11.sp)
            }
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(statusColor, CircleShape)
            )
        }
    }
}

@Composable
fun ProyectoCard(proyecto: Proyecto) {
    val statusColor = when (proyecto.estado.toLowerCase()) {
        "en curso" -> Color(0xFF29B6F6)
        "finalizado" -> Color(0xFF66BB6A)
        else -> Color(0xFF9E9E9E)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(proyecto.titulo, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text("Categoría: ${proyecto.categoria}", color = Color.Gray, fontSize = 11.sp)
                Text("Estado: ${proyecto.estado}", color = Color.Gray, fontSize = 11.sp)
            }
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(statusColor, CircleShape)
            )
        }
    }
}

@Composable
fun PropuestaCard(propuesta: Propuesta) {
    val priorityColor = when (propuesta.prioridad.toLowerCase()) {
        "alta" -> Color(0xFFE53935)
        "media" -> Color(0xFFFFA726)
        else -> Color(0xFF66BB6A)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(propuesta.titulo, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text("Categoría: ${propuesta.categoria}", color = Color.Gray, fontSize = 11.sp)
                Text("Prioridad: ${propuesta.prioridad}", color = Color.Gray, fontSize = 11.sp)
            }
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(priorityColor, CircleShape)
            )
        }
    }
}

@Composable
fun HistorialCard(historial: HistorialCargo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(historial.cargo, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text("Institución: ${historial.institucion}", color = Color.Gray, fontSize = 11.sp)
                Text(
                    text = "Período: ${historial.fecha_inicio} - ${historial.fecha_fin ?: "Actualidad"}",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }
    }
}
