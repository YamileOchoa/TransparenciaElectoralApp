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

@Composable
fun CandidateSelectionSection() {
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
            CandidateCard(
                name = "Candidato 1",
                party = "Partido político",
                hasCandidate = true,
                onButtonClick = { /* TODO: Cambiar candidato */ }
            )

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

            CandidateCard(
                name = "Seleccionar",
                party = "Candidato",
                hasCandidate = false,
                onButtonClick = { /* TODO: Seleccionar candidato */ }
            )
        }
    }
}
