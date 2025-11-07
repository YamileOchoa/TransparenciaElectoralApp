package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.ui.components.denuncia.DenunciaButton
import com.proyecto.app_electoral.ui.components.denuncia.DenunciaCard
import com.proyecto.app_electoral.ui.components.denuncia.DenunciaHeader

@Composable
fun DetailDenunciaScreen(
    onBackClick: () -> Unit = {},
    onFuenteClick: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            DenunciaButton(
                text = "Ver Fuente Oficial",
                onClick = onFuenteClick
            )
        },
        topBar = {
            DenunciaHeader(
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            DenunciaCard()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailDenunciaScreenPreview() {
    DetailDenunciaScreen()
}
