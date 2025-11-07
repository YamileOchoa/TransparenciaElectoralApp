package com.proyecto.app_electoral.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.ui.theme.PurplePrimary
import com.proyecto.app_electoral.ui.theme.White

data class ItemNavInferior(val etiqueta: String, val icono: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        ItemNavInferior("Inicio", Icons.Default.Home),
        ItemNavInferior("Candidatos", Icons.Default.Person),
        ItemNavInferior("Comparar", Icons.Default.SwapHoriz),
        ItemNavInferior("Estadísticas", Icons.Default.BarChart)
    )

    var itemSeleccionado by remember { mutableStateOf(0) }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        NavigationBar(
            containerColor = White,
            contentColor = Color.Gray
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(item.icono, contentDescription = item.etiqueta) },
                    label = { Text(item.etiqueta) },
                    selected = itemSeleccionado == index,
                    onClick = { itemSeleccionado = index },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PurplePrimary,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = PurplePrimary,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
