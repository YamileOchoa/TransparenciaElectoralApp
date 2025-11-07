package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.profile.CandidateProfileContent
import com.proyecto.app_electoral.ui.components.profile.CandidateProfileHeader

@Composable
fun CandidateProfileScreen(onBackClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CandidateProfileHeader(onBackClick = onBackClick)
        CandidateProfileContent()
        BottomNavigationBar()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewCandidateProfileScreen() {
    CandidateProfileScreen()
}