package com.ahmetsarac.tingirdak.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MusicPage(navController: NavController) {
    Scaffold(topBar = {
        AppBar(title = "Music Page")
    }
    )
    {
        Box(modifier = Modifier.fillMaxSize())
    }
}