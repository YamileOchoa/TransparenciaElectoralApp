package com.proyecto.app_electoral.ui.components.compare

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.proyecto.app_electoral.R

@Composable
fun CandidateCard(
    name: String,
    party: String,
    fotoUrl: String?,
    hasCandidate: Boolean,
    onButtonClick: () -> Unit
) {
    Log.d("CandidateCard", "Renderizando card: $name / hasCandidate=$hasCandidate / foto=$fotoUrl")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(120.dp)
            .then(
                // Si NO hay candidato, hacemos toda la tarjeta clickeable para abrir el diálogo
                if (!hasCandidate) {
                    Modifier.clickable { onButtonClick() }
                } else {
                    Modifier // Si hay candidato, el botón "Cambiar" se encarga del click
                }
            )
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(if (hasCandidate) Color(0xFFE8E8E8) else Color.Transparent)
                .then(
                    if (!hasCandidate) {
                        Modifier.border(2.dp, Color(0xFFDDDDDD), CircleShape)
                    } else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            if (hasCandidate) {
                val context = LocalContext.current

                val validUrl = fotoUrl?.let {
                    when {
                        it.startsWith("http://127.0.0.1") -> it.replace("127.0.0.1", "10.0.2.2")
                        it.startsWith("http://localhost") -> it.replace("localhost", "10.0.2.2")
                        it.startsWith("http") -> it
                        else -> "http://10.0.2.2:8000$it"
                    }
                }

                Log.d("CandidateCard", "📸 URL final que carga Coil: $validUrl")

                val imageRequest = ImageRequest.Builder(context)
                    .data(validUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .error(R.drawable.ic_profile_placeholder)
                    .build()

                AsyncImage(
                    model = imageRequest,
                    contentDescription = "Foto de $name",
                    placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                    error = painterResource(id = R.drawable.ic_profile_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

            }
            else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color(0xFF3b5998)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (hasCandidate) Color(0xFF333333) else Color(0xFF666666),
            textAlign = TextAlign.Center
        )

        Text(
            text = party,
            fontSize = 12.sp,
            color = if (hasCandidate) Color(0xFF888888) else Color(0xFF999999),
            textAlign = TextAlign.Center
        )

        // Este botón solo aparece si hay un candidato, y su onClick ya funciona correctamente.
        if (hasCandidate) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onButtonClick,
                modifier = Modifier.height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3b5998).copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text(
                    "Cambiar",
                    fontSize = 12.sp,
                    color = Color(0xFF3b5998)
                )
            }
        }
    }
}
