package com.ahmetsarac.tingirdak.composables

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun MusicCard(id:Long, cover: Bitmap?, artist: String, name: String, duration: String) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { }
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
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
                        text = artist
                    )
                    Text (name, maxLines = 1)
                }
            }
            Text(text = duration)
        }
    }
}