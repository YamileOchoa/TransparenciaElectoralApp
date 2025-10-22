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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.app_electoral.data.model.Denuncia

@Composable
fun DenunciasSection(
    denuncias1: List<Denuncia>,
    denuncias2: List<Denuncia>
) {
    Column {
        Text(
            text = "⚠️ Denuncias",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                DenunciaColumn(modifier = Modifier.weight(1f), denuncias = denuncias1, hasCandidate = true)

                VerticalDivider(
                    modifier = Modifier
                        .heightIn(min = 100.dp)
                        .width(1.dp),
                    color = Color(0xFFE0E0E0)
                )

                DenunciaColumn(modifier = Modifier.weight(1f), denuncias = denuncias2, hasCandidate = denuncias2.isNotEmpty())

            }
        }
    }
}

@Composable
private fun DenunciaColumn(modifier: Modifier = Modifier, denuncias: List<Denuncia>, hasCandidate: Boolean) {
    if (!hasCandidate) {
        Box(
            modifier = modifier
                .padding(16.dp)
                .heightIn(min = 100.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Selecciona un candidato",
                    fontSize = 12.sp,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "para ver sus denuncias",
                    fontSize = 12.sp,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center
                )
            }
        }
        return
    }

    Column(modifier = modifier.padding(16.dp)) {
        if (denuncias.isEmpty()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CAF50))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sin denuncias",
                    fontSize = 12.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { /* No action */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F5E9)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("✓ Registro limpio", fontSize = 11.sp, color = Color(0xFF4CAF50))
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${denuncias.size} denuncias",
                    fontSize = 12.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            denuncias.take(2).forEach {
                Text(it.titulo, fontSize = 11.sp, maxLines = 1)
            }
        }
    }
}
