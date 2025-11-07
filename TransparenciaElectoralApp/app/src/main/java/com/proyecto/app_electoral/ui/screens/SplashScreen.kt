package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proyecto.app_electoral.R
import com.proyecto.app_electoral.ui.navigation.Screen
import com.proyecto.app_electoral.ui.viewmodel.SplashViewModel

/**
 * The main Splash Screen composable.
 * Displays the app logo and navigates to the Home screen after a delay.
 * * @param navController Controller used for navigation.
 * @param viewModel ViewModel handling the splash screen duration logic.
 */
@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = viewModel()
) {
    // 1. Collect the navigation trigger from the ViewModel
    // This effect runs once the ViewModel sets navigateToHome to true (after 2 seconds)
    LaunchedEffect(key1 = viewModel.navigateToHome.value) {
        if (viewModel.navigateToHome.value) {
            navController.popBackStack() // Clear back stack
            navController.navigate(Screen.Home.route) // Navigate to the next screen
        }
    }

    // 2. UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA)), // Background color: #FAFAFA (White/Off-White)
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            // Assuming 'logo_elecciones' is correctly placed in your drawable folder
            painter = painterResource(id = R.drawable.logo_eleciones),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}