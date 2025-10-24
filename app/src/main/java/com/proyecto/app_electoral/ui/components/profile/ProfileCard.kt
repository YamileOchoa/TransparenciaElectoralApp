package com.proyecto.app_electoral.ui.components.profile

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import com.proyecto.app_electoral.data.model.Candidato

@Composable
fun ProfileCard(candidato: Candidato) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            val fotoResId = if (candidato.fotoResId != 0) candidato.fotoResId else R.drawable.ic_profile_placeholder
            Log.d("ImageCheck", "Cargando imagen para (Perfil): ${candidato.foto_url} → id=$fotoResId")

            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(fotoResId)
                .size(512) // Optimización: redimensionar la imagen
                .crossfade(true)
                .build()

            AsyncImage(
                model = imageRequest,
                contentDescription = "Foto del candidato",
                placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                error = painterResource(id = R.drawable.ic_profile_placeholder),
                modifier = Modifier
                    .size(140.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = candidato.nombre,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1a237e),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = candidato.partido,
                fontSize = 16.sp,
                color = Color(0xFF4FC3F7),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Candidato a ${candidato.cargo}",
                fontSize = 14.sp,
                color = Color(0xFF9E9E9E),
                textAlign = TextAlign.Center
            )
        }
    }
}
