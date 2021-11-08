package com.ahmetsarac.tingirdak

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ahmetsarac.tingirdak.composables.HomePage
import com.ahmetsarac.tingirdak.ui.theme.DarkBlue
import com.ahmetsarac.tingirdak.ui.theme.TingirdakTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windows = this.window
        windows.statusBarColor = Color.argb(255, 0, 13, 107)
        setContent {
            TingirdakTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                   HomePage()
                }
            }
        }
    }
}