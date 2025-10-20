package com.proyecto.app_electoral.ui.components.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.app_electoral.R

@Composable
fun CandidateListSection(onCandidateClick: (Int) -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("Todos los Candidatos", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        val ids = listOf(1, 2, 3, 4)
        ids.forEach { id ->
            CandidateCardFull(
                name = "Nombre del congresista",
                party = "Partido político del congresista",
                position = "Congresista • Lima",
                projects = 12,
                denuncias = if (id % 2 == 0) 2 else 0,
                onClick = { onCandidateClick(id) }
            )
        }
    }
}

@Composable
fun CandidateCardFull(
    name: String,
    party: String,
    position: String,
    projects: Int,
    denuncias: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.candidate1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(name, fontWeight = FontWeight.Bold)
                    Text(party, color = Color(0xFF1976D2))
                    Text(position, color = Color.Gray, fontSize = 12.sp)
                    Text("8 años de experiencia", fontSize = 12.sp)
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BadgeInfo("📘 $projects proyectos", Color(0xFF2E7D32))
                if (denuncias > 0)
                    BadgeInfo("⚠️ $denuncias denuncias", Color(0xFFE53935))
                else
                    BadgeInfo("✅ Sin denuncias", Color(0xFF1E88E5))
            }
            Spacer(Modifier.height(8.dp))
            Text("Fuentes: JNE • ONPE • Congreso", fontSize = 11.sp, color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onClick,
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(50)
            ) {
                Text("Ver perfil →")
            }
        }
    }
}

@Composable
fun BadgeInfo(text: String, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}
