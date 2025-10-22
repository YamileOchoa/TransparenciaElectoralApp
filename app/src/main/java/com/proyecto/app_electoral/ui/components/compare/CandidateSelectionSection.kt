package com.proyecto.app_electoral.ui.components.compare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.data.model.Candidato

@Composable
fun CandidateSelectionSection(
    candidatos: List<Candidato>,
    candidato1: Candidato?,
    candidato2: Candidato?,
    onSelectCandidato1: (Candidato) -> Unit,
    onSelectCandidato2: (Candidato) -> Unit
) {
    var showDialog1 by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }

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
            // Candidato 1
            CandidateCard(
                name = candidato1?.nombre ?: "Seleccionar",
                party = candidato1?.partido ?: "Candidato",
                imageUrl = candidato1?.foto_url,
                hasCandidate = candidato1 != null,
                onButtonClick = { showDialog1 = true }
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

            // Candidato 2
            CandidateCard(
                name = candidato2?.nombre ?: "Seleccionar",
                party = candidato2?.partido ?: "Candidato",
                imageUrl = candidato2?.foto_url,
                hasCandidate = candidato2 != null,
                onButtonClick = { showDialog2 = true }
            )
        }
    }

    // Diálogo Candidato 1
    if (showDialog1) {
        CandidateSelectionDialog(
            candidatos = candidatos.distinctBy { it.id },
            onDismiss = { showDialog1 = false },
            onSelect = {
                onSelectCandidato1(it)
                showDialog1 = false
            }
        )
    }

// Diálogo Candidato 2
    if (showDialog2) {
        CandidateSelectionDialog(
            candidatos = candidatos.distinctBy { it.id },
            onDismiss = { showDialog2 = false },
            onSelect = {
                onSelectCandidato2(it)
                showDialog2 = false
            }
        )
    }
}
