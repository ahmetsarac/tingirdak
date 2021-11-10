package com.ahmetsarac.tingirdak.composables

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ahmetsarac.tingirdak.ui.theme.DarkBlue

@Composable
fun AppBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title,
                    color = Color.White
            )
        },
        backgroundColor = DarkBlue
    )
}