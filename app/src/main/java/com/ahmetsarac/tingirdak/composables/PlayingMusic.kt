package com.ahmetsarac.tingirdak.composables

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer.SEEK_CLOSEST
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahmetsarac.tingirdak.R
import com.ahmetsarac.tingirdak.ui.theme.DarkBlue

@Composable
fun PlayingMusic(
    navController: NavController,
    uri: Uri?,
    playState: MutableState<Boolean>,
    artist: String?,
    name: String?,
) {


    val context = LocalContext.current
    val length = remember {
        mutableStateOf(0)
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navController.navigate("music_page")
        }
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .background(DarkBlue),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(70.dp),
                    bitmap = getAlbumArt(context, uri!!).asImageBitmap(),
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



            IconToggleButton(checked = false, onCheckedChange = {
                if(playState.value) {
                    mediaPlayer.pause()
                    length.value = mediaPlayer.currentPosition
                    playState.value = false
                } else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mediaPlayer.seekTo(length.value.toLong(), SEEK_CLOSEST)
                    }else{
                        mediaPlayer.seekTo(length.value)
                    }
                    mediaPlayer.start()
                    playState.value = true
                }

            }) {
                Icon(painter = painterResource(id = if (playState.value) R.drawable.ic_baseline_pause_24 else R.drawable.ic_baseline_play_arrow_24), contentDescription = "Play/Pause", tint = Color.White)
            }
        }
    }
}

fun getAlbumArt(context: Context, uri: Uri): Bitmap{
    val mmr = MediaMetadataRetriever()
    mmr.setDataSource(context, uri)
    val data = mmr.embeddedPicture
    mmr.release()
    return if(data != null){
        BitmapFactory.decodeByteArray(data, 0, data.size)
    }else{
        BitmapFactory.decodeResource(context.resources, R.drawable.note)
    }
}