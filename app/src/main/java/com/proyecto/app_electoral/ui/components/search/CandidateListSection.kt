package com.proyecto.app_electoral.ui.components.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.proyecto.app_electoral.R
import com.proyecto.app_electoral.data.model.Candidato

@Composable
fun CandidateListSection(
    candidatos: List<Candidato>,
    onCandidateClick: (Int) -> Unit,
    favoritos: List<Int>,
    onToggleFavorito: (Int) -> Unit
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
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
                    onClick = { onCandidateClick(candidato.id) },
                    isFavorito = candidato.id in favoritos,
                    onToggleFavorito = { onToggleFavorito(candidato.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidateListItem(
    candidato: Candidato,
    onClick: () -> Unit,
    isFavorito: Boolean,
    onToggleFavorito: () -> Unit
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
        Box { // Usamos un Box para superponer el icono de favorito
            Column(Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val imageRequest = ImageRequest.Builder(LocalContext.current)
                        .data("http://10.0.2.2:8000" + candidato.fotoUrl)
                        .size(512) // Optimización: redimensionar la imagen
                        .crossfade(true)
                        .placeholder(R.drawable.ic_profile_placeholder)
                        .error(R.drawable.ic_profile_placeholder)
                        .build()

                    AsyncImage(
                        model = imageRequest,
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
                            Text(candidato.nombre, fontWeight = FontWeight.Bold, fontSize = 17.sp, modifier = Modifier.weight(1f, fill = false))
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Filled.Shield,
                                contentDescription = null,
                                tint = Color(0xFF1FBF50),
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        Text(candidato.partido, color = Color(0xFF1976D2), fontSize = 14.sp)
                        Text("${candidato.historial?.firstOrNull()?.cargo ?: "Sin cargo"} • ${candidato.region}", color = Color.Gray, fontSize = 13.sp)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.AccessTime, "Experiencia", tint = Color.Gray, modifier = Modifier.size(12.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("${candidato.experiencia} años de experiencia", color = Color.Gray, fontSize = 12.sp)
                        }

                        Spacer(Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            BadgeSolid("${candidato.propuestas?.size ?: 0} propuestas", Color(0xFF067B60), Icons.Filled.DocumentScanner)
                            if (candidato.denuncias?.isNotEmpty() == true) {
                                BadgeSolid("${candidato.denuncias?.size ?: 0} Denuncias", Color(0xFFE53935), Icons.Filled.Warning)
                            } else {
                                BadgeSolid("Sin denuncias", Color(0xFF1976D2), Icons.Filled.CheckCircle)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFFE0E0E0))
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Link, "Fuentes", tint = Color(0xFF003DFF), modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Fuentes: JNE • ONPE • Congreso", color = Color(0xFF003DFF), fontSize = 11.sp)
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = onClick,
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(brush = Brush.horizontalGradient(colors = listOf(Color(0xFF007FF6), Color(0xFF074693))), shape = RoundedCornerShape(50))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Ver perfil →", fontSize = 13.sp, color = Color.White)
                        }
                    }
                }
            }
            // Icono de Favorito superpuesto
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp)
            ) {
                IconButton(onClick = onToggleFavorito, modifier = Modifier.size(36.dp)) {
                     Icon(
                        imageVector = if (isFavorito) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = if (isFavorito) "Quitar de favoritos" else "Marcar como favorito",
                        tint = if (isFavorito) Color(0xFFFFD700) else Color.Gray
                    )
                }
                Text(
                    text = if (isFavorito) "Favorito" else "Marcar",
                    fontSize = 9.sp,
                    color = if (isFavorito) Color(0xFFFFD700) else Color.Gray,
                    modifier = Modifier.offset(y = (-6).dp)
                )
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
