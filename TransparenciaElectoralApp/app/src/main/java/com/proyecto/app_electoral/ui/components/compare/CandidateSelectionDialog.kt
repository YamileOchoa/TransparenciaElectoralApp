package com.proyecto.app_electoral.ui.components.compare

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.data.network.model.Candidato
import androidx.compose.foundation.layout.*

@Composable
fun CandidateSelectionDialog(
    candidatos: List<Candidato>,
    onDismiss: () -> Unit,
    onSelect: (Candidato) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Selecciona un candidato") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (candidatos.isEmpty()) {
                    Text(
                        text = "Aún no hay candidatos disponibles.",
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    candidatos.forEach { candidato ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelect(candidato) }
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${candidato.nombre} (${candidato.partido})")
                        }
                        Divider()
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cerrar") }
        }
    )
}
