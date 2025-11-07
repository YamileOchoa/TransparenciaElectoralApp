package com.proyecto.app_electoral.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SPLASH_DURATION_MS = 2000L // 2 seconds delay

/**
 * ViewModel for the SplashScreen.
 * Handles the timer logic before navigating to the next screen.
 */
class SplashViewModel : ViewModel() {

    // State to trigger navigation after the delay
    val navigateToHome = mutableStateOf(false)

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(SPLASH_DURATION_MS)
            navigateToHome.value = true // Set state to true to trigger navigation in Composable
        }
    }
}