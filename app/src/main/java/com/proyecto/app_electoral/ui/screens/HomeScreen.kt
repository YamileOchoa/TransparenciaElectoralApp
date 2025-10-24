package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.proyecto.app_electoral.R
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavHostController) {
    val navigate = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500L)
        navigate.value = true
    }

    if (navigate.value) {
        LaunchedEffect(Unit) {
            navController.navigate("busqueda") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(350.dp)
        )
    }
}
