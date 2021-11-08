package com.ahmetsarac.tingirdak.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MusicCard(cover: String = "", artist: String = "", name: String = "", duration: String = ""){
    Card(modifier = Modifier.fillMaxWidth().clickable {  }
    ){
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically){
            Image(
                modifier = Modifier.size(70.dp),
                painter = painterResource(id = com.ahmetsarac.tingirdak.R.drawable.cover),
                contentDescription = "Cover Photo",
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(0.75f)
                    ,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(modifier = Modifier.padding(vertical = 5.dp), text = "Arctic Monkeys")
                Text("Do I Wanna Know?")
            }
            Text(
                modifier = Modifier.weight(0.1f),
                text = "4:26"
            )
        }
    }
}