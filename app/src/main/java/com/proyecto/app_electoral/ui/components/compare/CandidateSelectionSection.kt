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
import androidx.compose.ui.text.font.FontWeight
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
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }

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
            // Candidato 1 Dropdown
            Box {
                CandidateCard(
                    name = candidato1?.nombre ?: "Seleccionar",
                    party = candidato1?.partido ?: "Candidato",
                    fotoResId = candidato1?.fotoResId ?: 0,
                    hasCandidate = candidato1 != null,
                    onButtonClick = { expanded1 = true }
                )
                DropdownMenu(expanded = expanded1, onDismissRequest = { expanded1 = false }) {
                    candidatos.forEach { candidato ->
                        DropdownMenuItem(
                            text = { Text(candidato.nombre) },
                            onClick = { 
                                onSelectCandidato1(candidato)
                                expanded1 = false
                            }
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3b5998)),
                contentAlignment = Alignment.Center
            ) {
                Text("VS", color = Color.White, fontWeight = FontWeight.Bold)
            }

            // Candidato 2 Dropdown
            Box {
                CandidateCard(
                    name = candidato2?.nombre ?: "Seleccionar",
                    party = candidato2?.partido ?: "Candidato",
                    fotoResId = candidato2?.fotoResId ?: 0,
                    hasCandidate = candidato2 != null,
                    onButtonClick = { expanded2 = true }
                )
                DropdownMenu(expanded = expanded2, onDismissRequest = { expanded2 = false }) {
                    candidatos.forEach { candidato ->
                        DropdownMenuItem(
                            text = { Text(candidato.nombre) },
                            onClick = { 
                                onSelectCandidato2(candidato)
                                expanded2 = false
                            }
                        )
                    }
                }
            }
        }
    }
}
