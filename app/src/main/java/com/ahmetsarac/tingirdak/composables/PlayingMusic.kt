package com.ahmetsarac.tingirdak.composables

import android.graphics.Bitmap
import android.net.Uri
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahmetsarac.tingirdak.models.MusicCardModel
import com.ahmetsarac.tingirdak.ui.theme.DarkBlue
import java.time.format.TextStyle

@Composable
fun PlayingMusic(navController: NavController, uri: Uri?, songId: Long?, artist: String?, name: String?, cover: Bitmap?, duration: String?) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navController.navigate("music_page")
        }
    ) {
        Row(
            modifier = Modifier.padding(5.dp).background(DarkBlue),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(70.dp),
                    bitmap = cover!!.asImageBitmap(),
                    contentDescription = "Cover Photo",
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = artist!!,
                        color = Color.White
                    )
                    Text (name!!, maxLines = 1, color = Color.White)
                }
            }
            Text(text = duration!!, color = Color.White)
        }
    }
}