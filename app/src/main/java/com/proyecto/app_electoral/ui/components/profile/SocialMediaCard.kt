package com.proyecto.app_electoral.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.R
import androidx.compose.ui.text.font.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun SocialMediaCard() {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Redes Sociales",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1a237e),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_twitter),
                    contentDescription = "Twitter",
                    modifier = Modifier.size(48.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_facebook),
                    contentDescription = "Facebook",
                    modifier = Modifier.size(48.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_instagram),
                    contentDescription = "Instagram",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}