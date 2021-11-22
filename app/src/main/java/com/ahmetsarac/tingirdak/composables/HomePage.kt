package com.ahmetsarac.tingirdak.composables


import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ahmetsarac.tingirdak.models.MusicCardModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmetsarac.tingirdak.R



val MusicSaver = listSaver<MusicCardModel, Any?>(
    save = { listOf(it.contentUri, it.songId, it.cover, it.songTitle, it.artist, it.duration) },
    restore = { MusicCardModel(it[0] as Uri, it[1] as Long, it[2] as Bitmap, it[3] as String, it[4] as String, it[5] as String) }
)

@Composable
fun HomePageNav(){

    val context = LocalContext.current
    val list = context.musicList()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_page"){
        composable("home_page"){
            HomePage(navController, list)
        }
        composable("music_page"){
            MusicPage(navController)
        }
    }

}

@Composable
fun HomePage(navController: NavController, list: MutableList<MusicCardModel>) {


    Scaffold(
        topBar = {
            AppBar(title = "Tingirdak")
        }
    ) {

        val isPlaying = rememberSaveable {
            mutableStateOf(false)
        }
        val playingSong= rememberSaveable (stateSaver = MusicSaver){
            mutableStateOf(MusicCardModel(null, null, null, null, null, null))
        }
        Box(modifier = Modifier.fillMaxSize()) {
           Box(modifier = Modifier
                   .padding(bottom = if (isPlaying.value) 80.dp else 0.dp)){

               LazyColumn {
                   items(list) { index ->
                       MusicCard(
                           uri = index.contentUri!!,
                           songId = index.songId,
                           artist = index.artist!!,
                           name = index.songTitle!!,
                           cover = index.cover,
                           duration = index.duration!!,
                           isPlaying = isPlaying,
                           playingSong = playingSong
                       )
                   }
               }
           }
            if (isPlaying.value) {
                Box(modifier = Modifier
                    .align(Alignment.BottomCenter)){
                    PlayingMusic(navController = navController,
                    uri = playingSong.value.contentUri!!,
                    songId = playingSong.value.songId!!,
                    artist = playingSong.value.artist!!,
                    name = playingSong.value.songTitle!!,
                    cover = playingSong.value.cover!!,
                    duration = playingSong.value.duration!!)
                }
            }
        }

    }
}

@SuppressLint("Recycle")
fun Context.musicList(): MutableList<MusicCardModel> {
    val list = mutableListOf<MusicCardModel>()
    val collection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ALBUM_ID,
        MediaStore.Audio.Media.ARTIST
    )
    val bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.note)
    val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
    val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
    val query = this.contentResolver.query(
        collection,
        projection,
        selection,
        null,
        sortOrder
    )

    query?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
        val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val duration = cursor.getInt(durationColumn)
            val title = cursor.getString(titleColumn)
            val artist = cursor.getString(artistColumn)
            val albumId = cursor.getLong(albumIdColumn)
            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                id
            )
            //val bitmapUri = Uri.parse("content://media/external/audio/albumart/$albumId")
            //val bitmap = getBitmapfromUri(this, bitmapUri)
            val durationString = convertMili(duration)
            list.add(MusicCardModel(contentUri, id, bitmap, title, artist, durationString))
        }

    }
    return list
}


fun convertMili(milliseconds: Int): String {
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / (1000 * 60) % 60)
    return String.format("%02d:%02d", minutes, seconds)
}

/*
fun getAlbumart(context: Context, album_id: Long?): Bitmap? {
    var albumArtBitMap: Bitmap? = null
    val options = BitmapFactory.Options()
    try {
        val sArtworkUri = Uri
            .parse("content://media/external/audio/albumart")
        val uri = ContentUris.withAppendedId(sArtworkUri, album_id!!)
        Log.d("uri", uri.toString())
        var pfd = context.contentResolver
            .openFileDescriptor(uri, "r")
        if (pfd != null) {
            var fd: FileDescriptor? = pfd.fileDescriptor
            albumArtBitMap = BitmapFactory.decodeFileDescriptor(
                fd, null,
                options
            )
            pfd = null
            fd = null
        }
    } catch (ee: Error) {
        println("aka ee")
    } catch (e: Exception) {
        println("aka e")
    }
    return albumArtBitMap ?: return BitmapFactory.decodeResource(context.resources, R.drawable.note)
}

 */
