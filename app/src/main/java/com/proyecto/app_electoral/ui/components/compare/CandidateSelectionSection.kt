package com.proyecto.app_electoral.ui.components.compare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.data.model.Candidato

@Composable
fun CandidateSelectionSection(
    candidato1: Candidato?,
    candidato2: Candidato?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (candidato1 != null) {
                CandidateCard(
                    name = candidato1.nombre,
                    party = candidato1.partido,
                    imageUrl = candidato1.foto_url,
                    hasCandidate = true,
                    onButtonClick = { /* TODO: Cambiar candidato */ }
                )
            } else {
                CandidateCard(
                    name = "Seleccionar",
                    party = "Candidato",
                    hasCandidate = false,
                    onButtonClick = { /* TODO: Seleccionar candidato */ }
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3b5998)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "VS",
                    color = Color.White,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }

            if (candidato2 != null) {
                CandidateCard(
                    name = candidato2.nombre,
                    party = candidato2.partido,
                    imageUrl = candidato2.foto_url,
                    hasCandidate = true,
                    onButtonClick = { /* TODO: Cambiar candidato */ }
                )
            } else {
                CandidateCard(
                    name = "Seleccionar",
                    party = "Candidato",
                    hasCandidate = false,
                    onButtonClick = { /* TODO: Seleccionar candidato */ }
                )
            }
        }
    }
}
