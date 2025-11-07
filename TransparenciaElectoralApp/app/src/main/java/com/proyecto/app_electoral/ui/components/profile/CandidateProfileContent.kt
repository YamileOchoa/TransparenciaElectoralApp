package com.proyecto.app_electoral.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CandidateProfileContent(
    onTabSelected: (String) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Denuncias", "Proyectos", "Propuestas", "Historial")

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
            Image(
                painter = rememberAsyncImagePainter("https://placehold.co/120x159"),
                contentDescription = "Foto del candidato",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Ana María Valdivia", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Text("Partido Progreso Nacional", color = Color.Gray, fontSize = 13.sp)
            Text("Lima | Abogada", color = Color.Gray, fontSize = 11.sp)
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
                    "Un breve párrafo introductorio que resume la trayectoria y la historia profesional de la candidata. Esta sección ofrece una visión general de su formación, experiencia laboral y principales logros antes de ingresar a la política. Busca brindar al ciudadano una idea clara sobre su perfil profesional, destacando los cargos que ha ocupado, las áreas en las que ha trabajado y cómo su experiencia previa ha contribuido a su desarrollo como figura pública y representante política.",
                    fontSize = 12.sp,
                    color = Color(0xFF333333)
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
                        onTabSelected(title)
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
        CandidateReportList()
    }
}

@Composable
fun CandidateReportList() {
    val reports = listOf(
        Triple("Caso de investigación por tráfico de influencias", "15 de marzo, 2023", Color(0xFFFFA726)),
        Triple("Denuncia por difamación archivada", "02 de enero, 2022", Color(0xFF66BB6A)),
        Triple("Acusación de irregularidades en contratos", "20 de septiembre, 2021", Color(0xFFE53935)),
        Triple("Caso de nepotismo archivado", "11 de junio, 2020", Color(0xFF9E9E9E))
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        reports.forEach { (title, date, color) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text("Fecha: $date", color = Color.Gray, fontSize = 11.sp)
                    }
                    // 🔹 Círculo alineado al extremo derecho
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(color, CircleShape)
                    )
                }
            }
        }
    }
}