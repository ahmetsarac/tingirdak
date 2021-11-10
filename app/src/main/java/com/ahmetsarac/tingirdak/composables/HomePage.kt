package com.ahmetsarac.tingirdak.composables


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun HomePage(){
    Scaffold(
        topBar = {
           AppBar(title = "Tingirdak") 
        }
    ){

    }
}