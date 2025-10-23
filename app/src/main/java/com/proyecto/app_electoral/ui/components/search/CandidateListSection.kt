package com.proyecto.app_electoral.ui.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.proyecto.app_electoral.R
import com.proyecto.app_electoral.data.model.Candidato

@Composable
fun CandidateListSection(candidatos: List<Candidato>, onCandidateClick: (Int) -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text(
            "Todos los Candidatos",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(Modifier.height(12.dp))

        if (candidatos.isEmpty()) {
            Text("No se encontraron candidatos.", modifier = Modifier.padding(16.dp))
        } else {
            candidatos.forEach { candidato ->
                CandidateListItem(
                    candidato = candidato,
                    onClick = { onCandidateClick(candidato.id) }
                )
            }
        }
    }
}

@Composable
fun CandidateListItem(
    candidato: Candidato,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = if (candidato.fotoResId != 0) candidato.fotoResId else R.drawable.ic_profile_placeholder,
                    contentDescription = "Foto de ${candidato.nombre}",
                    placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                    error = painterResource(id = R.drawable.ic_profile_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(14.dp))
                )

                Spacer(Modifier.width(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(candidato.nombre, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Filled.Shield,
                            contentDescription = null,
                            tint = Color(0xFF1FBF50),
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    Text(candidato.partido, color = Color(0xFF1976D2), fontSize = 14.sp)

                    Text("${candidato.cargo} • ${candidato.region}", color = Color.Gray, fontSize = 13.sp)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = "Experiencia",
                            tint = Color.Gray,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("${candidato.experiencia} años de experiencia", color = Color.Gray, fontSize = 12.sp)
                    }

                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        BadgeSolid(
                            text = "${candidato.propuestas?.size ?: 0} propuestas",
                            color = Color(0xFF067B60),
                            icon = Icons.Filled.DocumentScanner
                        )

                        if (candidato.denuncias?.isNotEmpty() == true) {
                            BadgeSolid(
                                text = "${candidato.denuncias?.size ?: 0} Denuncias",
                                color = Color(0xFFE53935),
                                icon = Icons.Filled.Warning
                            )
                        } else {
                            BadgeSolid(
                                text = "Sin denuncias",
                                color = Color(0xFF1976D2),
                                icon = Icons.Filled.CheckCircle
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Divider(color = Color(0xFFE0E0E0))
            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Link,
                    contentDescription = "Fuentes",
                    tint = Color(0xFF003DFF),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "Fuentes: JNE • ONPE • Congreso",
                    color = Color(0xFF003DFF),
                    fontSize = 11.sp
                )
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = onClick,
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF007FF6), Color(0xFF074693))
                                ),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("Ver perfil →", fontSize = 13.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeSolid(text: String, color: Color, icon: ImageVector) {
    Box(
        modifier = Modifier
            .background(color, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
            Spacer(Modifier.width(4.dp))
            Text(text, color = Color.White, fontSize = 12.sp)
        }
    }
}
