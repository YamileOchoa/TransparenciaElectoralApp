package com.proyecto.app_electoral.ui.components.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun SearchBarSection() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color(0xFF011041).copy(alpha = 0.6f)
            )
        },
        placeholder = {
            Text(
                text = "Buscar por nombre, partido o región",
                color = Color(0xFF011041).copy(alpha = 0.4f)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFFEFEFE), RoundedCornerShape(10.dp)) // 👈 fondo y borde redondeado
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(10.dp)), // 👈 borde sutil gris claro
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFFEFEFE),
            unfocusedContainerColor = Color(0xFFFEFEFE),
            focusedBorderColor = Color(0xFFD0D0D0),
            unfocusedBorderColor = Color(0xFFE0E0E0),
            cursorColor = Color(0xFF011041)
        ),
        singleLine = true
    )
}


@Composable
fun FilterChipsSection() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip("Partido")
        FilterChip("Región")
        FilterChip("Cargo")
    }
}

@Composable
fun FilterChip(label: String) {
    OutlinedButton(
        onClick = {},
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0xFFFEFEFE),
            contentColor = Color.Black
        )
    ) {
        Text(label)
        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
    }
}
