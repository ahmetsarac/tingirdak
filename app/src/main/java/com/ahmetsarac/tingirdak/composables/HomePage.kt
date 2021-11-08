package com.ahmetsarac.tingirdak.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmetsarac.tingirdak.ui.theme.*

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

@Composable
fun AppBar(title: String){
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        backgroundColor = DarkBlue
    ) 
}




