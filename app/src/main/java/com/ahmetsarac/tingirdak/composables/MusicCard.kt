package com.ahmetsarac.tingirdak.composables

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ahmetsarac.tingirdak.models.MusicCardModel


val mediaPlayer = MediaPlayer().apply {
    setAudioAttributes(
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
    )
}


@Composable
fun MusicCard(
    uri: Uri,
    artist: String,
    name: String,
    duration: String,
    isPlaying: MutableState<Boolean>,
    playingSong: MutableState<MusicCardModel>,
    songId: Long?,
    playState: MutableState<Boolean>
) {

    val context = LocalContext.current

    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            playMusic(context, uri)
            playState.value = true
            isPlaying.value = true
            val playingSongModel = MusicCardModel(uri, songId,
                name, artist, duration)
            playingSong.value = playingSongModel

       }
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
                /*Image(
                    modifier = Modifier.size(70.dp),
                    bitmap = cover!!.asImageBitmap(),
                    contentDescription = "Cover Photo",
                )
                 */
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

fun playMusic(context: Context,  uri: Uri) {
    mediaPlayer.apply {
           reset()
            setDataSource(context, uri)
            prepare()
            start()
   }
}

