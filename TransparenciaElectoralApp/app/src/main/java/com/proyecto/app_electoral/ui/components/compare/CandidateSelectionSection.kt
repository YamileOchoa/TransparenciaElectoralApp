package com.proyecto.app_electoral.ui.components.compare

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.proyecto.app_electoral.data.model.Candidato

@Composable
fun CandidateSelectionSection(
    candidatos: List<Candidato>,
    candidato1: Candidato?,
    candidato2: Candidato?,
    onSelectCandidato1: () -> Unit,
    onSelectCandidato2: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CandidateCard(
                name = candidato1?.nombre ?: "Seleccionar",
                party = candidato1?.partido ?: "Candidato",
                fotoUrl = candidato1?.fotoUrl,
                hasCandidate = candidato1 != null,
                onButtonClick = onSelectCandidato1
            )

            Text("VS", color = Color(0xFF3b5998))

            CandidateCard(
                name = candidato2?.nombre ?: "Seleccionar",
                party = candidato2?.partido ?: "Candidato",
                fotoUrl = candidato2?.fotoUrl,
                hasCandidate = candidato2 != null,
                onButtonClick = onSelectCandidato2
            )
        }
    }
}


@Composable
fun CandidateDialog(
    candidatos: List<Candidato>,
    onSelect: (Candidato) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Selecciona un candidato",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF3b5998)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(candidatos.size) { index ->
                        val candidato = candidatos[index]
                        Text(
                            text = candidato.nombre,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelect(candidato)
                                }
                                .padding(vertical = 8.dp),
                            color = Color.Black
                        )
                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}

