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
import androidx.compose.ui.tooling.preview.Preview
import com.ahmetsarac.tingirdak.models.MusicCardModel
import android.content.res.Resources

import java.io.FileDescriptor
import java.lang.Error
import java.lang.Exception


@Preview(showBackground = true)
@Composable
fun HomePage(){
    Scaffold(
        topBar = {
           AppBar(title = "Tingirdak") 
        }
    ){
        val context = LocalContext.current
        val list = context.musicList()
        LazyColumn{
           items(list){index ->
               MusicCard(uri = index.contentUri, artist = index.artist, name = index.songTitle, cover = index.cover, duration = index.duration)
           }
        }
    }
}

@SuppressLint("Recycle")
fun Context.musicList(): MutableList<MusicCardModel>{
    val list = mutableListOf<MusicCardModel>()
    val collection =
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        }else{
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

    val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
    val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
    val query = this.contentResolver.query(
        collection,
        projection,
        selection,
        null,
        sortOrder
    )

    query?.use{cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
        val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

        while(cursor.moveToNext()){
            val id = cursor.getLong(idColumn)
            val duration = cursor.getInt(durationColumn)
            val title = cursor.getString(titleColumn)
            val artist = cursor.getString(artistColumn)
            val albumId = cursor.getLong(albumIdColumn)
            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                id
            )
            val bitmap = getAlbumart(this, albumId)
            val durationString = convertMili(duration)
            list.add(MusicCardModel(contentUri, id, bitmap!!, title, artist, durationString))
        }

    }
    return list
}

fun convertMili(milliseconds: Int): String {
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / (1000 * 60) % 60)
    return String.format("%02d:%02d", minutes, seconds)
}

fun getAlbumart(context: Context, album_id: Long?): Bitmap? {
    var albumArtBitMap: Bitmap? = null
    val options = BitmapFactory.Options()
    try {
        val sArtworkUri = Uri
            .parse("content://media/external/audio/albumart")
        val uri = ContentUris.withAppendedId(sArtworkUri, album_id!!)
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
    return albumArtBitMap ?: getDefaultAlbumArtEfficiently(context.resources)
}


fun getDefaultAlbumArtEfficiently(resource: Resources?): Bitmap? {
    val bitmap = BitmapFactory.decodeResource(resource, com.ahmetsarac.tingirdak.R.drawable.note)
    return bitmap
}